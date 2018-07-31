package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAuthBranch<M extends BaseAuthBranch<M>> extends Model<M> implements IBean {

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

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public void setAbbr_name(java.lang.String abbr_name) {
		set("abbr_name", abbr_name);
	}

	public java.lang.String getAbbr_name() {
		return getStr("abbr_name");
	}

	public void setParent_seq_id(java.lang.Integer parent_seq_id) {
		set("parent_seq_id", parent_seq_id);
	}

	public java.lang.Integer getParent_seq_id() {
		return getInt("parent_seq_id");
	}

	public void setLevel(java.lang.Integer level) {
		set("level", level);
	}

	public java.lang.Integer getLevel() {
		return getInt("level");
	}

	public void setOrder_no(java.lang.String order_no) {
		set("order_no", order_no);
	}

	public java.lang.String getOrder_no() {
		return getStr("order_no");
	}

	public void setAddress(java.lang.String address) {
		set("address", address);
	}

	public java.lang.String getAddress() {
		return getStr("address");
	}

	public void setTreepath(java.lang.String treepath) {
		set("treepath", treepath);
	}

	public java.lang.String getTreepath() {
		return getStr("treepath");
	}

	public void setApply_center_seq_id(java.lang.Integer apply_center_seq_id) {
		set("apply_center_seq_id", apply_center_seq_id);
	}

	public java.lang.Integer getApply_center_seq_id() {
		return getInt("apply_center_seq_id");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return getStr("remark");
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