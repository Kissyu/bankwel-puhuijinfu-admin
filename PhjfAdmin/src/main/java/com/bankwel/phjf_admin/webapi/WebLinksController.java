package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.WebLinks;
import com.bankwel.phjf_admin.common.model.core.WebPartner;
import com.bankwel.phjf_admin.service.WebLinksService;
import com.bankwel.phjf_admin.service.WebPartnerService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.WebLinksVO;
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
public class WebLinksController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(WebLinks.class);
    private WebLinksService linksService = Duang.duang(WebLinksService.class);
    /**
     * 友情链接管理 - 跳转友情链接列表查询页
     * http://localhost:8081/phjfwebht/api/v1/links/goLinksPage
     */
    @ActionKey("/phjfwebht/api/v1/links/goLinksPage")
    public void goLinksPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = linksService.queryLinksSearchList();
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
        this.render("/WEB-INF/velocity/webLinks/linksQuery.vm");
    }
    /**
     * 友情链接管理 - 友情链接分页查询
     */
    //http://localhost:8081/phjfwebht/api/v1/links/queryLinksByPage
    @ActionKey("/phjfwebht/api/v1/links/queryLinksByPage")
    public void queryLinksByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = this.getPara("language");
            String name = this.getPara("links_name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = linksService.queryLinksByPage(language, name, status,page);
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
     * 友情链接管理 - 跳转中文友情链接管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/links/goAddSimpLinksPage
    @ActionKey("/phjfwebht/api/v1/links/goAddSimpLinksPage")
    public void goAddSimpLinksPage() {
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
        this.renderJsp("/WEB-INF/velocity/webLinks/linksAdd.jsp");
    }
    /**
     * 友情链接管理 - 跳转维语友情链接管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/links/goAddUeyLinksPage
    @ActionKey("/phjfwebht/api/v1/links/goAddUeyLinksPage")
    public void goAddUeyLinksPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = linksService.findUeyLinksInfo(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/webLinks/linksAdd.jsp");
    }
    /**
     * 友情链接管理 - 新增友情链接
     */
    //http://localhost:8081/phjfwebht/api/v1/links/addLinks
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/links/addLinks")
    public void addLinks() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebLinksVO vo = this.getBean(WebLinksVO.class,"");
            AuthOperator opt = this.getUser();
            WebLinks webLinks = linksService.addLInks(opt,vo);
            Map<String,String> data = linksService.findLInksById(webLinks.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            linksService.generateLinks(webLinks.getLanguage());
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
            this.renderJsp("/WEB-INF/velocity/webLinks/linksAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webLinks/linksAdd.jsp");
        }
    }
    /**
     * 友情链接管理 - 跳转友情链接修改页
     */
    //http://localhost:8081/phjfwebht/api/v1/links/goModifyLinksPage
    @ActionKey("/phjfwebht/api/v1/links/goModifyLinksPage")
    public void goModifyLinksPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = linksService.findLInksById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webLinks/linksModify.jsp");
    }
    /**
     * 友情链接管理 - 修改友情链接
     */
    //http://localhost:8081/phjfwebht/api/v1/links/modifyLinks
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/links/modifyLinks")
    public void modifyLinks() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebLinksVO vo = this.getBean(WebLinksVO.class,"");
            AuthOperator opt = this.getUser();
            WebLinks model = linksService.modifyLinks(opt,vo);
            Map<String,String> data = linksService.findLInksById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
           linksService.generateLinks(model.getLanguage());
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
        this.renderJsp("/WEB-INF/velocity/webLinks/linksModify.jsp");
    }
    /**
     * 友情链接管理 - 删除友情链接
     * //http://localhost:8081/phjfwebht/api/v1/links/deleteLinks
     */
    @ActionKey("/phjfwebht/api/v1/links/deleteLinks")
    public void deleteLinks() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            linksService.deleteLinks(user,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            linksService.generateLinks(AdminConstants.LanguageType.ZH_SIMP.value);
            linksService.generateLinks(AdminConstants.LanguageType.ZH_UEY.value);
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
