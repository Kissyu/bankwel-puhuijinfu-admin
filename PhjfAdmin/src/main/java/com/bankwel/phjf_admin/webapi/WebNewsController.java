package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.service.NewsService;
import com.bankwel.phjf_admin.service.WebNewsService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.NewsArticleVO;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.bankwel.phjf_admin.webapi.vo.WebNewsInfoVO;
import com.bankwel.phjf_admin.webapi.vo.WebNewsPlateVO;
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
 * Created by admin on 2017/10/23.
 */
public class WebNewsController extends PhjfAdminBaseController {

    private static final Logger log = LoggerFactory.getLogger(WebNewsController.class);
    WebNewsService newsService = Duang.duang(WebNewsService.class);

    //-----------------------新闻版块管理------------------------
    /**
     * 跳转新闻版块查询页
     * phjfwebht/api/v1/webNews/goNewsPlatesQueryPage
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goNewsPlatesQueryPage")
    public void goNewsPlatesQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = newsService.queryNewsPlateSearchList();
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
        this.render("/WEB-INF/velocity/webNews/newsPlate/newsPlateQuery.vm");
    }

    /**
     * 新闻版块查询
     */
    @ActionKey("/phjfwebht/api/v1/webNews/queryNewsPlateByPage")
    public void queryNewsPlateByPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name");
            String language = this.getPara("language");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = newsService.queryNewsPlateByPage(name,language,status, page);
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
     * 跳转中文新闻版块新增页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goAddSimpNewsPlatePage")
    public void goAddSimpNewsPlatePage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String languageDesc=AdminConstants.LanguageType.ZH_SIMP.desc;
            Map data = newsService.queryTypeAndLocationList(language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",languageDesc,"showData",data));
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsPlate/newsPlateAdd.jsp");
    }

    /**
     * 跳转维语新闻版块新增页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goAddUeyNewsPlatePage")
    public void goAddUeyNewsPlatePage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = newsService.findUeyNewsPlate(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsPlate/newsPlateAdd.jsp");
    }

    /**
     * 新闻版块新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webNews/addNewsPlate")
    public void addNewsPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebNewsPlateVO vo = this.getBean(WebNewsPlateVO.class,"");
            AuthOperator opt = this.getUser();
            WebNewsPlate newsPlate = newsService.addNewsPlate(opt,vo);
            Map<String,String> data = newsService.findNewsPlateById(newsPlate.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/webNews/newsPlate/newsPlateAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webNews/newsPlate/newsPlateAdd.jsp");
        }
    }

    /**
     * 跳转新闻版块修改页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goModifyNewsPlatePage")
    public void goModifySimpNewsPlatePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = newsService.findNewsPlateById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsPlate/newsPlateModify.jsp");
    }

    /**
     * 修改新闻版块
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webNews/modifyNewsPlate")
    public void modifyNewsPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebNewsPlateVO vo = this.getBean(WebNewsPlateVO.class,"");
            AuthOperator opt = this.getUser();
            WebNewsPlate model = newsService.modifyNewsPlate(opt,vo);
            Map<String,String> data = newsService.findNewsPlateById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsPlate/newsPlateModify.jsp");
    }

    /**
     * 新闻版块管理 - 失效
     */
    //http://localhost:8081/phjfwebht/api/v1/webNews/deleteNewsPlate
    @ActionKey("/phjfwebht/api/v1/webNews/deleteNewsPlate")
    public void deleteNewsPlate() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            newsService.deleteNewsPlate(user,seq_uuid);
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

    //-----------------------新闻文章管理------------------------
    /**
     * 新闻文章管理 - 跳转新闻文章查询页
     * /phjfwebht/api/v1/webNews/goNewsInfoQueryPage
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goNewsInfoQueryPage")
    public void goNewsInfoQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = newsService.queryNewsArticleSearchList();
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
        this.render("/WEB-INF/velocity/webNews/newsArticle/newsArticleQuery.vm");
    }

    /**
     * 新闻文章管理 - 新闻文章查询
     */
    @ActionKey("/phjfwebht/api/v1/webNews/newsArticleQuery")
    public void newsArticleQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String title = this.getPara("title");
            String np_name = this.getPara("np_name");
            String publish_date = this.getPara("publish_date");
            String language = this.getPara("language");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = newsService.queryNewsArticleByPage(np_name,title,publish_date,language,status, page);
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
     * 新闻文章管理 - 跳转中文新闻文章新增页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goAddSimpNewsArticlePage")
    public void goAddSimpNewsArticlePage(){
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleAdd.jsp");
    }

    /**
     * 新闻文章管理 - 跳转维语新闻文章新增页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goAddUeyNewsArticlePage")
    public void goAddUeyNewsArticlePage(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = newsService.findUeyNewsArticle(seq_uuid,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", "2", "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleAdd.jsp");
    }

    /**
     * 新闻文章管理 - 新闻文章新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webNews/addNewsArticle")
    public void addNewsArticle(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebNewsInfoVO vo = this.getBean(WebNewsInfoVO.class,"");
            AuthOperator opt = this.getUser();
            WebNewsInfo newsArticle = newsService.addNewsArticle(opt,vo);
            Map<String,String> data = newsService.findNewsArticleById(newsArticle.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleAdd.jsp");
        }
    }

    /**
     * 新闻文章管理 - 跳转新闻文章预览页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goNewsArticleViewPage")
    public void goNewsArticleViewPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = newsService.findNewsArticleById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleDetail.jsp");
    }

    /**
     * 新闻文章管理 - 跳转新闻文章修改页
     */
    @ActionKey("/phjfwebht/api/v1/webNews/goModifyNewsArticlePage")
    public void goModifySimpNewsArticlePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = newsService.findNewsArticleById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleModify.jsp");
    }

    /**
     * 新闻文章管理 - 修改新闻文章
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/webNews/modifyNewsArticle")
    public void modifyNewsArticle(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebNewsInfoVO vo = this.getBean(WebNewsInfoVO.class,"");
            AuthOperator opt = this.getUser();
            WebNewsInfo model = newsService.modifyNewsArticle(opt,vo);
            Map<String,String> data = newsService.findNewsArticleById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/webNews/newsArticle/newsArticleModify.jsp");
    }

    /**
     * 新闻文章管理 - 发布
     */
    //http://localhost:8081/phjfht/api/v1/news/publishArticle
    @ActionKey("/phjfwebht/api/v1/webNews/publishArticle")
    public void publishArticle() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            Map data = newsService.publishArticle(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            newsService.generateNews(data.get("type").toString(),data.get("is_recom").toString(),data.get("language").toString(),data.get("np_code").toString(),data.get("ni_code").toString(),"publish");
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
     * 新闻文章管理 - 下架
     */
    //http://localhost:8081/phjfht/api/v1/news/unPublishArticle
    @ActionKey("/phjfwebht/api/v1/webNews/unPublishArticle")
    public void unPublishArticle() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            Map data = newsService.unPublishArticle(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            newsService.generateNews(data.get("type").toString(),data.get("is_recom").toString(),data.get("language").toString(),data.get("np_code").toString(),data.get("ni_code").toString(),"unpublish");
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
