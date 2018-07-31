package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.DateKit;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.DataLoader;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.webapi.vo.SysMonitorBuslogVO;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheSysMonitorBuslog;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.AdminSysMonitorBuslogEnum;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** 
 * @ClassName: SysMonitorBuslog 
 * @Description: 系统监控日志
 * @author: DukeMr.Chen
 * @date: 2018/5/14 17:52 
 * @version: V1.0 
 * 
 */
public class SysMonitorBuslog extends CacheSysMonitorBuslog<SysMonitorBuslog> {
    public static final SysMonitorBuslog dao = new SysMonitorBuslog().dao();

    public Pair<List<Record>,PageKit<Record>> queryMonitorLogIdByPage(SysMonitorBuslogVO vo, PageKit page) {
        final String systemCode = vo.getSys_code();
        final String sub_sys_code = vo.getSub_sys_code();
        final String userId = vo.getUser_id();
        final String clientip = vo.getClientip();
        final String startTime = vo.getStartTime();
        final String endTime = vo.getEndTime();
        final String bus_path = vo.getBus_path();
        final String bus_method = vo.getBus_method();
        final String url = vo.getUrl();
        ArrayList<Record> _ids = RedisClient.getClient().get(
                F.strKit.f(AdminSysMonitorBuslogEnum.CK_Phjf_Model_SysMonitorBuslog_KEY1.getKey()
                        , systemCode, userId, clientip, startTime, endTime, url, bus_path, bus_method, sub_sys_code)
                , AdminSysMonitorBuslogEnum.CK_Phjf_Model_SysMonitorBuslog_KEY1.getTime()
                , new DataLoader(){
                    public Object load() throws  Exception{
                        String sql = "SELECT DISTINCT pa.seq_id" +
                                " FROM sys_monitor_buslog pa " +
                                " WHERE 1 = 1 ";
                        List params = new ArrayList();
                        if (F.validate.isNotEmpty(url)){
                            sql += " and (pa.url = ? or pa.url LIKE concat('%',?,'%'))";
                            params.add(url);
                            params.add(url);
                        }
                        if (F.validate.isNotEmpty(systemCode)){
                            sql += " and (pa.sys_code = ?)";
                            params.add(systemCode);
                        }
                        if (F.validate.isNotEmpty(sub_sys_code)){
                            sql += " and (pa.sub_sys_code = ?)";
                            params.add(sub_sys_code);
                        }
                        if (F.validate.isNotEmpty(userId)){
                            sql += " and (pa.user_id = ?)";
                            params.add(userId);
                        }
                        if (F.validate.isNotEmpty(clientip)){
                            sql += " and (pa.clientip = ?)";
                            params.add(clientip);
                        }

                        if (F.validate.isNotEmpty(startTime) && F.validate.isNotEmpty(endTime)){
                            sql += " and DATE_FORMAT(pa.starttime,'%Y-%m-%d') >= ?";
                            sql += " and DATE_FORMAT(pa.starttime,'%Y-%m-%d') <= ?";
                            params.add(startTime);
                            params.add(endTime);
                        }else if (F.validate.isNotEmpty(startTime)){
                            sql += " and DATE_FORMAT(pa.starttime,'%Y-%m-%d') = ?";
                            params.add(startTime);
                        }

                        if (F.validate.isNotEmpty(bus_path)){
                            sql += " and (pa.bus_path = ? or pa.bus_path LIKE concat('%',?,'%')) ";
                            params.add(bus_path);
                            params.add(bus_path);
                        }
                        if (F.validate.isNotEmpty(bus_method)){
                            sql += " and (pa.bus_method = ? or pa.bus_method LIKE concat('%',?,'%')) ";
                            params.add(bus_method);
                            params.add(bus_method);
                        }
                        sql += " order by pa.starttime desc";

                        return Db.use("DBPublic").find(sql, params.toArray());
                    }
                });
        if(F.validate.isEmpty(_ids)) {
            _ids = new ArrayList<Record>();
        }
        //分页计算
        PageKit<Record> pageTwo = new PageKit<Record>(page.getNowPage(),_ids, page.getRowsPerPage());
        java.util.List<Record> selectIds = pageTwo.calcPagelist(_ids);
        return Pair.of(selectIds,pageTwo);
    }

    public SysMonitorBuslog findBySeqId(final String seq_id) {
        SysMonitorBuslog data = RedisClient.getClient().get(F.strKit.f(AdminSysMonitorBuslogEnum.CK_Phjf_Model_SysMonitorBuslog_KEY2.getKey(), seq_id)
                , AdminSysMonitorBuslogEnum.CK_Phjf_Model_SysMonitorBuslog_KEY2.getTime()
                , SysMonitorBuslog.class
                , new DataLoader(){
                    public Object load() throws Exception{
                        return SysMonitorBuslog.dao.findById(seq_id);
                    }
                });
        if(F.validate.isEmpty(data)) {
            data = new SysMonitorBuslog();
        }

        return data;
    }


    public String getSysCodeShow() {
        if(StringUtils.isBlank(this.getSys_code())){return "";}
        SysDatalibrary systemCode = SysDatalibrary.dao.queryDatalibrary("Phjf","system_code", this.getSys_code());
        return systemCode.getName();
    }

    public String getStarttimeShow() {
        return F.dateKit.translate2Str(this.getStarttime(), DateKit.PATTERN_All);
    }

    public String getEndtimeShow() {
        return F.dateKit.translate2Str(this.getEndtime(), DateKit.PATTERN_All);
    }

    public int findCountNeedReflush() {
        String sql = "select count(*) from sys_monitor_buslog where iscomplete = '0' " ;
        return Db.use("DBPublic").queryInt(sql);
    }

    public void updateBatch(List<SysMonitorBuslog> updateList) {
        String sql = "UPDATE sys_monitor_buslog set `iscomplete` = ?,`bus_path` = ?,`bus_method` = ?,`bus_target` = ?,`bus_content` = ? where seq_id= ?";
        int[] result = Db.use("DBPublic").batch(sql, "iscomplete,bus_path,bus_method,bus_target,bus_content,seq_id", updateList, 200);
        flashCatch(this);
    }
    
    /** 
     * @Title:
     * @Description: 获取控业务字段的日志
     * @author: DukeMr.Chen
     */
    public List<SysMonitorBuslog> findEmptyLog(long seq_id) {
        String sql = "select * from sys_monitor_buslog " +
                " where iscomplete = '0' " +
                " and seq_id>? " +
                " ORDER BY seq_id " +
                " LIMIT 1000  " ;
        return SysMonitorBuslog.dao.find(sql, seq_id);
    }

    /**
     * @Title:
     * @Description: 报表统计
     * @author: DukeMr.Chen
     */
    public List<Record> reportQuery(String reportWay, String startTime) {
        String sql = "";
        if("01".equals(reportWay)){
            sql += "SELECT s.clientip as reportSubject, COUNT(*) as reportNum FROM sys_monitor_buslog s WHERE locate('/checkLogin', s.url) > 0 OR locate('/user/login', s.url) > 0  and ";
        }else if("02".equals(reportWay)){
            sql += "SELECT s.user_id as reportSubject, COUNT(*) as reportNum  FROM sys_monitor_buslog s WHERE s.user_id != ''  and (locate('/checkLogin', s.url) > 0 OR locate('/user/login', s.url) > 0) and ";
        }else if("03".equals(reportWay)){
            sql += "SELECT s.clientip as reportSubject, COUNT(*) as reportNum  FROM sys_monitor_buslog s WHERE ";
        }else if("04".equals(reportWay)){
            sql += "SELECT s.client_type as reportSubject, COUNT(*) as reportNum FROM sys_monitor_buslog s WHERE ";
        }
        sql += " DATE_FORMAT(s.starttime,'%Y-%m-%d') >= ?";
        sql += " GROUP BY reportSubject " +
                " ORDER BY reportNum desc " +
                " LIMIT 10 ";

        List<Record> list=  Db.use("DBPublic").find(sql, startTime);
        if(F.validate.isEmpty(list)) {
            list = new ArrayList<Record>();
        }
        return list;
    }

    /**
     * @Title:
     * @Description: 查询日志列表
     * @author: DukeMr.Chen
     */
    public Pair<List<Record>, PageKit<Record>> queryMonitorBuslogList(final String reportWay, final String startTime, final String reportSubject, PageKit page) {
        ArrayList<Record> _ids = RedisClient.getClient().get(
                F.strKit.f(AdminSysMonitorBuslogEnum.CK_Phjf_Model_SysMonitorBuslog_KEY3.getKey()
                        , reportWay, reportSubject, startTime)
                , AdminSysMonitorBuslogEnum.CK_Phjf_Model_SysMonitorBuslog_KEY3.getTime()
                , new DataLoader(){
                    public Object load() throws  Exception{
                        String sql = "SELECT DISTINCT s.seq_id FROM sys_monitor_buslog s WHERE";
                        if("01".equals(reportWay)){
                            sql += " (locate('/checkLogin', s.url) > 0 OR locate('/user/login', s.url) > 0) and s.clientip=? and ";
                        }else if("02".equals(reportWay)){
                            sql += " (locate('/checkLogin', s.url) > 0 OR locate('/user/login', s.url) > 0) and s.user_id=? and ";
                        }else if("03".equals(reportWay)){
                            sql += "  s.clientip=? and ";
                        }else if("04".equals(reportWay)){
                            sql += " s.client_type=? and ";
                        }
                        sql += " DATE_FORMAT(s.starttime,'%Y-%m-%d') >= ?";
                        sql += " ORDER BY s.starttime desc ";
                        List params = new ArrayList();
                        params.add(reportSubject);
                        params.add(startTime);
                        return Db.use("DBPublic").find(sql, params.toArray());
                    }
                });
        if(F.validate.isEmpty(_ids)) {
            _ids = new ArrayList<Record>();
        }
        //分页计算
        PageKit<Record> pageTwo = new PageKit<Record>(page.getNowPage(),_ids, page.getRowsPerPage());
        java.util.List<Record> selectIds = pageTwo.calcPagelist(_ids);
        return Pair.of(selectIds,pageTwo);
    }


}
