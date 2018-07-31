package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.AuthBranch;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.bankwel.phjf_admin.common.model.core.AuthRole;
import com.jfinal.aop.Before;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bankwel on 2017/9/20.
 */
public class AuthOperatorAuthService {
    /**
     * 查找机构对应角色列表
     * @param operator_seq_id
     * @param branch_seq_id
     * @throws MsgBusinessException
     */
    public Map queryRoleList(String operator_seq_id,String branch_seq_id) throws MsgBusinessException {
        List<AuthRole> allRoles = AuthRole.dao.findAll();
        List<Map> roleList = new ArrayList<Map>();
        List<AuthOperatorAuth> _roleList = AuthOperatorAuth.dao.findByIds(operator_seq_id,branch_seq_id);

        //如果点击机构树且某机构拥有的角色不为空
        if(F.validate.isNotEmpty(branch_seq_id) && F.validate.isNotEmpty(_roleList)) {
            for (AuthRole ar : allRoles) {
                boolean checked = false;
                for(AuthOperatorAuth aoa : _roleList){
                    if(ar.getSeq_id().equals(aoa.getRole_seq_id())) {
                        checked = true;
                        break;
                    }
                }
                roleList.add(F.transKit.asMap("seq_id",ar.getSeq_id()
                        ,"name",ar.getName()
                        ,"apply_center_seq_id",ar.getApply_center_seq_id()
                        ,"checked",checked
                ));
            }
        } else {
            for(AuthRole ar : allRoles){
                roleList.add(F.transKit.asMap("seq_id",ar.getSeq_id()
                        ,"name",ar.getName()
                        ,"apply_center_seq_id",ar.getApply_center_seq_id()
                        ,"checked",false
                ));
            }
        }
        return F.transKit.asMap("rows",roleList);
    }

    @Before(TTx.class)
    public void modifyBranchRoles(String user_id,String operator_seq_id,String branch_seq_id,String[] roleIds) throws MsgBusinessException {
        //先删除
        List<AuthOperatorAuth> _roleList = AuthOperatorAuth.dao.findByIds(operator_seq_id,branch_seq_id);
        if(F.validate.isNotEmpty(_roleList)){
            for(AuthOperatorAuth aoa : _roleList){
                AuthOperatorAuth.dao.deleteOpAuthById(""+aoa.getSeq_uuid());
            }
        }
        //再添加
        if (F.validate.isNotEmpty(roleIds)) {
            for (String role_seq_id : roleIds) {
                AuthRole ar = AuthRole.dao.findByseqId(role_seq_id);
                AuthOperatorAuth.dao.saveOrUpdate(user_id,operator_seq_id,branch_seq_id,role_seq_id,""+ar.getApply_center_seq_id());
            }
        }
    }

}
