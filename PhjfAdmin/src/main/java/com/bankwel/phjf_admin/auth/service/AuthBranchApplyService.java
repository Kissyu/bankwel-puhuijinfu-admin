package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.AuthBranchApply;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2017/10/12.
 */
public class AuthBranchApplyService {

    public List<Record> queryListByBranchId(String branch_seq_id) throws MsgBusinessException {
        if (F.validate.isEmpty(branch_seq_id)){
            throw new MsgBusinessException("机构编码不能为空");
        }
        return AuthBranchApply.dao.queryListByBranchId(branch_seq_id);
    }

    @Before(TTx.class)
    public void modifyBranchApply(AuthOperator user, String branch_seq_id, String[] apply_seq_ids) throws MsgBusinessException{
        List<Record> recordList = this.queryListByBranchId(branch_seq_id);

        for (Record record : recordList){
            boolean bool = false;
            String apply_seq_id = record.getStr("seq_id");
            for (int i = 0; i < apply_seq_ids.length; i++){
                if (apply_seq_id.equals(apply_seq_ids[i])){
                    bool = true;
                    break;
                }
            }
            if (!bool){
                String branch_apply_seq_id = record.getStr("branch_apply_seq_id");
                if (F.validate.isNotEmpty(branch_apply_seq_id)) {//原来被选中
                    //判断是否可以取消
                    AuthOperatorAuth operatorAuth = AuthOperatorAuth.dao.findByApplySeqId(apply_seq_id, branch_seq_id);
                    if (F.validate.isNotEmpty(operatorAuth.getSeq_id())){
                        throw new MsgBusinessException("应用中心已经被使用，请先取消账户的授权！");
                    }

                }
            }
        }

        //删除
        for (Record record : recordList){
            String branch_apply_seq_id = record.getStr("branch_apply_seq_id");
            if (F.validate.isNotEmpty(branch_apply_seq_id)) {//原来被选中
                AuthBranchApply branchApply = AuthBranchApply.dao.findBranchApplyBySeqId(branch_apply_seq_id);
                if (F.validate.isNotEmpty(branchApply.getSeq_id())){
                    branchApply.setStatus("4");
                    AuthBranchApply.dao.saveOrUpdate(branchApply);
                }
            }
        }

        //新增
        for (String apply_seq_id : apply_seq_ids){
            AuthBranchApply branchApply = new AuthBranchApply();
            branchApply.setSeq_uuid(UUID.randomUUID().toString());
            branchApply.setBranch_seq_id(Integer.parseInt(branch_seq_id));
            branchApply.setApply_center_seq_id(Integer.parseInt(apply_seq_id));
            branchApply.setCreate_opt(user.getSeq_id());
            branchApply.setModify_opt(user.getSeq_id());
            AuthBranchApply.dao.saveOrUpdate(branchApply);
        }

    }
}
