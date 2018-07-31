package com.bankwel.phjf_admin.auth.webapi;

import com.alibaba.fastjson.JSON;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.auth.service.AuthRoleResourceService;
import com.bankwel.phjf_admin.auth.service.AuthRoleService;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Int;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AuthRoleController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthRoleController.class);

    private AuthRoleService authRoleService = Duang.duang(AuthRoleService.class);

    /**
     * 跳转角色管理列表页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goRolePage
    @ActionKey("/phjfht/api/v1/auth/goRolePage")
    public void goRolePage() {
        this.checkSensitiveMsg();
        this.render("/WEB-INF/velocity/auth/role/roleQuery.vm");
    }

    /**
     * 角色管理列表查询
     */
    //http://localhost:8081/phjfht/api/v1/auth/queryRoleByPage
    @ActionKey("/phjfht/api/v1/auth/queryRoleByPage")
    public void queryRoleByPage() {
        Map result = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name","");
            PageKit page = this.getBean(PageKit.class, "");
            AuthOperator user = this.getUser();
            result = authRoleService.queryRoleByPage(user.getSeq_id()+"",name, page);
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
     * 跳转角色管理添加页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goAddRolePage
    @ActionKey("/phjfht/api/v1/auth/goAddRolePage")
    public void goAddRolePage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperator user = this.getUser();
            List<SysApplyCenter> result = AuthOperator.dao.findApplyList(user.getSeq_id()+"");
            map = F.transKit.asMap("code", 1, "msg", "", "applyList",result);
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
        this.render("/WEB-INF/velocity/auth/role/roleAdd.vm");
    }

    /**
     * 添加角色
     */
    //http://localhost:8081/phjfht/api/v1/auth/addRole
    @ActionKey("/phjfht/api/v1/auth/addRole")
    public void addRole() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthRole model = this.getBean(AuthRole.class, "");
            AuthOperator user = this.getUser();
            authRoleService.addRole(model, user);
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
     * 删除角色
     */
    //http://localhost:8081/phjfht/api/v1/auth/deleteRoleById
    @ActionKey("/phjfht/api/v1/auth/deleteRoleById")
    public void deleteRoleById() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            String seq_id = this.getPara("seq_id");
            authRoleService.deleteRoleById(seq_uuid,seq_id);
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
     * 跳转角色管理修改页
     */
    //http://localhost:8081/phjfht/api/v1/auth/goModifyRolePage
    @ActionKey("/phjfht/api/v1/auth/goModifyRolePage")
    public void goModifyRolePage() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator user = this.getUser();
            List<SysApplyCenter> applyList = AuthOperator.dao.findApplyList(user.getSeq_id()+"");
            AuthRole result = authRoleService.getRoleById(seq_uuid);
            map = F.transKit.asMap("code", 1, "msg", "", "data",result, "applyList", applyList) ;
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
        this.render("/WEB-INF/velocity/auth/role/roleAdd.vm");
    }

    /**
     * 修改角色
     */
    //http://localhost:8081/phjfht/api/v1/auth/modifyRole
    @ActionKey("/phjfht/api/v1/auth/modifyRole")
    public void modifyRole() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthRole model = this.getBean(AuthRole.class, "");
            AuthOperator user = this.getUser();
            authRoleService.modifyRole(model, user);
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
     * 获取角色已分配资源列表
     */
    //http://localhost:8081/phjfht/api/v1/auth/queryResourceByRole
    @ActionKey("/phjfht/api/v1/auth/queryResourceByRole")
    public void queryResourceByRole() {
        List<Map> list = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String role_id = this.getPara("roleId");
            AuthOperator user = this.getUser();
            list = authRoleService.queryResourceByRole(user,role_id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(list);
    }

    /**
     * 判断角色是否有分配资源和修改资源的权限
     */
    //http://localhost:8081/phjfht/api/v1/auth/isHaveAllotModiAuth
    @ActionKey("/phjfht/api/v1/auth/isHaveAllotModiAuth")
    public void isHaveAllotModiAuth() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String role_seq_id = this.getPara("role_seq_id");
            AuthOperator user = this.getUser();
            boolean hasAuth = AuthRole.dao.isHaveAllotModiAuth(user,role_seq_id);
            map = F.transKit.asMap("code", 1, "msg", "", "data",hasAuth) ;
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
