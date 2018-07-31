package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: SensitiveWordsInterceptor
 * @Description: 敏感词拦截器
 * @author: DukeMr.Chen
 * @date: 2018/3/1 14:02
 * @version: V1.0
 *
 */
public class SensitiveWordsInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(SensitiveWordsInterceptor.class);

    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        log.info("RequestURI=[{}] params=[{}]", controller.getRequest().getRequestURI(), PhjfAdminBaseController.getReqParameterMap(controller.getRequest()));
        if("on".equalsIgnoreCase(PropKit.use("config.properties").get("sys.sensitiveWords.switch"))){
            //第一步先取到参数列表
            Boolean isSensitive = false;
            String msg = "";
            Enumeration<String> paramNames= controller.getParaNames();
            while (paramNames.hasMoreElements()) {
                String key = paramNames.nextElement();
                String val = controller.getPara(key);
                //第二步验证敏感词
                Pair<Boolean, Set<String>> pair = PhjfDfaKit.checkSensitiveWord(AdminConstants.ZH_SIMP, val);
                if(pair.getLeft()){
                    isSensitive = true;
                    msg += pair.getRight() + ",";
                }
            }
            //第三步处理响应
            if(isSensitive){
                msg = msg.substring(0,msg.length()-1);
                msg = "您填入的信息有敏感词，请修改。敏感词如下：" + msg.replaceAll("]","").replaceAll("\\[","");
                log.info(msg);
                /*String requestType = controller.getRequest().getHeader("X-Requested-With");
                if("XMLHttpRequest".equalsIgnoreCase(requestType)){
                    //controller.renderJson(returnMap);
                }else{*/
                    controller.setAttr("SensitiveMsg", msg);
                /*}*/
            }
        }
        invocation.invoke();
    }
}
