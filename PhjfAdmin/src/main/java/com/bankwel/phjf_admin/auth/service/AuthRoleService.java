package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AuthRoleService {
    /**
     * 添加角色
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void addRole(AuthRole model, AuthOperator user) throws MsgBusinessException {
        model.setCreate_opt(user.getSeq_id());
        checkModel(model);

        AuthRole.dao.saveOrUpdate(model);
    }

    /**
     * 删除角色
     * @param seq_uuid
     * @param seq_id
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteRoleById(String seq_uuid,String seq_id) throws MsgBusinessException {
        if(F.validate.isNotEmpty(AuthOperatorAuth.dao.findAOAByRoleId(seq_id))){
            throw new MsgBusinessException("该角色已授权，不能删除");
        }else {
            AuthRole authRole = AuthRole.dao.getById(seq_uuid);
            authRole.setStatus("4");

            AuthRole.dao.saveOrUpdate(authRole);
        }
    }

    /**
     * 修改角色
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void modifyRole(AuthRole model, AuthOperator user) throws MsgBusinessException {
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthRole.dao.saveOrUpdate(model);
    }

    /**
     * 通过ID获取角色
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public AuthRole getRoleById(String seq_uuid) throws MsgBusinessException {
        AuthRole authRole = AuthRole.dao.getById(seq_uuid);
        return authRole;
    }

    /**
     * 获取角色已分配资源
     * @param user
     * @param role_id
     * @return
     * @throws MsgBusinessException
     */
    public List<Map> queryResourceByRole(AuthOperator user,String role_id) throws MsgBusinessException {
        List<Map> list = new ArrayList<Map>();
        List<AuthRoleResource> authRoleResources = AuthRoleResource.dao.getByRoleId(role_id);
        List<AuthResource> authResources = AuthResource.dao.queryAllAllotResource(user.getSeq_id()+"", user.getApply_center_seq_id()+"");
        for(AuthResource res:authResources){
            boolean checked = false;
//                如果是已分配的资源
            for(AuthRoleResource roleRes:authRoleResources){
                if(res.getSeq_id().equals(roleRes.getResource_seq_id())){
                    checked = true;
                    break;
                }
            }
            list.add(F.transKit.asMap("seq_id",res.getSeq_id()
                    ,"seq_uuid",res.getSeq_uuid()
                    ,"name",res.getName()
                    ,"parent_seq_id",res.getParent_seq_id()
                    ,"checked",checked));
        }
        return list;
    }

    /**
     * 获取角色列表
     * @param operator_seq_id
     * @param name
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryRoleByPage(String operator_seq_id, String name, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AuthRole.dao.queryRoleByPage(operator_seq_id, name, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 检查输入完整性
     * @param model
     * @throws MsgBusinessException
     */
    public void checkModel(AuthRole model) throws MsgBusinessException {
        if (F.validate.isEmpty(model.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(model.getApply_center_seq_id())){
            throw new MsgBusinessException("应用中心不能为空");
        }
    }
}
