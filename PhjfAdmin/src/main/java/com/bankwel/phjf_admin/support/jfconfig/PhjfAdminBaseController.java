package com.bankwel.phjf_admin.support.jfconfig;

import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.support.jfinal.BaseController;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.component.c11assistant.ServletHelper;
import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;
import com.jfinal.kit.JsonKit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * Created by scottsln on 2016/11/11.
 */
public class PhjfAdminBaseController extends BaseController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PhjfAdminBaseController.class);
    /**
     * 获取URL请求中的用户信息（根据Session）
     * @return
     */
    public AuthOperator getUser(){
        return getSessionUser(this);
    }
    public static AuthOperator getSessionUser(Controller controller){
        AuthOperator operator = ServletHelper.getSessionUser(controller.getRequest());
        return operator;
    }

    /**
     * @Title:
     * @Description: 检验敏感词
     * @author: DukeMr.Chen
     */
    public void checkSensitiveMsg(){
        String msg = this.getAttr("SensitiveMsg");
        if(StringUtils.isNotBlank(msg)){
            throw new MsgBusinessException(msg);
        }
    }

    public void renderJson(Object object) {
        String jsonStr = JFinalJson.getJson().toJson(object);
        jsonStr = jsonStr.replaceAll("&","&amp;");
        jsonStr = jsonStr.replaceAll("<","&lt;");
        jsonStr = jsonStr.replaceAll(">","&gt;");
        log.info("renderJson=[" + jsonStr + "]");
        super.renderJson(jsonStr);
    }

}
