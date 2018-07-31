package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthBranchService;
import com.bankwel.phjf_admin.common.model.core.AuthBranch;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyuhu on 2017/9/19.
 */
public class AuthBranchController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthBranchController.class);

    private AuthBranchService branchService = Duang.duang(AuthBranchService.class);

    //http://localhost:19093/phjfht/api/v1/auth/goBranchPage
    @ActionKey("/phjfht/api/v1/auth/goBranchPage")
    public void goBranchPage() {
        this.checkSensitiveMsg();
        AuthOperator user = this.getUser();
        this.setAttr("operator",user);
        this.render("/WEB-INF/velocity/auth/authBranch/branchQuery.vm");
    }

    //http://localhost:19093/phjfht/api/v1/auth/queryBranchByPage?name=aaa&parent_seq_id=1&page_id=1
    @ActionKey("/phjfht/api/v1/auth/queryBranchByPage")
    public void queryBranchByPage() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map result = null;
        try{
            this.checkSensitiveMsg();
            AuthBranch model = this.getBean(AuthBranch.class, "");
            PageKit page = this.getBean(PageKit.class, "");
            result = branchService.queryAuthBranchByPage(model,page);
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }

    //http://localhost:19093/phjfht/api/v1/auth/queryBranchList
    @ActionKey("/phjfht/api/v1/auth/queryBranchList")
    public void queryBranchList() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        List<Map> result = new ArrayList<Map>();
        try {
            this.checkSensitiveMsg();
            result = branchService.queryAuthBranchList();
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }

    //http://localhost:19093/phjfht/api/v1/auth/queryOptBranchList
    @ActionKey("/phjfht/api/v1/auth/queryOptBranchList")
    public void queryOptBranchList() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        List<AuthBranch> result = null;
        try {
            this.checkSensitiveMsg();
            AuthOperator user = this.getUser();
            String branch_seq_id = this.getPara("branch_seq_id");
            result = AuthBranch.dao.queryOptBranchList(user,branch_seq_id);
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }
    //http://localhost:19093/phjfht/api/v1/auth/queryOptBranchList
    @ActionKey("/phjfht/api/v1/auth/queryOptAuthBranchList")
    public void queryOptAuthBranchList() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        List<Map> result = null;
        try {
            this.checkSensitiveMsg();
            String operator_seq_id = this.getPara("operator_seq_id");
            result = branchService.queryOptAuthBranchList(operator_seq_id);
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }
    //http://localhost:19093/phjfht/api/v1/auth/goAddBranchPage
    @ActionKey("/phjfht/api/v1/auth/goAddBranchPage")
    public void goAddBranchPage() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map map = null;
        try {
            this.checkSensitiveMsg();
            AuthBranch model = this.getBean(AuthBranch.class, "");
            map = F.transKit.asMap("treepath", model.getTreepath(), "parent_seq_id", model.getParent_seq_id());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        this.setAttrs(map);
        this.render("/WEB-INF/velocity/auth/authBranch/branchAdd.vm");
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
    }

    //http://localhost:19093/phjfht/api/v1/auth/addBranch
    @ActionKey("/phjfht/api/v1/auth/addBranch")
    public void addBranch() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthBranch model = this.getBean(AuthBranch.class, "");
            AuthOperator user = this.getUser();
            AuthBranch result = branchService.addBranch(model, user);
            map = F.transKit.asMap("code", 1, "msg", "", "data",result) ;
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

    //http://localhost:19093/phjfht/api/v1/auth/goModifyBranchPage
    @ActionKey("/phjfht/api/v1/auth/goModifyBranchPage")
    public void goModifyBranchPage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthBranch model = this.getBean(AuthBranch.class, "");
            Map result = branchService.queryAuthBranchInfo(model);
            map = F.transKit.asMap("code", 1, "msg", "", "data", result);
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
        this.setAttrs(map);
        this.render("/WEB-INF/velocity/auth/authBranch/branchModify.vm");
    }
    //http://localhost:19093/phjfht/api/v1/auth/modifyBranch?
    @ActionKey("/phjfht/api/v1/auth/modifyBranch")
    public void modifyBranch() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthBranch model = this.getBean(AuthBranch.class, "");
            AuthOperator user = this.getUser();
            branchService.modifyBranch(model, user);
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

    //http://localhost:19093/phjfht/api/v1/auth/deleteBranch?
    @ActionKey("/phjfht/api/v1/auth/deleteBranchById")
    public void deleteBranchById(){
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthBranch model = this.getBean(AuthBranch.class, "");
            branchService.deleteBranchById(""+model.getSeq_uuid());
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

    //http://localhost:19093/phjfht/api/v1/auth/goBranchAuthPage
    @ActionKey("/phjfht/api/v1/auth/goBranchAuthPage")
    public void goBranchAuthPage() throws MsgBusinessException {
        Map result = null;
        try{
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid", "");
            result = branchService.queryBranchById(seq_uuid);
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        this.setAttrs(result);
        this.render("/WEB-INF/velocity/auth/authBranch/branchAuth.vm");
    }
}
