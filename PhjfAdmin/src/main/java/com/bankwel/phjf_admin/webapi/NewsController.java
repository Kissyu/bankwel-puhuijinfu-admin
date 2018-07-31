package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.NewsArticle;
import com.bankwel.phjf_admin.common.model.core.NewsPlate;
import com.bankwel.phjf_admin.service.NewsService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.NewsArticleVO;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by admin on 2017/10/23.
 */
public class NewsController extends PhjfAdminBaseController {

    private static final Logger log = LoggerFactory.getLogger(NewsController.class);
    NewsService newsService = Duang.duang(NewsService.class);

    //-----------------------新闻版块管理------------------------
    /**
     * 跳转新闻版块查询页
     */
    @ActionKey("/phjfht/api/v1/news/goNewsPlateQueryPage")
    public void goNewsPlateQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
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
        this.render("/WEB-INF/velocity/news/newsPlate/newsPlateQuery.vm");
    }

    /**
     * 新闻版块查询
     */
    @ActionKey("/phjfht/api/v1/news/newsPlateQuery")
    public void newsPlateQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
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
    @ActionKey("/phjfht/api/v1/news/goAddSimpNewsPlatePage")
    public void goAddSimpNewsPlatePage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
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
        this.renderJsp("/WEB-INF/velocity/news/newsPlate/newsPlateAdd.jsp");
    }

    /**
     * 跳转维语新闻版块新增页
     */
    @ActionKey("/phjfht/api/v1/news/goAddUeyNewsPlatePage")
    public void goAddUeyNewsPlatePage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
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
        this.renderJsp("/WEB-INF/velocity/news/newsPlate/newsPlateAdd.jsp");
    }

    /**
     * 新闻版块新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/news/addNewsPlate")
    public void addNewsPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            NewsPlateVO vo = this.getBean(NewsPlateVO.class,"");
            AuthOperator opt = this.getUser();
            NewsPlate newsPlate = newsService.addNewsPlate(opt,vo);
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
            this.renderJsp("/WEB-INF/velocity/news/newsPlate/newsPlateAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/news/newsPlate/newsPlateAdd.jsp");
        }
    }

    /**
     * 跳转新闻版块修改页
     */
    @ActionKey("/phjfht/api/v1/news/goModifyNewsPlatePage")
    public void goModifySimpNewsPlatePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
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
        this.renderJsp("/WEB-INF/velocity/news/newsPlate/newsPlateModify.jsp");
    }

    /**
     * 修改新闻版块
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/news/modifyNewsPlate")
    public void modifyNewsPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            NewsPlateVO vo = this.getBean(NewsPlateVO.class,"");
            AuthOperator opt = this.getUser();
            NewsPlate model = newsService.modifyNewsPlate(opt,vo);
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
        this.renderJsp("/WEB-INF/velocity/news/newsPlate/newsPlateModify.jsp");
    }

    /**
     * 新闻版块管理 - 失效
     */
    //http://localhost:8081/phjfht/api/v1/news/deleteNewsPlate
    @ActionKey("/phjfht/api/v1/news/deleteNewsPlate")
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
     */
    @ActionKey("/phjfht/api/v1/news/goNewsArticleQueryPage")
    public void goNewsArticleQueryPage(){
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
        this.render("/WEB-INF/velocity/news/newsArticle/newsArticleQuery.vm");
    }

    /**
     * 新闻文章管理 - 新闻文章查询
     */
    @ActionKey("/phjfht/api/v1/news/newsArticleQuery")
    public void newsArticleQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String title = this.getPara("title");
            String plate_name = this.getPara("plate_name");
            String publish_date = this.getPara("publish_date");
            String language = this.getPara("language");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = newsService.queryNewsArticleByPage(plate_name,title,publish_date,language,status, page);
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
    @ActionKey("/phjfht/api/v1/news/goAddSimpNewsArticlePage")
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
        this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleAdd.jsp");
    }

    /**
     * 新闻文章管理 - 跳转维语新闻文章新增页
     */
    @ActionKey("/phjfht/api/v1/news/goAddUeyNewsArticlePage")
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
        this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleAdd.jsp");
    }

    /**
     * 新闻文章管理 - 新闻文章新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/news/addNewsArticle")
    public void addNewsArticle(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            NewsArticleVO vo = this.getBean(NewsArticleVO.class,"");
            AuthOperator opt = this.getUser();
            NewsArticle newsArticle = newsService.addNewsArticle(opt,vo);
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
            this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleAdd.jsp");
        }
    }

    /**
     * 新闻文章管理 - 跳转新闻文章预览页
     */
    @ActionKey("/phjfht/api/v1/news/goNewsArticleViewPage")
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
        this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleDetail.jsp");
    }

    /**
     * 新闻文章管理 - 跳转新闻文章修改页
     */
    @ActionKey("/phjfht/api/v1/news/goModifyNewsArticlePage")
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
        this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleModify.jsp");
    }

    /**
     * 新闻文章管理 - 修改新闻文章
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/news/modifyNewsArticle")
    public void modifyNewsArticle(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            NewsArticleVO vo = this.getBean(NewsArticleVO.class,"");
            AuthOperator opt = this.getUser();
            NewsArticle model = newsService.modifyNewsArticle(opt,vo);
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
        this.renderJsp("/WEB-INF/velocity/news/newsArticle/newsArticleModify.jsp");
    }

    /**
     * 新闻文章管理 - 发布
     */
    //http://localhost:8081/phjfht/api/v1/news/publishArticle
    @ActionKey("/phjfht/api/v1/news/publishArticle")
    public void publishArticle() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            newsService.publishArticle(opt,seq_uuid);
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
     * 新闻文章管理 - 下架
     */
    //http://localhost:8081/phjfht/api/v1/news/unPublishArticle
    @ActionKey("/phjfht/api/v1/news/unPublishArticle")
    public void unPublishArticle() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            newsService.unPublishArticle(opt,seq_uuid);
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
}
