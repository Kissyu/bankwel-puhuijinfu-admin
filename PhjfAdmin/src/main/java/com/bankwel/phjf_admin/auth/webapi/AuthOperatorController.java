package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by admin on 2017/9/19.
 */
public class AuthOperatorController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthOperatorController.class);

    private AuthOperatorService operatorService = Duang.duang(AuthOperatorService.class);

    @ActionKey("/phjfht/api/v1/auth/goOperatorPage")
    public void goOperatorPage() {
        this.checkSensitiveMsg();
        AuthOperator user = this.getUser();
        this.setAttr("operator",user);
        this.render("/WEB-INF/velocity/auth/authOperator/operatorQuery.vm");
    }

    //http://localhost:19093/phjfht/api/v1/auth/queryOperatorByPage?true_name=aaa&branch_seq_id=1&opt_type=1&page_id=1
    @ActionKey("/phjfht/api/v1/auth/queryOperatorByPage")
    public void queryOperatorByPage() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map result = null;
        try{
            this.checkSensitiveMsg();
            AuthOperator model = this.getBean(AuthOperator.class, "");
            String branch_seq_id = this.getPara("branch_id");
            PageKit page = this.getBean(PageKit.class, "");
            result = operatorService.queryOperatorByPage(branch_seq_id,model, page);
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

    //http://localhost:19093/phjfht/api/v1/auth/goAddOperatorPage
    @ActionKey("/phjfht/api/v1/auth/goAddOperatorPage")
    public void goAddOperatorPage() {
        Map map = null;
        try{
            this.checkSensitiveMsg();
            this.render("/WEB-INF/velocity/auth/authOperator/operatorAdd.vm");
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            map = F.transKit.asMap("code", e.getSuccess(), "msg", e.getMessage());
            this.renderJson(map);
        }
    }

    //http://localhost:19093/phjfht/api/v1/auth/addOperator
    @ActionKey("/phjfht/api/v1/auth/addOperator")
    public void addOperator() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperator model = this.getBean(AuthOperator.class, "");
            AuthOperator user = this.getUser();
            operatorService.addOperator(model, user);
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

    //http://localhost:19093/phjfht/api/v1/auth/goModifyOperatorPage?seq_uuid=1
    @ActionKey("/phjfht/api/v1/auth/goModifyOperatorPage")
    public void goModifyOperatorPage() throws MsgBusinessException {
        AuthOperator model = this.getBean(AuthOperator.class, "");
        Map result =null;
        try {
            this.checkSensitiveMsg();
             result = operatorService.getOperatorById("" + model.getSeq_uuid());
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        this.setAttrs(result);
        this.render("/WEB-INF/velocity/auth/authOperator/operatorModify.vm");
    }

    //http://localhost:19093/phjfht/api/v1/auth/modifyOperator
    @ActionKey("/phjfht/api/v1/auth/modifyOperator")
    public void modifyOperator() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperator model = this.getBean(AuthOperator.class, "");
            AuthOperator user = this.getUser();
            operatorService.modifyOperator(model, user);
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

    //http://localhost:19093/phjfht/api/v1/auth/deleteOperator?seq_uuid
    @ActionKey("/phjfht/api/v1/auth/deleteOperatorById")
    public void deleteOperatorById(){
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperator model = this.getBean(AuthOperator.class, "");
            AuthOperator user = this.getUser();
            operatorService.deleteOperatorById(""+model.getSeq_uuid());
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
        renderJson(map);
    }
}
