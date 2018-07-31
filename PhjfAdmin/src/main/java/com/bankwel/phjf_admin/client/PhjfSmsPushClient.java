package com.bankwel.phjf_admin.client;


import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.SysParam;

/**
 * Created by scottsln on 2016/9/28.
 */
public class PhjfSmsPushClient {

    /**
     * 发送短信验证码
     * @param mobile
     * @param code
     */
    public static void sendSmsCode(String mobile, String code){
        SysParam sysParam = SysParam.dao.findByCode("sms_channel");
//        SysParam sysParam = new SysParam();
//        sysParam.setParam_value("HL");
        if ("HL".equals(sysParam.getParam_value())){//鸿联95
            String template = "您的动态验证码为：%s。该码10分钟内有效，感谢您的使用！";
            String str = AdminConstants.Hl95SmsClient.sendSms(mobile, F.strKit.f(template, code));

            System.out.println(str);
        } else if ("AL".equals(sysParam.getParam_value())){//阿里
            AdminConstants.AliSmsClient.sendSms(mobile, AdminConstants.Sms_Ali_TCode_SMS_1143380179, F.transKit.asMap("code", code));
        } else {//默认用鸿联95
            String template = "您的动态验证码为：%s。该码10分钟内有效，感谢您的使用！";
            AdminConstants.Hl95SmsClient.sendSms(mobile, F.strKit.f(template, code));
        }
    }


    public static void main(String[] args){
        PhjfSmsPushClient.sendSmsCode("", "123123");
//        PhjfSmsPushClient.sendCardCode("", "yh", "123123");

    }

}
