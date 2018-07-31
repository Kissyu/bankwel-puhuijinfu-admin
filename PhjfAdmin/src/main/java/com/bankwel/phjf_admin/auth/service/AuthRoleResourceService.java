package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthRole;
import com.bankwel.phjf_admin.common.model.core.AuthRoleResource;
import com.jfinal.aop.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/29.
 */
public class AuthRoleResourceService {

    /**
     * 角色分配资源
     */
    @Before(TTx.class)
    public void addRoleResource(String[] resource_ids, String role_id, AuthOperator user) throws MsgBusinessException {
//        List<AuthRoleResource> authRoleResources = AuthRoleResource.dao.getByRoleId(role_id);
        AuthRole role = AuthRole.dao.findByseqId(role_id);
//        for(AuthRoleResource roleResource :authRoleResources){
//            roleResource.setStatus("4");
//            AuthRoleResource.dao.saveOrUpdate(roleResource);
//        }
        List<AuthRoleResource> list = new ArrayList<AuthRoleResource>();
        for(String resource_id :resource_ids){
            AuthRoleResource newRoleResource = new AuthRoleResource();
            newRoleResource.setRole_seq_id(Integer.valueOf(role_id));
            newRoleResource.setResource_seq_id(Integer.valueOf(resource_id));
            newRoleResource.setCreate_opt(user.getSeq_id());
            newRoleResource.setModify_opt(user.getSeq_id());
            newRoleResource.setApply_center_seq_id(role.getApply_center_seq_id());
//            AuthRoleResource.dao.saveOrUpdate(newRoleResource);
            newRoleResource.setSeq_uuid(UUID.randomUUID().toString());
            list.add(newRoleResource);
        }
        AuthRoleResource.dao.addRoleResource(role_id,list);
    }
}
