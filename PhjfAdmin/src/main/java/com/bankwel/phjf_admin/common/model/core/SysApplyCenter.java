package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.phjf_admin.common.model.core.base.BaseSysApplyCenter;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheSysApplyCenter;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysApplyCenter extends CacheSysApplyCenter<SysApplyCenter> {
	public static final SysApplyCenter dao = new SysApplyCenter().dao();

	public List<SysApplyCenter> queryAllApplyList(){
		String sql = "select seq_id apply_seq_id ," +
				"            name apply_name" +
				"       from sys_apply_center " +
				"      where status = 1 ";
		return SysApplyCenter.dao.use("DBPublic").find(sql);
	}
}
