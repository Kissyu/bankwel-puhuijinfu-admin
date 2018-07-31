package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.BannerInfo;
import com.bankwel.phjf_admin.service.CmsManageService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.BannerInfoVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/1.
 */
public class CmsMamageController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(CmsMamageController.class);
    CmsManageService cmsManageService = Duang.duang(CmsManageService.class);
    /**
     * 广告位管理 - 跳转广告位管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/cms/goBannerQueryPage
    @ActionKey("/phjfht/api/v1/cms/goBannerQueryPage")
    public void goBannerQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = cmsManageService.queryBannerSearchList();
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
        this.render("/WEB-INF/velocity/cms/bannerInfo/bannerInfoQuery.vm");
    }

    /**
     * 广告位管理 - 广告位管理查询
     */
    //http://localhost:8081/phjfht/api/v1/cms/queryBannerByPage
    @ActionKey("/phjfht/api/v1/cms/queryBannerByPage")
    public void queryBannerByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String location = this.getPara("location");
            String language = this.getPara("language",AdminConstants.LanguageType.ZH_SIMP.value);
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = cmsManageService.queryBannerByPage(location,language,status, page);
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
    //http://localhost:8081/phjfht/api/v1/cms/goAddSimpBannerPage
    @ActionKey("/phjfht/api/v1/cms/goAddSimpBannerPage")
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
        this.renderJsp("/WEB-INF/velocity/cms/bannerInfo/bannerInfoAdd.jsp");
    }

    /**
     * 广告位管理 - 跳转维语广告位管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/cms/goAddUeyBannerInfoPage
    @ActionKey("/phjfht/api/v1/cms/goAddUeyBannerPage")
    public void goAddUeyBannerInfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = cmsManageService.findUeyBanner(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/cms/bannerInfo/bannerInfoAdd.jsp");
    }

    /**
     * 广告位管理 - 广告位管理新增
     */
    //http://localhost:8081/phjfht/api/v1/cms/addBanner
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/cms/addBanner")
    public void addBanner() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BannerInfoVO vo = this.getBean(BannerInfoVO.class,"");
            AuthOperator opt = this.getUser();
            BannerInfo bannerInfo = cmsManageService.addBanner(opt,vo);
            Map<String,String> data = cmsManageService.findBannerById(bannerInfo.getSeq_uuid());
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
            this.renderJsp("/WEB-INF/velocity/cms/bannerInfo/bannerInfoAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/cms/bannerInfo/bannerInfoAdd.jsp");
        }
    }

    /**
     * 广告位管理 - 跳转广告位管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/cms/goModifyBannerPage
    @ActionKey("/phjfht/api/v1/cms/goModifyBannerPage")
    public void goModifyBannerPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = cmsManageService.findBannerById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/cms/bannerInfo/bannerInfoModify.jsp");
    }

    /**
     * 广告位管理 - 广告位管理修改
     */
    //http://localhost:8081/phjfht/api/v1/cms/modifyBanner
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/cms/modifyBanner")
    public void modifyBanner() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            BannerInfoVO vo = this.getBean(BannerInfoVO.class,"");
            AuthOperator opt = this.getUser();
            BannerInfo model = cmsManageService.modifyBanner(opt,vo);
            Map<String,String> data = cmsManageService.findBannerById(model.getSeq_uuid());
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
        this.renderJsp("/WEB-INF/velocity/cms/bannerInfo/bannerInfoModify.jsp");
    }

    /**
     * 广告位管理 - 失效
     */
    //http://localhost:8081/phjfht/api/v1/cms/deleteBanner
    @ActionKey("/phjfht/api/v1/cms/deleteBanner")
    public void deleteBanner() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            cmsManageService.deleteBanner(opt,seq_uuid);
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
