package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBankInfo<M extends BaseBankInfo<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setBank_id(java.lang.Integer bank_id) {
		set("bank_id", bank_id);
	}

	public java.lang.Integer getBank_id() {
		return getInt("bank_id");
	}

	public void setBank_code(java.lang.String bank_code) {
		set("bank_code", bank_code);
	}

	public java.lang.String getBank_code() {
		return getStr("bank_code");
	}

	public void setLanguage(java.lang.String language) {
		set("language", language);
	}

	public java.lang.String getLanguage() {
		return getStr("language");
	}

	public void setBt_code(java.lang.String bt_code) {
		set("bt_code", bt_code);
	}

	public java.lang.String getBt_code() {
		return getStr("bt_code");
	}

	public void setThird_bank_code(java.lang.String third_bank_code) {
		set("third_bank_code", third_bank_code);
	}

	public java.lang.String getThird_bank_code() {
		return getStr("third_bank_code");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public void setParent_bank_code(java.lang.String parent_bank_code) {
		set("parent_bank_code", parent_bank_code);
	}

	public java.lang.String getParent_bank_code() {
		return getStr("parent_bank_code");
	}

	public void setBank_path(java.lang.String bank_path) {
		set("bank_path", bank_path);
	}

	public java.lang.String getBank_path() {
		return getStr("bank_path");
	}

	public void setLat(java.lang.String lat) {
		set("lat", lat);
	}

	public java.lang.String getLat() {
		return getStr("lat");
	}

	public void setLng(java.lang.String lng) {
		set("lng", lng);
	}

	public java.lang.String getLng() {
		return getStr("lng");
	}

	public void setGeohash(java.lang.String geohash) {
		set("geohash", geohash);
	}

	public java.lang.String getGeohash() {
		return getStr("geohash");
	}

	public void setContact(java.lang.String contact) {
		set("contact", contact);
	}

	public java.lang.String getContact() {
		return getStr("contact");
	}

	public void setAddress(java.lang.String address) {
		set("address", address);
	}

	public java.lang.String getAddress() {
		return getStr("address");
	}

	public void setEmail(java.lang.String email) {
		set("email", email);
	}

	public java.lang.String getEmail() {
		return getStr("email");
	}

	public void setTelephone(java.lang.String telephone) {
		set("telephone", telephone);
	}

	public java.lang.String getTelephone() {
		return getStr("telephone");
	}

	public void setMobilephone(java.lang.String mobilephone) {
		set("mobilephone", mobilephone);
	}

	public java.lang.String getMobilephone() {
		return getStr("mobilephone");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return getStr("remark");
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

	public void setIs_confirm(java.lang.String is_confirm) {
		set("is_confirm", is_confirm);
	}

	public java.lang.String getIs_confirm() {
		return getStr("is_confirm");
	}

}
