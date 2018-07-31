package com.bankwel.phjf_admin.webapi;

import com.alibaba.fastjson.JSON;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;
import com.bankwel.phjf_admin.service.*;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/11/6.
 */
public class CommonAjaxController  extends PhjfAdminBaseController {

    private static final Logger log = LoggerFactory.getLogger(CommonAjaxController.class);
    private CitiesService citiesService = Duang.duang(CitiesService.class);
    private AreasService areasService = Duang.duang(AreasService.class);
    private ManagepointService mpService = Duang.duang(ManagepointService.class);
    private BankService bankService = Duang.duang(BankService.class);
    private NewsService newsService = Duang.duang(NewsService.class);
    private SystemService systemService = Duang.duang(SystemService.class);
    private WebNavService webNavService = Duang.duang(WebNavService.class);

    /**
     * 通过省份获取城市
     */
    @ActionKey("/phjfht/common/ajax/queryCitiesSelectData")
    public void queryCitiesSelectData(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String province_id = this.getPara("province_id");
            List<IDynamicSelectData> list = citiesService.querySelectData(province_id);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", list) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e,"data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }

    /**
     * 通过城市获取区
     */
    @ActionKey("/phjfht/common/ajax/queryAreasSelectData")
    public void queryAreasSelectData(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String city_id = this.getPara("city_id");
            List<IDynamicSelectData> list = areasService.querySelectData(city_id);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", list) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e,"data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }

    /**
     * 通过办理点代码获取办理点
     */
    @ActionKey("phjfht/common/ajax/getManagepoint")
    public void getManagepoint(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String mp_code = this.getPara("mp_code");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = mpService.findByCode(mp_code, language);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", map) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e, "data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }

    /**
     * 通过银行代码获取银行
     */
    @ActionKey("phjfht/common/ajax/getBank")
    public void getBank(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String bank_code = this.getPara("bank_code");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = bankService.findBankByCode(bank_code, language);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", map) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e, "data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }
    /**
     * 通过银行类型代码获取银行
     */
    @ActionKey("phjfht/common/ajax/getBankType")
    public void getBankType(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String bt_code = this.getPara("bt_code");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = bankService.findBankTypeByCode(bt_code, language);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", map) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e, "data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }

    /**
     * 通过新闻版块编号获取新闻版块
     */
    @ActionKey("phjfht/common/ajax/getNewsPlate")
    public void getNewsPlate(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String plate_code = this.getPara("plate_code");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = newsService.findNewsPlateByCode(plate_code, language);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", map) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e, "data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }
    /**
     * 通过app_id获取app
     */
    @ActionKey("phjfht/common/ajax/getApp")
    public void getApp(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String app_id = this.getPara("app_id");
            Map map = systemService.findAppByAppId(app_id);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", map) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e, "data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }
    /**
     * 通过新闻版块编号获取新闻版块
     */
    @ActionKey("phjfht/common/ajax/getAboutUsNav")
    public void getAboutUsNav(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            this.checkSensitiveMsg();
            String nav_code = this.getPara("nav_code");
            String language = AdminConstants.LanguageType.ZH_SIMP.value;
            Map map = webNavService.findAboutUsNav(nav_code, language);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", map) ;
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", "2", "msg", e, "data", this.getReqParameterMap());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }
}
