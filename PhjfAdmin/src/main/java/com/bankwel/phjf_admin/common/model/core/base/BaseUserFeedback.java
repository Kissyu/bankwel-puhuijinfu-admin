package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserFeedback<M extends BaseUserFeedback<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setFb_id(java.lang.Integer fb_id) {
		set("fb_id", fb_id);
	}

	public java.lang.Integer getFb_id() {
		return getInt("fb_id");
	}

	public void setUser_id(java.lang.Integer user_id) {
		set("user_id", user_id);
	}

	public java.lang.Integer getUser_id() {
		return getInt("user_id");
	}

	public void setType(java.lang.String type) {
		set("type", type);
	}

	public java.lang.String getType() {
		return getStr("type");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return getStr("content");
	}

	public void setRecontent(java.lang.String recontent) {
		set("recontent", recontent);
	}

	public java.lang.String getRecontent() {
		return getStr("recontent");
	}

	public void setResult_status(java.lang.String result_status) {
		set("result_status", result_status);
	}

	public java.lang.String getResult_status() {
		return getStr("result_status");
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