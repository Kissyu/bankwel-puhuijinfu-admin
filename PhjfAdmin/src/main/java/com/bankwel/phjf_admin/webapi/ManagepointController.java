package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.ManagepointBank;
import com.bankwel.phjf_admin.common.model.core.ManagepointInfo;
import com.bankwel.phjf_admin.common.model.core.ManagepointOptinfo;
import com.bankwel.phjf_admin.service.DatalibraryService;
import com.bankwel.phjf_admin.service.ManagepointService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.ManagepointBankVO;
import com.bankwel.phjf_admin.webapi.vo.ManagepointInfoVO;
import com.bankwel.phjf_admin.webapi.vo.ManagepointOptinfoVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */
public class ManagepointController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(ManagepointController.class);

    private ManagepointService managepointService = Duang.duang(ManagepointService.class);
    private DatalibraryService datalibraryService = Duang.duang(DatalibraryService.class);

    /**
     *
     * 助农办理点管理 - 跳转助农办理点管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goManagepointQueryPage
    @ActionKey("/phjfht/api/v1/managepoint/goManagepointQueryPage")
    public void goManagepointQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = managepointService.queryMpSearchList();
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
        this.render("/WEB-INF/velocity/managepoint/managepointInfo/managepointQuery.vm");
    }

    /**
     * 助农办理点管理 - 助农办理点管理查询
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/queryManagepointByPage
    @ActionKey("/phjfht/api/v1/managepoint/queryManagepointByPage")
    public void queryManagepointByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = this.getPara("language");
            String name = this.getPara("name");
            String contact = this.getPara("contact");
            String mobilephone = this.getPara("mobilephone");
            String status = this.getPara("status");
            String is_confirm = this.getPara("is_confirm");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = managepointService.queryManagepointByPage(language, name,contact,mobilephone,status,is_confirm, page);
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
     * 助农办理点管理 - 跳转中文助农办理点管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goAddSimpManagepointPage
    @ActionKey("/phjfht/api/v1/managepoint/goAddSimpManagepointPage")
    public void goAddSimpManagepointPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String languageDesc=AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",languageDesc));
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointAdd.jsp");
    }

    /**
     * 助农办理点管理 - 新增助农办理点
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/addManagepoint
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/managepoint/addManagepoint")
    public void addManagepoint() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            ManagepointInfoVO vo = this.getBean(ManagepointInfoVO.class,"");
            AuthOperator opt = this.getUser();
            ManagepointInfo managepointInfo = managepointService.addManagepoint(opt,vo);
            Map<String,String> data = managepointService.findManagePointById(managepointInfo.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointAdd.jsp");
        }
    }

    /**
     * 助农办理点管理 - 跳转维语助农办理点管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goAddUeyManagepointPage
    @ActionKey("/phjfht/api/v1/managepoint/goAddUeyManagepointPage")
    public void goAddUeyManagepointPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = managepointService.findUeyManagepointInfo(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointAdd.jsp");
    }

    /**
     * 助农办理点管理 - 跳转助农办理点管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goModifyManagepointPage
    @ActionKey("/phjfht/api/v1/managepoint/goModifyManagepointPage")
    public void goModifyManagepointPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = managepointService.findManagePointById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointModify.jsp");
    }

    /**
     * 助农办理点管理 - 修改助农办理点
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/modifyManagepoint
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/managepoint/modifyManagepoint")
    public void modifyManagepoint() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            ManagepointInfoVO vo = this.getBean(ManagepointInfoVO.class,"");
            AuthOperator opt = this.getUser();
            ManagepointInfo model = managepointService.modifyManagepoint(opt,vo);
            Map<String,String> data = managepointService.findManagePointById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointModify.jsp");
    }

    /**
     * 助农办理点管理 - 删除助农办理点
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/deleteManagepoint
    @ActionKey("/phjfht/api/v1/managepoint/deleteManagepoint")
    public void deleteManagepoint() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            managepointService.deleteManagepoint(user,seq_uuid);
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
     * 助农办理点管理 - 跳转助农办理点管理查看页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goViewManagepointPage
    @ActionKey("/phjfht/api/v1/managepoint/goViewManagepointPage")
    public void goViewManagepointPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map<String,String> data = managepointService.findManagePointById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointInfo/managepointDetail.jsp");
    }


    /**
     * 办理点银行管理 - 跳转办理点银行管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goMpBankTypePage
    @ActionKey("/phjfht/api/v1/managepoint/goMpBankTypePage")
    public void goMpBankTypePage() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> dataList = datalibraryService.queryByParentCode("Phjf", "is_open_account");
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
        this.render("/WEB-INF/velocity/managepoint/managepointBank/mpBankQuery.vm");
    }

    /**
     * 办理点银行管理 - 办理点银行管理查询
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/queryMpBankByPage
    @ActionKey("/phjfht/api/v1/managepoint/queryMpBankByPage")
    public void queryMpBankByPage() {
        Map result = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String mp_name = this.getPara("mp_name");
            String bank_name = this.getPara("bank_name");
            String language = this.getPara("language", AdminConstants.LanguageType.ZH_SIMP.value);
            String is_open_account = this.getPara("is_open_account", "");
            PageKit page = this.getBean(PageKit.class, "");
            result = managepointService.queryMpBankByPage(mp_name, bank_name, is_open_account, language, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }

    /**
     * 办理点银行管理 - 跳转办理点银行管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goAddMpBankTypePage
    @ActionKey("/phjfht/api/v1/managepoint/goAddMpBankTypePage")
    public void goAddMpBankTypePage() {
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointBank/mpBankAdd.jsp");
    }

    /**
     * 办理点银行管理 - 新增办理点银行
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/addMpBank
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/managepoint/addMpBank")
    public void addMpBank() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            ManagepointBankVO vo = this.getBean(ManagepointBankVO.class, "");
            AuthOperator opt = this.getUser();
            ManagepointBank mpBank = managepointService.addManagepointBank(opt, vo);
            Map map = managepointService.findById(mpBank.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data", map);
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
            this.renderJsp("/WEB-INF/velocity/managepoint/managepointBank/mpBankAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/managepoint/managepointBank/mpBankAdd.jsp");
        }
    }

    /**
     * 办理点银行管理 - 跳转办理点银行管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goModifyMpBankPage
    @ActionKey("/phjfht/api/v1/managepoint/goModifyMpBankPage")
    public void goModifyMpBankTypePage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid", "");
            Map map = managepointService.findById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data", map);
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointBank/mpBankModify.jsp");
    }

    /**
     * 办理点银行管理 - 修改办理点银行
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/ModifyMpBankType
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/managepoint/ModifyMpBankType")
    public void ModifyMpBankType() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            ManagepointBankVO vo = this.getBean(ManagepointBankVO.class, "");
            AuthOperator opt = this.getUser();
            ManagepointBank mpBank = managepointService.updateManagepointBank(opt, vo);
            Map map = managepointService.findById(mpBank.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "修改成功", "data", map);
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
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointBank/mpBankModify.jsp");
    }

    /**
     * 办理点银行管理 - 失效
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/ModifyMpBankType
    @ActionKey("/phjfht/api/v1/managepoint/deleteMpBank")
    public void deleteMpBank() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid", "");
            AuthOperator opt = this.getUser();
            managepointService.deleteMpBank(opt, seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "失效成功", "data", "");
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
     * 办理点账户管理 - 跳转办理点账户管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goMpOptQueryPage
    @ActionKey("/phjfht/api/v1/managepoint/goMpOptQueryPage")
    public void goMpOptQueryPage() {
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
        this.render("/WEB-INF/velocity/managepoint/managepointOptInfo/mpOptInfoQuery.vm");
    }

    /**
     * 办理点账户管理 - 办理点账户管理查询
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/queryMpOptinfoByPage
    @ActionKey("/phjfht/api/v1/managepoint/queryMpOptInfoByPage")
    public void queryMpOptinfoByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String mp_name = this.getPara("mp_name");
            String name = this.getPara("name");
            String user_name = this.getPara("user_name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = managepointService.queryMpOptInfoByPage(mp_name,name,user_name,status, page);
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
     * 办理点账户管理 - 跳转办理点账户管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goAddMpOptinfoPage
    @ActionKey("/phjfht/api/v1/managepoint/goAddMpOptInfoPage")
    public void goAddMpOptinfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = F.transKit.asMap("password",F.encryptionKit.md5(PropKit.use("config.properties").get("merch_password")));
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
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointOptInfo/mpOptInfoAdd.jsp");
    }

    /**
     * 办理点账户管理 - 新增办理点账户
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/addMpOptinfo
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/managepoint/addMpOptInfo")
    public void addMpOptinfo() {
        Map params = this.getReqParameterMap();
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            ManagepointOptinfoVO vo = this.getBean(ManagepointOptinfoVO.class,"");
            AuthOperator opt = this.getUser();
            ManagepointOptinfo managepointOptinfo = managepointService.addManagepointOptInfo(opt,vo);
            Map<String,String> data = managepointService.findMpOptInfoById(managepointOptinfo.getSeq_uuid());
            resultMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage(),"data",params);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data",params) ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        if("1".equals(resultMap.get("code")+"")){
            this.renderJsp("/WEB-INF/velocity/managepoint/managepointOptInfo/mpOptInfoAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/managepoint/managepointOptInfo/mpOptInfoAdd.jsp");
        }
    }

    /**
     * 办理点账户管理 - 跳转办理点账户管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/goModifyMpOptinfoPage
    @ActionKey("/phjfht/api/v1/managepoint/goModifyMpOptInfoPage")
    public void goModifyMpOptinfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = managepointService.findMpOptInfoById(seq_uuid);
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
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointOptInfo/mpOptInfoModify.jsp");
    }

    /**
     * 办理点账户管理 - 修改办理点账户
     */
    //http://localhost:8081/phjfht/api/v1/managepoint/modifyMpOptinfo
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/managepoint/modifyMpOptInfo")
    public void modifyMpOptinfo() {
        Map params = this.getReqParameterMap();
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            ManagepointOptinfoVO vo = this.getBean(ManagepointOptinfoVO.class,"");
            AuthOperator opt = this.getUser();
            ManagepointOptinfo model = managepointService.modifyMpOptInfo(opt,vo);
            Map<String,String> data = managepointService.findMpOptInfoById(model.getSeq_uuid());
            resultMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data",params);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data",params) ;
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/managepoint/managepointOptInfo/mpOptInfoModify.jsp");
    }
}
