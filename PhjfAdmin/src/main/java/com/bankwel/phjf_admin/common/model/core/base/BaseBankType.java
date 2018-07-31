package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBankType<M extends BaseBankType<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setBt_id(java.lang.Integer bt_id) {
		set("bt_id", bt_id);
	}

	public java.lang.Integer getBt_id() {
		return getInt("bt_id");
	}

	public void setBt_code(java.lang.String bt_code) {
		set("bt_code", bt_code);
	}

	public java.lang.String getBt_code() {
		return getStr("bt_code");
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

	public void setBt_logo(java.lang.String bt_logo) {
		set("bt_logo", bt_logo);
	}

	public java.lang.String getBt_logo() {
		return getStr("bt_logo");
	}

	public void setBt_card_bgimg(java.lang.String bt_card_bgimg) {
		set("bt_card_bgimg", bt_card_bgimg);
	}

	public java.lang.String getBt_card_bgimg() {
		return getStr("bt_card_bgimg");
	}

	public void setBt_card_img(java.lang.String bt_card_img) {
		set("bt_card_img", bt_card_img);
	}

	public java.lang.String getBt_card_img() {
		return getStr("bt_card_img");
	}

	public void setService_telephone(java.lang.String service_telephone) {
		set("service_telephone", service_telephone);
	}

	public java.lang.String getService_telephone() {
		return getStr("service_telephone");
	}

	public void setOpencard_need_material(java.lang.String opencard_need_material) {
		set("opencard_need_material", opencard_need_material);
	}

	public java.lang.String getOpencard_need_material() {
		return getStr("opencard_need_material");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setOrder_num(java.lang.Integer order_num) {
		set("order_num", order_num);
	}

	public java.lang.Integer getOrder_num() {
		return getInt("order_num");
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
