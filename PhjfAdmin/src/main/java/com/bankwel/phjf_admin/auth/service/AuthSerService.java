package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.AuthResourceService;
import com.bankwel.phjf_admin.common.model.core.AuthService;
import com.jfinal.aop.Before;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */
public class AuthSerService {
    /**
     * 添加服务
     * @param model
     * @param resource_id
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void addService(AuthService model,int resource_id, AuthOperator user) throws MsgBusinessException {
        AuthService oldService = AuthService.dao.getByurl(model.getUrl());
        if(F.validate.isNotEmpty(oldService)){
            AuthResourceService oldResourceService = AuthResourceService.dao.getByTypeSer(resource_id,oldService.getSeq_id());
            if(F.validate.isEmpty(oldResourceService)){
                AuthResourceService authResourceService = new AuthResourceService();
                authResourceService.setType_seq_id(resource_id);
                authResourceService.setService_seq_id(oldService.getSeq_id());
                authResourceService.setApply_center_seq_id(oldService.getApply_center_seq_id());
                authResourceService.setCreate_opt(user.getSeq_id());
                authResourceService.setModify_opt(user.getSeq_id());
                AuthResourceService.dao.saveOrUpdate(authResourceService);
            }else{
                throw new MsgBusinessException("该服务已存在");
            }
        }else {
            model.setCreate_opt(user.getSeq_id());
            model.setModify_opt(user.getSeq_id());
            checkModel(model);
            AuthService.dao.saveOrUpdate(model);

            AuthResourceService authResourceService = new AuthResourceService();
            authResourceService.setType_seq_id(resource_id);
            authResourceService.setService_seq_id(model.getSeq_id());
            authResourceService.setApply_center_seq_id(model.getApply_center_seq_id());
            authResourceService.setCreate_opt(user.getSeq_id());
            authResourceService.setModify_opt(user.getSeq_id());
            AuthResourceService.dao.saveOrUpdate(authResourceService);
        }
    }

    /**
     * 删除服务
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteServiceById(String seq_uuid) throws MsgBusinessException {
        AuthService authService = AuthService.dao.getById(seq_uuid);
        authService.setStatus("4");
        AuthService.dao.saveOrUpdate(authService);

        List<AuthResourceService> list =AuthResourceService.dao.queryByServiceId(authService.getSeq_id());
        for (AuthResourceService authResourceService : list) {
            authResourceService.setStatus("4");
            AuthResourceService.dao.saveOrUpdate(authResourceService);
        }
    }

    /**
     * 修改服务
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void modifyService(AuthService model, AuthOperator user) throws MsgBusinessException {
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthService.dao.saveOrUpdate(model);
    }

    /**
     * 通过ID获取服务
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public AuthService getServiceById(String seq_uuid) throws MsgBusinessException {
        AuthService authService = AuthService.dao.getById(seq_uuid);
        return authService;
    }

    /**
     * 检查输入完整性
     * @param model
     * @throws MsgBusinessException
     */
    public void checkModel(AuthService model) throws MsgBusinessException {
        if (F.validate.isEmpty(model.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(model.getUrl())){
            throw new MsgBusinessException("链接地址不能为空");
        }
        if (F.validate.isEmpty(model.getApply_center_seq_id())){
            throw new MsgBusinessException("应用中心不能为空");
        }
    }


}
