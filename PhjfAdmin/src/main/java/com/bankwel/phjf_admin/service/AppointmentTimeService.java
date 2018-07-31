package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.*;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.stat.ParseException;
import org.apache.commons.lang3.tuple.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/11/8.
 */
public class AppointmentTimeService {

    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankAmTimeSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> busList = SysDatalibrary.dao.querySysDatalibrary("phjf","bank_function");
        List<Map> bankBusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            bankBusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList, "bankFunctionList",busList);
    }
    /**
     *  - 银行预约时间列表
     * @param name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankAmTimeByPage(String name, String status,String business_code, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AppointmentTime.dao.queryAmTimeByPage(AdminConstants.Datalibrary_SYS_bank_business_1,name,status,business_code,page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     *  新增银行预约时间
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AppointmentTime addAppointTime(AuthOperator opt, AppointmentTimeVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getSeq_id()+"")) {
            checkBankAmTimeModel(vo);
            AppointmentTime model = new AppointmentTime();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
//            return this.modifyBankType(opt,vo);
            return null;
        }
    }
    /**
     * 银行预约时间管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBankAmTimeModel(AppointmentTimeVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getInstitution_code())){
            throw new MsgBusinessException("机构编码不能为空");
        }
        BankType bankType = BankType.dao.findByCode(vo.getInstitution_code(),AdminConstants.ZH_SIMP);
        if(F.validate.isEmpty(bankType.getSeq_uuid())) {
            throw new MsgBusinessException("机构编码不能为空");
        }
        if (F.validate.isEmpty(vo.getInstitution_type())){
            throw new MsgBusinessException("机构类型不能为空");
        }
        if (F.validate.isEmpty(vo.getTime_format())){
            throw new MsgBusinessException("时间格式不能为空");
        }
        if (F.validate.isEmpty(vo.getStart_time())){
            throw new MsgBusinessException("业务关闭开始时间不能为空");
        }
        if (F.validate.isEmpty(vo.getEnd_time())){
            throw new MsgBusinessException("业务关闭结束时间不能为空");
        }
        if(vo.getTime_format().equals("HH:mm:ss")) {
            String starttime = vo.getStart_time();
            String endtime = vo.getEnd_time();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format.parse(starttime);
                date2 = format.parse(endtime);
                //比较时间差
                if(date1.after(date2)){
                    throw new MsgBusinessException("结束时间必须大于开始时间");
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }else{
            Date startT = F.dateKit.translate2Date(vo.getStart_time(),vo.getTime_format());
            Date endT = F.dateKit.translate2Date(vo.getEnd_time(),vo.getTime_format());
            if(F.dateKit.compare(endT,startT)) {
                throw new MsgBusinessException("结束时间必须大于开始时间");
            }
        }
        if(F.validate.isEmpty(vo.getDescribe())) {
            throw new MsgBusinessException("业务关闭描述不能为空");
        }
        if(F.validate.isEmpty(vo.getIs_show())) {
            throw new MsgBusinessException("是否显示不能为空");
        }
        if(F.validate.isEmpty(vo.getStatus())) {
            throw new MsgBusinessException("状态不能为空");
        }
    }

    /**
     *  通过ID查找预约时间
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findAmTimeById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行类型编号不能为空");
        }
        AppointmentTime model = AppointmentTime.dao.findById(seq_uuid);
        Map data = outputBankAmTime(model);
        return data;
    }

    /**
     * 时间管理 - 银行预约时间输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankAmTime(AppointmentTime model) throws MsgBusinessException {
        BankType bankType = BankType.dao.findByCode(model.getInstitution_code(),AdminConstants.ZH_SIMP);
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"seq_id",model.getSeq_id()
                ,"bt_code",model.getInstitution_code()
                ,"bt_name",bankType.getName()
                ,"param_code",model.getParam_code()
                ,"business_code_name",SysDatalibrary.dao.findSysDatalibrary(AdminConstants.ZH_SIMP,"bank_function",model.getParam_code()).getName()
                ,"time_format",model.getTime_format()
                ,"time_format_name",SysDatalibrary.dao.findSysDatalibrary(AdminConstants.ZH_SIMP,"amTime_format",model.getTime_format()).getName()
                ,"start_time",model.getStart_time()
                ,"end_time",model.getEnd_time()
                ,"is_show",model.getIs_show()
                ,"is_show_name",SysDatalibrary.dao.findSysDatalibrary(AdminConstants.ZH_SIMP,"sys_isShow",model.getIs_show()).getName()
                ,"status_name",SysDatalibrary.dao.findSysDatalibrary(AdminConstants.ZH_SIMP,"sys_status",model.getStatus()).getName()
                ,"status",model.getStatus()
                ,"describe",model.getDescribe()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 预约时间管理 - 修改预约时间
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AppointmentTime modifyAmTime(AuthOperator opt, AppointmentTimeVO vo) throws MsgBusinessException {
        checkBankAmTimeModel(vo);
        AppointmentTime model = AppointmentTime.dao.findById(vo.getSeq_uuid());
        model.setBusiness_stat("N");
        model.setIs_show(vo.getIs_show());
        model.setStatus(vo.getStatus());
        model.setInstitution_type(vo.getInstitution_type());
        model.setTime_format(vo.getTime_format());
        model.setStart_time(vo.getStart_time());
        model.setEnd_time(vo.getEnd_time());
        model.setInstitution_code(vo.getInstitution_code());
        model.setDescribe(vo.getDescribe());
        model.saveOrUpdate(opt);
        return model;
    }
}
