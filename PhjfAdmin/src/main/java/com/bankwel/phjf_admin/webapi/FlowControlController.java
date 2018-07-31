package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.service.FlowControlService;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.FlowControlEnum;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FlowControlController
 * @Description: 流量控制
 * @author: DukeMr.Chen
 * @date: 2018/4/19 17:27
 * @version: V1.0
 *
 */
public class FlowControlController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(FlowControlController.class);
    private FlowControlService flowControlService = Duang.duang(FlowControlService.class);

    /**
     * @Title:
     * @Description: 查看流量key
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/flow/indexMainPage")
    public void indexMain(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<FlowControlEnum> ruleList = FlowControlEnum.getClearList();
            List<SysDatalibrary> systemCodeList = SysDatalibrary.dao.querySysDatalibrary("Phjf","system_code");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("ruleList", ruleList, "systemCodeList", systemCodeList));
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.render("/WEB-INF/velocity/flowControl/main.vm");
    }

    /**
     * @Title:
     * @Description: 获取流量key
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/flow/queryKey")
    public void queryKey(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String flag = this.getPara("flag", "");
            String mobilephone = this.getPara("mobilephone");
            String rule = this.getPara("rule");
            String systemCode = this.getPara("systemCode");
            resultMap = flowControlService.getKey(flag, mobilephone, rule, systemCode);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * @Title:
     * @Description: key复位
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/flow/delKey")
    public void delKey() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String key = this.getPara("key");
            RedisClient.getClient().del(key);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }
}
