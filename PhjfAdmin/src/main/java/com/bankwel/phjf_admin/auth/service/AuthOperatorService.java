package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.AuthBranch;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.SysApplyCenter;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/9/12.
 */
public class AuthOperatorService {

    @Before(TTx.class)
    public void addOperator(AuthOperator model, AuthOperator user) throws MsgBusinessException {
        model.setCreate_opt(user.getSeq_id());
        model.setStatus("1");
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthOperator.dao.saveOrUpdate(model);
    }
    @Before(TTx.class)
    public void modifyOperator(AuthOperator model, AuthOperator user) throws MsgBusinessException {
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthOperator.dao.saveOrUpdate(model);
    }

    public Map queryOperatorByPage(String branch_seq_id,AuthOperator model, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AuthOperator.dao.queryAuthOperatorByPage(branch_seq_id,model, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    public Map getOperatorById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)) {
            throw new MsgBusinessException("账户ID不能为空");
        }
        AuthOperator result = AuthOperator.dao.getOperatorById(seq_uuid);
        Map map = null;
        map = F.transKit.asMap(
                "branch_name", result.findBranch().getName()
                ,"seq_id",result.getSeq_id()
                ,"seq_uuid",result.getSeq_uuid()
                ,"operate_name",result.getOperate_name()
                ,"password",result.getPassword()
                ,"true_name",result.getTrue_name()
                ,"email",result.getEmail()
                ,"mobile",result.getMobile()
                ,"branch_seq_id", result.getBranch_seq_id()
                ,"opt_type", result.getOpt_type()
                ,"apply_center_seq_id", result.getApply_center_seq_id()
        );
        return map;
    }

    @Before(TTx.class)
    public void deleteOperatorById(String seq_uuid) throws MsgBusinessException {
        if (F.validate.isEmpty(seq_uuid)) {
            throw new MsgBusinessException("id不能为空");
        }
        AuthOperator.dao.deleteOperatorById(""+seq_uuid);
    }

    public void checkModel(AuthOperator model) throws MsgBusinessException {
        if (F.validate.isEmpty(model.getOperate_name())){
            throw new MsgBusinessException("账户名不能为空");
        }
        if (F.validate.isEmpty(model.getPassword())){
            throw new MsgBusinessException("密码不能为空");
        }
        if (F.validate.isEmpty(model.getTrue_name())) {
            throw new MsgBusinessException("真实姓名不能为空");
        }
        if (F.validate.isEmpty(model.getMobile())) {
            throw new MsgBusinessException("联系电话不能为空");
        }
        if (F.validate.isEmpty(model.getBranch_seq_id())) {
            throw new MsgBusinessException("组织机构不能为空");
        }

    }


    public AuthOperator getAuthOperator(String operator_name, String password){
        return AuthOperator.dao.getAuthOperator(operator_name, password);
    }
    public AuthOperator getAuthOperatorMobile(String operator_name, String password){
        return AuthOperator.dao.getAuthOperatorMobile(operator_name, password);
    }

    public Map getOperatorBySeqId(String seq_id) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_id)) {
            throw new MsgBusinessException("账户ID不能为空");
        }
        Map map = null;
        AuthOperator result = AuthOperator.dao.getOperatorBySeqId(seq_id);
        map = F.transKit.asMap(
                "operator_seq_id", seq_id
                ,"operator_seq_name", result.getOperate_name()
                ,"branch_seq_id",result.getBranch_seq_id()
                ,"branch_seq_name",result.findBranch().getName()
        );
        return map;
    }

    /**
     * @Title:
     * @Description: 设置登录的token
     * @author: DukeMr.Chen
     */
    public void setToken(Integer seq_id, String dukeToken) {
        if(seq_id == null) return;
        AuthOperator user = AuthOperator.dao.findById(seq_id);
        user.setToken(dukeToken);
        user.setModify_time(new Date());
        user.update();
    }

    /**
     * @Title:
     * @Description: 获取token
     * @author: DukeMr.Chen
     */
    public static String getToken(Integer seq_id){
        if(seq_id == null) return "";
        return AuthOperator.dao.getToken(seq_id + "");
    }
}
