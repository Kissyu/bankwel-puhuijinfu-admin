package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSearchTerms<M extends BaseSearchTerms<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setSt_id(java.lang.Integer st_id) {
		set("st_id", st_id);
	}

	public java.lang.Integer getSt_id() {
		return getInt("st_id");
	}

	public void setSearch_code(java.lang.String search_code) {
		set("search_code", search_code);
	}

	public java.lang.String getSearch_code() {
		return getStr("search_code");
	}

	public void setLanguage(java.lang.String language) {
		set("language", language);
	}

	public java.lang.String getLanguage() {
		return getStr("language");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public void setType(java.lang.String type) {
		set("type", type);
	}

	public java.lang.String getType() {
		return getStr("type");
	}

	public void setParent_st_id(java.lang.Integer parent_st_id) {
		set("parent_st_id", parent_st_id);
	}

	public java.lang.Integer getParent_st_id() {
		return getInt("parent_st_id");
	}

	public void setTreepath(java.lang.String treepath) {
		set("treepath", treepath);
	}

	public java.lang.String getTreepath() {
		return getStr("treepath");
	}

	public void setProvince(java.lang.String province) {
		set("province", province);
	}

	public java.lang.String getProvince() {
		return getStr("province");
	}

	public void setOrder_no(java.lang.String order_no) {
		set("order_no", order_no);
	}

	public java.lang.String getOrder_no() {
		return getStr("order_no");
	}

	public void setIs_child(java.lang.String is_child) {
		set("is_child", is_child);
	}

	public java.lang.String getIs_child() {
		return getStr("is_child");
	}

	public void setLogo(java.lang.String logo) {
		set("logo", logo);
	}

	public java.lang.String getLogo() {
		return getStr("logo");
	}

	public void setIs_show(java.lang.String is_show) {
		set("is_show", is_show);
	}

	public java.lang.String getIs_show() {
		return getStr("is_show");
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

	public void setCreate_opt(java.lang.String create_opt) {
		set("create_opt", create_opt);
	}

	public java.lang.String getCreate_opt() {
		return getStr("create_opt");
	}

	public void setModify_opt(java.lang.String modify_opt) {
		set("modify_opt", modify_opt);
	}

	public java.lang.String getModify_opt() {
		return getStr("modify_opt");
	}

}