package com.bankwel.phjf_admin.auth.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/9/19.
 */
public class AuthResService {
    /**
     * 添加资源
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void addAuthResource(AuthResource model, AuthOperator user) throws MsgBusinessException {
        AuthResource oldResource = AuthResource.dao.getByNameUrl(model.getName(),model.getUrl());
        if(F.validate.isNotEmpty(oldResource) && model.getType().equals("button")){
            throw new MsgBusinessException("该按钮已存在");
        }else {
            model.setCreate_opt(user.getSeq_id());
            model.setModify_opt(user.getSeq_id());
            if(model.getParent_seq_id().equals(0)){
                model.setLevel(1);
                checkModel(model);
                AuthResource.dao.saveOrUpdate(model);
                model.setTreepath("0/"+model.getSeq_id());
            }else {
                AuthResource parentResource = AuthResource.dao.getBySeqId(model.getParent_seq_id()+"");
                model.setLevel(parentResource.getLevel()+1);
                checkModel(model);
                AuthResource.dao.saveOrUpdate(model);
                model.setTreepath(parentResource.getTreepath()+'/'+model.getSeq_id());
            }
            checkModel(model);
            AuthResource.dao.saveOrUpdate(model);
        }
    }

    /**
     * 删除资源
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteResourceById(String seq_uuid) throws MsgBusinessException {
        AuthResource authResource = AuthResource.dao.getById(seq_uuid);
        authResource.setStatus("4");
        AuthResource.dao.saveOrUpdate(authResource);
    }

    /**
     * 修改资源
     * @param model
     * @param user
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void modifyResource(AuthResource model, AuthOperator user) throws MsgBusinessException {
        model.setModify_opt(user.getSeq_id());
        checkModel(model);

        AuthResource.dao.saveOrUpdate(model);
    }

    /**
     * 通过ID获取资源
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map getResourceById(String seq_uuid) throws MsgBusinessException {
        AuthResource authResource = AuthResource.dao.getById(seq_uuid);
        String parent_name = "";
        if(!"0".equals(authResource.getParent_seq_id()+"")){
            AuthResource pResource = AuthResource.dao.getBySeqId(authResource.getParent_seq_id()+"");
            parent_name = pResource.getName();
        }
        List<SysApplyCenter> applyList = SysApplyCenter.dao.queryAllApplyList();
        return F.transKit.asMap("parent_name",parent_name,"data",authResource,"applyList",applyList);
    }

    /**
     * 获取资源列表
     * @param operator_seq_id
     * @param name
     * @param url
     * @param type
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryResourceByPage(String operator_seq_id, String name, String url, String type, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = AuthResource.dao.queryResourceByPage(operator_seq_id, name,url,type,page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 检查输入完整性
     * @param model
     * @throws MsgBusinessException
     */
    public void checkModel(AuthResource model) throws MsgBusinessException {
        if (F.validate.isEmpty(model.getType())){
            throw new MsgBusinessException("类型不能为空");
        }
        if (F.validate.isEmpty(model.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(model.getApply_center_seq_id())){
            throw new MsgBusinessException("应用中心不能为空");
        }
    }


    /**
     * 获取首页左侧菜单资源列表
     *
     * @param operator_seq_id
     * @param apply_seq_id
     * @param branch_seq_id
     * @return
     */
    public List<Map> queryResourceList(String operator_seq_id, String apply_seq_id, String branch_seq_id, String DukeToken){
        List<AuthResource> list = AuthResource.dao.queryResourceList(operator_seq_id,apply_seq_id,branch_seq_id);
        List<Map> mapList = new ArrayList<Map>();
        Map map = null;
        if (F.validate.isNotEmpty(list)){
            for (AuthResource resource : list){
                map = F.transKit.asMap("name", resource.getName()
                        ,"aurl", StringUtils.isBlank(resource.getUrl()) ? "": resource.getUrl() + "?DukeToken=" + DukeToken
                        ,"seq_id", resource.getSeq_id()
                        ,"parent_seq_id", resource.getParent_seq_id());
                if ("1".equals(resource.getLevel()+"")){
                    map.put("open", true);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 输出资源添加页的数据
     * @param parent_seq_id
     * @param type
     * @return
     */
    public Map findAddPageData(String parent_seq_id,String type){
        String parent_name = "";
        if(!"0".equals(parent_seq_id)){
            AuthResource pResource = AuthResource.dao.getBySeqId(parent_seq_id);
            parent_name = pResource.getName();
        }
        List<SysApplyCenter> result = SysApplyCenter.dao.queryAllApplyList();
        Map data = F.transKit.asMap("parent_seq_id",parent_seq_id,"type",type);
        return F.transKit.asMap("data",data,"applyList",result,"parent_name",parent_name) ;
    }


    /**
     * 用户是否有权限访问链接地址
     * @param operator
     * @param resource_url
     * @return
     */
    public boolean isHandleAuth(AuthOperator operator, String resource_url){
        return AuthResource.dao.isHandleAuth(operator, resource_url);
    }

}
