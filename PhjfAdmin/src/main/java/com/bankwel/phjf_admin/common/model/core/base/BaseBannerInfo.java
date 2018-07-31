package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBannerInfo<M extends BaseBannerInfo<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setBanner_id(java.lang.Integer banner_id) {
		set("banner_id", banner_id);
	}

	public java.lang.Integer getBanner_id() {
		return getInt("banner_id");
	}

	public void setBanner_code(java.lang.String banner_code) {
		set("banner_code", banner_code);
	}

	public java.lang.String getBanner_code() {
		return getStr("banner_code");
	}

	public void setLanguage(java.lang.String language) {
		set("language", language);
	}

	public java.lang.String getLanguage() {
		return getStr("language");
	}

	public void setLocation(java.lang.String location) {
		set("location", location);
	}

	public java.lang.String getLocation() {
		return getStr("location");
	}

	public void setContent_type(java.lang.String content_type) {
		set("content_type", content_type);
	}

	public java.lang.String getContent_type() {
		return getStr("content_type");
	}

	public void setOpen_type(java.lang.String open_type) {
		set("open_type", open_type);
	}

	public java.lang.String getOpen_type() {
		return getStr("open_type");
	}

	public void setImg_url(java.lang.String img_url) {
		set("img_url", img_url);
	}

	public java.lang.String getImg_url() {
		return getStr("img_url");
	}

	public void setHttp_url(java.lang.String http_url) {
		set("http_url", http_url);
	}

	public java.lang.String getHttp_url() {
		return getStr("http_url");
	}

	public void setParams(java.lang.String params) {
		set("params", params);
	}

	public java.lang.String getParams() {
		return getStr("params");
	}

	public void setClick_count(java.lang.Integer click_count) {
		set("click_count", click_count);
	}

	public java.lang.Integer getClick_count() {
		return getInt("click_count");
	}

	public void setOrder_num(java.lang.Integer order_num) {
		set("order_num", order_num);
	}

	public java.lang.Integer getOrder_num() {
		return getInt("order_num");
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

}
