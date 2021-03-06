package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserAppauth<M extends BaseUserAppauth<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setUad_id(java.lang.Integer uad_id) {
		set("uad_id", uad_id);
	}

	public java.lang.Integer getUad_id() {
		return getInt("uad_id");
	}

	public void setUser_id(java.lang.Integer user_id) {
		set("user_id", user_id);
	}

	public java.lang.Integer getUser_id() {
		return getInt("user_id");
	}

	public void setJpush_id(java.lang.String jpush_id) {
		set("jpush_id", jpush_id);
	}

	public java.lang.String getJpush_id() {
		return getStr("jpush_id");
	}

	public void setApp_code(java.lang.String app_code) {
		set("app_code", app_code);
	}

	public java.lang.String getApp_code() {
		return getStr("app_code");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return getStr("password");
	}

	public void setHand_password(java.lang.String hand_password) {
		set("hand_password", hand_password);
	}

	public java.lang.String getHand_password() {
		return getStr("hand_password");
	}

	public void setLanguage(java.lang.String language) {
		set("language", language);
	}

	public java.lang.String getLanguage() {
		return getStr("language");
	}

	public void setPackage_name(java.lang.String package_name) {
		set("package_name", package_name);
	}

	public java.lang.String getPackage_name() {
		return getStr("package_name");
	}

	public void setDevice_type(java.lang.String device_type) {
		set("device_type", device_type);
	}

	public java.lang.String getDevice_type() {
		return getStr("device_type");
	}

	public void setDevice_version(java.lang.String device_version) {
		set("device_version", device_version);
	}

	public java.lang.String getDevice_version() {
		return getStr("device_version");
	}

	public void setDevice_num(java.lang.String device_num) {
		set("device_num", device_num);
	}

	public java.lang.String getDevice_num() {
		return getStr("device_num");
	}

	public void setApp_version(java.lang.String app_version) {
		set("app_version", app_version);
	}

	public java.lang.String getApp_version() {
		return getStr("app_version");
	}

	public void setToken(java.lang.String token) {
		set("token", token);
	}

	public java.lang.String getToken() {
		return getStr("token");
	}

	public void setReg_time(java.util.Date reg_time) {
		set("reg_time", reg_time);
	}

	public java.util.Date getReg_time() {
		return get("reg_time");
	}

	public void setLast_login_time(java.util.Date last_login_time) {
		set("last_login_time", last_login_time);
	}

	public java.util.Date getLast_login_time() {
		return get("last_login_time");
	}

	public void setLast_visit_time(java.util.Date last_visit_time) {
		set("last_visit_time", last_visit_time);
	}

	public java.util.Date getLast_visit_time() {
		return get("last_visit_time");
	}

	public void setToken_exp_time(java.util.Date token_exp_time) {
		set("token_exp_time", token_exp_time);
	}

	public java.util.Date getToken_exp_time() {
		return get("token_exp_time");
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

	public void setRequest_token(java.lang.String request_token) {
		set("request_token", request_token);
	}

	public java.lang.String getRequest_token() {
		return getStr("request_token");
	}

}
