package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.phjf_admin.common.model.core.base.BaseAuthRoleResource;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheAuthRoleResource;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class AuthRoleResource extends CacheAuthRoleResource<AuthRoleResource> {
	public static final AuthRoleResource dao = new AuthRoleResource().dao();

	/**
	 * 保存或修改角色
	 * @param model
	 * @return
	 */
	public AuthRoleResource saveOrUpdate(AuthRoleResource model){
		if (model.getSeq_uuid() != null){
			model.update();
		} else {
			model.setSeq_uuid(UUID.randomUUID().toString());
			model.save();
		}
		flashCatch(model);
		return model;
	}

	/**
	 * 通过ID获取角色
	 * @param role_id
	 * @return
	 */
	public List<AuthRoleResource> getByRoleId(String role_id){
		String sql = "SELECT * " +
				"       FROM auth_role_resource " +
				"      where role_seq_id = ? " +
				"        and status = 1 ";
		return dao.use("DBPublic").find(sql,role_id);
	}

	/**
	 * 添加角色资源关系
	 * @param role_id
	 * @param list
	 */
	public void addRoleResource(String role_id, List<AuthRoleResource> list){
		String sql = "update auth_role_resource " +
				"        set status = 4" +
				"      where role_seq_id = ? " +
				"        and status = 1 ";
		Db.use("DBPublic").update(sql, role_id);
		Db.use("DBPublic").batchSave(list, list.size());
	}
}
