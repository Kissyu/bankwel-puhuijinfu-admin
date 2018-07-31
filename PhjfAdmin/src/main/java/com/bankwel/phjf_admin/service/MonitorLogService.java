package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.DateKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.support.MyMBusLogLoader;
import com.bankwel.phjf_admin.webapi.vo.SysMonitorBuslogVO;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: MonitorLogService
 * @Description: (这里用一句话描述这个类的作用) 
 * @author: DukeMr.Chen
 * @date: 2018/5/16 17:44
 * @version: V1.0 
 * 
 */
public class MonitorLogService {
    private SystemService sysService = Duang.duang(SystemService.class);

    /**
     * @Title:
     * @Description: 查询惠民政策文章 分页
     * @author: DukeMr.Chen
     */
    public Map queryMonitorLogByPage(SysMonitorBuslogVO vo, PageKit page) {
        Pair<List<Record>,PageKit<Record>> pair  =  SysMonitorBuslog.dao.queryMonitorLogIdByPage(vo, page);
        List<Record> idList = pair.getLeft();
        List<Map> processList = new ArrayList<Map>();
        for(Record r : idList){
            SysMonitorBuslog sysMonitorBuslog = SysMonitorBuslog.dao.findBySeqId(""+r.get("seq_id"));
            processList.add(F.transKit.asMap(
                    "seq_id", sysMonitorBuslog.getSeq_id()
                    , "sys_code", sysMonitorBuslog.getSys_code()
                    , "sys_code_show", sysMonitorBuslog.getSysCodeShow()
                    , "sub_sys_code", sysMonitorBuslog.getSub_sys_code()
                    , "user_id", sysMonitorBuslog.getUser_id()
                    , "clientip", sysMonitorBuslog.getClientip()
                    , "client_type", sysMonitorBuslog.getClient_type()
                    , "url", sysMonitorBuslog.getUrl()
                    , "protocol", sysMonitorBuslog.getProtocol()
                    , "bus_path", sysMonitorBuslog.getBus_path()
                    , "bus_method", sysMonitorBuslog.getBus_method()
                    , "bus_target", sysMonitorBuslog.getBus_target()
                    , "bus_content", sysMonitorBuslog.getBus_content()
                    , "useragent", sysMonitorBuslog.getUseragent()
                    , "querystring", sysMonitorBuslog.getQuerystring()
                    , "bus_id", sysMonitorBuslog.getBus_id()
                    , "starttime", sysMonitorBuslog.getStarttimeShow()
                    , "endtime", sysMonitorBuslog.getEndtimeShow()
            ));
        }
        return F.transKit.asMap("rows", processList, "total", pair.getRight().getTotal());
    }

    /**
     * @Title:
     * @Description: 刷新业务字段
     * @author: DukeMr.Chen
     */
    public void reflushLogBusField() {
        //查询业务业务日志
        int needReflushCount = SysMonitorBuslog.dao.findCountNeedReflush();
        int batch = needReflushCount / 1000 ;
        //业务处理
        long pos = 0L;
        for (int i = 0; i <= batch; i++){
            List<SysMonitorBuslog> list =  SysMonitorBuslog.dao.findEmptyLog(pos);
            List<SysMonitorBuslog> updateList = new ArrayList<SysMonitorBuslog>();
            if(F.validate.isNotEmpty(list)){
                pos = list.get(list.size()-1).getSeq_id();
            }else{
                continue;
            }
            for (SysMonitorBuslog obj : list) {
                String actionKey = sysService.getActionKey(obj.getUrl());
                if("/".equals(actionKey) || "".equals(actionKey)){
                    continue;
                }
                Map<String, String> busMap = sysService.getBusMap(actionKey);
                if(F.validate.isNotEmpty(busMap)){
                    obj.setBus_path(busMap.get("BusPath"));
                    obj.setBus_method(busMap.get("BusMethod"));
                    obj.setBus_target(busMap.get("BusTarget"));
                    obj.setBus_content(busMap.get("BusContent"));
                    obj.setIscomplete("1");
                    updateList.add(obj);
                }
            }

            //批量更新
            if(F.validate.isNotEmpty(updateList)){
                SysMonitorBuslog.dao.updateBatch(updateList);
            }
        }
    }

    /**
     * @Title:
     * @Description: 报表统计
     * @author: DukeMr.Chen
     */
    public Map reportQuery(String reportWay, String timePeriod) {
        //字段验证
        if(StringUtils.isBlank(reportWay)){
            throw new MsgBusinessException("请选择报表系列");
        }
        if(StringUtils.isBlank(timePeriod)){
            throw new MsgBusinessException("请选择统计周期");
        }
        //处理时间维度
        Calendar today = Calendar.getInstance();
        if("week".equals(timePeriod)){
            today.add(Calendar.DAY_OF_YEAR, -7);
        }else if("month".equals(timePeriod)){
            today.add(Calendar.DAY_OF_YEAR, -30);
        }else if("season".equals(timePeriod)){
            today.add(Calendar.DAY_OF_YEAR, -90);
        }
        String startTime = F.dateKit.translate2Str(today.getTime(), DateKit.PATTERN_Day);
        //统计业务
        List<Record>  rl=  SysMonitorBuslog.dao.reportQuery(reportWay, startTime);
        List<Map> processList = new ArrayList<Map>();
        for(Record r : rl){
            processList.add(F.transKit.asMap(
                    "reportWay", reportWay
                    , "reportSubject", r.get("reportSubject")
                    , "reportNum", r.get("reportNum")
                    , "startTime", startTime
                    , "action", "action"
            ));
        }
        return F.transKit.asMap("rows", processList, "total", rl.size());
    }

    /**
     * @Title:
     * @Description: 查询统计明细
     * @author: DukeMr.Chen
     */
    public Map queryMonitorBuslogListByPage(String reportWay, String startTime, String reportSubject, PageKit page) {
        Pair<List<Record>,PageKit<Record>> pair  =  SysMonitorBuslog.dao.queryMonitorBuslogList(reportWay, startTime, reportSubject, page);
        List<Record> idList = pair.getLeft();
        List<Map> processList = new ArrayList<Map>();
        for(Record r : idList){
            SysMonitorBuslog sysMonitorBuslog = SysMonitorBuslog.dao.findBySeqId(""+r.get("seq_id"));
            processList.add(F.transKit.asMap(
                    "seq_id", sysMonitorBuslog.getSeq_id()
                    , "sys_code", sysMonitorBuslog.getSys_code()
                    , "sys_code_show", sysMonitorBuslog.getSysCodeShow()
                    , "sub_sys_code", sysMonitorBuslog.getSub_sys_code()
                    , "user_id", sysMonitorBuslog.getUser_id()
                    , "clientip", sysMonitorBuslog.getClientip()
                    , "client_type", sysMonitorBuslog.getClient_type()
                    , "url", sysMonitorBuslog.getUrl()
                    , "protocol", sysMonitorBuslog.getProtocol()
                    , "bus_path", sysMonitorBuslog.getBus_path()
                    , "bus_method", sysMonitorBuslog.getBus_method()
                    , "bus_target", sysMonitorBuslog.getBus_target()
                    , "bus_content", sysMonitorBuslog.getBus_content()
                    , "useragent", sysMonitorBuslog.getUseragent()
                    , "querystring", sysMonitorBuslog.getQuerystring()
                    , "bus_id", sysMonitorBuslog.getBus_id()
                    , "starttime", sysMonitorBuslog.getStarttimeShow()
                    , "endtime", sysMonitorBuslog.getEndtimeShow()
            ));
        }
        return F.transKit.asMap("rows", processList, "total", pair.getRight().getTotal());
    }
}
