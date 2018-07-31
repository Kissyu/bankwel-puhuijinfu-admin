package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
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
public class BankInfoController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(BankInfoController.class);

    private BankInfoService bankInfoService = Duang.duang(BankInfoService.class);
    private DatalibraryService datalibraryService = Duang.duang(DatalibraryService.class);

    /**
     * 银行类型管理 - 跳转查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankTypeQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankTypeQueryPage")
    public void goBankTypePage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = bankInfoService.queryBTSearchList();
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
        this.render("/WEB-INF/velocity/bankinfo/bankType/bankTypeQuery.vm");
    }

    /**
     * 银行类型管理 - 银行类型管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankTypeByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankTypeByPage")
    public void queryBankTypeByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name");
            String status = this.getPara("status");
            String language = this.getPara("language",AdminConstants.ZH_SIMP);
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = bankInfoService.queryBankTypeByPage(name,status,language, page);
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
     * 银行类型管理 - 跳转中文银行类型管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddSimpBankTypePage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddSimpBankTypePage")
    public void goAddSimpBankTypePage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show = AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", F.transKit.asMap("language", language, "language_show", language_show));
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankType/bankTypeAdd.jsp");
    }

    /**
     * 银行类型管理 - 跳转维语银行类型管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddUeyBankTypePage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddUeyBankTypePage")
    public void goAddUeyBankTypePage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = bankInfoService.findUeyBankType(seq_uuid,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", data);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankType/bankTypeAdd.jsp");
    }

    /**
     * 银行类型管理 - 新增银行类型
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/addBankType
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addBankType")
    public void addBankType() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankTypeVO vo = this.getBean(BankTypeVO.class,"");
            AuthOperator opt = this.getUser();
            BankType bankType = bankInfoService.addBankType(opt,vo);
            Map<String,String> data = bankInfoService.findBankTypeById(bankType.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankType/bankTypeAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankType/bankTypeAdd.jsp");
        }
    }

    /**
     * 银行类型管理 - 跳转银行类型管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyBankTypePage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyBankTypePage")
    public void goModifyBankTypePage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankTypeById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankType/bankTypeModify.jsp");
    }

    /**
     * 银行类型管理 - 银行类型管理修改
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/modifyBankType
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyBankType")
    public void modifyBankType() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankTypeVO vo = this.getBean(BankTypeVO.class,"");
            AuthOperator opt = this.getUser();
            BankType model = bankInfoService.modifyBankType(opt,vo);
            Map<String,String> data = bankInfoService.findBankTypeById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankType/bankTypeModify.jsp");
    }
    /*
    * *************************************************************
    *银行机构管理
    * */

    /**
     * 银行机构管理 - 跳转银行机构管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankInfoPage")
    public void goBankInfoPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = bankInfoService.queryBTSearchList();
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
        this.render("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoQuery.vm");
    }

    /**
     * 银行机构管理 - 银行机构管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankInfoByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankInfoByPage")
    public void queryBankInfoByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bt_name = this.getPara("bt_name");
            String name = this.getPara("name");
            String status = this.getPara("status");
            String is_confirm = this.getPara("is_confirm");
            String language = this.getPara("language",AdminConstants.ZH_SIMP);
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = bankInfoService.queryBankInfoByPage(bt_name,name,status,is_confirm,language, page);
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
     * 银行机构管理 - 跳转中文银行机构管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddSimpBankInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddSimpBankInfoPage")
    public void goAddSimpBankInfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String parent_bank_code = this.getPara("parent_bank_code");
            Map data = bankInfoService.findAddSimpBankInfo(parent_bank_code);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", data);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoAdd.jsp");
    }

    /**
     * 银行机构管理 - 跳转维语银行机构管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddUeyBankInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddUeyBankInfoPage")
    public void goAddUeyBankInfoPage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = bankInfoService.findUeyBankInfo(seq_uuid,language);
            map = F.transKit.asMap("code", 1, "msg", "", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            map = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(map);
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoAdd.jsp");
    }

    /**
     * 银行机构管理 - 银行机构管理新增
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/addBankInfo
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addBankInfo")
    public void addBankInfo() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankInfoVO vo = this.getBean(BankInfoVO.class,"");
            AuthOperator opt = this.getUser();
            BankInfo bankInfo = bankInfoService.addBankInfo(opt,vo);
            Map<String,String> data = bankInfoService.findBankInfoById(bankInfo.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoAdd.jsp");
        }
    }

    /**
     * 银行机构管理 - 跳转银行机构管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyBankInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyBankInfoPage")
    public void goModifyBankInfoPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankInfoById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoModify.jsp");
    }

    /**
     * 银行机构管理 - 银行机构管理修改
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/modifyBankInfo
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyBankInfo")
    public void modifyBankInfo() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankInfoVO vo = this.getBean(BankInfoVO.class,"");
            AuthOperator opt = this.getUser();
            BankInfo model = bankInfoService.modifyBankInfo(opt,vo);
            Map<String,String> data = bankInfoService.findBankInfoById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoModify.jsp");
    }

    /**
     * 银行机构管理 - 跳转银行机构管理查看页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goViewBankInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goViewBankInfoPage")
    public void goViewBankInfoPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankInfoById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankInfo/bankInfoDetail.jsp");
    }

    /*
    * *************************************************************
    *银行账户管理
    * */

    /**
     * 银行账户管理 - 跳转银行账户管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankOptQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankOptQueryPage")
    public void goBankOptQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> statusList = datalibraryService.queryByParentCode("phjf", "sys_status");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", F.transKit.asMap("statusList", statusList));
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
        this.render("/WEB-INF/velocity/bankinfo/bankOptinfo/bankOptinfoQuery.vm");
    }

    /**
     * 银行账户管理 - 银行账户管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankOptinfoByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankOptinfoByPage")
    public void queryBankOptinfoByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bank_name = this.getPara("bank_name");
            String name = this.getPara("name");
            String user_name = this.getPara("user_name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = bankInfoService.queryBankOptInfoByPage(bank_name, name, user_name, status, page);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * 银行账户管理 - 跳转银行账户管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddBankOptinfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddBankOptinfoPage")
    public void goAddBankOptinfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = F.transKit.asMap("password", F.encryptionKit.md5(PropKit.use("config.properties").get("merch_password")));
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankOptinfo/bankOptinfoAdd.jsp");
    }

    /**
     * 银行账户管理 - 银行账户管理新增
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/addBankOptinfo
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addBankOptinfo")
    public void addBankOptinfo() {
        Map params = this.getReqParameterMap();
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankOptinfoVO vo = this.getBean(BankOptinfoVO.class, "");
            AuthOperator opt = this.getUser();
            BankOptinfo bankOptinfo = bankInfoService.addBankOptInfo(opt, vo);
            Map<String, String> data = bankInfoService.findBankOptInfoById(bankOptinfo.getSeq_uuid());
            resultMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(resultMap);
        if ("1".equals(resultMap.get("code") + "")) {
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankOptinfo/bankOptinfoAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankOptinfo/bankOptinfoAdd.jsp");
        }
    }

    /**
     * 银行账户管理 - 跳转银行账户管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyBankOptinfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyBankOptinfoPage")
    public void goModifyBankOptinfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankOptInfoById(seq_uuid);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankOptinfo/bankOptinfoModify.jsp");
    }

    /**
     * 银行账户管理 - 银行账户管理修改
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/modifyBankOptinfo
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyBankOptinfo")
    public void modifyBankOptinfo() {
        Map params = this.getReqParameterMap();
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankOptinfoVO vo = this.getBean(BankOptinfoVO.class, "");
            AuthOperator opt = this.getUser();
            BankOptinfo model = bankInfoService.modifyBankOptInfo(opt, vo);
            Map<String, String> data = bankInfoService.findBankOptInfoById(model.getSeq_uuid());
            resultMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params);
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankOptinfo/bankOptinfoModify.jsp");
    }

     /*
    * *************************************************************
    *储蓄产品管理
    * */

    /**
     * 储蓄产品管理 - 跳转储蓄产品管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankDepositQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankDepositQueryPage")
    public void goBankDepositQueryPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
//            List<Map> dataList = datalibraryService.queryByParentCode("Phjf", "is_open_account");
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", "");
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
        this.render("/WEB-INF/velocity/bankinfo/bankDepositInfo/bankDepositInfoQuery.vm");
    }

    /**
     * 储蓄产品管理 - 储蓄产品管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankDepositByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankDepositByPage")
    public void queryBankDepositInfoByPage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {

            map = F.transKit.asMap("code", 1, "msg", "", "data", "");
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJson(map);
    }

    /**
     * 储蓄产品管理 - 跳转储蓄产品管理查看页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goViewBankDepositInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goViewBankDepositInfoPage")
    public void goViewBankDepositInfoPage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            map = F.transKit.asMap("code", 1, "msg", "", "data", "");
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            map = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankDepositInfo/bankDepositInfoDetail.jsp");
    }


    /**
     * 贷款产品管理 - 跳转查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goLoanQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goLoanQueryPage")
    public void goLoanQueryPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            Map dataList = bankInfoService.queryLoanSearchList();
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", dataList);
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
        this.render("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoQuery.vm");
    }

    /**
     * 贷款产品管理 - 查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryLoanInfoByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryLoanInfoByPage")
    public void queryLoanInfoByPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String bt_name = this.getPara("bt_name", "");
            String loan_plate = this.getPara("loan_plate", "");
            String name = this.getPara("name", "");
            String loan_use_type = this.getPara("loan_use_type", "");
            String is_recom = this.getPara("is_recom", "");
            String status = this.getPara("status", "");

            PageKit page = this.getBean(PageKit.class, "");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            returnMap = bankInfoService.queryLoanInfoByPage(bt_name, loan_plate, name,loan_use_type, is_recom, status, language, page);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     * 贷款产品管理 - 跳转新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddLoanInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddLoanInfoPage")
    public void goAddLoanInfoPage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show = AdminConstants.LanguageType.ZH_SIMP.desc;
            Map data = F.transKit.asMap("language", language, "language_show", language_show);
            map = F.transKit.asMap("code", 1, "msg", "", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            map = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(map);
        this.renderJsp("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoAdd.jsp");
    }

    /**
     * 贷款产品管理 - 新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addLoanInfo")
    public void addLoanInfo() {
        Map params = this.getReqParameterMap();
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            LoanInfoVO vo = this.getBean(LoanInfoVO.class, "");
            AuthOperator opt = this.getUser();
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            vo.setLanguage(language);
            LoanInfo loanInfo = bankInfoService.addLoanInfo(opt, vo);
            Map<String, String> data = bankInfoService.findLoanInfoById(loanInfo.getSeq_uuid(), language);
            resultMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(resultMap);
        if ("1".equals(resultMap.get("code") + "")) {
            this.renderJsp("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoAdd.jsp");
        }
    }

    /**
     * 贷款产品管理 - 跳转修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyLoanInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyLoanInfoPage")
    public void goModifyLoanInfoPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid", "");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = bankInfoService.findLoanInfoById(seq_uuid, language);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", map);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoModify.jsp");
    }

    /**
     * 贷款产品管理 - 修改
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyLoanInfo")
    public void modifyLoanInfo() {
        Map params = this.getReqParameterMap();
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            LoanInfoVO vo = this.getBean(LoanInfoVO.class, "");
            AuthOperator opt = this.getUser();
            LoanInfo loanInfo = bankInfoService.modifyLoanInfo(opt, vo);
            Map<String, String> data = bankInfoService.findLoanInfoById(loanInfo.getSeq_uuid(), vo.getLanguage());
            resultMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data", data);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoModify.jsp");
    }

    /**
     * 贷款产品管理 - 跳转查看页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goDetailLoanInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goDetailLoanInfoPage")
    public void goDetailLoanInfoPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid", "");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = bankInfoService.findLoanInfoById(seq_uuid, language);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", map);
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/bankinfo/loanInfo/loanInfoDetail.jsp");
    }

    /**
     * 贷款产品管理 - 发布
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/publishLoan
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/publishLoan")
    public void publishLoan() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            bankInfoService.publishLoan(opt, seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", "");
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     * 贷款产品管理 - 下架
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/unPublishLoan
    @ActionKey("/phjfht/api/v1/bankinfo/unPublishLoan")
    public void unPublishLoan() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            bankInfoService.unPublishLoan(opt, seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", "");
        } catch (MsgBusinessException e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(), tps_startTime, System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     * 银行ATM管理 - 跳转查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankATMQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankATMQueryPage")
    public void goBankATMQueryPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            List<Map> dataList = datalibraryService.queryByParentCode("Phjf", "sys_status");
            List<Map> isConfirmList = datalibraryService.queryByParentCode("Phjf", "sys_isConfirm");
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", F.transKit.asMap("statusList",dataList,"isConfirmList",isConfirmList));
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
        this.render("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMInfoQuery.vm");
    }


    /**
     * ATM管理 - ATM管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankATMByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankATMByPage")
    public void queryManagepointByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String name = this.getPara("name");
            String status = this.getPara("status");
            String is_confirm = this.getPara("is_confirm");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = bankInfoService.queryATMByPage( name,status,is_confirm, page);
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
     * ATM点管理 - 跳转ATM点管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddBankATMPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddBankATMPage")
    public void goAddBankATMPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show=AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",language_show));
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMInfoAdd.jsp");
    }

    /**
     * ATM管理 - 新增ATM
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/addBankATM
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addBankATM")
    public void addManagepoint() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankAtmVO vo = this.getBean(BankAtmVO.class,"");
            AuthOperator opt = this.getUser();
            BankAtm bankAtm = bankInfoService.addBankAtm(opt,vo);
            Map<String,String> data = bankInfoService.findBankATMById(bankAtm.getSeq_uuid());
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
        if("1".equals(returnMap.get("code")+"")){
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMInfoAdd.jsp");
        }
    }

    /**
     * 银行ATM管理 - 跳转银行ATM修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyBankATMPage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyBankATMPage")
    public void goModifyBankATMPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankATMById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMInfoModify.jsp");
    }

    /**
     * ATM管理 - 修改ATM
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/modifyBankATM
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyBankATM")
    public void modifyBankATM() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankAtmVO vo = this.getBean(BankAtmVO.class,"");
            AuthOperator opt = this.getUser();
            BankAtm model = bankInfoService.modifyBankATM(opt,vo);
            Map data = bankInfoService.findBankATMById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMInfoModify.jsp");
    }

    /**
     * ATM管理 - 跳转ATM管理查看页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goViewBankATMInfoPage
    @ActionKey("/phjfht/api/v1/bankinfo/goViewBankATMInfoPage")
    public void goViewManagepointPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankATMById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankATMInfo/bankATMInfoDetail.jsp");
    }

    /**
     * ATM管理 - 删除ATM
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/deleteBankATMInfo
    @ActionKey("/phjfht/api/v1/bankinfo/deleteBankATMInfo")
    public void deleteBankATMInfo() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            bankInfoService.deleteBankATMInfo(user,seq_uuid);
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
    * *************************************************************
    *银行业务管理
    * */
    /**
     * 银行业务管理 - 跳转业务管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankBusinessSettingQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankBusinessSettingQueryPage")
    public void goBankBusinessSettingPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = bankInfoService.queryBsSearchList();
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
        this.render("/WEB-INF/velocity/bankinfo/bankBusinessSetting/bankBusinessSettingQuery.vm");
    }
    /**
     * 银行业务管理 - 银行业务管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankBusinessSettingByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankBusinessSettingByPage")
    public void queryBankBusinessSettingByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bt_name = this.getPara("bt_name");
            String type = this.getPara("type");
            String bussiness_code = this.getPara("business_code");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = bankInfoService.queryBankBusinessSettingByPage(bt_name,type,bussiness_code,status, page);
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
     * 银行业务管理 - 跳转银行业务管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddSimpBankBusinessSettingPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddSimpBankBusinessSettingPage")
    public void goAddSimpBankBusinessSettingPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show = AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", F.transKit.asMap("language", language, "language_show", language_show));
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessSetting/bankBusinessSettingAdd.jsp");
    }
    /**
     * 银行业务管理 - 新增银行业务
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/addBankBusinessSetting
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addBankBusinessSetting")
    public void addBankBusinessSetting() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankBusinessSettingVO vo = this.getBean(BankBusinessSettingVO.class,"");
            AuthOperator opt = this.getUser();
            BankBusinessSetting bankBusiness = bankInfoService.addBankBusiness(opt,vo);
            Map<String,String> data = bankInfoService.findBankBusinessById(bankBusiness.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/bankInfo/bankBusinessSetting/bankBusinessSettingAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/bankInfo/bankBusinessSetting/bankBusinessSettingAdd.jsp");
        }
    }
    /**
     * 银行业务管理 - 跳转银行业务管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyBankBusinessSettingPage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyBankBusinessSettingPage")
    public void goModifyBankBusinessSettingPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankBusinessById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessSetting/bankBusinessSettingModify.jsp");
    }

    /**
     * 银行业务管理 - 银行业务管理修改
     */
//    http://localhost:8081/phjfht/api/v1/bankinfo/modifyBankBusinessSetting
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyBankBusinessSetting")
    public void modifyBankBusinessSetting() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankBusinessSettingVO vo = this.getBean(BankBusinessSettingVO.class,"");
            AuthOperator opt = this.getUser();
            BankBusinessSetting model = bankInfoService.modifyBankBusiness(opt,vo);
            Map<String,String> data = bankInfoService.findBankBusinessById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessSetting/bankBusinessSettingModify.jsp");
    }
    /**
     * 银行业务管理 - 跳转银行业务管理查看页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goViewBankBusinessSettingPage
    @ActionKey("/phjfht/api/v1/bankinfo/goViewBankBusinessSettingPage")
    public void goViewBankBusinessSettingPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankBusinessById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessSetting/bankBusinessSettingDetail.jsp");
    }
    /*
    * *************************************************************
    *银行业务配置管理
    * */
    /**
     * 银行业务配置管理 - 跳转业务配置管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goBankBusinessConfigQueryPage
    @ActionKey("/phjfht/api/v1/bankinfo/goBankBusinessConfigQueryPage")
    public void goBankBusinessConfigPage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = bankInfoService.queryBbcSearchList();
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
        this.render("/WEB-INF/velocity/bankinfo/bankBusinessConfig/bankBusinessConfigQuery.vm");
    }
    /**
     * 银行业务配置管理 - 银行业务配置管理查询
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBankBusinessConfigByPage
    @ActionKey("/phjfht/api/v1/bankinfo/queryBankBusinessConfigByPage")
    public void queryBankBusinessConfigByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String bt_name = this.getPara("bt_name");
            String bussiness_code = this.getPara("business_code");
            String status = this.getPara("status");
            String device_type = this.getPara("deviceType");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = bankInfoService.queryBankBusinessConfigByPage(bt_name,bussiness_code,status, device_type,page);
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
     * 银行业务配置管理 - 跳转银行业务配置管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goAddSimpBankBusinessConfigPage
    @ActionKey("/phjfht/api/v1/bankinfo/goAddSimpBankBusinessConfigPage")
    public void goAddSimpBankBusinessConfigPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show = AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data", F.transKit.asMap("language", language, "language_show", language_show));
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessConfig/bankBusinessConfigAdd.jsp");
    }
    /**
     * 银行业务配置管理 - 新增银行业务配置
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/addBankBusinessConfig
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/addBankBusinessConfig")
    public void addBankBusinessConfig() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankBusinessConfigVO vo = this.getBean(BankBusinessConfigVO.class,"");
            String app_version = this.getPara("app_version");
            AuthOperator opt = this.getUser();
            BankBusinessConfig bankBusinessConfig = bankInfoService.addBankBusinessConfig(opt,vo,app_version);
            Map<String,String> data = bankInfoService.findBankBusinessConfigById(bankBusinessConfig.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/bankInfo/bankBusinessConfig/bankBusinessConfigAddShow.jsp");
        } else {
            this.renderJsp("/WEB-INF/velocity/bankInfo/bankBusinessConfig/bankBusinessConfigAdd.jsp");
        }
    }
    /**
     * 银行业务配置管理 - 跳转银行业务配置管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goModifyBankBusinessConfigPage
    @ActionKey("/phjfht/api/v1/bankinfo/goModifyBankBusinessConfigPage")
    public void goModifyBankBusinessConfigPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankBusinessConfigById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessConfig/bankBusinessConfigModify.jsp");
    }

    /**
     * 银行业务配置管理 - 银行业务配置管理修改
     */
//    http://localhost:8081/phjfht/api/v1/bankinfo/modifyBankBusinessConfig
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/bankinfo/modifyBankBusinessConfig")
    public void modifyBankBusinessConfig() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BankBusinessConfigVO vo = this.getBean(BankBusinessConfigVO.class,"");
            String app_version = this.getPara("app_version");
            AuthOperator opt = this.getUser();
            BankBusinessConfig model = bankInfoService.modifyBankBusinessConfig(opt,vo,app_version);
            Map<String,String> data = bankInfoService.findBankBusinessConfigById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessConfig/bankBusinessConfigModify.jsp");
    }
    /**
     * 银行业务管理 - 跳转银行业务管理查看页
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/goViewBankBusinessConfigPage
    @ActionKey("/phjfht/api/v1/bankinfo/goViewBankBusinessConfigPage")
    public void goViewBankBusinessConfigPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = bankInfoService.findBankBusinessConfigById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/bankinfo/bankBusinessConfig/bankBusinessConfigDetail.jsp");
    }
}
