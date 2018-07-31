package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthRoleResourceService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/9.
 */
public class AuthRoleResourceController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthRoleController.class);

    private AuthRoleResourceService authRoleResourceService = Duang.duang(AuthRoleResourceService.class);

    /**
     * 角色分配资源
     */
    //http://localhost:8081/phjfht/api/v1/auth/allotResource
    @ActionKey("/phjfht/api/v1/auth/allotResource")
    public void allotResource() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String[] resource_ids = this.getParaValues("resourceIds");
            if(F.validate.isEmpty(resource_ids)){
                resource_ids = new String[0];
            }
            String role_id =this.getPara("roleId");
            AuthOperator user = this.getUser();
            authRoleResourceService.addRoleResource(resource_ids,role_id,user);
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
