package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.common.model.core.SearchTerms;
import com.bankwel.phjf_admin.service.HuiminPolicyService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyArticleVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyPlateVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyPlateCityVO;
import com.bankwel.phjf_admin.webapi.vo.SearchTermVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @ClassName: HuiminPolicyController
 * @Description: 惠民政策管理
 * @author: DukeMr.Chen
 * @date: 2018/4/27 14:50
 * @version: V1.0
 *
 */
public class HuiminPolicyController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(HuiminPolicyController.class);
    HuiminPolicyService huiminPolicyService = Duang.duang(HuiminPolicyService.class);

    /**
     * @Title:
     * @Description: 添加维语版惠民政策
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goAddUeyPolicyArticlePage")
    public void goAddUeyPolicyArticlePage(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            String pa_id = this.getPara("pa_id");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = huiminPolicyService.findUeyPolicyArticle(pa_id,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", "2", "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyAdd.jsp");
    }

    /**
     * @Title:
     * @Description: 发布政策文章
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/publishArticle")
    public void publishArticle() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String pa_id = this.getPara("pa_id");
            AuthOperator opt = this.getUser();
            huiminPolicyService.publishOrUnArticle(opt, pa_id, "2");
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
     * @Title:
     * @Description: 下架惠民政策文章
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/unPublishArticle")
    public void unPublishArticle() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String pa_id = this.getPara("pa_id");
            AuthOperator opt = this.getUser();
            huiminPolicyService.publishOrUnArticle(opt, pa_id, "3");
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
     * @Title: 
     * @Description: 预览惠民政策文章
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goPolicyArticleViewPage")
    public void goPolicyArticleViewPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String pa_id = this.getPara("pa_id");
            Map data = huiminPolicyService.findPolicyArticleById(pa_id);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyDetail.jsp");
    }

    /**
     * @Title:
     * @Description: 修改惠民政策文章
     * @author: DukeMr.Chen
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/modifyPolicyArticle")
    public void modifyPolicyArticle(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            PolicyArticleVO vo = this.getBean(PolicyArticleVO.class,"");
            AuthOperator opt = this.getUser();
            PolicyArticle model = huiminPolicyService.modifyPolicyArticle(opt,vo);
            Map<String,String> data = huiminPolicyService.outputHuiminPolicyArticle(model);
            data.put("search_terms", vo.getSearch_terms());
            data.put("policy_plate", vo.getPolicy_plate());
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyModify.jsp");
    }

    /**
     * @Title:
     * @Description: 修改惠民政策 跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goModifyPolicyArticlePage")
    public void goModifyPolicyArticlePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String pa_id = this.getPara("pa_id");
            Map data = huiminPolicyService.findPolicyArticleById(pa_id);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyModify.jsp");
    }

    /**
     * @Title:
     * @Description: 添加惠民政策
     * @author: DukeMr.Chen
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/addHuiminPolicy")
    public void addHuiminPolicy(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            PolicyArticleVO vo = this.getBean(PolicyArticleVO.class,"");
            AuthOperator opt = this.getUser();
            PolicyArticle policyArticle = huiminPolicyService.addPolicyArticle(opt,vo);
            Map<String,String> data = huiminPolicyService.outputHuiminPolicyArticle(policyArticle);
            data.put("search_terms", vo.getSearch_terms());
            data.put("policy_plate", vo.getPolicy_plate());

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
            this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyAdd.jsp");
        }
    }

    /**
     * @Title:
     * @Description: 添加跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goAddSimpHuiminPolicyPage")
    public void goAddSimpHuiminPolicyPage(){
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyAdd.jsp");
    }

    /**
     * @Title:
     * @Description: 惠民政策的数据查询
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/huiminPolicyQuery")
    public void huiminPolicyQuery(){
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
            resultMap = huiminPolicyService.queryHuiminPolicyByPage(plate_name,title,publish_date,language,status, page);
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
     * @Title:
     * @Description: 惠民政策管理页面跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goHuiminPolicyQueryPage")
    public void goHuiminPolicyQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = huiminPolicyService.queryHuiminPolicySearchList();
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
        this.render("/WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyQuery.vm");
    }
    //-----------------------惠民政策版块管理------------------------
    /**
     * @Title:
     * @Description: 惠民政策版块管理页面跳转
     */
    @ActionKey("/phjfht/api/v1/policy/goPolicyPlateQueryPage")
    public void goPolicyPlateQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = huiminPolicyService.queryHuiminPolicyPlateSearchList();
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
        this.render("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateQuery.vm");
    }
    /**
     * 惠民政策版块管理查询
     */
    @ActionKey("/phjfht/api/v1/policy/huiminPolicyPlateQuery")
    public void huiminPolicyPlateQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String name = this.getPara("name");
            String language = this.getPara("language");
            String status = this.getPara("status");
            String province = this.getPara("province");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = huiminPolicyService.queryPolicyPlateByPage(name,language,status,province, page);
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
     * 跳转中文惠民政策版块新增页
     */
    @ActionKey("/phjfht/api/v1/policy/goAddSimpPolicyPlatePage")
    public void goAddSimpPolicyPlatePage(){
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAdd.jsp");
    }
    /**
     * 跳转维语惠民政策版块新增页
     */
    @ActionKey("/phjfht/api/v1/policy/goAddUeyPolicyPlatePage")
    public void goAddUeyPolicyPlatePage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = huiminPolicyService.findUeyPolicyPlate(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAdd.jsp");
    }
    /**
     * 惠民政策版块新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/addPolicyPlate")
    public void addPolicyPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            PolicyPlateVO vo = this.getBean(PolicyPlateVO.class,"");
            AuthOperator opt = this.getUser();
            PolicyPlate policyPlate = huiminPolicyService.addPolicyPlate(opt,vo);
            Map<String,String> data = huiminPolicyService.findPolicyPlateById(policyPlate.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAdd.jsp");
        }
    }
    /**
     * 跳转惠民政策版块修改页
     */
    @ActionKey("/phjfht/api/v1/policy/goModifyPolicyPlatePage")
    public void goModifyPolicyPlatePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            Map data = huiminPolicyService.findPolicyPlateById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateModify.jsp");
    }
    /**
     * 修改惠民政策版块
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/modifyPolicyPlate")
    public void modifyPolicyPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            PolicyPlateVO vo = this.getBean(PolicyPlateVO.class,"");
            AuthOperator opt = this.getUser();
            PolicyPlate model = huiminPolicyService.modifyPolicyPlate(opt,vo);
            Map<String,String> data = huiminPolicyService.findPolicyPlateById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateModify.jsp");
    }
    /**
     * 惠民政策版块管理 - 失效
     */
    @ActionKey("/phjfht/api/v1/policy/deletePolicyPlate")
    public void deletePolicyPlate() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            huiminPolicyService.deletePolicyPlate(user,seq_uuid);
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
     * 跳转惠民政策版块分配页
     */
    @ActionKey("/phjfht/api/v1/policy/goAllocationPolicyPlatePage")
    public void goAllocationPolicyPlatePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String seq_uuid = this.getPara("seq_uuid");
            Map data = huiminPolicyService.findPolicyPlateById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAllocation.jsp");
    }
    /**
     * 分配惠民政策版块
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/allocationPolicyPlate")
    public void allocationPolicyPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            PolicyPlateCityVO vo = this.getBean(PolicyPlateCityVO.class,"");
            AuthOperator opt = this.getUser();
            PolicyPlateCity model = huiminPolicyService.allocationPolicyPlate(opt,vo);
            Map<String,String> data = huiminPolicyService.findPolicyPlateCityById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAllocation.jsp");
    }
    /**
     * 跳转惠民政策版块分配修改页
     */
    @ActionKey("/phjfht/api/v1/policy/goModifyAllocationPolicyPlatePage")
    public void goModifyAllocationPolicyPlatePage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String plate_code = this.getPara("plate_code");
            String province_id = this.getPara("province_id");
            Map data = huiminPolicyService.findPolicyPlateCityByCode(plate_code,province_id);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAllocationModify.jsp");
    }
    /**
     * 修改惠民政策版块分配
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/modifyAllocationPolicyPlate")
    public void modifyAllocationPolicyPlate(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            PolicyPlateCityVO vo = this.getBean(PolicyPlateCityVO.class,"");
            AuthOperator opt = this.getUser();
            PolicyPlateCity model = huiminPolicyService.modifyPolicyPlateCity(opt,vo);
            Map<String,String> data = huiminPolicyService.findPolicyPlateCityByCode(model.getPlate_code(),model.getProvince_id());
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/policyPlate/huiminPolicyPlateAllocationModify.jsp");
    }
    /**
     * @Title:
     * @Description: 惠民政搜索条管理跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goPolicySearchQueryPage")
    public void goPolicySearchQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = huiminPolicyService.querySearchTermList();
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
        this.render("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermQuery.vm");
    }
    /**
     * @Title:
     * @Description: 惠民政策的数据查询
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/policySearchTermQuery")
    public void policySearchTermQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name");
            String type = this.getPara("type");
            String language = this.getPara("language");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = huiminPolicyService.queryPolicySearchTermByPage(name,type,language,status, page);
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
     * @Title:
     * @Description: 跳转添加中文搜索条件
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goAddSimpSearchTermPage")
    public void goAddSimpSearchTermPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String parent_st_id = this.getPara("parent_st_id");
            String type = this.getPara("type");
            String parent_st_name = this.getPara("parent_st_name");
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String languageDesc=AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",languageDesc,"parent_st_id",parent_st_id,"type",type,"parent_st_name",parent_st_name));
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermAdd.jsp");
    }
    /**
     * @Title:
     * @Description: 添加搜索条件
     * @author: xiooyuhu
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/addSearchTerm")
    public void addSearchTerm(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            SearchTermVO vo = this.getBean(SearchTermVO.class,"");
            AuthOperator opt = this.getUser();
            SearchTerms searchTerms = huiminPolicyService.addSearchTerm(opt,vo);
            Map<String,String> data = huiminPolicyService.outputSearchTerm(searchTerms);
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
            this.renderJsp("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermAdd.jsp");
        }
    }

    /**
     * @Title:
     * @Description: 添加维语版惠民政策
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goAddUeyPolicySearchTermPage")
    public void goAddUeyPolicySearchTermPage(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            String st_id = this.getPara("st_id");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = huiminPolicyService.findUeySearchTerm(st_id,language);
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", "2", "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        this.setAttrs(resultMap);
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermAdd.jsp");
    }
    /**
     * @Title:
     * @Description: 修改搜索条件 跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/goModifySearchTermPage")
    public void goModifySearchTermPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String st_id = this.getPara("st_id");
            Map data = huiminPolicyService.findSearchTermById(st_id);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermModify.jsp");
    }
    /**
     * @Title:
     * @Description: 修改搜索条件
     * @author: DukeMr.Chen
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/policy/modifySearchTerm")
    public void modifySearchTerm(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            SearchTermVO vo = this.getBean(SearchTermVO.class,"");
            AuthOperator opt = this.getUser();
            SearchTerms model = huiminPolicyService.modifySearchTerm(opt,vo);
            Map<String,String> data = huiminPolicyService.outputSearchTerm(model);
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
        this.renderJsp("/WEB-INF/velocity/huiminPolicy/searchTerm/searchTermModify.jsp");
    }

    /**
     * @Title:
     * @Description: 失效搜索条件
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/policy/unPublishSearchTerm")
    public void unPublishSearchTerm() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            huiminPolicyService.unPublishSearchTerm(opt, seq_uuid);
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
