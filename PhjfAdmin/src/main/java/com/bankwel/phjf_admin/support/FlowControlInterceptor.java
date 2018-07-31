package com.bankwel.phjf_admin.support;

import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.component.c11assistant.ServletHelper;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: FlowControlInterceptor
 * @Description: 接口请求流量控
 * @author: LiuFengtong、DukeMr.Chen
 * @date: 2018/6/1
 * @version: V2.0
 *
 */
public class FlowControlInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(FlowControlInterceptor.class);
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        HttpServletRequest request = controller.getRequest();
        AuthOperator operator = ServletHelper.getSessionUser(request);
        String ip = RealIpAddrKit.getRealIpAddr(request);
        String uri = request.getRequestURI();
        try {
            FlowControlKit.apiFlContr(uri,controller);//接口请求流量限制
            if(null!=operator){
                FlowControlKit.userReqFlContr(operator.getOperate_name(),controller);//用户请求次数控制
            }else{
                FlowControlKit.userReqFlContr(request.getSession().getId(),controller);//用户请求次数控制
            }
            FlowControlKit.ipReqFlContr(ip,controller);//ip请求次数控制
        }catch (Exception e){
            e.printStackTrace();
        }
        invocation.invoke();
    }


}
