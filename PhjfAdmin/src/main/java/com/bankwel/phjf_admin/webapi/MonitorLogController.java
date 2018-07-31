package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.service.MonitorLogService;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.SysMonitorBuslogVO;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/** 
 * @ClassName: MonitorLogController 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author: DukeMr.Chen
 * @date: 2018/5/17 9:16
 * @version: V1.0 
 * 
 */
public class MonitorLogController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(MonitorLogController.class);
    MonitorLogService monitorLogService = Duang.duang(MonitorLogService.class);


    @ActionKey("/phjfht/api/v1/monitor/monitorLogQuery")
    public void monitorLogQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            SysMonitorBuslogVO vo = this.getBean(SysMonitorBuslogVO.class,"");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = monitorLogService.queryMonitorLogByPage(vo, page);
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
     * @Description:
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/monitor/goMonitorLogQueryPage")
    public void goMonitorLogQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            List<SysDatalibrary> systemCodeList = SysDatalibrary.dao.querySysDatalibrary("Phjf","system_code");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap( "systemCodeList", systemCodeList));
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
        this.render("/WEB-INF/velocity/monitorLog/monitorLogQuery.vm");
    }

    private static boolean ALLOWED_ENTER = true;
    /**
     * @Title:
     * @Description: 刷新业务字段的值, 不对外提供
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/monitor/reflushLogBusField")
    public void reflushLogBusField(){
        Map resultMap = null;
        try {
            if(ALLOWED_ENTER){
                ALLOWED_ENTER = false;
                monitorLogService.reflushLogBusField();
                ALLOWED_ENTER = true;
                resultMap = F.transKit.asMap("code", 0, "msg", "业务字段刷新完成!", "data", "");
            }else{
                resultMap = F.transKit.asMap("code", 2, "msg", "业务字段刷新已在进行中，请稍后！", "data", "");
            }
        } catch (Exception e) {
            resultMap = F.transKit.asMap("code", -1, "msg", "很抱歉！操作失败，请检查网络是否通畅！");
        }
        this.renderJson(resultMap);
    }

    /**
     * 进入刷新业务字段的值,刷新页面
     * @return
     */
    @ActionKey("/phjfht/api/v1/monitor/goReflushLogBusFieldPage")
    public void refreshProcessMain(){
        this.render("/WEB-INF/velocity/monitorLog/reflushLogBusFieldPage.vm");
    }

    /**
     * @Title:
     * @Description: 报表首页
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/monitor/goReportPortalPage")
    public void goReportPortalPage(){
        this.render("/WEB-INF/velocity/monitorLog/reportPortalPage.vm");
    }

    /**
     * @Title:
     * @Description: 报表统计
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/monitor/reportQuery")
    public void reportQuery(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String reportWay = this.getPara("reportWay","");
            String timePeriod = this.getPara("timePeriod","");
            resultMap = monitorLogService.reportQuery(reportWay, timePeriod);
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
     * @Description: 查询统计明细
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/monitor/queryMonitorBuslogListByPage")
    public void queryMonitorBuslogListByPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String reportWay = this.getPara("reportWay", "");
            String startTime = this.getPara("startTime", "");
            String reportSubject = this.getPara("reportSubject", "");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = monitorLogService.queryMonitorBuslogListByPage(reportWay, startTime, reportSubject, page);
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

    @ActionKey("/phjfht/api/v1/monitor/goQueryLogListPage")
    public void goQueryLogListPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            String reportWay = this.getPara("reportWay", "");
            String startTime = this.getPara("startTime", "");
            String reportSubject = this.getPara("reportSubject", "");
            resultMap = F.transKit.asMap("reportWay", reportWay, "startTime", startTime, "reportSubject", reportSubject);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.render("/WEB-INF/velocity/monitorLog/reportPortalDetailPage.vm");
    }

}
