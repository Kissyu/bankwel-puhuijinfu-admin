package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthBranchApplyService;
import com.bankwel.phjf_admin.auth.service.AuthBranchService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/12.
 */
public class AuthBranchApplyController extends PhjfAdminBaseController {

    private static final Logger log = LoggerFactory.getLogger(AuthBranchApplyController.class);

    private AuthBranchApplyService branchApplyService = Duang.duang(AuthBranchApplyService.class);

    @ActionKey("/phjfht/api/v1/auth/queryBranchApply")
    public void queryBranchApply(){

        List<Record> result = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String branch_seq_id = this.getPara("branch_seq_id", "");
            result = branchApplyService.queryListByBranchId(branch_seq_id);
        }  catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }

    @ActionKey("/phjfht/api/v1/auth/modifyBranchApply")
    public void modifyBranchApply(){
        Map result = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String branch_seq_id = this.getPara("branch_seq_id", "");
            String[] apply_seq_ids = this.getParaValues("apply_seq_ids[]");
            AuthOperator user = this.getUser();
            branchApplyService.modifyBranchApply(user, branch_seq_id, apply_seq_ids);
            result = F.transKit.asMap("code", 1, "msg", "", "data", "");
        }  catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            result = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            result = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }

}
