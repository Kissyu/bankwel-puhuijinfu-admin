package com.bankwel.phjf_website.webapi.content;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.DeviceGeoPageModel;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.support.jfinal.BaseController;
import com.bankwel.framework.support.jfinal.ControllerBind;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by dingbs on 2017/2/28.
 */
//@ControllerBind(key = "/")
public class NewsController extends BaseController {

    private  static Logger logger = LoggerFactory.getLogger(NewsController.class);

   /* @ActionKey("/disclaimer")
    public void disclaimer(){
        wrapperData();
        render("/velocity/about/disclaimer.vm");
    }
    @ActionKey("/joinUs")
    public void joinUs(){
        wrapperData();
        render("/velocity/about/joinUs.vm");
    }
    @ActionKey("/aboutUs")
    public void aboutUs(){
        wrapperData();
        render("/velocity/about/companyIntro.vm");
    }

    @ActionKey("/companyIntro")
    public void companyIntro(){

        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();
        Map map;
        try {
            *//*Map result = CmsCompanyCultureService.queryCompanyCulture(language,uri);
            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
            result.putAll(topLeftNavs);*//*
            Map result = F.transKit.asMap("currentPage","companyIntro");
//            result.put("currentPage","companyIntro");
            map = F.transKit.asMap("code", 1, "msg", "", "data",result);
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        render("/velocity/about/companyIntro.vm");
    }

    @ActionKey("/team")
    public void team(){
        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();
        Map map;
        try {
           *//* Map result = CmsManagerService.queryCmsManager(language,uri);
            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
            result.putAll(topLeftNavs);*//*
            Map result = F.transKit.asMap("currentPage","team");
            map = F.transKit.asMap("code", 1, "msg", "", "data",result);
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        render("/velocity/about/team.vm");

    }
    private void wrapperData() {
        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();

        Map map;
        try {
            //对应栏目的信息
//            Map result = CmsContentService.queryCmsContent(language,uri);
//            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
//
//            result.putAll(topLeftNavs);

            map = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");

        } catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;

        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());

        this.setAttrs(map);
        render("/velocity/about/team.vm");
    }

    @ActionKey("/businessIntro")
    public void bussinessIntro(){
        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();
        Map map=null;
        try {
           *//* Map result = CmsManagerService.queryCmsManager(language,uri);
            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
            result.putAll(topLeftNavs);*//*
            Map result = F.transKit.asMap("currentPage","businessIntro");
            map = F.transKit.asMap("code", 1, "msg", "", "data",result);
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        render("/velocity/about/businessIntro.vm");
    }
    @ActionKey("/companyCulture")
    public void companyCulture(){
        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();
        Map map=null;
        try {
           *//* Map result = CmsManagerService.queryCmsManager(language,uri);
            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
            result.putAll(topLeftNavs);*//*
            Map result = F.transKit.asMap("currentPage","companyCulture");
            map = F.transKit.asMap("code", 1, "msg", "", "data",result);
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        render("/velocity/about/companyCulture.vm");
    }
    @ActionKey("/contactUs")
    public void contactUs(){
        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();
        Map map=null;
        try {
           *//* Map result = CmsManagerService.queryCmsManager(language,uri);
            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
            result.putAll(topLeftNavs);*//*
            Map result = F.transKit.asMap("currentPage","contactUs");
            map = F.transKit.asMap("code", 1, "msg", "", "data",result);
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        render("/velocity/about/contactUs.vm");
    }
    @ActionKey("/loanBusiness")
    public void loanBusiness(){
        logger.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        DeviceGeoPageModel dg = this.getDeviceAndGeo();
        String uri = this.getRequest().getRequestURI();
        String language = dg.getLanguage();
        Map map=null;
        try {
           *//* Map result = CmsManagerService.queryCmsManager(language,uri);
            Map topLeftNavs = CmsNavService.queryTopLeftCmsNavs(language,uri);
            result.putAll(topLeftNavs);*//*
            Map result = F.transKit.asMap("currentPage","loanBusiness");
            map = F.transKit.asMap("code", 1, "msg", "", "data",result);
        } catch (MsgBusinessException e) {
            logger.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            logger.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        render("/velocity/showBoards/loanBusiness.vm");
    }*/
}
