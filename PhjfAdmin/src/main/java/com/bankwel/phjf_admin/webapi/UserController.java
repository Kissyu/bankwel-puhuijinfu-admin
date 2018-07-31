package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.bankwel.phjf_admin.service.DatalibraryService;
import com.bankwel.phjf_admin.service.UserService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/25.
 */
public class UserController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private DatalibraryService datalibraryService = Duang.duang(DatalibraryService.class);
    private UserService userService = Duang.duang(UserService.class);
    private AuthOperatorService operatorService = Duang.duang(AuthOperatorService.class);

    /**
     * 会员列表 - 跳转会员列表查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goUserQueryPage
    @ActionKey("/phjfht/api/v1/user/goUserQueryPage")
    public void goUserQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> statusList = datalibraryService.queryByParentCode("phjf","sys_status");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("statusList",statusList));
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
        this.render("/WEB-INF/velocity/user/userInfo/userInfoQuery.vm");
    }

    /**
     * 会员列表 - 会员列表查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryUserInfoByPage
    @ActionKey("/phjfht/api/v1/user/queryUserInfoByPage")
    public void queryUserInfoByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String user_name = this.getPara("user_name");
            String id_num = this.getPara("id_num");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryUserInfoByPage(user_name,id_num,status, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * 会员列表 - 跳转会员列表查看页
     */
    //http://localhost:8081/phjfht/api/v1/user/goViewUserInfoPage
    @ActionKey("/phjfht/api/v1/user/goViewUserInfoPage")
    public void goViewUserInfoPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map<String,String> data = userService.findUserInfoById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/user/userInfo/userInfoDetail.jsp");
    }

    /**
     * 会员列表 - 冻结
     */
    //http://localhost:8081/phjfht/api/v1/user/freezeUserInfo
    @ActionKey("/phjfht/api/v1/user/freezeUserInfo")
    public void freezeUserInfo() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String app_code = this.getPara("app_code","");
            AuthOperator user = this.getUser();
            userService.freezeUserInfo(user,app_code,seq_uuid);
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

    /**
     * 会员列表 - 解冻
     */
    //http://localhost:8081/phjfht/api/v1/user/unfreezeUserInfo
    @ActionKey("/phjfht/api/v1/user/unfreezeUserInfo")
    public void unfreezeUserInfo() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String app_code = this.getPara("app_code","");
            AuthOperator user = this.getUser();
            userService.unfreezeUserInfo(user,app_code,seq_uuid);
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

    /*
    ******************************************************************
    * 预约开户
    * */
    /**
     * 预约开户 - 跳转预约开户查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goUserBankcardAppointmentPage
    @ActionKey("/phjfht/api/v1/user/goUserBankcardAppointmentPage")
    public void goUserBankcardAppointmentPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> dataList = datalibraryService.queryByParentCode("Phjf", "appointment_status");
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", dataList);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.render("/WEB-INF/velocity/user/userBankcardAppointment/userBankcardAppointmentQuery.vm");
    }

    /**
     * 预约开户 - 预约开户查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryUserBankcardAppointmentByPage
    @ActionKey("/phjfht/api/v1/user/queryUserBankcardAppointmentByPage")
    public void queryUserBankcardAppointmentByPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bank_name = this.getPara("bank_name", "");
            String mp_name = this.getPara("mp_name", "");
            String true_name = this.getPara("true_name", "");
            String mobile = this.getPara("mobile", "");
            String status = this.getPara("status", "");
            PageKit page = this.getBean(PageKit.class, "");
            returnMap = userService.queryUserBankCardAmByPage(bank_name, mp_name, true_name, mobile, status, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     * 预约开户 - 跳转预约开户查看页
     */
    //http://localhost:8081/phjfht/api/v1/user/goViewUserBankcardAppointmentPage
    @ActionKey("/phjfht/api/v1/user/goViewUserBankcardAppointmentPage")
    public void goViewUserBankcardAppointmentPage() {
        this.renderJsp("/WEB-INF/velocity/user/userBankcardAppointment/userBankcardAppointmentDetail.jsp");
    }
    /*
    ******************************************************************
    * 预约取款
    * */
    /**
     * 预约取款 - 跳转预约取款查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goUserWithdrawAmPage
    @ActionKey("/phjfht/api/v1/user/goUserWithdrawAmPage")
    public void goUserWithdrawAmPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> statusList = datalibraryService.queryByParentCode("phjf","sys_withdrawStatus");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("statusList",statusList));
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
        this.render("/WEB-INF/velocity/user/userWithdrawAppointment/userWithdrawAppointmentQuery.vm");
    }

    /**
     * 预约取款 - 预约取款查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryUserWithdrawAmByPage
    @ActionKey("/phjfht/api/v1/user/queryUserWithdrawAmByPage")
    public void queryUserWithdrawAmByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String mp_name = this.getPara("mp_name");
            String name = this.getPara("name");
            String user_name = this.getPara("user_name");
            String mobilephone = this.getPara("mobilephone");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryUserWithdrawAmByPage(mp_name,name,user_name,mobilephone,status, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * 预约取款 - 跳转预约取款查看页
     */
    //http://localhost:8081/phjfht/api/v1/user/goViewUserWithdrawAmPage
    @ActionKey("/phjfht/api/v1/user/goViewUserWithdrawAmPage")
    public void goViewUserWithdrawAmPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map<String,String> data = userService.findUserWithdrawAmById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/user/userWithdrawAppointment/userWithdrawAppointmentDetail.jsp");
    }

    /**
     * 贷款申请管理 - 跳转查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goUserLoanQueryPage
    @ActionKey("/phjfht/api/v1/user/goUserLoanQueryPage")
    public void goUserLoanQueryPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> dataList = datalibraryService.queryByParentCode("Phjf", "loan_useType");
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", dataList);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.render("/WEB-INF/velocity/user/userLoan/userLoanQuery.vm");
    }

    /**
     * 贷款申请管理 - 查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryUserLoanByPage
    @ActionKey("/phjfht/api/v1/user/queryUserLoanByPage")
    public void queryUserLoanByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String mobilephone = this.getPara("mobilephone");
            String name = this.getPara("name");
            String loan_use_type = this.getPara("loan_use_type", "");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryUserLoanByPage(mobilephone, name, loan_use_type, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * 贷款申请发送银行 - 跳转页面
     */
    //http://localhost:8081/phjfht/api/v1/user/goSendLoanInfoToBankPage
    @ActionKey("/phjfht/api/v1/user/goSendLoanInfoToBankPage")
    public void goSendLoanInfoToBankPage() {
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = userService.getUserLoanInfo(seq_uuid);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/user/userLoan/userLoanInfoToBank.jsp");
    }

    /**
     * 获取银行与贷款对应关系列表
     */
    @ActionKey("/phjfht/api/v1/user/queryBankByUserLoanId")
    public void queryBankByUserLoanId() {
        List<Map> list = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String userLoan_id = this.getPara("seq_uuid");
            String language = "ZH_SIMP";
            list = userService.queryBankByUserLoanId(userLoan_id,language);
        }catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(list);
    }
    /**
     * 意见反馈管理 - 跳转查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goUserFeedbackQueryPage
    @ActionKey("/phjfht/api/v1/user/goUserFeedbackQueryPage")
    public void goUserFeedbackQueryPage() {
        this.render("/WEB-INF/velocity/user/userFeedback/userFeedbackQuery.vm");
    }

    /**
     * 意见反馈管理 - 查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryUserFeedbackByPage
    @ActionKey("/phjfht/api/v1/user/queryUserFeedbackByPage")
    public void queryUserFeedbackByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String user_name = this.getPara("user_name");
            String name = this.getPara("name");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryUserFeedbackByPage(user_name, name, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * 会员银行卡管理 - 跳转查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goUserBankcardQueryPage
    @ActionKey("/phjfht/api/v1/user/goUserBankcardQueryPage")
    public void goUserBankcardQueryPage() {
        this.checkSensitiveMsg();
        this.render("/WEB-INF/velocity/user/userBankcard/userBankcardQuery.vm");
    }

    /**
     * 会员银行卡管理 - 查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryUserBankcardByPage
    @ActionKey("/phjfht/api/v1/user/queryUserBankcardByPage")
    public void queryUserBankcardByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bt_name = this.getPara("bt_name");
            String mobilephone = this.getPara("mobilephone");
            String name = this.getPara("name");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryUserBankcardByPage(bt_name, mobilephone, name, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }
    /**
     * 用户贷款申请银行受理信息 - 跳转用户贷款申请银行受理信息表列表查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goULBankProcessQueryPage
    @ActionKey("/phjfht/api/v1/user/goULBankProcessQueryPage")
    public void goULBankProcessQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = userService.queryMpSearchList();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.render("/WEB-INF/velocity/user/userLoanBankProcess/userLoanBankProcessQuery.vm");
    }

    /**
     * 用户贷款申请银行受理信息表 - 用户贷款申请银行受理信息表列表查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryULBankProcessByPage
    @ActionKey("/phjfht/api/v1/user/queryULBankProcessByPage")
    public void queryULBankProcessByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String user_name = this.getPara("user_name");
            String bt_code = this.getPara("bt_code");
            String loan_use_type = this.getPara("loan_use_type");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryULBankProcessByPage(user_name,bt_code,loan_use_type,status, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }
    /**
     * 用户提交贷款发送银行
     */
    //http://localhost:8081/phjfht/api/v1/user/saveLoanSendToBanks
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/user/saveLoanSendToBanks")
    public void saveLoanSendToBanks() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String user_id = this.getPara("user_id");
            String seq_uuid = this.getPara("seq_uuid");
            String[] bankArray = this.getParaValues("bankArray[]");
            String remark = this.getPara("remark");
            userService.saveLoanSendToBanks(user_id,seq_uuid,bankArray,remark);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", "");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }
    /**
     * 账户管理交易记录 - 跳转账户管理交易记录查询页
     */
    //http://localhost:8081/phjfht/api/v1/user/goBankcardTradelogQueryPage
    @ActionKey("/phjfht/api/v1/user/goBankcardTradelogQueryPage")
    public void goBankcardTradelogQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> statusList = datalibraryService.queryByParentCode("phjf","sys_tradeStatus");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("statusList",statusList));
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
        this.render("/WEB-INF/velocity/user/bankBankcardTradelog/bankBankcardTradelogQuery.vm");
    }

    /**
     * 账户管理交易记录 - 账户管理交易记录查询
     */
    //http://localhost:8081/phjfht/api/v1/user/queryBankcardTradelogByPage
    @ActionKey("/phjfht/api/v1/user/queryBankcardTradelogByPage")
    public void queryBankcardTradelogByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bt_name = this.getPara("bt_name");
            String user_name = this.getPara("user_name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = userService.queryBankcardTradelogByPage(bt_name,user_name,status, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }
}
