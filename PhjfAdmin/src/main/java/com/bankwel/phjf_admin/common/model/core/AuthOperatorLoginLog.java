package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheAuthOperatorLoginLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AuthOperatorLoginLog extends CacheAuthOperatorLoginLog<AuthOperatorLoginLog> {
    public static final AuthOperatorLoginLog dao = new AuthOperatorLoginLog().dao();


    /**
     * 通过名称和url获取资源
     * @param operator_name
     * @param loginDate
     * @return
     */
    public List<AuthOperatorLoginLog> getAuthOperatorLoginLog(String operator_name,String loginDate,String ipAddr,String loginStat){
        String sql = "select * from auth_operator_login_log where `status`= ? " ;
        List paramList = new ArrayList();
        paramList.add("1");
        if(!F.validate.isEmpty(operator_name)){
            sql += "and operator_name = ? ";
            paramList.add(operator_name);
        }
        if(!F.validate.isEmpty(loginDate)){
            sql += "and login_date = ? ";
            paramList.add(loginDate);
        }
        if(!F.validate.isEmpty(ipAddr)){
            sql += "and ip_addr = ? ";
            paramList.add(ipAddr);
        }
        if(!F.validate.isEmpty(loginStat)){
            sql += "and login_stat = ? ";
            paramList.add(loginStat);
        }
        sql +=" order by seq_id desc";
        return AuthOperatorLoginLog.dao.use("DBPublic").find(sql,paramList.toArray());
    }

    public Integer getLoginErrCount(String operator_name,String loginDate, boolean isContinuity){
        Integer errCount = 0;
        List<AuthOperatorLoginLog> list = null;
        if(isContinuity){
            list = getAuthOperatorLoginLog(operator_name,loginDate,null,null);
            if(null!=list&&0<list.size()){
                for(AuthOperatorLoginLog bean:list){
                    if("1".equals(bean.getLogin_stat())){
                        errCount = 0;
                    }else if("2".equals(bean.getLogin_stat())){
                        errCount++;
                    }
                }
            }
        }else{
            list = getAuthOperatorLoginLog(operator_name,loginDate,null,"2");
            errCount = list.size();
        }
        return errCount;
    }

    public AuthOperatorLoginLog logData(String operator_name,String ipAddr,String loginStatus) throws MsgBusinessException{
        AuthOperatorLoginLog data = new AuthOperatorLoginLog();
        data.setOperator_name(operator_name);
        data.setIp_addr(ipAddr);
        data.setLogin_stat(loginStatus);
        data.setLogin_date(F.dateKit.translate2Str(new Date(),"yyyyMMdd"));
        data.setSeq_uuid(UUID.randomUUID().toString());
        data.setStatus("1");
        data.checkModelUtem(data);
        try{

            data.save();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    public void checkModelUtem(AuthOperatorLoginLog data) {
        data.checkOperator_name("登录人用户名");
        data.checkIp_addr("登录IP");
        data.checkLogin_stat("登录状态");
        data.checkLogin_date("登录时间");
    }
    /**
     * 检验业务对象是否为空
     * @return
     */
    public boolean isEmpty(){
        if(F.validate.isEmpty(this.getSeq_uuid())){
            return true;
        }
        return false;
    }
}
