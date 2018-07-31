package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthSerService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthResource;
import com.bankwel.phjf_admin.common.model.core.AuthService;
import com.bankwel.phjf_admin.common.model.core.SysApplyCenter;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */
public class AuthServiceController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceController.class);

    private AuthSerService authSerService = Duang.duang(AuthSerService.class);

    /**
     * 跳转服务添加页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goAddServicePage
    @ActionKey("/phjfht/api/v1/auth/goAddServicePage")
    public void goAddServicePage() {
        Map map =null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(),this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String resource_id = this.getPara("resourceId","");
            List<SysApplyCenter> result = SysApplyCenter.dao.queryAllApplyList();
            AuthResource resource = AuthResource.dao.getBySeqId(resource_id);
            map = F.transKit.asMap("resourceId",resource_id,"applyList",result,"parent_name",resource.getName()) ;
        }catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }

        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        this.render("/WEB-INF/velocity/auth/service/serviceAdd.vm");
    }

    /**
     * 添加服务
     */
    //http://localhost:8081/phjfht/api/v1/auth/addService
    @ActionKey("/phjfht/api/v1/auth/addService")
    public void addService() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthService model = this.getBean(AuthService.class, "");
            int resource_id = this.getParaToInt("resourceId");
            AuthOperator user = this.getUser();
            authSerService.addService(model, resource_id, user);
            map = F.transKit.asMap("code", 1, "msg", "", "data","") ;
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(map);
    }

    /**
     * 删除服务
     */
    //http://localhost:8081/phjfht/api/v1/auth/deleteServiceById
    @ActionKey("/phjfht/api/v1/auth/deleteServiceById")
    public void deleteServiceById() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid","");
            authSerService.deleteServiceById(seq_uuid);
            map = F.transKit.asMap("code", 1, "msg", "", "data","") ;
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(map);
    }

    /**
     * 跳转修改服务页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goModifyServicePage
    @ActionKey("/phjfht/api/v1/auth/goModifyServicePage")
    public void goModifyServicePage() {
        Map map = null;
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid", "");
            AuthService result = authSerService.getServiceById(seq_uuid);
            List<SysApplyCenter> applyList = SysApplyCenter.dao.queryAllApplyList();
            map = F.transKit.asMap("data",result,"applyList",applyList) ;
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        this.setAttrs(map);
        this.render("/WEB-INF/velocity/auth/service/serviceAdd.vm");
    }

    /**
     * 修改服务
     */
    //http://localhost:8081/phjfht/api/v1/auth/modifyService
    @ActionKey("/phjfht/api/v1/auth/modifyService")
    public void modiAuthService() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthService model = this.getBean(AuthService.class, "");
            AuthOperator user = this.getUser();
            authSerService.modifyService(model, user);
            map = F.transKit.asMap("code", 1, "msg", "", "data","") ;
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(map);
    }

}
