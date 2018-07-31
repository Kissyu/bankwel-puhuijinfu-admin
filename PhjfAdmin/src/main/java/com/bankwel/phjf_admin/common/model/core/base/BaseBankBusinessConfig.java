package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBankBusinessConfig<M extends BaseBankBusinessConfig<M>> extends Model<M> implements IBean {

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

	public void setApp_version_1(java.lang.String app_version_1) {
		set("app_version_1", app_version_1);
	}

	public java.lang.String getApp_version_1() {
		return getStr("app_version_1");
	}

	public void setApp_version_2(java.lang.String app_version_2) {
		set("app_version_2", app_version_2);
	}

	public java.lang.String getApp_version_2() {
		return getStr("app_version_2");
	}

	public void setApp_version_3(java.lang.String app_version_3) {
		set("app_version_3", app_version_3);
	}

	public java.lang.String getApp_version_3() {
		return getStr("app_version_3");
	}

	public void setDevice_type(java.lang.String device_type) {
		set("device_type", device_type);
	}

	public java.lang.String getDevice_type() {
		return getStr("device_type");
	}

	public void setBt_code(java.lang.String bt_code) {
		set("bt_code", bt_code);
	}

	public java.lang.String getBt_code() {
		return getStr("bt_code");
	}

	public void setBusiness_code(java.lang.String business_code) {
		set("business_code", business_code);
	}

	public java.lang.String getBusiness_code() {
		return getStr("business_code");
	}

	public void setValue(java.lang.String value) {
		set("value", value);
	}

	public java.lang.String getValue() {
		return getStr("value");
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
