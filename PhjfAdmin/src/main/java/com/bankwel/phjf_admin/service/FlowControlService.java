package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.webapi.FlowControlController;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.FlowControlEnum;
import com.jfinal.kit.PropKit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FlowControlService
 * @Description: 流量业务
 * @author: DukeMr.Chen
 * @date: 2018/4/19 17:27
 * @version: V1.0
 *
 */
public class FlowControlService {
    private static final Logger log = LoggerFactory.getLogger(FlowControlService.class);

    /**
     * @Title:
     * @Description: 获取流量key
     * @author: DukeMr.Chen
     */
    public Map getKey(String flag, String mobilephone, String rule, String systemCode) {
        if(!"1".equals(flag)) return F.transKit.asMap("rows", null, "total", 0);
        if(F.validate.isEmpty(mobilephone)){
            throw new MsgBusinessException("手机号不能为空");
        }else if(F.validate.isEmpty(rule)){
            throw new MsgBusinessException("规则不能为空");
        }else if(F.validate.isEmpty(systemCode)){
            throw new MsgBusinessException("系统编号不能为空");
        }

        String key = "";
        if(FlowControlEnum.CK_PHJF_SYS_FC_SMSRECOMMEND.getKey().equals(rule)){
            key = F.strKit.f(rule, F.dateKit.translate2Str(new Date(),"yyyyMMdd"), mobilephone);
        }else{
            key = F.strKit.f(rule, F.dateKit.translate2Str(new Date(),"yyyyMMdd"), mobilephone, systemCode);
        }
        log.info(key);
        String value = RedisClient.getClient().getIncrValue(key);
        String status = "正常";
        if(StringUtils.isNotBlank(value)){
            String valve = "0";
            if(FlowControlEnum.CK_PHJF_SYS_FC_SMSRECOMMEND.getKey().equals(rule)){
                valve = PropKit.use("flowControl.properties").get("sys.FlContr_smsRecommend.count");
            }else if(FlowControlEnum.CK_Phjf_SYS_FC_SMSCODE_MOBILEPHONE.getKey().equals(rule)){
                valve = PropKit.use("flowControl.properties").get("sys.FlContr_smsCode_mobilephone.count");
            }else if(FlowControlEnum.CK_PHJF_SYS_FC_USERLOGINERRKEY.getKey().equals(rule)){
                valve = PropKit.use("flowControl.properties").get("sys.FlContr_loginErr.count");
            }
            status = Integer.parseInt(value) < Integer.parseInt(valve) ? "正常" : "已锁定";
        }
        List<Map> sensitivewordsList = new ArrayList<Map>();
        sensitivewordsList.add(F.transKit.asMap(
                "key", key
                , "value", value
                ,"status", status
        ));
        return F.transKit.asMap("rows", sensitivewordsList, "total", 1);
    }

}
