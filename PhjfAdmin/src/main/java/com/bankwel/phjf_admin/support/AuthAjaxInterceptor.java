package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * wx鉴权拦截器
 * Created by scottsln on 2016/9/5.
 */
public class AuthAjaxInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthAjaxInterceptor.class);

    public void intercept(Invocation inv){
        Controller controller = inv.getController();
        log.info("RequestURI=[{}] params=[{}]", controller.getRequest().getRequestURI(), PhjfAdminBaseController.getReqParameterMap(controller.getRequest()));
        AuthOperator user = PhjfAdminBaseController.getSessionUser(controller);
        if(F.validate.isEmpty(user)){//如果没有绑定，则跳转登录页
            Map map = F.transKit.asMap("code", 4, "msg", "Auth Error", "data","") ;
            controller.renderJson(map);
            return;
        }
        inv.invoke();
    }
}
