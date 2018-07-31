package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserBankcardAppointment<M extends BaseUserBankcardAppointment<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setUba_id(java.lang.Integer uba_id) {
		set("uba_id", uba_id);
	}

	public java.lang.Integer getUba_id() {
		return getInt("uba_id");
	}

	public void setUser_id(java.lang.Integer user_id) {
		set("user_id", user_id);
	}

	public java.lang.Integer getUser_id() {
		return getInt("user_id");
	}

	public void setMp_code(java.lang.String mp_code) {
		set("mp_code", mp_code);
	}

	public java.lang.String getMp_code() {
		return getStr("mp_code");
	}

	public void setBank_code(java.lang.String bank_code) {
		set("bank_code", bank_code);
	}

	public java.lang.String getBank_code() {
		return getStr("bank_code");
	}

	public void setBank_type(java.lang.String bank_type) {
		set("bank_type", bank_type);
	}

	public java.lang.String getBank_type() {
		return getStr("bank_type");
	}

	public void setTrue_name(java.lang.String true_name) {
		set("true_name", true_name);
	}

	public java.lang.String getTrue_name() {
		return getStr("true_name");
	}

	public void setId_card_no(java.lang.String id_card_no) {
		set("id_card_no", id_card_no);
	}

	public java.lang.String getId_card_no() {
		return getStr("id_card_no");
	}

	public void setMobilephone(java.lang.String mobilephone) {
		set("mobilephone", mobilephone);
	}

	public java.lang.String getMobilephone() {
		return getStr("mobilephone");
	}

	public void setAm_time(java.util.Date am_time) {
		set("am_time", am_time);
	}

	public java.util.Date getAm_time() {
		return get("am_time");
	}

	public void setAm_specific_time(java.lang.String am_specific_time) {
		set("am_specific_time", am_specific_time);
	}

	public java.lang.String getAm_specific_time() {
		return getStr("am_specific_time");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setBank_accept_time(java.util.Date bank_accept_time) {
		set("bank_accept_time", bank_accept_time);
	}

	public java.util.Date getBank_accept_time() {
		return get("bank_accept_time");
	}

	public void setBank_refuse_time(java.util.Date bank_refuse_time) {
		set("bank_refuse_time", bank_refuse_time);
	}

	public java.util.Date getBank_refuse_time() {
		return get("bank_refuse_time");
	}

	public void setBank_opt(java.lang.String bank_opt) {
		set("bank_opt", bank_opt);
	}

	public java.lang.String getBank_opt() {
		return getStr("bank_opt");
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
