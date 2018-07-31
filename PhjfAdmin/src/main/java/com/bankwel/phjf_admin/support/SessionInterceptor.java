package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.auth.service.AuthResService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthResource;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import javax.servlet.http.Cookie;

/**
 * Created by admin on 2017/9/26.
 */
public class SessionInterceptor implements Interceptor {


    private AuthResService authResService = Duang.duang(AuthResService.class);


    public void intercept(Invocation invocation) {
        Controller ctl = invocation.getController();

        //判断用户是否登录
        AuthOperator operator = (AuthOperator) ctl.getSession().getAttribute("operator");
        String action = invocation.getActionKey();
        if(operator != null || action.indexOf("index")>=0 || action.indexOf("checkLogin")>=0 || "/".equals(action) ||action.indexOf("/phjfht/api/v1/system/sendSmsCode")>=0){
            if (action.indexOf("index")>=0 || action.indexOf("checkLogin")>=0 || action.indexOf("login")>=0 || action.indexOf("logout")>=0 || action.indexOf("queryResourceList")>=0 || "/".equals(action) ||action.indexOf("/phjfht/api/v1/system/sendSmsCode")>=0){
                invocation.invoke();
            } else {
                boolean bool = authResService.isHandleAuth(operator, action);
                if (!bool){
                    String requestType = ctl.getRequest().getHeader("X-Requested-With");
                    if ("XMLHttpRequest".equals(requestType)){//ajax请求
                        ctl.renderJson(F.transKit.asMap("code", "4", "redirect", "/"));
                    } else {
                        ctl.redirect(PropKit.use("config.properties").get("sys.loginURL"));
                    }
                } else {
                    invocation.invoke();
                }
            }
//            invocation.invoke();
        }else{
            ctl.getSession(false).invalidate();
            Cookie cookie = new Cookie("JSESSIONID","");
            ctl.getResponse().addCookie(cookie);
            ctl.getRequest().getSession().removeAttribute("operator");
            ctl.getRequest().getSession().removeAttribute("DukeToken");
            //ctl.getResponse().
            ctl.redirect(PropKit.use("config.properties").get("sys.loginURL"));
        }
    }
}
