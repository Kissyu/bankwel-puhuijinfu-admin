package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.common.model.core.SmsCode;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.FlowControlEnum;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName: FlowControlKit
 * @Description: 流量控制 工具集
 * @author: DukeMr.Chen
 * @date: 2018/4/18 10:54
 * @version: V1.0
 *
 */
public final class FlowControlKit {
    private static final Logger log = LoggerFactory.getLogger(FlowControlKit.class);
    public final static Prop properties= PropKit.use("flowControl.properties");
    private static final String ORIGINAL_SWITCH_KEY = properties.get("sys.FlContr.key");
    private static final String CACHE_SWITCH_KEY = PropKit.use("config.properties").get("phjf.catch.sys.patch") + "." + ORIGINAL_SWITCH_KEY;

    /**
     * @Title:
     * @Description: 每个验证码的验证次数控制
     * @author: DukeMr.Chen
     */
    public static void ruleSmsValidateAttack(SmsCode smsCode){
        String key = F.strKit.f(FlowControlEnum.CK_PHJF_SYS_FC_SMSCODE_VALIDATE.getKey(), smsCode.getSeq_uuid());
        Long count = RedisClient.getClient().incr(key);
        String settingCount = properties.get("sys.FlContr_validateSmsCode.count");
        if(StringUtils.isNotBlank(settingCount) && !"0".equals(settingCount)){
            int scount = Integer.parseInt(settingCount);
            if(scount<count){
                smsCode.used();
                throw new MsgBusinessException("此验证已失效");
            }
        }
    }
    /**
     * @Title:
     * @Description: 防止短信攻击 规则1 每分钟最多2000条 sys.FlContr_smsCode.count=2000
     */
    public static void ruleSmsAttack(String keyTemplate,String errMsg){
        if(!isPass()) return;
        String minTimeKey = F.strKit.f(keyTemplate, (System.currentTimeMillis()/1000/60));
        Long count = RedisClient.getClient().incr(minTimeKey);
        String settingCount = properties.get("sys.FlContr_smsCode.count");
        if(StringUtils.isNotBlank(settingCount) && !"0".equals(settingCount)){
            int scount = Integer.parseInt(settingCount);
            if(scount<count){
                throw new MsgBusinessException(errMsg);
            }
        }
    }

    /** 
     * @Title: 
     * @Description: 如果一个ip登录多个账号密码错误次数超过15次 锁定该ip
     * @author: DukeMr.Chen
     */
    public static boolean ruleIpLoginAttack(String keyTemplate, String realIpAddr, String appCode){
        if(!isPass()) return true;
        if(cacheIsOpen()){
            String dateStr = F.dateKit.translate2Str(new Date(),"yyyyMMdd");
            String ilflKey = F.strKit.f(keyTemplate, dateStr, realIpAddr, appCode);
            String ipLoginSetCount = properties.get("sys.FlContr_ipLoginErr.count");
            String ipLoginErrCount = RedisClient.getClient().getIncrValue(ilflKey);
            if(StringUtils.isNotBlank(ipLoginSetCount) && !"0".equals(ipLoginSetCount)
                    && StringUtils.isNotBlank(ipLoginErrCount)
                    && F.validate.isZInt(ipLoginSetCount)
                    && F.validate.isZInt(ipLoginErrCount)){
                Integer ipLc = Integer.parseInt(ipLoginErrCount);
                Integer ipSc = Integer.parseInt(ipLoginSetCount);
                if(ipSc<=ipLc){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @Title:
     * @Description: 一个账号一天密码错误连续错误次数控制
     * @author: DukeMr.Chen
     */
    public static boolean ruleUserLoginErr(String keyTemplate, String mobilephone,String appCode){
        if(!isPass()) return true;
        if(cacheIsOpen()){
            String userLoginErrKey = F.strKit.f(keyTemplate, F.dateKit.translate2Str(new Date(),"yyyyMMdd"),mobilephone, appCode);
            String loginerrCount = RedisClient.getClient().getIncrValue(userLoginErrKey);
            String settingCount = properties.get("sys.FlContr_loginErr.count");
            if(StringUtils.isNotBlank(settingCount) && !"0".equals(settingCount)
                    && StringUtils.isNotBlank(loginerrCount)
                    && F.validate.isZInt(loginerrCount)
                    && F.validate.isZInt(settingCount)){
                Integer lc = Integer.parseInt(loginerrCount);
                Integer sc = Integer.parseInt(settingCount);
                if(sc<=lc){
                    return false;
                }
            }
        }else{

        }
        return true;
    }

    /**
     * @Title:
     * @Description: 未启用缓存时的登录验证
     * @author: DukeMr.Chen
     */
    public static boolean ruleUserLoginErr(String loginerrCount){
        if(!isPass()) return true;
        String settingCount = properties.get("sys.FlContr_loginErr.count");
        if(StringUtils.isNotBlank(settingCount) && !"0".equals(settingCount)
                && StringUtils.isNotBlank(loginerrCount)
                && F.validate.isZInt(loginerrCount)
                && F.validate.isZInt(settingCount)){
            Integer lc = Integer.parseInt(loginerrCount);
            Integer sc = Integer.parseInt(settingCount);
            if(sc<=lc){
                return false;
            }
        }
        return true;
    }

    /**
     * @Title:
     * @Description: 一个账号每天最多发的验证码
     * @author: DukeMr.Chen
     */
    public static void ruleSmsUserTimesLimit(String keyTemplate, String mobilephone,String errMsg, String appCode){
        if(!isPass()) return;
        String key = F.strKit.f(keyTemplate, F.dateKit.translate2Str(new Date(),"yyyyMMdd"), mobilephone, appCode);
        log.info(key);
        long rquestCount = RedisClient.getClient().incr(key);
        String value = properties.get("sys.FlContr_smsCode_mobilephone.count");
        if(StringUtils.isNotBlank(value) && !"0".equals(value)){
            int scount = Integer.parseInt(value);
            if(rquestCount > scount){
                throw new MsgBusinessException(errMsg);
            }
        }
    }

    /**
     * @Title:
     * @Description: 一个ip每天最多发送验证码次数
     * @author: DukeMr.Chen
     */
    public static void ruleSmsIpAttack(String keyTemplate, String realIpAddr,String errMsg, String appCode){
        if(!isPass()) return;
        String key = F.strKit.f(keyTemplate, F.dateKit.translate2Str(new Date(),"yyyyMMdd"), realIpAddr, appCode);
        log.info(key);
        long rquestCount = RedisClient.getClient().incr(key);
        String value = properties.get("sys.FlContr_smsCode_ip.count");
        if(StringUtils.isNotBlank(value) && !"0".equals(value)){
            int scount = Integer.parseInt(value);
            if(rquestCount > scount){
                throw new MsgBusinessException(errMsg);
            }
        }
    }

    /**
     * @Title:
     * @Description: 短信推荐每个账号一天最多发送推荐次数
     * @author: DukeMr.Chen
     */
    public static void ruleSmsRecommendLimit(String keyTemplate, String mobilephone,String errMsg){
        if(!isPass()) return;
        String key = F.strKit.f(keyTemplate, F.dateKit.translate2Str(new Date(),"yyyyMMdd"), mobilephone);
        log.info(key);
        long rquestCount = RedisClient.getClient().incr(key);
        String value = properties.get("sys.FlContr_smsRecommend.count");
        if(StringUtils.isNotBlank(value) && !"0".equals(value)){
            int scount = Integer.parseInt(value);
            if(rquestCount > scount){
                throw new MsgBusinessException(errMsg);
            }
        }
    }

    /**
     * @Title:
     * @Description: 一个接口每秒钟最多接受请求次数
     * @author: DukeMr.Chen
     */
    public static void apiFlContr(String uri ,Controller controller){
        if(!isPass()) return;
        String defaultSettingCountStr = properties.get("sys.FlContr.api.defaultCount");
        String uriKey = uri.replaceAll("/","_");
        Long time = System.currentTimeMillis()/1000;
        String key = F.strKit.f(FlowControlEnum.CK_PHJF_SYS_FC_APIFLCONTRKEY.getKey(),uriKey,time+"");
        //单独定制接口次数限制 下次开发
        //根据uri进行库表配置

        RedisClient.getClient().incr(key, FlowControlEnum.CK_PHJF_SYS_FC_APIFLCONTRKEY.getTime());
        String reqCountStr = RedisClient.getClient().getIncrValue(key);
        if(null!=defaultSettingCountStr&&!"0".equals(defaultSettingCountStr)&&null!=reqCountStr&& F.validate.isZInt(defaultSettingCountStr)&& F.validate.isZInt(reqCountStr)){
            Integer defaultSettingCount = Integer.parseInt(defaultSettingCountStr);
            Integer reqCount = Integer.parseInt(reqCountStr);
            if(defaultSettingCount<=reqCount){
                controller.setAttr("SensitiveMsg","系统繁忙，请稍后尝试。");
                throw  new MsgBusinessException("系统繁忙，请稍后尝试。");
            }
        }
        return;
    }

    /**
     * @Title:
     * @Description: 用户请求次数控制
     * @author: DukeMr.Chen
     */
    public static void userReqFlContr(String operate_name,Controller controller){
        if(!isPass()) return;
        String settingCount = properties.get("sys.FlContr.userReq.count");
        Long time = System.currentTimeMillis()/1000;
        String key = F.strKit.f(FlowControlEnum.CK_PHJF_SYS_FC_USERREQFLCONTRKEY.getKey(),operate_name,time);

        RedisClient.getClient().incr(key, FlowControlEnum.CK_PHJF_SYS_FC_USERREQFLCONTRKEY.getTime());
        String reqCountStr = RedisClient.getClient().getIncrValue(key);
        if(null!=settingCount&&!"0".equals(settingCount)&&null!=reqCountStr&& F.validate.isZInt(settingCount)&& F.validate.isZInt(reqCountStr)){
            Integer defaultSettingCount = Integer.parseInt(settingCount);
            Integer reqCount = Integer.parseInt(reqCountStr);
            if(defaultSettingCount<=reqCount){
                controller.setAttr("SensitiveMsg","当前用户请求过于频繁，请稍后尝试。");
                throw  new MsgBusinessException("当前用户请求过于频繁，请稍后尝试。");
            }
        }
        return;
    }

    /**
     * @Title:
     * @Description: ip请求控制
     * @author: DukeMr.Chen
     */
    public static void ipReqFlContr(String ip,Controller controller){
        if(!isPass()) return ;
        String settingCountStr = properties.get("sys.FlContr.ipReq.count");
        Long time = System.currentTimeMillis()/1000;
        String key = F.strKit.f(FlowControlEnum.CK_PHJF_SYS_FC_IPREQFLCONTRKEY.getKey(), ip, time);

        RedisClient.getClient().incr(key, FlowControlEnum.CK_PHJF_SYS_FC_IPREQFLCONTRKEY.getTime());
        String reqCountStr = RedisClient.getClient().getIncrValue(key);
        if(null!=settingCountStr&&!"0".equals(settingCountStr)&&null!=reqCountStr&& F.validate.isZInt(settingCountStr)&& F.validate.isZInt(reqCountStr)){
            Integer settingCount = Integer.parseInt(settingCountStr);
            Integer reqCount = Integer.parseInt(reqCountStr);
            if(settingCount<=reqCount){
                controller.setAttr("SensitiveMsg","当前ip请求过于频繁，请稍后尝试。");
                throw  new MsgBusinessException("当前ip请求过于频繁，请稍后尝试。");
            }
        }
        return;
    }

    private FlowControlKit(){

    }

    public static boolean cacheIsOpen(){
       return RedisClient.isTurnOn();
    }

    /**
     * @Title:
     * @Description: 是否做流量控制
     * @author: DukeMr.Chen
     */
    public static boolean isPass(){
        String on_off_status = "off";
        if(null != CACHE_SWITCH_KEY && !"".equals(CACHE_SWITCH_KEY)) {
            on_off_status = RedisClient.getClient().get(CACHE_SWITCH_KEY);
            if (null == on_off_status || "".equals(on_off_status)) {
                on_off_status = properties.get(ORIGINAL_SWITCH_KEY);
                if (!"on".equals(on_off_status)) {
                    //设置为关闭
                    on_off_status = "off";
                }
                RedisClient.getClient().set(CACHE_SWITCH_KEY, RedisClient.EXRP_DAY, on_off_status);
            }
        }
        if("on".equals(on_off_status)){
            return true;
        }
        return false;
    }
}
