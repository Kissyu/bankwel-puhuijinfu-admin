package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.AppReqHeader;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AppInfo;
import com.bankwel.phjf_admin.common.model.core.AppVersionInfo;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.SysParam;
import com.bankwel.phjf_admin.service.DatalibraryService;
import com.bankwel.phjf_admin.service.SystemService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.AppInfoVO;
import com.bankwel.phjf_admin.webapi.vo.AppVersionInfoVO;
import com.bankwel.phjf_admin.webapi.vo.SysParamVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/21.
 */
public class SystemController extends PhjfAdminBaseController{
    private static final Logger log = LoggerFactory.getLogger(SystemController.class);
    SystemService systemService = Duang.duang(SystemService.class);
    DatalibraryService datalibraryService = Duang.duang(DatalibraryService.class);
    private AuthOperatorService operatorService = Duang.duang(AuthOperatorService.class);
    /**
     *
     * app发布管理 - 跳转app发布管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/system/goAppVersionQueryPage
    @ActionKey("/phjfht/api/v1/system/goAppVersionQueryPage")
    public void goAppVersionQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = systemService.queryAppVersionSearchList();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.render("/WEB-INF/velocity/system/appVersionInfo/appVersionQuery.vm");
    }

    /**
     * app发布管理 - app发布管理查询
     */
    //http://localhost:8081/phjfht/api/v1/system/queryAppVersionByPage
    @ActionKey("/phjfht/api/v1/system/queryAppVersionByPage")
    public void queryAppVersionByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String app_version = this.getPara("app_version");
            String is_update = this.getPara("is_update");
            String channel = this.getPara("channel");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = systemService.queryAppVersionByPage(app_version, is_update,channel,status, page);
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
     * app发布管理 - 跳转app发布管理新增页
     */
    //http://localhost:8081/phjfht/api/v1/system/goAddAppVersionPage
    @ActionKey("/phjfht/api/v1/system/goAddAppVersionPage")
    public void goAddAppVersionPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data","");
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
//        this.setAttrs(resultMap);
        this.renderJsp("/WEB-INF/velocity/system/appVersionInfo/appVersionAdd.jsp");
    }

    /**
     * app发布管理 - app发布新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/system/addAppVersion")
    public void addAppVersion(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AppVersionInfoVO vo = this.getBean(AppVersionInfoVO.class,"");
            AuthOperator opt = this.getUser();
            AppVersionInfo appVersionInfo = systemService.addAppVersion(opt,vo);
            Map<String,String> data = systemService.findAppVersionById(appVersionInfo.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.setAttrs(returnMap);
        if("1".equals(returnMap.get("code")+"")){
            this.renderJsp("/WEB-INF/velocity/system/appVersionInfo/appVersionAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/system/appVersionInfo/appVersionAdd.jsp");
        }
    }

    /**
     * app发布管理 - 跳转app发布修改页
     */
    @ActionKey("/phjfht/api/v1/system/goModifyAppVersionPage")
    public void goModifyAppVersionPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = systemService.findAppVersionById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/system/appVersionInfo/appVersionModify.jsp");
    }

    /**
     * app发布管理 - 修改app发布
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/system/modifyAppVersion")
    public void modifyAppVersion(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AppVersionInfoVO vo = this.getBean(AppVersionInfoVO.class,"");
            AuthOperator opt = this.getUser();
            AppVersionInfo model = systemService.modifyAppVersion(opt,vo);
            Map<String,String> data = systemService.findAppVersionById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/system/appVersionInfo/appVersionModify.jsp");
    }

    /**
     * app发布管理 - 发布
     */
    //http://localhost:8081/phjfht/api/v1/system/publishAppVersion
    @ActionKey("/phjfht/api/v1/system/publishAppVersion")
    public void publishAppVersion() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            systemService.publishAppVersion(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     * app发布管理 - 下架
     */
    //http://localhost:8081/phjfht/api/v1/system/unPublishAppVersion
    @ActionKey("/phjfht/api/v1/system/unPublishAppVersion")
    public void unPublishAppVersion() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            systemService.unPublishAppVersion(opt,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     *
     * app信息维护 - 跳转app信息维护查询页
     */
    //http://localhost:8081/phjfht/api/v1/system/goAppInfoQueryPage
    @ActionKey("/phjfht/api/v1/system/goAppInfoQueryPage")
    public void goAppInfoQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = systemService.queryAppInfoSearchList();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.render("/WEB-INF/velocity/system/appInfo/appInfoQuery.vm");
    }

    /**
     * app信息维护 - app信息维护查询
     */
    //http://localhost:8081/phjfht/api/v1/system/queryAppInfoByPage
    @ActionKey("/phjfht/api/v1/system/queryAppInfoByPage")
    public void queryAppInfoByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name");
            String language = this.getPara("language",AdminConstants.ZH_SIMP);
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = systemService.queryAppInfoByPage(name, language,status, page);
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
     * app信息维护 - 跳转app信息维护新增页
     */
    //http://localhost:8081/phjfht/api/v1/system/goAddAppInfoPage
    @ActionKey("/phjfht/api/v1/system/goAddSimpAppInfoPage")
    public void goAddAppInfoPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String language_show=AdminConstants.LanguageType.ZH_SIMP.desc;
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",language_show));
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
        this.renderJsp("/WEB-INF/velocity/system/appInfo/appInfoAdd.jsp");
    }

    /**
     * app信息维护 - app信息新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/system/addAppInfo")
    public void addAppInfo(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AppInfoVO vo = this.getBean(AppInfoVO.class,"");
            AuthOperator opt = this.getUser();
            AppInfo appInfo = systemService.addAppInfo(opt,vo);
            Map<String,String> data = systemService.findAppInfoById(appInfo.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.setAttrs(returnMap);
        if("1".equals(returnMap.get("code")+"")){
            this.renderJsp("/WEB-INF/velocity/system/appInfo/appInfoAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/system/appInfo/appInfoAdd.jsp");
        }
    }

    /**
     * app信息维护 - 跳转app信息维护修改页
     */
    @ActionKey("/phjfht/api/v1/system/goModifyAppInfoPage")
    public void goModifyAppInfoPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = systemService.findAppInfoById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/system/appInfo/appInfoModify.jsp");
    }

    /**
     * app信息维护 - 修改app信息
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/system/modifyAppInfo")
    public void modifyAppInfo(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AppInfoVO vo = this.getBean(AppInfoVO.class,"");
            AuthOperator opt = this.getUser();
            AppInfo model = systemService.modifyAppInfo(opt,vo);
            Map<String,String> data = systemService.findAppInfoById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/system/appInfo/appInfoModify.jsp");
    }

    /**
     * app信息维护 - 失效
     */
    //http://localhost:8081/phjfht/api/v1/system/deleteAppInfo
    @ActionKey("/phjfht/api/v1/system/deleteAppInfo")
    public void deleteAppInfo() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid", "");
            AuthOperator opt = this.getUser();
            systemService.deleteAppInfo(opt, seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "失效成功", "data", "");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data","");
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", "") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     *
     * 短信管理 - 跳转短信管理查询页
     */
    //http://localhost:8081/phjfht/api/v1/system/goSmsLogQueryPage
    @ActionKey("/phjfht/api/v1/system/goSmsLogQueryPage")
    public void goSmsLogQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = systemService.querySmsLogSearchList();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.render("/WEB-INF/velocity/system/smsLog/smsLogQuery.vm");
    }

    /**
     * 短信管理 - 短信管理查询
     */
    //http://localhost:8081/phjfht/api/v1/system/querySmsLogByPage
    @ActionKey("/phjfht/api/v1/system/querySmsLogByPage")
    public void querySmsLogByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String type = this.getPara("type");
            String mobilephone = this.getPara("mobilephone");
            String result_type = this.getPara("result_type");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = systemService.querySmsLogByPage(type, mobilephone,result_type,status, page);
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
     * 系统参数维护 - 跳转系统参数维护查询页
     */
    @ActionKey("/phjfht/api/v1/system/goSysParamQueryPage")
    public void goSysParamQueryPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            List<Map> statusList = datalibraryService.queryByParentCode("phjf","sys_status");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("statusList",statusList));
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
        this.render("/WEB-INF/velocity/system/sysParam/sysParamQuery.vm");
    }

    /**
     * 系统参数维护 - 系统参数维护查询
     */
    @ActionKey("/phjfht/api/v1/system/querysysParamByPage")
    public void querysysParamByPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String param_name = this.getPara("param_name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = systemService.querySysParamByPage(param_name,status, page);
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
     * 系统参数维护 - 跳转系统参数新增页
     */
    @ActionKey("/phjfht/api/v1/system/goAddSysParamPage")
    public void goAddSysParamPage(){
        this.renderJsp("/WEB-INF/velocity/system/sysParam/sysParamAdd.jsp");
    }

    /**
     * 系统参数维护 - 系统参数新增
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/system/addSysParam")
    public void addSysParam(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            SysParamVO vo = this.getBean(SysParamVO.class,"");
            AuthOperator opt = this.getUser();
            SysParam sysParam = systemService.addSysParam(opt,vo);
            Map<String,String> data = systemService.findSysParamById(sysParam.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.setAttrs(returnMap);
        if("1".equals(returnMap.get("code")+"")){
            this.renderJsp("/WEB-INF/velocity/system/sysParam/sysParamAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/system/sysParam/sysParamAdd.jsp");
        }
    }

    /**
     * 系统参数维护 - 跳转系统参数修改页
     */
    @ActionKey("/phjfht/api/v1/system/goModifySysParamPage")
    public void goModifySimpSysParamPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = systemService.findSysParamById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/system/sysParam/sysParamModify.jsp");
    }

    /**
     * 系统参数维护 - 修改系统参数
     */
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfht/api/v1/system/modifySysParam")
    public void modifySysParam(){
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            SysParamVO vo = this.getBean(SysParamVO.class,"");
            AuthOperator opt = this.getUser();
            SysParam model = systemService.modifySysParam(opt,vo);
            Map<String,String> data = systemService.findSysParamById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/system/sysParam/sysParamModify.jsp");
    }

    /**
     * 系统参数维护管理 - 失效
     */
    //http://localhost:8081/phjfht/api/v1/system/deleteSysParam
    @ActionKey("/phjfht/api/v1/system/deleteSysParam")
    public void deleteSysParam() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            systemService.deleteSysParam(user,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    @ActionKey("/phjfht/api/v1/system/sendSmsCode")
    public void sendSmsCode(){
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(),this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
             this.getUser();
            String mobilephone  = this.getPara("mobilephone","");
            String userName = this.getPara("userName","");
            String type  = this.getPara("type","");
            if(type.equals(AdminConstants.Sms_Type_IPLOGINCHECK)){
                String password  = this.getPara("password","");
                AuthOperator authOperator = operatorService.getAuthOperatorMobile(userName, password);
                if(null!=authOperator){
                    mobilephone = authOperator.getMobile();
                }else{
                    throw new MsgBusinessException("用户名或密码不正确");
                }
            }
            systemService.sendSmsCode(mobilephone,type, this.getRealIpAddr());
            resultMap = F.transKit.asMap("code", 1, "msg", "信息发送成功", "data","");
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }
}
