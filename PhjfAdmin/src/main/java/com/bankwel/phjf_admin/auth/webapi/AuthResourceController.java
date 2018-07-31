package com.bankwel.phjf_admin.auth.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthResService;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthResource;
import com.bankwel.phjf_admin.common.model.core.SysApplyCenter;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AuthResourceController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthResourceController.class);

    private AuthResService authResService = Duang.duang(AuthResService.class);

    /**
     * 跳转资源管理列表页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goResourcePage
    @ActionKey("/phjfht/api/v1/auth/goResourcePage")
    public void goResourcePage() {
        this.checkSensitiveMsg();
        this.render("/WEB-INF/velocity/auth/authResource/resourceQuery.vm");
    }

    /**
     * 资源列表查询
     */
    //http://localhost:8081/phjfht/api/v1/auth/queryResourceByPage
    @ActionKey("/phjfht/api/v1/auth/queryResourceByPage")
    public void queryResourceByPage() {
        Map result = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name","");
            String url = this.getPara("url","");
            String type = this.getPara("type","");
            AuthOperator user = this.getUser();
            PageKit page = this.getBean(PageKit.class, "");
            result = authResService.queryResourceByPage(user.getSeq_id()+"",name,url,type,page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(result);
    }

    /**
     * 跳转资源添加页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goAddResourcePage
    @ActionKey("/phjfht/api/v1/auth/goAddResourcePage")
    public void goAddResourcePage() {
        Map map =null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String parent_seq_id = this.getPara("parent_seq_id","0");
            String type = this.getPara("type","");
            map = authResService.findAddPageData(parent_seq_id,type);
        }catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        this.render("/WEB-INF/velocity/auth/authResource/resourceAdd.vm");
    }

    /**
     * 添加资源
     */
    //http://localhost:8081/phjfht/api/v1/auth/addResource
    @ActionKey("/phjfht/api/v1/auth/addResource")
    public void addResource() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthResource model = this.getBean(AuthResource.class, "");
            AuthOperator user = this.getUser();
            authResService.addAuthResource(model, user);
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
     * 删除资源
     */
    //http://localhost:8081/phjfht/api/v1/auth/deleteResourceById
    @ActionKey("/phjfht/api/v1/auth/deleteResourceById")
    public void deleteResourceById() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            authResService.deleteResourceById(seq_uuid);
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
     * 跳转资源修改页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goModifyResourcePage
    @ActionKey("/phjfht/api/v1/auth/goModifyResourcePage")
    public void goModifyResourcePage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            map = authResService.getResourceById(seq_uuid);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(map);
        this.render("/WEB-INF/velocity/auth/authResource/resourceAdd.vm");
    }

    /**
     * 修改资源
     */
    //http://localhost:8081/phjfht/api/v1/auth/modifyResource
    @ActionKey("/phjfht/api/v1/auth/modifyResource")
    public void modifyResource() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthResource model = this.getBean(AuthResource.class, "");
            AuthOperator user = this.getUser();
            authResService.modifyResource(model, user);
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

//首页左侧菜单
    @ActionKey("/queryResourceList")
    public void queryResourceList(){
        this.checkSensitiveMsg();
        AuthResource model = this.getBean(AuthResource.class, "");
        AuthOperator user = this.getUser();
        String operator_id = ""+user.getSeq_id();
        String apply_seq_id = ""+model.getApply_center_seq_id();
        String branch_seq_id = this.getPara("branch_seq_id");
        String DukeToken = (String) this.getRequest().getSession().getAttribute("DukeToken");
        List<Map> list = authResService.queryResourceList(operator_id,apply_seq_id,branch_seq_id, DukeToken);
        this.renderJson(list);
    }
}
