package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.AuthBranch;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthOperatorAuth;
import com.jfinal.aop.Before;
import org.apache.commons.lang3.tuple.Pair;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/9/12.
 */
public class AuthBranchService {

    /**
     * 添加机构
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public AuthBranch addBranch(AuthBranch model, AuthOperator user) throws MsgBusinessException {
        model.setCreate_opt(user.getSeq_id());
        model.setStatus("1");
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthBranch abr = AuthBranch.dao.saveOrUpdate(model);
        if(abr.getTreepath().equals("root")) {
            abr.setTreepath(""+abr.getSeq_id());
        }else {
            abr.setTreepath(abr.getTreepath()+"/"+abr.getSeq_id());
        }
        return AuthBranch.dao.saveOrUpdate(abr);
    }

    /**
     * 修改
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void modifyBranch(AuthBranch model, AuthOperator user) throws MsgBusinessException {
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthBranch.dao.saveOrUpdate(model);
    }

    /**
     * 获取机构信息
     * @param model
     * @throws MsgBusinessException
     */
    public Map queryAuthBranchInfo(AuthBranch model) throws MsgBusinessException {
        if(F.validate.isEmpty(model.getSeq_uuid())) {
            throw new MsgBusinessException("机构编码不能为空");
        }
        Map result = F.transKit.asMap();
        AuthBranch authBranch = AuthBranch.dao.findByUuid(""+model.getSeq_uuid());
        if (F.validate.isNotEmpty(authBranch)) {
            result.put("branchInfo",authBranch);
        }
        return result;
    }

    /**
     * 获取机构信息
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    public Map queryBranchById(String seq_uuid) throws MsgBusinessException{
        if(F.validate.isEmpty(seq_uuid)) {
            throw new MsgBusinessException("机构编码不能为空");
        }
        AuthBranch branch = AuthBranch.dao.findByUuid(seq_uuid);
        Map result = F.transKit.asMap("seq_uuid", seq_uuid
                , "name", branch.getName()
                , "seq_id", branch.getSeq_id()
                , "parent_name", branch.findBranch().getName());
        return result;
    }

    /**
     * 获取机构列表
     * @throws MsgBusinessException
     */
    public List<Map> queryAuthBranchList() throws MsgBusinessException {
        List<Map> branchList = new ArrayList<Map>();
        List<AuthBranch> _branchList = AuthBranch.dao.findAll();
        if(F.validate.isNotEmpty(_branchList)){
            for(AuthBranch abr : _branchList){
                branchList.add(F.transKit.asMap("seq_id",abr.getSeq_id()
                        ,"seq_uuid",abr.getSeq_uuid()
                        ,"parent_name",abr.findBranch().getName()
                        ,"name",abr.getName()
                        ,"abbr_name",abr.getAbbr_name()
                        ,"parent_seq_id",abr.getParent_seq_id()
                        ,"level",abr.getLevel()
                        ,"order_no",abr.getOrder_no()
                        ,"address",abr.getAddress()
                        ,"treepath",abr.getTreepath()
                        ,"apply_center_seq_id",abr.getApply_center_seq_id()));
            }
        }
        return branchList;
    }
    /**
     * 账户授权时获取机构列表
     * @throws MsgBusinessException
     */
    public List<Map> queryOptAuthBranchList(String operator_seq_id) throws MsgBusinessException {
        List<Map> branchList = new ArrayList<Map>();
        List<AuthBranch> _branchList = AuthBranch.dao.findAll();
        if(F.validate.isNotEmpty(_branchList)){
            for(AuthBranch abr : _branchList){
                boolean checked = false;
//                如果已授过权
                if(F.validate.isNotEmpty(AuthOperatorAuth.dao.findByIds(operator_seq_id,abr.getSeq_id()+""))){
                    checked = true;
                }
                branchList.add(F.transKit.asMap("seq_id",abr.getSeq_id()
                        ,"seq_uuid",abr.getSeq_uuid()
                        ,"name",abr.getName()
                        ,"parent_seq_id",abr.getParent_seq_id()
                        , "chkDisabled",true
                        ,"checked",checked));
            }
        }
        return branchList;
    }
    /**
     * 获取机构分页列表
     * @throws MsgBusinessException
     */
    public Map queryAuthBranchByPage(AuthBranch model, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AuthBranch.dao.queryAuthBranchByPage(model, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }
    /**
     * 删除机构
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteBranchById(String seq_uuid) throws MsgBusinessException {
        if (F.validate.isEmpty(seq_uuid)) {
            throw new MsgBusinessException("id不能为空");
        }
        AuthBranch.dao.deleteBranchById(""+seq_uuid);
    }

    public void checkModel(AuthBranch model) throws MsgBusinessException {
        if (F.validate.isEmpty(model.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(model.getApply_center_seq_id())){
            throw new MsgBusinessException("应用中心ID不能为空");
        }
    }
}
