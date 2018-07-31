package com.bankwel.phjf_admin.service;

import com.alibaba.fastjson.JSON;
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
import net.sf.json.util.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Administrator on 2017/11/9.
 */
public class BankInfoService {

    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBTSearchList() throws MsgBusinessException{
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
     * 银行类型管理 - 银行类型列表
     * @param name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankTypeByPage(String name, String status,String language, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankType.dao.queryBankTypeByPage(name,status,language, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 银行类型管理 - 新增银行类型
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankType addBankType(AuthOperator opt, BankTypeVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getBt_id()+"")) {
            checkBankTypeModel(vo);
            BankType bankType = BankType.dao.findByName(vo.getName());
            if(F.validate.isNotEmpty(bankType)){
                throw new MsgBusinessException("该银行类型名称已存在");
            }
            BankType model = new BankType();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBankType(opt,vo);
        }
    }


    /**
     * 银行类型管理 - 获取维语银行类型
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyBankType(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行类型编号不能为空");
        }
        BankType bankType = BankType.dao.findById(seq_uuid);
        BankType UeybankType = BankType.dao.findByCode(bankType.getBt_code(),language);
        if(F.validate.isEmpty(UeybankType.getBt_id())){
            bankType.setSeq_uuid(null);
            bankType.setBt_id(null);
            bankType.setLanguage(language);
            return outputBankType(bankType);
        }
        return outputBankType(UeybankType);
    }

    /**
     * 银行类型管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBankTypeModel(BankTypeVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("银行类型名称不能为空");
        }
        if (F.validate.isEmpty(vo.getBt_logo())){
            throw new MsgBusinessException("LOGO图片不能为空");
        }
    }

    /**
     * 银行类型管理 - 通过ID查找银行类型
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBankTypeById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行类型编号不能为空");
        }
        BankType model = BankType.dao.findById(seq_uuid);
        Map data = outputBankType(model);
        return data;
    }

    /**
     * 银行类型管理 - 银行类型输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankType(BankType model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"bt_id",model.getBt_id()
                ,"bt_code",model.getBt_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"bt_logo",model.getBt_logo()
                ,"bt_card_bgimg",model.getBt_card_bgimg()
                ,"bt_card_img",model.getBt_card_img()
                ,"name",model.getName()
                ,"service_telephone",model.getService_telephone()
                ,"opencard_need_material",model.getOpencard_need_material()
                ,"order_num",model.getOrder_num()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 银行类型管理 - 修改银行类型
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankType modifyBankType(AuthOperator opt, BankTypeVO vo) throws MsgBusinessException {
        checkBankTypeModel(vo);
        Boolean bool = BankType.dao.isHaveBtName(vo.getSeq_uuid(),vo.getName());
        if(bool){
            throw new MsgBusinessException("该银行类型名称已存在");
        }
        BankType model = BankType.dao.findById(vo.getSeq_uuid());
        model.setName(vo.getName());
        model.setBt_logo(vo.getBt_logo());
        model.setBt_card_bgimg(vo.getBt_card_bgimg());
        model.setBt_card_img(vo.getBt_card_img());
        model.setOpencard_need_material(vo.getOpencard_need_material());
        model.setService_telephone(vo.getService_telephone());
        model.setOrder_num(vo.getOrder_num());
        model.setIs_show(vo.getIs_show());
        model.setRemark(vo.getRemark());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 银行机构管理 - 银行机构列表
     *
     * @param bt_name
     * @param name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankInfoByPage(String bt_name, String name, String status, String isConfirm, String language, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankInfo.dao.queryBankInfoByPage(bt_name,name,status,isConfirm,language, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 银行机构管理 - 新增银行机构
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankInfo addBankInfo(AuthOperator opt, BankInfoVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getBank_id()+"")) {
            checkBankInfoModel(vo);
            BankInfo bankInfo = BankInfo.dao.findByName(vo.getName());
            if(F.validate.isNotEmpty(bankInfo)){
                throw new MsgBusinessException("该银行机构名称已存在");
            }
            BankInfo model = new BankInfo();
            F.transKit.copyProperties(model, vo);
            if(F.validate.isEmpty(vo.getParent_bank_code())||"0".equals(vo.getParent_bank_code())){
                model.saveOrUpdate(opt);
                model.setParent_bank_code("0");
                model.setBank_path(model.getBank_id()+"");
            }else {
                BankInfo pBank = BankInfo.dao.findByCode(model.getParent_bank_code(),AdminConstants.ZH_SIMP);
                if(!pBank.getBt_code().equals(model.getBt_code())){
                    throw new MsgBusinessException("银行类型应与上级银行机构的银行类型保持一致");
                }
                model.saveOrUpdate(opt);
                model.setBank_path(pBank.getBank_path()+"-"+model.getBank_id());
            }
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBankInfo(opt,vo);
        }
    }

    /**
     * 获取添加中文银行机构页的信息
     * @param parent_bank_code
     * @return
     */
    public Map findAddSimpBankInfo(String parent_bank_code){
        String language = AdminConstants.LanguageType.ZH_SIMP.value;
        String language_show = AdminConstants.LanguageType.ZH_SIMP.desc;
        String parent_bank_name = null;
        String bt_code = null;
        String bt_name = null;
        if(F.validate.isNotEmpty(parent_bank_code)){
            BankInfo pBank = BankInfo.dao.findBankInfo(parent_bank_code,language);
            bt_code = pBank.getBt_code();
            BankType pBankType = BankType.dao.findByCode(bt_code,language);
            parent_bank_name = pBank.getName();
            bt_name = pBankType.getName();
        }
        Map data = F.transKit.asMap("language", language, "language_show", language_show,"parent_bank_code",parent_bank_code,"parent_bank_name",parent_bank_name,"bt_code",bt_code,"bt_name",bt_name);
        return data;
    }

    /**
     * 银行机构管理 - 获取维语银行机构
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyBankInfo(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行类型编号不能为空");
        }
        BankInfo bankInfo = BankInfo.dao.findById(seq_uuid);
        BankInfo UeybankInfo = BankInfo.dao.findBankInfo(bankInfo.getBank_code(),language);
        if(F.validate.isEmpty(UeybankInfo.getBank_id())){
            bankInfo.setSeq_uuid(null);
            bankInfo.setBank_id(null);
            bankInfo.setLanguage(language);
            return outputBankInfo(bankInfo);
        }
        return outputBankInfo(UeybankInfo);
    }

    /**
     * 银行机构管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBankInfoModel(BankInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("银行机构名称不能为空");
        }
        if (F.validate.isEmpty(vo.getBt_code())){
            throw new MsgBusinessException("银行类型编号不能为空");
        }
        if (F.validate.isEmpty(vo.getLat())){
            throw new MsgBusinessException("纬度不能为空");
        }
        if (F.validate.isEmpty(vo.getLng())){
            throw new MsgBusinessException("经度不能为空");
        }
        if (F.validate.isEmpty(vo.getAddress())){
            throw new MsgBusinessException("地址不能为空");
        }
    }

    /**
     * 银行机构管理 - 通过ID查找银行机构
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBankInfoById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行机构编号不能为空");
        }
        BankInfo model = BankInfo.dao.findById(seq_uuid);
        Map data = outputBankInfo(model);
        return data;
    }

    /**
     * 银行机构管理 - 银行机构输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankInfo(BankInfo model) throws MsgBusinessException {
        String parent_bank_name = null;
        if(F.validate.isNotEmpty(model.getParent_bank_code())&&!model.getParent_bank_code().equals("0")){
            parent_bank_name = model.findParentBankName();
        }
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"bank_id",model.getBank_id()
                ,"bt_code",model.getBt_code()
                ,"bank_code",model.getBank_code()
                ,"third_bank_code",model.getThird_bank_code()
                ,"bt_name",model.findBankType().getName()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"parent_bank_code",model.getParent_bank_code()
                ,"parent_bank_name",parent_bank_name
                ,"bank_path",model.getBank_path()
                ,"contact",model.getContact()
                ,"name",model.getName()
                ,"lat",model.getLat()
                ,"lng",model.getLng()
                ,"address",model.getAddress()
                ,"email",model.getEmail()
                ,"telephone",model.getTelephone()
                ,"mobilephone",model.getMobilephone()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"is_confirm",model.getIs_confirm()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 银行机构管理 - 修改银行机构
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankInfo modifyBankInfo(AuthOperator opt, BankInfoVO vo) throws MsgBusinessException {
        checkBankInfoModel(vo);
        Boolean bool = BankInfo.dao.isHaveBtName(vo.getSeq_uuid(),vo.getName());
        if(bool){
            throw new MsgBusinessException("该银行机构名称已存在");
        }
        BankInfo model = BankInfo.dao.findById(vo.getSeq_uuid());
        model.setThird_bank_code(vo.getThird_bank_code());
        model.setName(vo.getName());
        model.setContact(vo.getContact());
        model.setLat(vo.getLat());
        model.setLng(vo.getLng());
        model.setAddress(vo.getAddress());
        model.setEmail(vo.getEmail());
        model.setTelephone(vo.getTelephone());
        model.setMobilephone(vo.getMobilephone());
        model.setIs_show(vo.getIs_show());
        model.setRemark(vo.getRemark());
        model.setIs_confirm(vo.getIs_confirm());
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * 银行账户管理 - 银行账户列表
     * @param name
     * @param contact
     * @param mobilephone
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankOptInfoByPage( String name, String contact, String mobilephone, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankOptinfo.dao.queryBankOptInfoByPage(name,contact,mobilephone,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 银行账户管理 - 新增银行账户
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankOptinfo addBankOptInfo(AuthOperator opt, BankOptinfoVO vo) throws MsgBusinessException {
        checkBankOptInfoModel(vo);
        UserInfo user = UserInfo.dao.findByPhoneOrId(vo.getMobilephone(),vo.getCerti_no());
//        BankOptinfo bankOpt = BankOptinfo.dao.findBankOptByUserId(user.getUser_id()+"");
//        if(F.validate.isNotEmpty(bankOpt.getOpt_id())){
//            throw new MsgBusinessException("该银行账户已存在");
//        }
        if(F.validate.isEmpty(user.getUser_id())) {
            user.setUser_name(vo.getMobilephone());
            user.setMobilephone(vo.getMobilephone());
            user.setTrue_name(vo.getName());
            user.setId_type(vo.getCerti_type());
            user.setId_num(vo.getCerti_no());
            user.setGender(vo.getGender());
            user.setAddress(vo.getAddress());
            user.saveOrUpdate(opt);
        }
        BankOptinfo model = new BankOptinfo();
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
     * 银行账户管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBankOptInfoModel(BankOptinfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("姓名不能为空");
        }
        if (F.validate.isEmpty(vo.getBank_code())){
            throw new MsgBusinessException("银行机构编号不能为空");
        }
        if(F.validate.isEmpty(vo.getMobilephone())){
            throw new MsgBusinessException("移动电话不能为空");
        }
    }

    /**
     * 银行账户管理 - 通过ID查找银行账户
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBankOptInfoById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行账户编号不能为空");
        }
        BankOptinfo model = BankOptinfo.dao.findById(seq_uuid);
        Map data = outputBankOptInfo(model);
        return data;
    }

    /**
     * 银行账户管理 - 银行账户输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankOptInfo(BankOptinfo model) throws MsgBusinessException {
        BankInfo bank = model.findBankInfo();
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"opt_id",model.getOpt_id()
                ,"bank_code",model.getBank_code()
                ,"bank_name",bank.getName()
                ,"gender",model.getGender()
                ,"bank_contact",bank.getContact()
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
     * 银行账户管理 - 修改银行账户
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankOptinfo modifyBankOptInfo(AuthOperator opt, BankOptinfoVO vo) throws MsgBusinessException {
        checkBankOptInfoModel(vo);
        BankOptinfo model = BankOptinfo.dao.findById(vo.getSeq_uuid());
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

    /**
     * 贷款管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryLoanSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysPlateList = SysDatalibrary.dao.querySysDatalibrary("phjf","loan_plate");
        List<Map> plateList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysPlateList){
            plateList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysUseTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","loan_useType");
        List<Map> useTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysUseTypeList){
            useTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }

        List<SysDatalibrary> sysIsRecomList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_isRecom");
        List<Map> isRecomList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysIsRecomList){
            isRecomList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_articleStatus");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("plateList",plateList,"useTypeList",useTypeList,"isRecomList",isRecomList,"statusList",statusList);
    }

    /**
     * 贷款管理 - 查询
     * @param bt_name
     * @param loan_plate
     * @param name
     * @param language
     * @param page
     * @return
     */
    public Map queryLoanInfoByPage(String bt_name, String loan_plate, String name,String loan_use_type, String is_recom, String status, String language, PageKit page){
        Pair<List<Record>, PageKit<Record>> pair = LoanInfo.dao.queryLoanInfoByPage(bt_name, loan_plate, name,loan_use_type, is_recom, status, language, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 贷款管理 - 新增
     * @param opt
     * @param vo
     * @return
     */
    public LoanInfo addLoanInfo(AuthOperator opt, LoanInfoVO vo){
        if (F.validate.isEmpty(vo.getBt_code())){
            throw new MsgBusinessException("银行类型不能为空");
        }
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("贷款产品名称不能为空");
        }
        if (F.validate.isEmpty(vo.getLoan_plate())){
            throw new MsgBusinessException("贷款大类不能为空");
        }
        if (F.validate.isEmpty(vo.getLoan_use_type())){
            throw new MsgBusinessException("贷款用途不能为空");
        }
        LoanInfo loanInfo = new LoanInfo();
        F.transKit.copyProperties(loanInfo, vo);
        loanInfo.saveOrUpdate(opt);
        return loanInfo;
    }

    /**
     * 贷款管理 - 修改
     * @param opt
     * @param vo
     * @return
     */
    public LoanInfo modifyLoanInfo(AuthOperator opt, LoanInfoVO vo){
        if (F.validate.isEmpty(vo.getSeq_uuid())){
            throw new MsgBusinessException("贷款编号不能为空");
        }
        if (F.validate.isEmpty(vo.getBt_code())){
            throw new MsgBusinessException("银行类型不能为空");
        }
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("贷款产品名称不能为空");
        }
        if (F.validate.isEmpty(vo.getLoan_plate())){
            throw new MsgBusinessException("贷款大类不能为空");
        }
        if (F.validate.isEmpty(vo.getLoan_use_type())){
            throw new MsgBusinessException("贷款用途不能为空");
        }
        LoanInfo loanInfo = LoanInfo.dao.findById(vo.getSeq_uuid());
        loanInfo.setBt_code(vo.getBt_code());
        loanInfo.setLoan_plate(vo.getLoan_plate());
        loanInfo.setLoan_use_type(vo.getLoan_use_type());
        loanInfo.setName(vo.getName());
        loanInfo.setTags(vo.getTags());
        loanInfo.setLogo(vo.getLogo());
        loanInfo.setLoan_term_upper(vo.getLoan_term_upper());
        loanInfo.setLoan_term_lower(vo.getLoan_term_lower());
        loanInfo.setLoan_rate_upper(vo.getLoan_rate_upper());
        loanInfo.setLoan_rate_lower(vo.getLoan_rate_lower());
        loanInfo.setLoan_amount_upper(vo.getLoan_amount_upper());
        loanInfo.setLoan_amount_lower(vo.getLoan_amount_lower());
        loanInfo.setIs_recom(vo.getIs_recom());
        loanInfo.setLoan_desc(vo.getLoan_desc());
        loanInfo.setApply_condition(vo.getApply_condition());
        loanInfo.setApply_need_item(vo.getApply_need_item());
        loanInfo.setApply_need_materials(vo.getApply_need_materials());
        loanInfo.setOrder_num(vo.getOrder_num());
        loanInfo.saveOrUpdate(opt);
        return loanInfo;
    }

    /**
     * 贷款管理 - 根据贷款代码获取贷款信息
     * @param seq_uuid
     * @param language
     * @return
     */
    public Map findLoanInfoById(String seq_uuid, String language){
        if (F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("贷款代码不能为空");
        }
        LoanInfo loanInfo = LoanInfo.dao.findById(seq_uuid);
        Map map = F.transKit.asMap("seq_uuid", loanInfo.getSeq_uuid()
                ,"loan_id", loanInfo.getLoan_id()
                ,"bt_code", loanInfo.getBt_code()
                ,"language",loanInfo.getLanguage()
                ,"language_show",loanInfo.findLanguage_show()
                ,"bt_name", loanInfo.findBankType(language).getName()
                ,"loan_plate", loanInfo.getLoan_plate()
                ,"loan_code",loanInfo.getLoan_code()
                ,"name", loanInfo.getName()
                ,"logo", loanInfo.getLogo()
                ,"loan_use_type", loanInfo.getLoan_use_type()
                ,"tags", loanInfo.getTags()
                ,"loan_term_upper", loanInfo.getLoan_term_upper()
                ,"loan_term_lower", loanInfo.getLoan_term_lower()
                ,"loan_rate_upper", loanInfo.getLoan_rate_upper()
                ,"loan_rate_lower", loanInfo.getLoan_rate_lower()
                ,"loan_amount_upper", loanInfo.getLoan_amount_upper()
                ,"loan_amount_lower", loanInfo.getLoan_amount_lower()
                ,"is_recom", loanInfo.getIs_recom()
                ,"loan_desc", loanInfo.getLoan_desc()
                ,"apply_condition", loanInfo.getApply_condition()
                ,"apply_need_materials", loanInfo.getApply_need_materials()
                ,"apply_need_item", loanInfo.getApply_need_item()
                ,"source", loanInfo.getSource()
                ,"order_num", loanInfo.getOrder_num()
                ,"status", loanInfo.getStatus()
                ,"is_show", loanInfo.getIs_show()
        );

        return map;
    }

    /**
     * 贷款管理 - 下架
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void unPublishLoan(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("贷款产品编号不能为空");
        }
        LoanInfo loanInfo = LoanInfo.dao.findById(seq_uuid);
        List<LoanInfo> loanInfoList = LoanInfo.dao.findByLoanCode(loanInfo.getLoan_code());
        for (LoanInfo loan : loanInfoList){
            loan.setStatus("3");
            loan.saveOrUpdate(opt);
        }
    }

    /**
     * 贷款管理 - 发布
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void publishLoan(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("贷款产品编号不能为空");
        }
        LoanInfo loanInfo = LoanInfo.dao.findById(seq_uuid);
        loanInfo.setStatus("2");
        loanInfo.saveOrUpdate(opt);
    }


    @Before(TTx.class)
    public BankAtm addBankAtm(AuthOperator opt, BankAtmVO vo) throws MsgBusinessException {
        checkBankATMModel(vo);
        BankAtm mp = BankAtm.dao.findByMapName(vo.getName());
        if(F.validate.isNotEmpty(mp)){
            throw new MsgBusinessException("该ATM已存在");
        }
        if(F.validate.isEmpty(vo.getBank_atm_id()+"")){
            BankAtm model = new BankAtm();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBankATM(opt,vo);
        }
    }

    /**
     * 助农办理点管理 - 修改助农办理点
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankAtm modifyBankATM(AuthOperator opt, BankAtmVO vo) throws MsgBusinessException {
        Boolean bool = BankAtm.dao.isHaveATMName(vo.getSeq_uuid(),vo.getName());
        checkBankATMModel(vo);
        if(bool){
            throw new MsgBusinessException("该办理点名称已存在");
        }
        BankAtm model = BankAtm.dao.findByUuid(vo.getSeq_uuid());
        if(F.validate.isNotEmpty(vo.getBank_code())){
            BankInfo bi = BankInfo.dao.findByCode(vo.getBank_code(),"ZH_SIMP");
            if(F.validate.isNotEmpty(bi)){
                if(bi.getBt_code().equals(model.getBt_code())){
                    model.setBank_code(vo.getBank_code());
                }else{
                    BankType bt = BankType.dao.findByCode(model.getBt_code(),"ZH_SIMP");
                    throw new MsgBusinessException("请填写"+bt.getName()+"所属的机构。");
                }
            }else{
                throw new MsgBusinessException("填写的银行机构号有误，请查证。");
            }
        }else{
            model.setBank_code("");
        }
        model.setName(vo.getName());
        model.setContact(vo.getContact());
        model.setMobilephone(vo.getMobilephone());
        model.setTelephone(vo.getTelephone());

        model.setLat(vo.getLat());
        model.setLng(vo.getLng());

        model.setIs_show(vo.getIs_show());
        model.setAddress(vo.getAddress());
        model.setRemark(vo.getRemark());
        model.setIs_confirm(vo.getIs_confirm());
        model.setThird_atm_code(vo.getThird_atm_code());

        model.saveOrUpdate(opt);
        return model;
    }

    public Map<String,String> findBankATMById(String seq_uuid) {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("办理点编号不能为空");
        }
        BankAtm model = BankAtm.dao.findByUuid(seq_uuid);

        Map<String,String> data = model.outputBankATMInfo(model);
        return data;
    }
    /**
     * ATM管理 - ATM列表
     * @param name
     * @param status
     * @param is_confirm
     *@param page  @return
     * @throws MsgBusinessException
     */
    public Map queryATMByPage(String name, String status, String is_confirm, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankAtm.dao.queryBankATMByPage( name,status,is_confirm, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }
    /**
     * ATM管理 - 删除ATM
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteBankATMInfo(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("ATM编号不能为空");
        }
        BankAtm bankAtmInfo = BankAtm.dao.findByUuid(seq_uuid);
        bankAtmInfo.setStatus("4");
        bankAtmInfo.setIs_show("N");
        bankAtmInfo.saveOrUpdate(opt);
    }

    /**
     * 助农办理点管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public static void checkBankATMModel(BankAtmVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(vo.getLat())){
            throw new MsgBusinessException("纬度不能为空");
        }
        if (F.validate.isEmpty(vo.getLng())){
            throw new MsgBusinessException("经度不能为空");
        }
        if (F.validate.isEmpty(vo.getAddress())){
            throw new MsgBusinessException("地址不能为空");
        }
    }

    /**
     * 获取银行业务查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBsSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysBankTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","bank_business_type");
        List<Map> bankTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysBankTypeList){
            bankTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysBankBusinessList = SysDatalibrary.dao.querySysDatalibrary("phjf","bank_function");
        List<Map> bankBusinessList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysBankBusinessList){
            bankBusinessList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList, "bankTypeList", bankTypeList,"bankBusinessList",bankBusinessList);
    }

    /**
     * 银行业务管理 - 银行业务列表
     * @param bt_name
     * @param type
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */

    public Map queryBankBusinessSettingByPage(String bt_name, String type,String bussiness_code, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankBusinessSetting.dao.queryBankBSByPage(bt_name,type,bussiness_code,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }
    /**
     * 银行业务管理 - 新增银行业务
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankBusinessSetting addBankBusiness(AuthOperator opt, BankBusinessSettingVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getSeq_uuid()+"")) {
            checkBankBusinessModel(vo);
            BankBusinessSetting bankBusiness = BankBusinessSetting.dao.findByCode(vo.getBt_code(),vo.getBusiness_code());
            if(F.validate.isNotEmpty(bankBusiness.getSeq_uuid())){
                throw new MsgBusinessException("该银行业务已存在");
            }
            BankBusinessSetting model = new BankBusinessSetting();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBankBusiness(opt,vo);
        }
    }
    /**
     * 银行业务管理 - 通过ID查找银行业务
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBankBusinessById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行业务编号不能为空");
        }
        BankBusinessSetting model = BankBusinessSetting.dao.findById(seq_uuid);
        Map data = outputBankBusiness(model);
        return data;
    }
    /**
     * 银行业务管理 - 银行业务输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankBusiness(BankBusinessSetting model) throws MsgBusinessException {
        BankType bankName = BankType.dao.findByCode(model.getBt_code(),AdminConstants.ZH_SIMP);
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"seq_id",model.getSeq_id()
                ,"bt_name",bankName.getName()
                ,"bt_code",model.getBt_code()
                ,"type",model.getType()
                ,"business_code",model.getBusiness_code()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }
    /**
     * 银行业务管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBankBusinessModel(BankBusinessSettingVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getBt_code())){
            throw new MsgBusinessException("银行类型不能为空");
        }
        if (F.validate.isEmpty(vo.getBusiness_code())){
            throw new MsgBusinessException("银行业务不能为空");
        }
    }
    /**
     * 银行业务管理 - 修改银行业务
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankBusinessSetting modifyBankBusiness(AuthOperator opt, BankBusinessSettingVO vo) throws MsgBusinessException {
        checkBankBusinessModel(vo);
        Boolean bool = BankBusinessSetting.dao.isHaveBbsName(vo.getSeq_uuid(),vo.getBt_code(),vo.getBusiness_code());
        if(bool){
            throw new MsgBusinessException("该银行业务已存在");
        }
        BankBusinessSetting model = BankBusinessSetting.dao.findById(vo.getSeq_uuid());
        model.setBt_code(vo.getBt_code());
        model.setBusiness_code(vo.getBusiness_code());
        model.setIs_show(vo.getIs_show());
        model.setStatus(vo.getStatus());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 获取银行业务配置查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBbcSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysBankTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","bank_business_type");
        List<Map> bankTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysBankTypeList){
            bankTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysBankBusinessList = SysDatalibrary.dao.querySysDatalibrary("phjf","bank_function");
        List<Map> bankBusinessList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysBankBusinessList){
            bankBusinessList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysDeviceTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_deviceType");
        List<Map> deviceTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysDeviceTypeList){
            deviceTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList, "bankTypeList", bankTypeList,"bankBusinessList",bankBusinessList,"deviceTypeList",deviceTypeList);
    }
    /**
     * 银行业务配置管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBankBusinessConfigModel(BankBusinessConfigVO vo,String app_version) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getBt_code())){
            throw new MsgBusinessException("银行类型不能为空");
        }
        if (F.validate.isEmpty(vo.getBusiness_code())){
            throw new MsgBusinessException("银行业务不能为空");
        }
        if (F.validate.isEmpty(vo.getDevice_type())){
            throw new MsgBusinessException("设备类型不能为空");
        }
        if (F.validate.isEmpty(app_version)){
            throw new MsgBusinessException("版本号不能为空");
        }
        app_version = app_version.replaceAll("\\.","-");
        List<String> appVersionArray = F.strKit.splitStr2List(app_version,"-");
        String app_v1 = appVersionArray.get(0);
        String app_v2 = appVersionArray.get(1);
        String app_v3 = appVersionArray.get(2);
        if(F.validate.isEmpty(app_v1)){
            throw new MsgBusinessException("版本号输入错误");
        }
        if(F.validate.isEmpty(app_v2)){
            throw new MsgBusinessException("版本号输入错误");
        }
        if(F.validate.isEmpty(app_v3)){
            throw new MsgBusinessException("版本号输入错误");
        }
        vo.setApp_version_1(app_v1);
        vo.setApp_version_2(app_v2);
        vo.setApp_version_3(app_v3);

    }
    /**
     * 银行业务配置管理 - 银行业务配置列表
     * @param bt_name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBankBusinessConfigByPage(String bt_name,String bussiness_code, String status,String device_typ, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BankBusinessConfig.dao.queryBankBbcByPage(bt_name,bussiness_code,status,device_typ, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }
    /**
     * 银行业务配置管理 - 新增银行业务配置
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankBusinessConfig addBankBusinessConfig(AuthOperator opt, BankBusinessConfigVO vo,String app_version) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getSeq_uuid()+"")) {
            checkBankBusinessConfigModel(vo,app_version);
            Boolean bool = BankBusinessSetting.dao.findByIsHaveBank(vo.getBt_code(),vo.getBusiness_code());
            if(!bool){
                throw new MsgBusinessException("该银行业务不存在");
            }
            BankBusinessConfig bankBusinessConfig = BankBusinessConfig.dao.findByInfo(vo.getBt_code(),vo.getBusiness_code(),vo.getDevice_type(),vo.getApp_version_1(),vo.getApp_version_2(),vo.getApp_version_3());
            if(F.validate.isNotEmpty(bankBusinessConfig.getSeq_uuid())){
                throw new MsgBusinessException("该银行业务配置已存在");
            }
            BankBusinessConfig model = new BankBusinessConfig();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBankBusinessConfig(opt,vo,app_version);
        }
    }
    /**
     * 银行业务配置管理 - 修改银行业务配置
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BankBusinessConfig modifyBankBusinessConfig(AuthOperator opt, BankBusinessConfigVO vo,String app_version) throws MsgBusinessException {
        checkBankBusinessConfigModel(vo, app_version);
        Boolean bool = BankBusinessConfig.dao.isHaveBbcName(vo.getSeq_uuid(),vo.getBt_code(),vo.getBusiness_code(),vo.getDevice_type(),vo.getApp_version_1(),vo.getApp_version_2(),vo.getApp_version_3());
        if(bool){
            throw new MsgBusinessException("该银行业务配置已存在");
        }
        BankBusinessConfig model = BankBusinessConfig.dao.findById(vo.getSeq_uuid());
        model.setBt_code(vo.getBt_code());
        model.setBusiness_code(vo.getBusiness_code());
        model.setDevice_type(vo.getDevice_type());
        model.setValue(vo.getValue());
        app_version = app_version.replaceAll("\\.", "-");
        List<String> appVersionArray = F.strKit.splitStr2List(app_version, "-");
        model.setApp_version_1(appVersionArray.get(0));
        model.setApp_version_2(appVersionArray.get(1));
        model.setApp_version_3(appVersionArray.get(2));

        model.setIs_show(vo.getIs_show());
        model.setStatus(vo.getStatus());
        model.saveOrUpdate(opt);
        return model;

    }
    /**
     * 银行业务配置管理 - 通过ID查找银行业务配置
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBankBusinessConfigById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("银行业务配置不能为空");
        }
        BankBusinessConfig model = BankBusinessConfig.dao.findById(seq_uuid);
        Map data = outputBankBusinessConfig(model);
        return data;
    }
    /**
     * 银行业务配置管理 - 银行业务配置输出信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBankBusinessConfig(BankBusinessConfig model) throws MsgBusinessException {
        BankType bankName = BankType.dao.findByCode(model.getBt_code(),AdminConstants.ZH_SIMP);
        String appVersion = model.getApp_version_1()+'.'+model.getApp_version_2()+'.'+model.getApp_version_3();
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"seq_id",model.getSeq_id()
                ,"bt_name",bankName.getName()
                ,"bt_code",model.getBt_code()
                ,"app_version",appVersion
                ,"business_code",model.getBusiness_code()
                ,"device_type",model.getDevice_type()
                ,"value",model.getValue()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }
}
