package com.bankwel.phjf_website.webapi.v1;

import com.alibaba.fastjson.JSON;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.GeoHashKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.support.jfinal.BaseController;
import com.bankwel.phjf_website.service.BankService;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/12/2.
 */
public class CommonAjaxController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(CommonAjaxController.class);

    BankService bankService = Duang.duang(BankService.class);

    /**
     * 获取银行信息
     */
    @ActionKey("/phjf/common/ajax/queryBank")
    public void queryBank(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            String bt_code = this.getPara("bt_code");
            String lat = this.getPara("lat");
            String lng = this.getPara("lng");
            List<Map> list = bankService.queryBankList(bt_code, lat, lng);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", F.transKit.asMap("bankList", list)) ;
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
     * 获取银行信息
     */
    @ActionKey("/phjf/common/ajax/queryBankAtm")
    public void queryBankAtm(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            String bt_code = this.getPara("bt_code");
            String lat = this.getPara("lat");
            String lng = this.getPara("lng");
            List<Map> list = bankService.queryBankAtmList(bt_code, lat, lng);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", F.transKit.asMap("bankAtmList", list)) ;
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
     * 获取银行信息
     */
    @ActionKey("/phjf/common/ajax/getGeoHash")
    public void getGeoHash(){
        Map params = this.getReqParameterMap();
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        Map returnMap;
        try {
            String accuracy = this.getPara("accuracy");
            String lat = this.getPara("lat");
            String lng = this.getPara("lng");
            String geoHashValue = GeoHashKit.encode(Double.parseDouble(lat),Double.parseDouble(lng),Integer.parseInt(accuracy));
            //List<Map> list =
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", geoHashValue) ;
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
