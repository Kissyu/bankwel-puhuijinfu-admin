package com.bankwel.phjf_website.client;

import com.bankwel.phjf_website.common.utils.ConstStr;
import com.bankwel.framework.core.F;

/**
 * Created by scottsln on 2016/9/28.
 */
public class PhjfSmsPushClient {

    /**
     * 发送短信验证码
     * @param mobile
     * @param code
     */
    public static void sendSmsCode(String mobile,String code){
       /* ConstStr.PhjfSmsClient.send(mobile,ConstStr.Sms_Ali_TCode_SMS_475005,F.transKit.asMap("code",code));*/
    }

}
