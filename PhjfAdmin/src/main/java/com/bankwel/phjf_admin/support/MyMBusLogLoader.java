package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.framework.support.s10monitor.mlog.MBusLogLoader;
import com.bankwel.framework.support.s10monitor.mlog.MBuslog;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.service.SystemService;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName: MyMBusLogLoader
 * @Description: 日志对象装载器
 * @author: DukeMr.Chen
 * @date: 2018/6/5 13:45
 * @version: V1.0
 *
 */
public class MyMBusLogLoader extends MBusLogLoader {
    private SystemService sysService = Duang.duang(SystemService.class);
    private final static String[] arrStr = {".css", ".CSS", ".jpg", ".JPG", ".jpeg", ".JPEG"
            , ".png", ".PNG", ".js", ".JS", ".html", ".HTML"
            , ".gif", ".GIF", ".ico", ".ICO", ".swf", ".SWF"
            , ".woff", ".WOFF", ".map", ".MAP"};

    public MBuslog load(ServletRequest request) {
        MBuslog blog = new MBuslog();
        blog.setSysCode(AdminConstants.PLATFORM);
        blog.setSubSysCode(PropKit.use("monitor.properties").get("monitor.phjf.system"));
        blog.setBusId("");
        String url = ((HttpServletRequest)request).getRequestURL().toString();
        try{
            boolean flag = true;
            for(String str : arrStr){
                if(-1 < url.indexOf(str)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                String actionKey = sysService.getActionKey(url);
                if("/".equals(actionKey) || "".equals(actionKey)){
                    blog.setIsComplete("1");
                }else{
                    Map<String, String> busMap = sysService.getBusMap(actionKey);
                    if(F.validate.isNotEmpty(busMap)){
                        blog.setBusPath(busMap.get("BusPath"));
                        blog.setBusMethod(busMap.get("BusMethod"));
                        blog.setBusTarget(busMap.get("BusTarget"));
                        blog.setBusContent(busMap.get("BusContent"));
                        blog.setIsComplete("1");
                    }else{
                        blog.setIsComplete("0");
                    }
                }
            }else{
                blog.setIsComplete("1");
            }
        }catch (Exception e){
            blog.setIsComplete("0");
            e.printStackTrace();
        }
        // 从session中获取当前登录用户
        AuthOperator authOperator = (AuthOperator) ((HttpServletRequest)request).getSession().getAttribute(PropKit.use("monitor.properties").get("user.session"));
        if(authOperator != null){
            blog.setUserId(authOperator.getOperate_name()+ "");
        }else {
            blog.setUserId("");
        }
        return blog;
    }
}
