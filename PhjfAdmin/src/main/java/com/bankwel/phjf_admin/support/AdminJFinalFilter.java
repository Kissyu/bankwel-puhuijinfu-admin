package com.bankwel.phjf_admin.support;

import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.jfinal.core.JFinalFilter;
import com.jfinal.kit.PropKit;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminJFinalFilter extends JFinalFilter {

    private static String[] arrStr = {".css",".jpg",".jpeg",".png",".jsp",".html",".JPG",".JPEG",".PNG",".CSS",".JSP",".HTML",".GIF",".gif",".ico",".ICO"};
    private static String[] loginStr = {"jquery/jquery.min.js","easyui/jquery.easyui.min.js","easyui/easyui-lang-zh_CN.js","common/util.js","jquery/jquery.md5.js","jquery/jquery.cookie.js","bootstrap/bootstrap.min.js","js/login.js"};
    private static String[] tokenFilter = {".js",".map",".woff"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        //Controller ctl = invocation.getController();
        //判断用户是否登录
        AuthOperator operator = (AuthOperator) request.getSession().getAttribute("operator");
        String action = request.getRequestURI();
        boolean flag = false;
        for(String str:arrStr){
            if(-1<action.indexOf(str)){
                flag = true;
                break;
            }
        }

        if(!flag){
            for(String str:loginStr){
                if(-1<action.indexOf(str)){
                    flag = true;
                    break;
                }
            }
        }

        /* 设置Cookie HttpOnly*/
        //Cookie off = request.getCookies()[0];
        Cookie off = new Cookie("", "");
        off.setHttpOnly(true);
        response.addCookie(off);

        if(flag || action.indexOf("index")>=0 || action.indexOf("checkLogin")>=0 || action.indexOf("logout")>=0 || "/".equals(action) ||action.indexOf("/phjfht/api/v1/system/sendSmsCode")>=0){
            super.doFilter(req,res,chain);
        }else if(operator != null){
            // 验证Referer
            if("on".equalsIgnoreCase(PropKit.use("config.properties").get("sys.Referer.switch"))){
                String referer = request.getHeader("Referer");
                if( StringUtils.isBlank(referer) || !(referer.trim().startsWith(PropKit.use("config.properties").get("sys.Referer.website")))){
                    return;
                }
            }

            boolean  isValidateToken = true;
            for(String str:tokenFilter){
                if(-1<action.indexOf(str)){
                    isValidateToken = false;
                    break;
                }
            }
            //验证Token
            if(isValidateToken){
                String token = (String)request.getSession().getAttribute("DukeToken");
                if(StringUtils.isBlank(token)){
                    jumpLoginPage(req, res);
                }else {
                    String userToken = AuthOperatorService.getToken(operator.getSeq_id());
                    if(StringUtils.isNotBlank(userToken) && !token.equalsIgnoreCase(userToken)){
                        request.getSession(false).invalidate();
                        Cookie cookie = new Cookie("JSESSIONID","");
                        response.addCookie(cookie);
                        request.getSession().removeAttribute("operator");
                        request.getSession().removeAttribute("DukeToken");
                        jumpLoginPage(req, res);
                    }else{
                        String xhrToken = request.getHeader("DukeToken");
                        // 从请求参数中取得 csrftoken
                        String pToken = request.getParameter("DukeToken");
                        if(xhrToken !=null && token.equals(xhrToken)){
                            super.doFilter(req,res,chain);
                        }else if(pToken != null && token.equals(pToken)){
                            super.doFilter(req,res,chain);
                        }else{
                            jumpLoginPage(req, res);
                        }
                    }
                }
            }else {
                super.doFilter(req,res,chain);
            }
        }else{
            jumpLoginPage(req, res);
        }

    }

    private void jumpLoginPage(ServletRequest req, ServletResponse res){
        try {
            ((HttpServletResponse) res).sendRedirect(PropKit.use("config.properties").get("sys.loginURL"));
            //req.getRequestDispatcher("/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
