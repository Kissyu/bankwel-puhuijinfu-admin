package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.ManagepointInfo;
import com.bankwel.phjf_admin.common.model.core.WebNav;
import com.bankwel.phjf_admin.service.DatalibraryService;
import com.bankwel.phjf_admin.service.UserService;
import com.bankwel.phjf_admin.service.WebNavService;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.ManagepointInfoVO;
import com.bankwel.phjf_admin.webapi.vo.WebNavVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import org.apache.commons.io.FileUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/25.
 */
public class WebNavController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(WebNavController.class);
    private DatalibraryService datalibraryService = Duang.duang(DatalibraryService.class);
    private WebNavService webNavService = Duang.duang(WebNavService.class);
    private AuthOperatorService operatorService = Duang.duang(AuthOperatorService.class);

    /**
     * 导航管理 - 跳转导航列表查询页
     * http://localhost:8081/phjfwebht/api/v1/nav/goNavPage
     */
    @ActionKey("/phjfwebht/api/v1/nav/goNavPage")
    public void goNavPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = webNavService.queryNavSearchList();
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
        this.render("/WEB-INF/velocity/webNav/navQuery.vm");
    }
    /**
     * 导航管理 - 导航分页查询
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/queryNavByPage
    @ActionKey("/phjfwebht/api/v1/nav/queryNavByPage")
    public void queryNavByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = this.getPara("language");
            String name = this.getPara("nav_name");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = webNavService.queryNavByPage(language, name,status, page);
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
     * 导航管理 - 跳转中文导航管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/goAddSimpNavPage
    @ActionKey("/phjfwebht/api/v1/nav/goAddSimpNavPage")
    public void goAddSimpNavPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language= AdminConstants.LanguageType.ZH_SIMP.value;
            String languageDesc=AdminConstants.LanguageType.ZH_SIMP.desc;
            String parent_nav_code = this.getPara("parent_nav_code");
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",F.transKit.asMap("language",language,"language_show",languageDesc,"parent_nav_code",parent_nav_code));
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
        this.renderJsp("/WEB-INF/velocity/webNav/navAdd.jsp");
    }
    /**
     * 导航管理 - 新增导航
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/addNav
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/nav/addNav")
    public void addNav() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebNavVO vo = this.getBean(WebNavVO.class,"");
            AuthOperator opt = this.getUser();
            WebNav webNav = webNavService.addManagepoint(opt,vo);
            WebNav webNav1 = webNavService.savePath(opt,webNav.getParent_nav_code(),webNav.getLanguage(), String.valueOf(webNav.getNav_id()),webNav.getNav_code());
            Map<String,String> data = webNavService.findNavById(webNav1.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            webNavService.generateNavs(webNav.getLanguage());
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
            this.renderJsp("/WEB-INF/velocity/webNav/navAddShow.jsp");
        }else {
            this.renderJsp("/WEB-INF/velocity/webNav/navAdd.jsp");
        }
    }

    /**
     * 导航管理 - 跳转维语导航管理新增页
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/goAddUeyNavPage
    @ActionKey("/phjfwebht/api/v1/nav/goAddUeyNavPage")
    public void goAddUeyNavPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String language = AdminConstants.LanguageType.ZH_UEY.value;
            Map data = webNavService.findUeyNavInfo(seq_uuid,language);
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
        this.renderJsp("/WEB-INF/velocity/webNav/navAdd.jsp");
    }
    /**
     * 导航管理 - 跳转导航修改页
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/goModifyNavPage
    @ActionKey("/phjfwebht/api/v1/nav/goModifyNavPage")
    public void goModifyNavPage() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = webNavService.findNavById(seq_uuid);
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
        this.renderJsp("/WEB-INF/velocity/webNav/navModify.jsp");
    }
    /**
     * 导航管理 - 修改导航
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/modifyNav
    @Before(value= SensitiveWordsInterceptor.class)
    @ActionKey("/phjfwebht/api/v1/nav/modifyNav")
    public void modifyNav() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            WebNavVO vo = this.getBean(WebNavVO.class,"");
            AuthOperator opt = this.getUser();
            WebNav model = webNavService.modifyNav(opt,vo);
            Map<String,String> data = webNavService.findNavById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
            webNavService.generateNavs(model.getLanguage());
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
        this.renderJsp("/WEB-INF/velocity/webNav/navModify.jsp");
    }
    /**
     * 导航管理 - 删除导航
     */
    //http://localhost:8081/phjfwebht/api/v1/nav/deleteNav
    @ActionKey("/phjfwebht/api/v1/nav/deleteNav")
    public void deleteNav() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            webNavService.deleteNav(user,seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
            webNavService.generateNavs(AdminConstants.LanguageType.ZH_SIMP.value);
            webNavService.generateNavs(AdminConstants.LanguageType.ZH_UEY.value);
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
}
