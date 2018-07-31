package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBankDepositInfo<M extends BaseBankDepositInfo<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setBdi_id(java.lang.Integer bdi_id) {
		set("bdi_id", bdi_id);
	}

	public java.lang.Integer getBdi_id() {
		return getInt("bdi_id");
	}

	public void setBd_code(java.lang.String bd_code) {
		set("bd_code", bd_code);
	}

	public java.lang.String getBd_code() {
		return getStr("bd_code");
	}

	public void setLanguage(java.lang.String language) {
		set("language", language);
	}

	public java.lang.String getLanguage() {
		return getStr("language");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public void setBt_code(java.lang.String bt_code) {
		set("bt_code", bt_code);
	}

	public java.lang.String getBt_code() {
		return getStr("bt_code");
	}

	public void setTerm_type(java.lang.String term_type) {
		set("term_type", term_type);
	}

	public java.lang.String getTerm_type() {
		return getStr("term_type");
	}

	public void setTerm(java.lang.String term) {
		set("term", term);
	}

	public java.lang.String getTerm() {
		return getStr("term");
	}

	public void setSeven_rate(java.math.BigDecimal seven_rate) {
		set("seven_rate", seven_rate);
	}

	public java.math.BigDecimal getSeven_rate() {
		return get("seven_rate");
	}

	public void setYear_rate(java.math.BigDecimal year_rate) {
		set("year_rate", year_rate);
	}

	public java.math.BigDecimal getYear_rate() {
		return get("year_rate");
	}

	public void setMin_amount(java.math.BigDecimal min_amount) {
		set("min_amount", min_amount);
	}

	public java.math.BigDecimal getMin_amount() {
		return get("min_amount");
	}

	public void setCurrency(java.lang.String currency) {
		set("currency", currency);
	}

	public java.lang.String getCurrency() {
		return getStr("currency");
	}

	public void setIs_redeem(java.lang.String is_redeem) {
		set("is_redeem", is_redeem);
	}

	public java.lang.String getIs_redeem() {
		return getStr("is_redeem");
	}

	public void setIs_top(java.lang.String is_top) {
		set("is_top", is_top);
	}

	public java.lang.String getIs_top() {
		return getStr("is_top");
	}

	public void setIs_recom(java.lang.String is_recom) {
		set("is_recom", is_recom);
	}

	public java.lang.String getIs_recom() {
		return getStr("is_recom");
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
