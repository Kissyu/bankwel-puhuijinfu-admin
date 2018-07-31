package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.service.AppointmentTimeService;
import com.bankwel.phjf_admin.service.BankInfoService;
import com.bankwel.phjf_admin.service.DatalibraryService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.*;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/23.
 */
public class AppointmentTimeController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AppointmentTimeController.class);

    private AppointmentTimeService amTimeService = Duang.duang(AppointmentTimeService.class);
    private DatalibraryService datalibraryService = Duang.duang(DatalibraryService.class);

    /**
     * 预约时间管理 - 跳转查询银行预约时间页
     */
    //http://localhost:8081/phjfht/api/v1/appointmentTime/goQueryBankAppointmentTimePage
    @ActionKey("/phjfht/api/v1/appointmentTime/goQueryBankAppointmentTimePage")
    public void goQueryBankAppointmentTimePage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = amTimeService.queryBankAmTimeSearchList();
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.render("/WEB-INF/velocity/amTime/bankAmTime/bankAmTimeQuery.vm");
    }

    /**
     * 预约时间管理 - 银行预约时间管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankTypeByPage
    @ActionKey("/phjfht/api/v1/appointmentTime/queryBankAmTimeByPage")
    public void queryBankAmTimeByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("bt_name");
            String status = this.getPara("status");
            String business_code = this.getPara("bank_function");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = amTimeService.queryBankAmTimeByPage(name,status,business_code, page);
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
     * 预约时间管理 - 跳转银行预约时间新增页
     */
    ///phjfht/api/v1/appointmentTime/goAddSimpBankAmTimePage
    @ActionKey("/phjfht/api/v1/appointmentTime/goAddSimpBankAmTimePage")
    public void goAddSimpBankAmTimePage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", F.transKit.asMap());
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/amTime/bankAmTime/bankAmTimeAdd.jsp");
    }

    /**
     * 预约时间管理 - 新增银行预约时间
     */
    //http://localhost:8081/phjfht/api/v1/appointmentTime/addBankAmTime
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/appointmentTime/addBankAmTime")
    public void addBankType() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AppointmentTimeVO vo = this.getBean(AppointmentTimeVO.class,"");
            String bt_code = this.getPara("bt_code");
            vo.setInstitution_code(bt_code);
            vo.setInstitution_type("bank_institution");
            vo.setBusiness_stat("N");
            AuthOperator opt = this.getUser();
            AppointmentTime amTime = amTimeService.addAppointTime(opt,vo);
            Map<String,String> data = amTimeService.findAmTimeById(amTime.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        if ("1".equals(returnMap.get("code") + "")) {
            this.renderJsp("/WEB-INF/velocity/amTime/bankAmTime/bankAmTimeAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/amTime/bankAmTime/bankAmTimeAdd.jsp");
        }
    }

    /**
     * 预约时间管理 - 跳转银行预约时间修改页
     */
    //http://localhost:8081/phjfht/api/v1/appointmentTime/goModifyBankAmTimePage
    @ActionKey("/phjfht/api/v1/appointmentTime/goModifyBankAmTimePage")
    public void goModifyBankAmTimePage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = amTimeService.findAmTimeById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/amTime/bankAmTime/bankAmTimeModify.jsp");
    }

    /**
     * 银行预约时间管理 - 银行预约时间修改
     */
    //http://localhost:8081/phjfht/api/v1/appointmentTime/modifyBankAmTime
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/appointmentTime/modifyBankAmTime")
    public void modifyBankAmTime() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AppointmentTimeVO vo = this.getBean(AppointmentTimeVO.class,"");
            AuthOperator opt = this.getUser();
            vo.setInstitution_type("bank_institution");
            AppointmentTime model = amTimeService.modifyAmTime(opt,vo);
            Map<String,String> data = amTimeService.findAmTimeById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.renderJsp("/WEB-INF/velocity/amTime/bankAmTime/bankAmTimeModify.jsp");
    }

}
