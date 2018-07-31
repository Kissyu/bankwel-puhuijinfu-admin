package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysAreas<M extends BaseSysAreas<M>> extends Model<M> implements IBean {

	public void setSeq_id(java.lang.Integer seq_id) {
		set("seq_id", seq_id);
	}

	public java.lang.Integer getSeq_id() {
		return getInt("seq_id");
	}

	public void setArea_id(java.lang.String area_id) {
		set("area_id", area_id);
	}

	public java.lang.String getArea_id() {
		return getStr("area_id");
	}

	public void setArea(java.lang.String area) {
		set("area", area);
	}

	public java.lang.String getArea() {
		return getStr("area");
	}

	public void setCity_id(java.lang.String city_id) {
		set("city_id", city_id);
	}

	public java.lang.String getCity_id() {
		return getStr("city_id");
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
