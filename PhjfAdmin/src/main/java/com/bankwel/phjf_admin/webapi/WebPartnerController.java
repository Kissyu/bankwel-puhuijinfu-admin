package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.core.util.EnvUtil;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.WebNav;
import com.bankwel.phjf_admin.common.model.core.WebPartner;
import com.bankwel.phjf_admin.service.WebNavService;
import com.bankwel.phjf_admin.service.WebPartnerService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.WebNavVO;
import com.bankwel.phjf_admin.webapi.vo.WebPartnerVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import org.apache.commons.io.FileUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by bankwel on 2018/1/8.
 */
public class WebPartnerController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(WebPartner.class);
    private WebPartnerService partnerService = Duang.duang(WebPartnerService.class);
    /**
     * 合作伙伴管理 - 跳转合作伙伴列表查询页
     * http://localhost:8081/phjfwebht/api/v1/partner/goPartnerPage
     */
    @ActionKey("/phjfwebht/api/v1/partner/goPartnerPage")
    public void goPartnerPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = partnerService.queryPartnerSearchList();
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
        this.render("/WEB-INF/velocity/webPartner/partnerQuery.vm");
    }
    /**
     * 合作伙伴管理 - 合作伙伴分页查询
     */
    //http://localhost:8081/phjfwebht/api/v1/partner/queryPartnerByPage
    @ActionKey("/phjfwebht/api/v1/partner/queryPartnerByPage")
    public void queryPartnerByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = this.getPara("language");
            String name = this.getPara("name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = partnerService.queryPartnerByPage(language, name,status, page);
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
     * 合作伙伴管理 - 跳转中文合作伙伴管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/partner/goAddSimpPartnerPage
    @ActionKey("/phjfwebht/api/v1/partner/goAddSimpPartnerPage")
    public void goAddSimpPartnerPage() {
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
        this.renderJsp("/WEB-INF/velocity/webPartner/partnerAdd.jsp");
    }
    /**
     * 合作伙伴管理 - 跳转维语合作伙伴管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/partner/goAddUeyPartnerPage
    @ActionKey("/phjfwebht/api/v1/partner/goAddUeyPartnerPage")
    public void goAddUeyPartnerPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = partnerService.findUeyPartnerInfo(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/webPartner/partnerAdd.jsp");
    }
    /**
     * 合作伙伴管理 - 新增合作伙伴
     */
    //http://localhost:8081/phjfwebht/api/v1/partner/addPartner
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/partner/addPartner")
    public void addPartner() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebPartnerVO vo = this.getBean(WebPartnerVO.class,"");
            AuthOperator opt = this.getUser();
            WebPartner webPartner = partnerService.addPartner(opt,vo);
            Map<String,String> data = partnerService.findPartnerById(webPartner.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            partnerService.generatePartners(webPartner.getLanguage());
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
            this.renderJsp("/WEB-INF/velocity/webPartner/partnerAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webPartner/partnerAdd.jsp");
        }
    }
    /**
     * 合作伙伴管理 - 跳转合作伙伴修改页
     */
    //http://localhost:8081/phjfwebht/api/v1/partner/goModifyPartnerPage
    @ActionKey("/phjfwebht/api/v1/partner/goModifyPartnerPage")
    public void goModifyPartnerPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = partnerService.findPartnerById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webPartner/partnerModify.jsp");
    }
    /**
     * 合作伙伴管理 - 修改合作伙伴
     */
    //http://localhost:8081/phjfwebht/api/v1/partner/modifyPartner
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/partner/modifyPartner")
    public void modifyPartner() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebPartnerVO vo = this.getBean(WebPartnerVO.class,"");
            AuthOperator opt = this.getUser();
            WebPartner model = partnerService.modifyPartner(opt,vo);
            Map<String,String> data = partnerService.findPartnerById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            partnerService.generatePartners(model.getLanguage());
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
        this.renderJsp("/WEB-INF/velocity/webPartner/partnerModify.jsp");
    }
    /**
     * 合作伙伴管理 - 删除合作伙伴
     * //http://localhost:8081/phjfwebht/api/v1/partner/deletePartner
     */
    @ActionKey("/phjfwebht/api/v1/partner/deletePartner")
    public void deletePartner() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            partnerService.deletePartner(user,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            partnerService.generatePartners(AdminConstants.LanguageType.ZH_SIMP.value);
            partnerService.generatePartners(AdminConstants.LanguageType.ZH_UEY.value);
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
}
