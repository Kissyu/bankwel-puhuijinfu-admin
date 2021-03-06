package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAppVersionInfo<M extends BaseAppVersionInfo<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setAvi_id(java.lang.Integer avi_id) {
		set("avi_id", avi_id);
	}

	public java.lang.Integer getAvi_id() {
		return getInt("avi_id");
	}

	public void setApp_id(java.lang.Integer app_id) {
		set("app_id", app_id);
	}

	public java.lang.Integer getApp_id() {
		return getInt("app_id");
	}

	public void setDevice_type(java.lang.String device_type) {
		set("device_type", device_type);
	}

	public java.lang.String getDevice_type() {
		return getStr("device_type");
	}

	public void setApp_version(java.lang.String app_version) {
		set("app_version", app_version);
	}

	public java.lang.String getApp_version() {
		return getStr("app_version");
	}

	public void setIs_update(java.lang.String is_update) {
		set("is_update", is_update);
	}

	public java.lang.String getIs_update() {
		return getStr("is_update");
	}

	public void setH5_url(java.lang.String h5_url) {
		set("h5_url", h5_url);
	}

	public java.lang.String getH5_url() {
		return getStr("h5_url");
	}

	public void setApp_url(java.lang.String app_url) {
		set("app_url", app_url);
	}

	public java.lang.String getApp_url() {
		return getStr("app_url");
	}

	public void setSize(java.math.BigDecimal size) {
		set("size", size);
	}

	public java.math.BigDecimal getSize() {
		return get("size");
	}

	public void setQr_code_url(java.lang.String qr_code_url) {
		set("qr_code_url", qr_code_url);
	}

	public java.lang.String getQr_code_url() {
		return getStr("qr_code_url");
	}

	public void setChange_content(java.lang.String change_content) {
		set("change_content", change_content);
	}

	public java.lang.String getChange_content() {
		return getStr("change_content");
	}

	public void setChannel(java.lang.String channel) {
		set("channel", channel);
	}

	public java.lang.String getChannel() {
		return getStr("channel");
	}

	public void setShare_count(java.lang.Integer share_count) {
		set("share_count", share_count);
	}

	public java.lang.Integer getShare_count() {
		return getInt("share_count");
	}

	public void setDownload_count(java.lang.Integer download_count) {
		set("download_count", download_count);
	}

	public java.lang.Integer getDownload_count() {
		return getInt("download_count");
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

	public void setPublish_date(java.util.Date publish_date) {
		set("publish_date", publish_date);
	}

	public java.util.Date getPublish_date() {
		return get("publish_date");
	}

	public void setUnder_date(java.util.Date under_date) {
		set("under_date", under_date);
	}

	public java.util.Date getUnder_date() {
		return get("under_date");
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
