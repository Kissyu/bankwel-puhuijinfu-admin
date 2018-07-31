package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAuthBranchApply<M extends BaseAuthBranchApply<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setSeq_id(java.lang.Integer seq_id) {
		set("seq_id", seq_id);
	}

	public java.lang.Integer getSeq_id() {
		return getInt("seq_id");
	}

	public void setBranch_seq_id(java.lang.Integer branch_seq_id) {
		set("branch_seq_id", branch_seq_id);
	}

	public java.lang.Integer getBranch_seq_id() {
		return getInt("branch_seq_id");
	}

	public void setApply_center_seq_id(java.lang.Integer apply_center_seq_id) {
		set("apply_center_seq_id", apply_center_seq_id);
	}

	public java.lang.Integer getApply_center_seq_id() {
		return getInt("apply_center_seq_id");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return getStr("status");
	}

	public void setCreate_time(java.util.Date create_time) {
		set("create_time", create_time);
	}

	public java.util.Date getCreate_time() {
		return get("create_time");
	}

	public void setModify_time(java.util.Date modify_time) {
		set("modify_time", modify_time);
	}

	public java.util.Date getModify_time() {
		return get("modify_time");
	}

	public void setCreate_opt(java.lang.Integer create_opt) {
		set("create_opt", create_opt);
	}

	public java.lang.Integer getCreate_opt() {
		return getInt("create_opt");
	}

	public void setModify_opt(java.lang.Integer modify_opt) {
		set("modify_opt", modify_opt);
	}

	public java.lang.Integer getModify_opt() {
		return getInt("modify_opt");
	}

}
