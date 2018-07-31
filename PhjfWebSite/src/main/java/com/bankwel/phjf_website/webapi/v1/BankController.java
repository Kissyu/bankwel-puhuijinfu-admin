package com.bankwel.phjf_website.webapi.v1;

import com.alibaba.fastjson.JSON;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.GeoHashKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.support.jfinal.BaseController;
import com.bankwel.phjf_website.common.model.website.BankAtm;
import com.bankwel.phjf_website.common.model.website.ManagepointInfo;
import com.bankwel.phjf_website.service.BankService;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BankController.class);
    private BankService bankService = Duang.duang(BankService.class);
    /**
     * 查询网点、ATM、助农网点信息
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBank
    @ActionKey("/phjf/bank/ajax/queryBank")
    public void queryBank() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            String bt_code = this.getPara("bt_code");
            String lat = this.getPara("lat");
            String lng = this.getPara("lng");
            String type = this.getPara("type");
            List<Map> bankInfoList = new ArrayList<Map>();
            List<Map> atmlist = new ArrayList<Map>();
            List<Map> managerPointList = new ArrayList<Map>();
            if(F.validate.isEmpty(type)){
                type = "0";
            }
            if (type.equals("0")||type.equals("1"))
                bankInfoList = bankService.queryBankInfoList(bt_code, lat, lng);
            if (type.equals("0")||type.equals("2"))
                atmlist = bankService.queryBankAtmList(bt_code, lat, lng);
            if (type.equals("0")||type.equals("3"))
                managerPointList = bankService.querymanagerPointList( lat, lng);
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data", F.transKit.asMap("bankAtmList", atmlist,"bankInfoList",bankInfoList,"managerPointList",managerPointList)) ;
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
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }

    /**
     * 查询网点、ATM、助农网点信息
     */
    //http://localhost:8081/phjfht/api/v1/bankinfo/queryBank
    @ActionKey("/phjf/bank/ajax/InitGeoHash")
    public void InitGeoHash() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            List<ManagepointInfo> list = ManagepointInfo.dao.queryAllManagepointInfo();
            for(ManagepointInfo model :list){
                String geoHash = model.getGeohash();
                if(null==geoHash||"".equals(geoHash)){
                    model.setGeohash(GeoHashKit.encode(model.getLat(),model.getLng()));
                    model.update();
                }
            }
            returnMap = F.transKit.asMap("code", "1", "msg", "", "data","") ;
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
        String jsonStr= JSON.toJSONString(returnMap) ;
        super.renderJson(jsonStr);
    }
}
