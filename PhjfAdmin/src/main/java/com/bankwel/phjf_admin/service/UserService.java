package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/11/9.
 */
public class UserService {

    /**
     * 会员列表 - 获取会员列表
     * @param user_name
     * @param id_num
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryUserInfoByPage( String user_name, String id_num, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = UserInfo.dao.queryUserInfoByPage(user_name,id_num,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 会员列表 - 通过ID查找会员
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findUserInfoById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("会员编号不能为空");
        }
        UserInfo model = UserInfo.dao.findById(seq_uuid);
        Map data = outputUserInfo(model);
        return data;
    }

    /**
     * 会员列表 - 输出会员列表信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputUserInfo(UserInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "user_name",model.getUser_name()
                ,"head_img",model.getHead_img()
                ,"mobilephone",model.getMobilephone()
                ,"true_name",model.getTrue_name()
                ,"head_img",model.getHead_img()
                ,"id_type",model.getId_type()
                ,"id_num",model.getId_num()
                ,"nickname",model.getNickname()
                ,"gender",model.getGender()
                ,"address",model.getAddress()
                ,"reg_time",model.getReg_time()
                ,"frozen_time",model.getFrozen_time()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 会员列表 - 冻结
     * @param opt
     * @param app_code
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void freezeUserInfo(AuthOperator opt,String app_code, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("会员编号不能为空");
        }
        UserInfo userInfo = UserInfo.dao.findById(seq_uuid);
        UserAppauth userAppauth = UserAppauth.dao.findById(userInfo.getUser_id()+"",app_code);
        if(F.validate.isNotEmpty(userAppauth.getUad_id())) {
            userAppauth.setStatus("2");
            userAppauth.saveOrUpdate(opt);
        }
    }

    /**
     * 会员列表 - 解冻
     * @param opt
     * @param app_code
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void unfreezeUserInfo(AuthOperator opt,String app_code, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("会员编号不能为空");
        }
        UserInfo userInfo = UserInfo.dao.findById(seq_uuid);
        UserAppauth userAppauth = UserAppauth.dao.findById(userInfo.getUser_id()+"",app_code);
        if(F.validate.isNotEmpty(userAppauth.getUad_id())) {
            userAppauth.setStatus("1");
            userAppauth.saveOrUpdate(opt);
        }
    }
    /**
     * 用户预约开户查询
     * @param bank_name
     * @param mp_name
     * @param true_name
     * @param mobile
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryUserBankCardAmByPage(String bank_name, String mp_name, String true_name, String mobile, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = UserBankcardAppointment.dao.queryUserBankCardAmByPage(bank_name, mp_name, true_name, mobile, status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 预约取款 - 获取预约取款列表
     * @param mp_name
     * @param name
     * @param user_name
     * @param mobilephone
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryUserWithdrawAmByPage(String mp_name,String name, String user_name, String mobilephone, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = UserWithdrawAppointment.dao.queryUserWithdrawAmByPage(mp_name,name,user_name,mobilephone,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 预约取款 - 通过ID查找预约取款
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findUserWithdrawAmById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("预约取款编号不能为空");
        }
        UserWithdrawAppointment model = UserWithdrawAppointment.dao.findById(seq_uuid);
        Map data = outputManagePointInfo(model);
        return data;
    }
    /*
    * 预约取款 - 输出预约取款信息
    * */
    public Map outputManagePointInfo(UserWithdrawAppointment model) throws MsgBusinessException {
        UserInfo user = model.findUser(model.getUser_id()+"");
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"mp_code",model.getMp_code()
                ,"amount",model.getAmount()
                ,"mp_name",model.findManagepoint().getName()
                ,"am_date",model.getAm_date()
                ,"accept_time",model.getAccept_time()
                ,"refuse_time",model.getRefuse_time()
                ,"cancel_time",model.getCancel_time()
                ,"remark",model.getRemark()
                ,"name",user.getTrue_name()
                ,"nickname",user.getNickname()
                ,"user_name",user.getUser_name()
                ,"mobilephone",user.getMobilephone()
                ,"mp_opt",model.findUser(model.getMp_opt()+"").getUser_name()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 贷款申请管理 - 查询
     * @param mobilephone
     * @param name
     * @param loan_use_type
     * @param page
     * @return
     */
    public Map queryUserLoanByPage(String mobilephone, String name, String loan_use_type, PageKit page){
        Pair<List<Record>, PageKit<Record>> pair = UserLoanApply.dao.queryUserLoanByPage(mobilephone, name, loan_use_type, page);
        List<Record> list = pair.getLeft();
        for (int i = 0; i < list.size(); i++){
            Record record = list.get(i);
            String mobilephoneStr = record.getStr("mobilephone");
            if (F.validate.isNotEmpty(mobilephoneStr)){
                Pattern p = Pattern.compile("^(\\d{4})(\\d*)(\\d{4})$");
                mobilephoneStr = p.matcher(mobilephoneStr).replaceAll("$1****$3");
            }
            record.set("mobilephone", mobilephoneStr);
        }
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }
    /**
     * 贷款发送银行 - 查询单条用户贷款信息
     * @param seq_uuid
     * @return
     */
    public Map getUserLoanInfo(String seq_uuid){
        UserLoanApply ula = UserLoanApply.dao.findBySeqUuid(seq_uuid);
        //用户信息
        UserInfo userInfo = UserInfo.dao.findByUserId(""+ula.getUser_id());
        //手机号
        Pattern p = Pattern.compile("^(\\d{4})(\\d*)(\\d{4})$");
        String mobilephoneStr = p.matcher(userInfo.getMobilephone()).replaceAll("$1****$3");
        //贷款用途
        String loanUse = SysDatalibrary.dao.queryDatalibrary("Phjf","loan_useType",ula.getLoan_use_type()).getName();
        //贷款金额
        String loanAmount = SysDatalibrary.dao.queryDatalibrary("Phjf","loan_amountSection",ula.getLoan_amount()).getName();
        //贷款期限
        String loanTerm = SysDatalibrary.dao.queryDatalibrary("Phjf","loan_termSection",ula.getLoan_use_term()).getName();
        //贷款状态
        String loanStatus = SysDatalibrary.dao.queryDatalibrary("Phjf","loan_user_applyStatus",ula.getStatus()).getName();
        Map result = F.transKit.asMap();
        result.put("userInfo",userInfo);
        result.put("userLoanInfo",ula);
        result.put("loanUse",loanUse);
        result.put("loanStatus",loanStatus);
        result.put("mobilephone",mobilephoneStr);
        result.put("loanApplyTime",F.dateKit.translate2Str(ula.getCreate_time(),F.dateKit.PATTERN_Min));
        String platform_remark = "";
        platform_remark = UserLoanBankProcess.dao.findselfRemark(""+ula.getUlp_id()).getPlatform_remark();
        result.put("platform_remark",platform_remark);
        return result;
    }

    /**
     * 贷款申请管理 - 查询
     * @param user_name
     * @param name
     * @param page
     * @return
     */
    public Map queryUserFeedbackByPage(String user_name, String name, PageKit page){
        Pair<List<Record>, PageKit<Record>> pair = UserFeedback.dao.queryUserFeedbackByPage(user_name, name, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 会员银行卡管理 - 查询
     * @param mobilephone
     * @param name
     * @param page
     * @return
     */
    public Map queryUserBankcardByPage(String bt_name, String mobilephone, String name, PageKit page){
        Pair<List<Record>, PageKit<Record>> pair = UserBankcard.dao.queryUserBankcardByPage(bt_name, mobilephone, name, page);
        List<Record> list = pair.getLeft();
        for (int i = 0; i < list.size(); i++){
            Record record = list.get(i);
            String mobilephoneStr = record.getStr("mobilephone");
            if (F.validate.isNotEmpty(mobilephoneStr)){
                Pattern p = Pattern.compile("^(\\d{4})(\\d*)(\\d{4})$");
                mobilephoneStr = p.matcher(mobilephoneStr).replaceAll("$1****$3");
            }
            record.set("mobilephone", mobilephoneStr);
            String mobile = record.getStr("mobile");
            if (F.validate.isNotEmpty(mobile)){
                Pattern p = Pattern.compile("^(\\d{4})(\\d*)(\\d{4})$");
                mobile = p.matcher(mobile).replaceAll("$1****$3");
            }
            record.set("mobile", mobile);
            String bankcard_no = record.getStr("bankcard_no");
            if (F.validate.isNotEmpty(bankcard_no)){
                Pattern p = Pattern.compile("^(\\d*)(\\d{4})$");
                String starStr = F.strKit.generateStarStr(bankcard_no.length() - 4);
                bankcard_no = p.matcher(bankcard_no).replaceAll(starStr +"$2");
            }
            record.set("bankcard_no", bankcard_no);
        }

        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 用户贷款申请银行受理信息 - 获取用户贷款申请银行受理信息列表
     * @param user_name
     * @param bt_code
     * @param loan_use_type
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryULBankProcessByPage(String user_name, String bt_code,String loan_use_type, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = UserLoanBankProcess.dao.queryULBankProcessByPage(user_name,bt_code,loan_use_type,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 用户贷款申请银行受理信息 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryMpSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysLoanUseTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","loan_useType");
        List<Map> loanUseTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLoanUseTypeList){
            loanUseTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","loan_bank_processStatus");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList,"loanUseTypeList",loanUseTypeList);
    }

    public List<Map> queryBankByUserLoanId(String seq_uuid,String langugage) throws MsgBusinessException {
        List<Map> list = new ArrayList<Map>();
        //先获取所有银行
        List<BankInfo> bankInfos = BankInfo.dao.queryAllBank(langugage);
        UserLoanApply ulp = UserLoanApply.dao.findBySeqUuid(seq_uuid);
        List<UserLoanBankProcess> ulbs = UserLoanBankProcess.dao.queryUserloanBankById(""+ulp.getUlp_id());
       // List<AuthRoleResource> authRoleResources = AuthRoleResource.dao.getByRoleId(role_id);
        //List<AuthResource> authResources = AuthResource.dao.queryAllAllotResource(user.getSeq_id()+"");
        for(BankInfo bank:bankInfos){
            boolean checked = false;
            boolean chkDisabled = false;
            //如果银行是总行 不能选择
            if(bank.getParent_bank_code().equals("0")) {
                chkDisabled = true;
            }else{
                if (ulbs.size()>0) {
                    for(UserLoanBankProcess ulb:ulbs){
                        if(ulb.getBank_code().equals(bank.getBank_code())) {
                            //如果被某家银行拒绝过,就是灰色 不能再选
                            if(ulb.getStatus().equals("9")) {
                                chkDisabled = false;
                                break;
                            }else {
                                checked = true;
                                chkDisabled = true;
                                break;
                            }
                        }
                    }
                }
            }
            list.add(F.transKit.asMap("bank_code",bank.getBank_code()
                    ,"parent_bank_code",bank.getParent_bank_code()
                    ,"name",bank.getName()
                    ,"checked",checked
                    ,"chkDisabled",chkDisabled));

        }
        return list;
    }

    @Before(TTx.class)
    public void saveLoanSendToBanks(String user_id, String seq_uuid,String[] bankArray,String remark) throws MsgBusinessException {
        if(F.validate.isEmpty(user_id)){
            throw new MsgBusinessException("用户id不能为空");
        }
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("贷款id不能为空");
        }
        if(F.validate.isEmpty(bankArray)){
            throw new MsgBusinessException("银行不能为空");
        }
        UserInfo userInfo = UserInfo.dao.findByUserId(user_id);
        UserLoanApply userLoanApply = UserLoanApply.dao.findBySeqUuid(seq_uuid);
        for (String bank : bankArray){
            BankInfo bi = BankInfo.dao.findByCode(bank,"ZH_SIMP");
            UserLoanBankProcess.dao.saveLoanSendToBanks(userInfo,userLoanApply,bi,remark);
        }
        userLoanApply.setStatus("2");
    }

    /**
     * 账户管理交易记录 - 获取交易记录列表
     * @param bt_name
     * @param user_name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankcardTradelogByPage( String bt_name, String user_name, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankBankcardTradelog.dao.queryBankcardTradelogByPage(bt_name,user_name,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 账户管理交易记录 - 通过ID查找交易记录
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBankcardTradelogById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("会员编号不能为空");
        }
        UserInfo model = UserInfo.dao.findById(seq_uuid);
        Map data = outputUserInfo(model);
        return data;
    }

    /**
     * 账户管理交易记录 - 输出交易记录列表信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankcardTradelog(UserInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "user_name",model.getUser_name()
                ,"head_img",model.getHead_img()
                ,"mobilephone",model.getMobilephone()
                ,"true_name",model.getTrue_name()
                ,"head_img",model.getHead_img()
                ,"id_type",model.getId_type()
                ,"id_num",model.getId_num()
                ,"nickname",model.getNickname()
                ,"gender",model.getGender()
                ,"address",model.getAddress()
                ,"reg_time",model.getReg_time()
                ,"frozen_time",model.getFrozen_time()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

}
