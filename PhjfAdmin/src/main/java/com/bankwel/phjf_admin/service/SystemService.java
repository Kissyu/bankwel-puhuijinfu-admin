package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.client.PhjfSmsPushClient;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.support.FlowControlKit;
import com.bankwel.phjf_admin.support.MyMBusLogLoader;
import com.bankwel.phjf_admin.webapi.vo.AppInfoVO;
import com.bankwel.phjf_admin.webapi.vo.AppVersionInfoVO;
import com.bankwel.phjf_admin.webapi.vo.SysParamVO;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.FlowControlEnum;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * Created by Administrator on 2017/11/21.
 */
public class SystemService {
    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAppVersionSearchList() throws MsgBusinessException {
        List<SysDatalibrary> sysIsUpdateList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_isUpdate");
        List<Map> isUpdateList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysIsUpdateList){
            isUpdateList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysChannelList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_channel");
        List<Map> channelList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysChannelList){
            channelList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_articleStatus");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("isUpdateList",isUpdateList,"statusList",statusList,"channelList",channelList);
    }
    /**
     * app发布管理 - 获取app版本列表
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAppVersionByPage(String app_version, String is_update,String channel, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AppVersionInfo.dao.queryAppVersionByPage(app_version, is_update,channel,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * app发布管理 - 添加app版本
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AppVersionInfo addAppVersion(AuthOperator opt, AppVersionInfoVO vo) throws MsgBusinessException {
        checkAppVersionInfoModel(vo);
        AppVersionInfo appVersionInfo = AppVersionInfo.dao.findByDeviceVersion(vo.getApp_id()+"",vo.getDevice_type(),vo.getApp_version());
        if(F.validate.isNotEmpty(appVersionInfo.getAvi_id())){
            throw new MsgBusinessException("该app版本已存在");
        }
        AppVersionInfo model = new AppVersionInfo();
        F.transKit.copyProperties(model, vo);
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * app发布管理 - 修改app版本
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AppVersionInfo modifyAppVersion(AuthOperator opt, AppVersionInfoVO vo) throws MsgBusinessException {
        checkAppVersionInfoModel(vo);
        Boolean bool = AppVersionInfo.dao.isHasThisVersion(vo.getSeq_uuid(),vo.getApp_id()+"",vo.getDevice_type(),vo.getApp_version());
        if (bool){
            throw new MsgBusinessException("该app版本号已存在");
        }
        AppVersionInfo model = AppVersionInfo.dao.findById(vo.getSeq_uuid());
        model.setIs_update(vo.getIs_update());
        model.setApp_version(vo.getApp_version());
        model.setH5_url(vo.getH5_url());
        model.setApp_url(vo.getApp_url());
        model.setQr_code_url(vo.getQr_code_url());
        model.setChange_content(vo.getChange_content());
        model.setSize(vo.getSize());
        model.setIs_show(vo.getIs_show());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * app发布管理 - 发布
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void publishAppVersion(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("app版本编号不能为空");
        }
        AppVersionInfo appVersionInfo = AppVersionInfo.dao.findById(seq_uuid);
        appVersionInfo.setPublish_date(new Date());
        appVersionInfo.setStatus("2");
        appVersionInfo.saveOrUpdate(opt);
    }

    /**
     * app发布管理 - 下架
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void unPublishAppVersion(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("app版本编号不能为空");
        }
        AppVersionInfo appVersionInfo = AppVersionInfo.dao.findById(seq_uuid);
        appVersionInfo.setUnder_date(new Date());
        appVersionInfo.setStatus("3");
        appVersionInfo.saveOrUpdate(opt);
    }

    /**
     * app发布管理 - 查找app版本
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findAppVersionById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("app版本编号不能为空");
        }
        AppVersionInfo model = AppVersionInfo.dao.findById(seq_uuid);
        Map data = outputAppVersionInfo(model);
        return data;
    }

    /**
     * app发布管理 - 输出app版本信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputAppVersionInfo(AppVersionInfo model) throws MsgBusinessException {
        AppInfo app = model.findAppInfo();
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"avi_id",model.getAvi_id()
                ,"app_id",model.getApp_id()
                ,"app_name",app.getName()
                ,"package_name",app.getPackage_name()
                ,"device_type",model.getDevice_type()
                ,"app_version",model.getApp_version()
                ,"is_update",model.getIs_update()
                ,"app_url",model.getApp_url()
                ,"h5_url",model.getH5_url()
                ,"qr_code_url",model.getQr_code_url()
                ,"size",model.getSize()
                ,"change_content",model.getChange_content()
                ,"channel",model.getChannel()
                ,"publish_date",model.getPublish_date()
                ,"under_date",model.getUnder_date()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus());
        return data;
    }

    /**
     * app发布管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkAppVersionInfoModel(AppVersionInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getApp_id())){
            throw new MsgBusinessException("app编号不能为空");
        }
        if (F.validate.isEmpty(vo.getDevice_type())){
            throw new MsgBusinessException("当期设备不能为空");
        }
        if (F.validate.isEmpty(vo.getApp_version())){
            throw new MsgBusinessException("版本不能为空");
        }
        if (F.validate.isEmpty(vo.getIs_update())){
            throw new MsgBusinessException("是否强制更新不能为空");
        }
        if (F.validate.isEmpty(vo.getChannel())){
            throw new MsgBusinessException("渠道不能为空");
        }
    }

    /**
     * app发布管理 - 通过app_id获取app
     * @param app_id
     * @return
     * @throws MsgBusinessException
     */
    public Map findAppByAppId(String app_id) throws MsgBusinessException{
        AppInfo appInfo = AppInfo.dao.findAppByAppId(app_id);
        Map map = F.transKit.asMap("seq_uuid", appInfo.getSeq_uuid()
                ,"app_name",appInfo.getName()
                ,"package_name", appInfo.getPackage_name());
        return map;
    }

    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAppInfoSearchList() throws MsgBusinessException{
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
        return F.transKit.asMap("statusList",statusList,"languageList",languageList);
    }
    /**
     * app信息维护 - 获取app信息列表
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAppInfoByPage(String name, String language, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AppInfo.dao.queryAppInfoByPage(name, language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * app信息维护 - 添加app信息
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AppInfo addAppInfo(AuthOperator opt, AppInfoVO vo) throws MsgBusinessException {
        checkAppInfoModel(vo);
        AppInfo appInfo = AppInfo.dao.findByName(vo.getName());
        if(F.validate.isNotEmpty(appInfo.getApp_id())){
            throw new MsgBusinessException("该app名称已存在");
        }
        AppInfo app = AppInfo.dao.findByPackageName(vo.getPackage_name());
        if(F.validate.isNotEmpty(app.getApp_id())){
            throw new MsgBusinessException("该app包名已存在");
        }
        AppInfo model = new AppInfo();
        F.transKit.copyProperties(model, vo);
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * app信息维护 - 修改app信息
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AppInfo modifyAppInfo(AuthOperator opt, AppInfoVO vo) throws MsgBusinessException {
        checkAppInfoModel(vo);
        Boolean nameBool = AppInfo.dao.isHasName(vo.getSeq_uuid(),vo.getName());
        if (nameBool){
            throw new MsgBusinessException("该app名称已存在");
        }
        Boolean packageBool = AppInfo.dao.isHasPackageName(vo.getSeq_uuid(),vo.getPackage_name());
        if (packageBool){
            throw new MsgBusinessException("该app包名已存在");
        }
        AppInfo model = AppInfo.dao.findById(vo.getSeq_uuid());
        model.setName(vo.getName());
        model.setPackage_name(vo.getPackage_name());
        model.setRemark(vo.getRemark());
        model.setIs_show(vo.getIs_show());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * app信息维护 - 查找app信息
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findAppInfoById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("app信息编号不能为空");
        }
        AppInfo model = AppInfo.dao.findById(seq_uuid);
        Map data = outputAppInfo(model);
        return data;
    }

    /**
     * app信息维护 - 删除app信息
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteAppInfo(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("app编号不能为空");
        }
        AppInfo appInfo = AppInfo.dao.findById(seq_uuid);
        List<AppInfo> appList = AppInfo.dao.findByAppCode(appInfo.getApp_code());
        for (AppInfo app : appList){
            app.setStatus("4");
            app.setIs_show("N");
            app.saveOrUpdate(opt);
        }
    }
    /**
     * app信息维护 - 输出app信息信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputAppInfo(AppInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"app_id",model.getApp_id()
                ,"name",model.getName()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"app_code",model.getApp_code()
                ,"package_name",model.getPackage_name()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus());
        return data;
    }

    /**
     * app信息维护 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkAppInfoModel(AppInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(vo.getPackage_name())){
            throw new MsgBusinessException("包名不能为空");
        }
    }

    /**
     * 短信管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map querySmsLogSearchList() throws MsgBusinessException {
        List<SysDatalibrary> sysSmsTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_smsCodeType");
        List<Map> smsTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysSmsTypeList){
            smsTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysResultTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_smsResultType");
        List<Map> resultTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysResultTypeList){
            resultTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("smsTypeList",smsTypeList,"statusList",statusList,"resultTypeList",resultTypeList);
    }

    /**
     * 短信管理 - 获取短信发送历史列表
     * @param type
     * @param mobilephone
     * @param result_type
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map querySmsLogByPage(String type, String mobilephone,String result_type, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = SmsLog.dao.querySmsLogByPage(type, mobilephone,result_type,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 系统参数维护 - 获取系统参数列表
     * @param param_name
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map querySysParamByPage(String param_name, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = SysParam.dao.querySysParamByPage(param_name,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 系统参数维护 - 添加系统参数
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public SysParam addSysParam(AuthOperator opt, SysParamVO vo) throws MsgBusinessException {
        checkSysParamModel(vo);
        SysParam sysParam = SysParam.dao.findByName(vo.getParam_name());
        if(F.validate.isNotEmpty(sysParam.getParam_id())){
            throw new MsgBusinessException("该系统参数名称已存在");
        }
        SysParam sysParam1 = SysParam.dao.findByName(vo.getParam_value());
        if(F.validate.isNotEmpty(sysParam1.getParam_value())){
            throw new MsgBusinessException("该系统参数值已存在");
        }
        SysParam model = new SysParam();
        F.transKit.copyProperties(model, vo);
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 系统参数维护 - 修改系统参数
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public SysParam modifySysParam(AuthOperator opt, SysParamVO vo) throws MsgBusinessException {
        checkSysParamModel(vo);
        Boolean nameBool = SysParam.dao.isHaveParamName(vo.getSeq_uuid(),vo.getParam_name());
        if(nameBool){
            throw new MsgBusinessException("该系统参数名称已存在");
        }
        Boolean valueBool = SysParam.dao.isHaveParamName(vo.getSeq_uuid(),vo.getParam_value());
        if(valueBool){
            throw new MsgBusinessException("该系统参数值已存在");
        }
        SysParam model = SysParam.dao.findById(vo.getSeq_uuid());
        model.setParam_name(vo.getParam_name());
        model.setParam_value(vo.getParam_value());
        model.setRemark(vo.getRemark());
        model.setIs_show(vo.getIs_show());

        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 系统参数维护 - 删除系统参数
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteSysParam(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("系统参数编号不能为空");
        }
        SysParam sysParam = SysParam.dao.findById(seq_uuid);
        sysParam.setStatus("4");
        sysParam.setIs_show("N");
        sysParam.saveOrUpdate(opt);
    }

    /**
     * 系统参数维护 - 查找系统参数
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findSysParamById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("系统参数编号不能为空");
        }
        SysParam model = SysParam.dao.findById(seq_uuid);
        Map data = outputSysParam(model);
        return data;
    }

    /**
     * 系统参数维护 - 输出系统参数维护信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputSysParam(SysParam model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"param_id",model.getParam_id()
                ,"param_code",model.getParam_code()
                ,"param_name",model.getParam_name()
                ,"param_value",model.getParam_value()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 系统参数维护 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkSysParamModel(SysParamVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getParam_name())){
            throw new MsgBusinessException("系统参数名称不能为空");
        }
        if (F.validate.isEmpty(vo.getParam_value())){
            throw new MsgBusinessException("系统参数值不能为空");
        }
    }

    /**
     * 发送短信
     * @return
     */
    @Before(TTx.class)
    public void sendSmsCode(String mobilephone, String type, String realIpAddr) throws MsgBusinessException {
        //验证参数的合法性
        validataSms(mobilephone,type, realIpAddr);

        //发送验证码
        String code = "";
        //如果短信验证码存在，则发送原号码
        SmsCode smsCode = SmsCode.dao.findByMobile(mobilephone,type);
        if(smsCode.isEmpty()){
            //验证码随机四位数
            code = "111111";//F.randomKit.randomString(6,F.randomKit.SEED_NUM_NOZERO);
            //保存验证码
            SmsCode.dao.addSmsCode(type,mobilephone,F.strKit.f("验证码为%s",code),code);
            //保存验证码log
            SmsLog.dao.addSmslog(type,mobilephone,F.strKit.f("验证码为%s",code));
        }else {
            code = smsCode.getCode();
        }
        //发送短信
//        PhjfSmsPushClient.sendSmsCode(mobilephone,code);

    }

    //验证 发送短信 参数的合法性
    public boolean validataSms(String mobilephone,String type, String realIpAddr) throws MsgBusinessException {
        if(F.validate.isEmpty(mobilephone)){
            throw new MsgBusinessException("手机号不能为空");
        }
        if(F.validate.isEmpty(type)){
            throw new MsgBusinessException("验证码类型不能为空");
        }
        if(!F.validate.isMobile(mobilephone)){
            throw new MsgBusinessException("手机号码格式不正确");
        }
        SysDatalibrary typeLib = SysDatalibrary.dao.findSysDatalibrary(AdminConstants.ZH_SIMP,AdminConstants.Datalibrary_Type_SysSmsCodeType,type);
        if(typeLib.isEmpty()){
            throw new MsgBusinessException("短信发送类型不正确");
        }
        //验证码在60秒之内
        if(!SmsCode.dao.isCanSend(type,mobilephone)){
            throw new MsgBusinessException("操作过于频繁，请稍后再试");
        }
        FlowControlKit.ruleSmsAttack(FlowControlEnum.CK_PHJF_SYS_FC_MINTIMEKEY.getKey(),"系统繁忙，请稍后再试");//防止短信攻击 规则1 每分钟最多2000条 sys.FlContr_smsCode.count=2000
        FlowControlKit.ruleSmsIpAttack(FlowControlEnum.CK_Phjf_SYS_FC_SMSCODE_IP.getKey(), realIpAddr,"系统繁忙，请稍后再试", AdminConstants.PLATFORM);//ip
        FlowControlKit.ruleSmsUserTimesLimit(FlowControlEnum.CK_Phjf_SYS_FC_SMSCODE_MOBILEPHONE.getKey(), mobilephone,"操作过于频繁，请稍后再试", AdminConstants.PLATFORM);//账户
        return true;
    }
    public boolean checkCode(String mobiphone, String type, String code){
        return SmsCode.dao.checkCode(mobiphone,type,code);
    }

    /**
     * @Title:
     * @Description: 获取业务名称
     * @author: DukeMr.Chen
     */
    public Map<String, String> getBusMap(String url){
        Map<String, String> retMap = new HashMap<String, String>();
        AuthService authService = AuthService.dao.getAuthServiceByUrl(url);
        String idPath = "";
        String busPath = "";
        String busMethod = "";
        String busTarget = "";
        String busContent = "";
        if(F.validate.isEmpty(authService)){
            AuthResource authResource = AuthResource.dao.getAuthResourceByUrl(url);
            if(!F.validate.isEmpty(authResource)){
                idPath = authResource.getTreepath();
                String type = authResource.getType();
                if("button".equals(type)){
                    busMethod =  authResource.getName();
                }else if("menu".equals(type)){
                    busMethod =  "进入主工作区";
                }
                busTarget = authResource.getOpt_target();
                busContent = authResource.getOpt_content();
            }
        }else{
            busMethod = authService.getName();
            busTarget = authService.getOpt_target();
            busContent = authService.getOpt_content();
            AuthResourceService authResourceService= AuthResourceService.dao.getByServiceId(authService.getSeq_id());
            AuthResource authResource = AuthResource.dao.findById(authResourceService.getType_seq_id());
            idPath = authResource.getTreepath();
        }
        if(!F.validate.isEmpty(idPath)){
            idPath = idPath.replaceAll("/", ",");
            List<AuthResource> list = AuthResource.dao.getResourceTree(idPath);
            for (AuthResource obj : list) {
                busPath += "/" + obj.getName();
            }
            busPath = busPath.length() > 0 ? busPath.substring(1) : busPath;
        }
        if(!"".equals(busPath) || !"".equals(busMethod) || !"".equals(busTarget) || !"".equals(busContent)){
            retMap.put("BusPath", busPath);
            retMap.put("BusMethod" , busMethod);
            retMap.put("BusTarget" , busTarget);
            retMap.put("BusContent", busContent);
        }else{
            retMap = null;
        }

        return retMap;
    }

    public final static String POSITION_OPERATOR = "://";

    /**
     * @Title:
     * @Description: 处理ActionKey
     * @author: DukeMr.Chen
     */
    public String getActionKey(String url) {
        if(StringUtils.isBlank(url)){
            return "";
        }
        int index = url.indexOf(POSITION_OPERATOR) + POSITION_OPERATOR.length();
        url = url.substring(index);
        int startIndex = url.indexOf("/") ;
        url = url.substring(startIndex);
        return url;
    }
}
