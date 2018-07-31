package com.bankwel.phjf_admin.webapi;

import com.alibaba.druid.support.http.AbstractWebStatImpl;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.service.WebCmsService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.WebAboutUsVO;
import com.bankwel.phjf_admin.webapi.vo.WebAppDownloadInfoVO;
import com.bankwel.phjf_admin.webapi.vo.WebBannerVO;
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
 * Created by Administrator on 2018/1/11.
 */
public class WebCmsController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(WebCmsController.class);
    WebCmsService webCmsService = Duang.duang(WebCmsService.class);
    /**
     * 广告位管理 - 跳转广告位管理查询页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goBannerQueryPage
    @ActionKey("/phjfwebht/api/v1/webCms/goBannerQueryPage")
    public void goBannerQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = webCmsService.queryBannerSearchList();
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
        this.render("/WEB-INF/velocity/webCms/webBanner/webBannerQuery.vm");
    }

    /**
     * 广告位管理 - 广告位管理查询
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/queryBannerByPage
    @ActionKey("/phjfwebht/api/v1/webCms/queryBannerByPage")
    public void queryBannerByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String contents = this.getPara("contents");
            String language = this.getPara("language", AdminConstants.LanguageType.ZH_SIMP.value);
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = webCmsService.queryBannerByPage(contents,language,status, page);
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
     * 广告位管理 - 跳转广告位管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAddSimpBannerPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAddSimpBannerPage")
    public void goAddSimpBannerPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show=AdminConstants.LanguageType.ZH_SIMP.desc;
            String open_type = "http";
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",language_show,"open_type",open_type));
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
        this.renderJsp("/WEB-INF/velocity/webCms/webBanner/webBannerAdd.jsp");
    }

    /**
     * 广告位管理 - 跳转维语广告位管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAddUeyWebBannerPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAddUeyBannerPage")
    public void goAddUeyWebBannerPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = webCmsService.findUeyBanner(seq_uuid,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/webCms/webBanner/webBannerAdd.jsp");
    }

    /**
     * 广告位管理 - 广告位管理新增
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/addBanner
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webCms/addBanner")
    public void addBanner() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebBannerVO vo = this.getBean(WebBannerVO.class,"");
            AuthOperator opt = this.getUser();
            WebBanner webBanner = webCmsService.addBanner(opt,vo);
            Map<String,String> data = webCmsService.findBannerById(webBanner.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);

            webCmsService.generateBannerHtml(vo.getLanguage());

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
            this.renderJsp("/WEB-INF/velocity/webCms/webBanner/webBannerAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webCms/webBanner/webBannerAdd.jsp");
        }
    }

    /**
     * 广告位管理 - 跳转广告位管理修改页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goModifyBannerPage
    @ActionKey("/phjfwebht/api/v1/webCms/goModifyBannerPage")
    public void goModifyBannerPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = webCmsService.findBannerById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webCms/webBanner/webBannerModify.jsp");
    }

    /**
     * 广告位管理 - 广告位管理修改
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/modifyBanner
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webCms/modifyBanner")
    public void modifyBanner() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebBannerVO vo = this.getBean(WebBannerVO.class,"");
            AuthOperator opt = this.getUser();
            WebBanner model = webCmsService.modifyBanner(opt,vo);
            Map<String,String> data = webCmsService.findBannerById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            webCmsService.generateBannerHtml(vo.getLanguage());
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
        this.renderJsp("/WEB-INF/velocity/webCms/webBanner/webBannerModify.jsp");
    }

    /**
     * 广告位管理 - 失效
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/deleteBanner
    @ActionKey("/phjfwebht/api/v1/webCms/deleteBanner")
    public void deleteBanner() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            webCmsService.deleteBanner(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            webCmsService.generateBannerHtml(AdminConstants.ZH_SIMP);
            webCmsService.generateBannerHtml(AdminConstants.ZH_UEY);
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
     * 关于我们管理 - 跳转关于我们管理查询页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAboutUsQueryPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAboutUsQueryPage")
    public void goAboutUsQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = webCmsService.queryBannerSearchList();
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
        this.render("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsQuery.vm");
    }

    /**
     * 关于我们管理 - 关于我们管理查询
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/queryAboutUsByPage
    @ActionKey("/phjfwebht/api/v1/webCms/queryAboutUsByPage")
    public void queryAboutUsByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String contents = this.getPara("contents");
            String language = this.getPara("language", AdminConstants.LanguageType.ZH_SIMP.value);
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = webCmsService.queryAboutUsByPage(contents,language,status, page);
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
     * 关于我们管理 - 跳转关于我们管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAddSimpAboutUsPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAddSimpAboutUsPage")
    public void goAddSimpAboutUsPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show=AdminConstants.LanguageType.ZH_SIMP.desc;
            String open_type = "http";
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",language_show,"open_type",open_type));
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsAdd.jsp");
    }

    /**
     * 关于我们管理 - 跳转维语关于我们管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAddUeyWebAboutUsPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAddUeyAboutUsPage")
    public void goAddUeyWebAboutUsPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = webCmsService.findUeyAboutUs(seq_uuid,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsAdd.jsp");
    }

    /**
     * 关于我们管理 - 关于我们管理新增
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/addAboutUs
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webCms/addAboutUs")
    public void addAboutUs() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebAboutUsVO vo = this.getBean(WebAboutUsVO.class,"");
            AuthOperator opt = this.getUser();
            WebAboutUs webAboutUs = webCmsService.addAboutUs(opt,vo);
            Map<String,String> data = webCmsService.findAboutUsById(webAboutUs.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
//            webCmsService.generateAboutHtml(webAboutUs.getSeq_uuid(),webAboutUs.getLanguage());

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
            this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsAdd.jsp");
        }
    }

    /**
     * 关于我们管理 - 跳转关于我们管理修改页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goModifyAboutUsPage
    @ActionKey("/phjfwebht/api/v1/webCms/goModifyAboutUsPage")
    public void goModifyAboutUsPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = webCmsService.findAboutUsById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsModify.jsp");
    }

    /**
     * 关于我们管理 - 关于我们管理修改
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/modifyAboutUs
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webCms/modifyAboutUs")
    public void modifyAboutUs() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebAboutUsVO vo = this.getBean(WebAboutUsVO.class,"");
            AuthOperator opt = this.getUser();
            WebAboutUs model = webCmsService.modifyAboutUs(opt,vo);
            Map<String,String> data = webCmsService.findAboutUsById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
//            webCmsService.generateAboutHtml(model.getSeq_uuid(),model.getLanguage());
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsModify.jsp");
    }
    /**
     * 关于我们管理 - 跳转关于我们预览页
     */
    @ActionKey("/phjfwebht/api/v1/webCms/goAboutUsViewPage")
    public void goAboutUsViewPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = webCmsService.findAboutUsById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAboutUs/webAboutUsDetail.jsp");
    }
    /**
     * 关于我们管理 - 失效
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/deleteAboutUs
    @ActionKey("/phjfwebht/api/v1/webCms/deleteAboutUs")
    public void deleteAboutUs() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            webCmsService.deleteAboutUs(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
//            webCmsService.generateAboutHtml(seq_uuid,AdminConstants.ZH_SIMP);
//            webCmsService.generateAboutHtml(seq_uuid,AdminConstants.ZH_UEY);
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
     * App下载信息管理 - 跳转App下载信息管理查询页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAppQueryPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAppQueryPage")
    public void goAppQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = webCmsService.queryAppSearchList();
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
        this.render("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoQuery.vm");
    }

    /**
     * App下载信息管理 - App下载信息管理查询
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/queryAppByPage
    @ActionKey("/phjfwebht/api/v1/webCms/queryAppByPage")
    public void queryAppByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String user_type = this.getPara("user_type");
            String app_type = this.getPara("app_type");
            String language = this.getPara("language", AdminConstants.LanguageType.ZH_SIMP.value);
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = webCmsService.queryAppByPage(user_type,app_type,language,status, page);
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
     * App下载信息管理 - 跳转App下载信息管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAddSimpAppPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAddSimpAppPage")
    public void goAddSimpAppPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoAdd.jsp");
    }

    /**
     * App下载信息管理 - 跳转维语App下载信息管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goAddUeyWebAppPage
    @ActionKey("/phjfwebht/api/v1/webCms/goAddUeyAppPage")
    public void goAddUeyWebAppPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = webCmsService.findUeyApp(seq_uuid,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoAdd.jsp");
    }

    /**
     * App下载信息管理 - App下载信息管理新增
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/addApp
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webCms/addApp")
    public void addApp() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebAppDownloadInfoVO vo = this.getBean(WebAppDownloadInfoVO.class,"");
            AuthOperator opt = this.getUser();
            WebAppDownloadInfo webAppInfo = webCmsService.addApp(opt,vo);
            Map<String,String> data = webCmsService.findAppById(webAppInfo.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);

            webCmsService.generateAppHtml(vo.getLanguage());

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
            this.renderJsp("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoAdd.jsp");
        }
    }

    /**
     * App下载信息管理 - 跳转App下载信息管理修改页
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/goModifyAppPage
    @ActionKey("/phjfwebht/api/v1/webCms/goModifyAppPage")
    public void goModifyAppPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = webCmsService.findAppById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoModify.jsp");
    }

    /**
     * App下载信息管理 - App下载信息管理修改
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/modifyApp
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webCms/modifyApp")
    public void modifyApp() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebAppDownloadInfoVO vo = this.getBean(WebAppDownloadInfoVO.class,"");
            AuthOperator opt = this.getUser();
            WebAppDownloadInfo model = webCmsService.modifyApp(opt,vo);
            Map<String,String> data = webCmsService.findAppById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            webCmsService.generateAppHtml(vo.getLanguage());
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
        this.renderJsp("/WEB-INF/velocity/webCms/webAppInfo/webAppInfoModify.jsp");
    }

    /**
     * App下载信息管理 - 失效
     */
    //http://localhost:8081/phjfwebht/api/v1/webCms/deleteApp
    @ActionKey("/phjfwebht/api/v1/webCms/deleteApp")
    public void deleteApp() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            webCmsService.deleteApp(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            webCmsService.generateAppHtml(AdminConstants.LanguageType.ZH_SIMP.value);
            webCmsService.generateAppHtml(AdminConstants.LanguageType.ZH_UEY.value);
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
