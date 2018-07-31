package com.bankwel.phjf_admin.service;

import com.bankwel.phjf_admin.common.model.core.AuthOperatorLoginLog;
import com.bankwel.phjf_admin.common.model.core.AuthResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by admin on 2017/9/20.
 */
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
//    public static List<AuthResource> findTopTabList(String center_seq_id, String operator_seq_id){
//        return AuthResource.findTopTabList(center_seq_id, operator_seq_id);
//    }

    public String getLoginErrCount(String operator_name,String loginDate, boolean isContinuity){
       Integer errLoginCount = AuthOperatorLoginLog.dao.getLoginErrCount(operator_name,loginDate,isContinuity);

       if(null==errLoginCount){
           return "0";
       }
       return errLoginCount+"";
    }

    public Integer getLoginSuccessCountByIpAddr(String operator_name,String ipAddr){
        List<AuthOperatorLoginLog> list = AuthOperatorLoginLog.dao.getAuthOperatorLoginLog(operator_name,null,ipAddr,"1");
        if(null ==list){
            return 0;
        }else{
            return list.size();
        }
    }
    public AuthOperatorLoginLog logData(String operator_name,String ipAddr,String loginStatus){
        return AuthOperatorLoginLog.dao.logData(operator_name,ipAddr,loginStatus);
    }
}
