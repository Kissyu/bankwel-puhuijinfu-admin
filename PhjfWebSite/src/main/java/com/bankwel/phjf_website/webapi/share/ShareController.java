package com.bankwel.phjf_website.webapi.share;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.support.jfinal.BaseController;
import com.bankwel.phjf_website.service.AppInfoService;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by bankwel on 2018/4/2.
 */
public class ShareController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ShareController.class);
    private AppInfoService appInfoService = Duang.duang(AppInfoService.class);
    @ActionKey("/phjf/api/v1/page/share")
    public void appShare() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map returnMap = null;
        String app_code = this.getPara("app_code");
        String language = this.getPara("language");
        try {
            appInfoService.setShare(language,app_code);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    @ActionKey("/phjf/api/v1/page/setDownloadAppCount")
    public void setDownloadAppCount() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map returnMap = null;
        String app_code = this.getPara("app_code");
        String language = this.getPara("language");
        try {
            appInfoService.setDownloadAppCount(language,app_code);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage(),"data","");
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！","data","") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }
}
