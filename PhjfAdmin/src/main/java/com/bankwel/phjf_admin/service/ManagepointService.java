package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.ManagepointBankVO;
import com.bankwel.phjf_admin.webapi.vo.ManagepointInfoVO;
import com.bankwel.phjf_admin.webapi.vo.ManagepointOptinfoVO;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */
public class ManagepointService {

    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryMpSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_language");
        List<Map> languageList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLanguageList){
            languageList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysIsConfirmList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_isConfirm");
        List<Map> isConfirmList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysIsConfirmList){
            isConfirmList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList,"languageList",languageList, "isConfirmList", isConfirmList);
    }

    /**
     * 助农办理点管理 - 助农办理点列表
     * @param language
     * @param name
     * @param contact
     * @param mobilephone
     * @param status
     * @param isConfirm
     *@param page  @return
     * @throws MsgBusinessException
     */
    public Map queryManagepointByPage(String language, String name, String contact, String mobilephone, String status, String isConfirm, PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = ManagepointInfo.dao.queryManagepointByPage(language, name,contact,mobilephone,status,isConfirm, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 助农办理点管理 - 添加助农办理点
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public ManagepointInfo addManagepoint(AuthOperator opt, ManagepointInfoVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getMp_id()+"")){
            checkModel(vo);
            ManagepointInfo mp = ManagepointInfo.dao.findByMapName(vo.getName());
            if(F.validate.isNotEmpty(mp)){
                throw new MsgBusinessException("该办理点名称已存在");
            }
            ManagepointInfo model = new ManagepointInfo();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyManagepoint(opt,vo);
        }
    }

    /**
     * 助农办理点管理 - 获取维语助农办理点
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyManagepointInfo(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("办理点编号不能为空");
        }
        ManagepointInfo managepointInfo = ManagepointInfo.dao.findById(seq_uuid);
        ManagepointInfo Ueymanagepoint = ManagepointInfo.dao.findManagepoint(managepointInfo.getMp_code(),language);
        if(F.validate.isEmpty(Ueymanagepoint)){
            managepointInfo.setSeq_uuid(null);
            managepointInfo.setMp_id(null);
            managepointInfo.setLanguage(language);
            return outputManagePointInfo(managepointInfo);
        }
        return outputManagePointInfo(Ueymanagepoint);
    }

    /**
     * 助农办理点管理 - 修改助农办理点
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public ManagepointInfo modifyManagepoint(AuthOperator opt, ManagepointInfoVO vo) throws MsgBusinessException {
        checkModel(vo);
        Boolean bool = ManagepointInfo.dao.isHaveMpName(vo.getSeq_uuid(),vo.getName());
        if(bool){
            throw new MsgBusinessException("该办理点名称已存在");
        }
        ManagepointInfo model = ManagepointInfo.dao.findById(vo.getSeq_uuid());
        model.setName(vo.getName());
        model.setContact(vo.getContact());
        model.setMobilephone(vo.getMobilephone());
        model.setTelephone(vo.getTelephone());
        model.setOpen_hours(vo.getOpen_hours());
        model.setProvince(vo.getProvince());
        model.setCity(vo.getCity());
        model.setArea(vo.getArea());
        model.setLat(vo.getLat());
        model.setLng(vo.getLng());
        model.setDay_total_amount(vo.getDay_total_amount());
        model.setIs_show(vo.getIs_show());
        model.setAddress(vo.getAddress());
        model.setRemark(vo.getRemark());
        model.setIs_withdraw(vo.getIs_withdraw());
        model.setIs_confirm(vo.getIs_confirm());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 助农办理点管理 - 删除助农办理点
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteManagepoint(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("办理点编号不能为空");
        }
        ManagepointInfo managepointInfo = ManagepointInfo.dao.findById(seq_uuid);
        //根据mpId获取删除的mapCode
        List<ManagepointInfo> mpList = ManagepointInfo.dao.findByMapCode(managepointInfo.getMp_code());
        for (ManagepointInfo mp : mpList){
            mp.setStatus("4");
            mp.setIs_show("N");
            mp.saveOrUpdate(opt);
        }
    }

    /**
     * 助农办理点管理 - 查找助农办理点
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findManagePointById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("办理点编号不能为空");
        }
        ManagepointInfo model = ManagepointInfo.dao.findById(seq_uuid);
        Map data = outputManagePointInfo(model);
        return data;
    }

    /**
     * 助农办理点管理 - 输出助农办理点管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputManagePointInfo(ManagepointInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"mp_id",model.getMp_id()
                ,"mp_code",model.getMp_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"name",model.getName()
                ,"contact",model.getContact()
                ,"mobilephone",model.getMobilephone()
                ,"telephone",model.getTelephone()
                ,"province",model.getProvince()
                ,"city",model.getCity()
                ,"area",model.getArea()
                ,"province_show",model.findProvice_show()
                ,"city_show",model.findCity_show()
                ,"area_show",model.findArea_show()
                ,"address",model.getAddress()
                ,"open_hours",model.getOpen_hours()
                ,"lat",model.getLat()
                ,"lng",model.getLng()
                ,"day_total_amount",model.getDay_total_amount()
                ,"is_withdraw",model.getIs_withdraw()
                ,"is_confirm",model.getIs_confirm()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 助农办理点管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkModel(ManagepointInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(vo.getProvince())){
            throw new MsgBusinessException("省不能为空");
        }
        if (F.validate.isEmpty(vo.getCity())){
            throw new MsgBusinessException("市不能为空");
        }
        if (F.validate.isEmpty(vo.getAddress())){
            throw new MsgBusinessException("地址不能为空");
        }
//        if (F.validate.isEmpty(vo.getContact())){
//            throw new MsgBusinessException("联系人不能为空");
//        }
//        if(F.validate.isEmpty(vo.getMobilephone())&&F.validate.isEmpty(vo.getTelephone())){
//            throw new MsgBusinessException("移动电话和固定电话必须填一个");
//        }

    }

    /**
     * 办理点管理 - 通过代码获取办理点
     * @param mp_code
     * @param language
     * @return
     */
    public Map findByCode(String mp_code, String language){
        ManagepointInfo data = ManagepointInfo.dao.findManagepoint(mp_code, language);
        return this.outputManagePointInfo(data);
    }


    /**
     * 办理点银行管理 - 办理点银行查询
     * @param mp_name
     * @param bank_name
     * @param is_open_account
     * @param language
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryMpBankByPage(String mp_name, String bank_name, String is_open_account, String language, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = ManagepointBank.dao.queryManagepointByPage(mp_name, bank_name, is_open_account, language, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 办理点银行管理 - 新增办理点银行
     * @param opt
     * @param vo
     * @return
     */
    public ManagepointBank addManagepointBank(AuthOperator opt, ManagepointBankVO vo){
        if (F.validate.isEmpty(vo.getMp_code())){
            throw new MsgBusinessException("办理点代码不能为空");
        }
        if (F.validate.isEmpty(vo.getBank_code())){
            throw new MsgBusinessException("银行代码不能为空");
        }
        List<ManagepointInfo> mpList = ManagepointInfo.dao.findByMapCode(vo.getMp_code());
        if (F.validate.isEmpty(mpList)){
            throw new MsgBusinessException("办理点不存在");
        }
        BankInfo bank = BankInfo.dao.findByCode(vo.getBank_code(), AdminConstants.LanguageType.ZH_SIMP.value);
        if (bank.isEmpty()){
            throw new MsgBusinessException("银行不存在");
        }

        boolean bool = ManagepointBank.dao.findByMpCodeAndBankCode(vo.getMp_code(), vo.getBank_code());
        if (!bool){
            throw new MsgBusinessException("办理点银行已存在");
        }

        ManagepointBank mpBank = new ManagepointBank();
        F.transKit.copyProperties(mpBank, vo);
        mpBank.setBt_code(bank.getBt_code());
        mpBank.saveOrUpdate(opt);
        return mpBank;
    }

    /**
     * 办理点银行管理 - 通过ID获取办理点银行
     * @param seq_uuid
     * @return
     */
    public Map findById(String seq_uuid){
        ManagepointBank data = ManagepointBank.dao.findById(seq_uuid);
        Map returnMap = F.transKit.asMap();
        returnMap.put("seq_uuid", data.getSeq_uuid());
        returnMap.put("mp_code", data.getMp_code());
        returnMap.put("mp_name", data.findManagepoint().getName());
        returnMap.put("mp_contact", data.findManagepoint().getContact());
        returnMap.put("bank_code", data.getBank_code());
        returnMap.put("bank_name", data.findBank().getName());
        returnMap.put("bank_contact", data.findBank().getContact());
        returnMap.put("is_open_account", data.getIs_open_account());
        returnMap.put("start_date", data.getStart_date());
        returnMap.put("end_date", data.getEnd_date());
        returnMap.put("remark", data.getRemark());
        returnMap.put("is_show", data.getIs_show());
        return returnMap;
    }

    /**
     * 办理点银行管理 - 修改办理点银行
     * @param opt
     * @param vo
     * @return
     */
    public ManagepointBank updateManagepointBank(AuthOperator opt, ManagepointBankVO vo) {
        if (F.validate.isEmpty(vo.getSeq_uuid())) {
            throw new MsgBusinessException("办理点银行代码不能为空");
        }

        ManagepointBank mpBank = ManagepointBank.dao.findById(vo.getSeq_uuid());
        mpBank.setIs_open_account(vo.getIs_open_account());
        mpBank.setStart_date(vo.getStart_date());
        mpBank.setEnd_date(vo.getEnd_date());
        mpBank.setRemark(vo.getRemark());
        mpBank.setIs_show(vo.getIs_show());
        mpBank.saveOrUpdate(opt);
        return mpBank;
    }

    /**
     * 办理点银行管理 - 失效
     * @param opt
     * @param seq_uuid
     */
    public void deleteMpBank(AuthOperator opt, String seq_uuid){
        if (F.validate.isEmpty(seq_uuid)) {
            throw new MsgBusinessException("办理点银行代码不能为空");
        }
        ManagepointBank mpBank = ManagepointBank.dao.findById(seq_uuid);
        mpBank.setStatus("4");
        mpBank.setIs_show("N");
        mpBank.setEnd_date(new Date());
        mpBank.saveOrUpdate(opt);
    }


    //-----------------------------------------

    /**
     * 办理点账户管理 - 获取办理点账户列表
     * @param name
     * @param user_name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryMpOptInfoByPage(String mp_name, String name,String user_name,String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = ManagepointOptinfo.dao.queryMpOptInfoByPage(mp_name, name,user_name,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 办理点账户管理 - 新增办理点账户
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public ManagepointOptinfo addManagepointOptInfo(AuthOperator opt, ManagepointOptinfoVO vo) throws MsgBusinessException {
        checkMpOptInfoModel(vo);
        UserInfo user = UserInfo.dao.findByPhoneOrId(vo.getMobilephone(),vo.getCerti_no());
        if(F.validate.isEmpty(user.getUser_id())){
            user.setUser_name(vo.getMobilephone());
            user.setMobilephone(vo.getMobilephone());
            user.setTrue_name(vo.getName());
            user.setId_type(vo.getCerti_type());
            user.setId_num(vo.getCerti_no());
            user.setGender(vo.getGender());
            user.setAddress(vo.getAddress());
            user.saveOrUpdate(opt);
        }
        ManagepointOptinfo mpOpt = ManagepointOptinfo.dao.findMpOptByUserId(user.getUser_id()+"");
        if(F.validate.isNotEmpty(mpOpt.getOpt_id())){
            throw new MsgBusinessException("该办理点账户已存在");
        }
        ManagepointOptinfo model = new ManagepointOptinfo();
        F.transKit.copyProperties(model, vo);
        model.setUser_id(user.getUser_id());
        model.saveOrUpdate(opt);
        UserAppauth userAppauth = UserAppauth.dao.findById(user.getUser_id()+"","phjf_merchclient");
        if (F.validate.isEmpty(userAppauth.getUad_id())){
            userAppauth.setUser_id(user.getUser_id());
            userAppauth.setPassword(F.encryptionKit.md5(PropKit.use("config.properties").get("merch_password")));
            userAppauth.setApp_code("phjf_merchclient");
            userAppauth.saveOrUpdate(opt);
        }
        return model;
    }

    /**
     * 办理点账户管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkMpOptInfoModel(ManagepointOptinfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("姓名不能为空");
        }
        if (F.validate.isEmpty(vo.getMp_code())){
            throw new MsgBusinessException("办理点编号不能为空");
        }
        if(F.validate.isEmpty(vo.getMobilephone())){
            throw new MsgBusinessException("移动电话不能为空");
        }
    }

    /**
     * 办理点账户管理 - 通过ID查找办理点账户
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findMpOptInfoById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("办理点账户编号不能为空");
        }
        ManagepointOptinfo model = ManagepointOptinfo.dao.findById(seq_uuid);
        Map data = outputMpOptInfo(model);
        return data;
    }

    /**
     * 办理点账户管理 - 办理点账户输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputMpOptInfo(ManagepointOptinfo model) throws MsgBusinessException {
        ManagepointInfo mp = model.findManagepoint();
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"opt_id",model.getOpt_id()
                ,"mp_code",model.getMp_code()
                ,"mp_name",mp.getName()
                ,"gender",model.getGender()
                ,"mp_contact",mp.getContact()
                ,"user_id",model.getUser_id()
                ,"name",model.getName()
                ,"mobilephone",model.getMobilephone()
                ,"telephone",model.getTelephone()
                ,"certi_type",model.getCerti_type()
                ,"certi_no",model.getCerti_no()
                ,"email",model.getEmail()
                ,"password",F.encryptionKit.md5(PropKit.use("config.properties").get("merch_password"))
                ,"address",model.getAddress()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 办理点账户管理 - 修改办理点账户
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public ManagepointOptinfo modifyMpOptInfo(AuthOperator opt, ManagepointOptinfoVO vo) throws MsgBusinessException {
        checkMpOptInfoModel(vo);
        ManagepointOptinfo model = ManagepointOptinfo.dao.findById(vo.getSeq_uuid());
        model.setName(vo.getName());
        model.setTelephone(vo.getTelephone());
        model.setEmail(vo.getEmail());
        model.setGender(vo.getGender());
        model.setCerti_type(vo.getCerti_type());
        model.setCerti_no(vo.getCerti_no());
        model.setIs_show(vo.getIs_show());
        model.setAddress(vo.getAddress());
        model.setRemark(vo.getRemark());
        model.saveOrUpdate(opt);
        UserInfo user = UserInfo.dao.findByUserId(model.getUser_id()+"");
        user.setTrue_name(vo.getName());
        user.setId_type(vo.getCerti_type());
        user.setId_num(vo.getCerti_no());
        user.setGender(vo.getGender());
        user.setAddress(vo.getAddress());
        user.saveOrUpdate(opt);
        return model;
    }
}
