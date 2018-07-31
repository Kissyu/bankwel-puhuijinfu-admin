package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthOperatorAuthService;
import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by bankwel on 2017/9/20.
 */
public class AuthOperatorAuthController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthOperatorAuthController.class);
    private AuthOperatorService operatorService = Duang.duang(AuthOperatorService.class);
    private AuthOperatorAuthService operatorAuthService = Duang.duang(AuthOperatorAuthService.class);

    //http://localhost:19093/phjfht/api/v1/auth/goOperatorAuthPage
    @ActionKey("/phjfht/api/v1/auth/goOperatorAuthPage")
    public void goOperatorAuthPage() throws MsgBusinessException {
        Map result = null;
        try{
            this.checkSensitiveMsg();
            AuthOperatorAuth model = this.getBean(AuthOperatorAuth.class, "");
            result = operatorService.getOperatorBySeqId("" + model.getOperator_seq_id());
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        this.setAttrs(result);
        this.render("/WEB-INF/velocity/auth/authOperatorAuth/branchAuthNav.vm");
    }

    //http://localhost:19093/phjfht/api/v1/auth/queryBranchRoles?operator_seq_id=1&branch_seq_id=1
    @ActionKey("/phjfht/api/v1/auth/queryBranchRoles")
    public void queryBranchRoles(){
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperatorAuth model = this.getBean(AuthOperatorAuth.class, "");
            map = operatorAuthService.queryRoleList(""+model.getOperator_seq_id(),""+model.getBranch_seq_id());
        }  catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(map);
    }

    //http://localhost:19093/phjfht/api/v1/auth/goBranchPage
    @ActionKey("/phjfht/api/v1/auth/goRoleAuthPage")
    public void goRoleAuthPage() {
        this.render("/WEB-INF/velocity/auth/authOperatorAuth/roleAuth.vm");
    }

    //http://localhost:19093/phjfht/api/v1/modifyBranchRoles
    @ActionKey("/phjfht/api/v1/auth/modifyBranchRoles")
    public void modifyBranchRoles(){
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperatorAuth model = this.getBean(AuthOperatorAuth.class, "");
            String[] roleIds = this.getParaValues("roleIds[]");
            AuthOperator user = this.getUser();
            operatorAuthService.modifyBranchRoles(""+user.getSeq_id(),""+model.getOperator_seq_id(),""+model.getBranch_seq_id(),roleIds);
            map = F.transKit.asMap("code", 1, "msg", "", "data", "");
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
