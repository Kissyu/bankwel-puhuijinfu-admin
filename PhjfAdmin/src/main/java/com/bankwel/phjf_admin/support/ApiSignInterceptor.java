package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.Const;
import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * wx鉴权拦截器
 * Created by scottsln on 2016/9/5.
 */
public class ApiSignInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiSignInterceptor.class);

    public void intercept(Invocation inv){
        Controller controller = inv.getController();
        log.info("RequestURI=[{}] params=[{}]", controller.getRequest().getRequestURI(), PhjfAdminBaseController.getReqParameterMap(controller.getRequest()));

        //AK校验
        String ak = controller.getPara("ak","");
        String timestamp = controller.getPara("timestamp","");
        if(!AkSign.sign.checkSign(timestamp,ak)){
            Map map = F.transKit.asMap("code", 4, "msg", "Sign Error", "data","") ;
            controller.renderJson(map);
            return;
        }

        //全参校验
        Map<String, String> para = getReqParameterMap(controller.getRequest());
        String sign = controller.getPara("sign","");
        if(!F.signKit.checkSign(para,sign, PropKit.use("config.properties").get("phjfAdmin.sign.Sign_AES_KEY"))){
            Map map = F.transKit.asMap("code", 4, "msg", "Sign Error", "data","") ;
            controller.renderJson(map);
            return;
        }
        inv.invoke();
    }

    public Map<String, String> getReqParameterMap(HttpServletRequest req){
        Map<String, String[]> para = req.getParameterMap();
        Map<String, String> p = new HashMap();
        for (Map.Entry<String, String[]> entry : para.entrySet()){
            String[] p_v = entry.getValue();
            if (p_v != null){
                p.put(entry.getKey(), p_v[0]);
            }
        }
        return p;
    }
}
