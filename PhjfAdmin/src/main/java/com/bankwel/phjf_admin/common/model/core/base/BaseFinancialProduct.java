package com.bankwel.phjf_admin.common.model.core.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFinancialProduct<M extends BaseFinancialProduct<M>> extends Model<M> implements IBean {

	public void setSeq_uuid(java.lang.String seq_uuid) {
		set("seq_uuid", seq_uuid);
	}

	public java.lang.String getSeq_uuid() {
		return getStr("seq_uuid");
	}

	public void setFp_id(java.lang.Integer fp_id) {
		set("fp_id", fp_id);
	}

	public java.lang.Integer getFp_id() {
		return getInt("fp_id");
	}

	public void setFp_code(java.lang.String fp_code) {
		set("fp_code", fp_code);
	}

	public java.lang.String getFp_code() {
		return getStr("fp_code");
	}

	public void setLanguage(java.lang.String language) {
		set("language", language);
	}

	public java.lang.String getLanguage() {
		return getStr("language");
	}

	public void setFp_type(java.lang.String fp_type) {
		set("fp_type", fp_type);
	}

	public java.lang.String getFp_type() {
		return getStr("fp_type");
	}

	public void setBt_code(java.lang.String bt_code) {
		set("bt_code", bt_code);
	}

	public java.lang.String getBt_code() {
		return getStr("bt_code");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public void setThird_code(java.lang.String third_code) {
		set("third_code", third_code);
	}

	public java.lang.String getThird_code() {
		return getStr("third_code");
	}

	public void setCurrency(java.lang.String currency) {
		set("currency", currency);
	}

	public java.lang.String getCurrency() {
		return getStr("currency");
	}

	public void setOpen_type(java.lang.String open_type) {
		set("open_type", open_type);
	}

	public java.lang.String getOpen_type() {
		return getStr("open_type");
	}

	public void setRisk_level(java.lang.String risk_level) {
		set("risk_level", risk_level);
	}

	public java.lang.String getRisk_level() {
		return getStr("risk_level");
	}

	public void setInverate_expect_min(java.lang.String inverate_expect_min) {
		set("inverate_expect_min", inverate_expect_min);
	}

	public java.lang.String getInverate_expect_min() {
		return getStr("inverate_expect_min");
	}

	public void setInverate_expect_max(java.lang.String inverate_expect_max) {
		set("inverate_expect_max", inverate_expect_max);
	}

	public java.lang.String getInverate_expect_max() {
		return getStr("inverate_expect_max");
	}

	public void setInverate_actual(java.lang.String inverate_actual) {
		set("inverate_actual", inverate_actual);
	}

	public java.lang.String getInverate_actual() {
		return getStr("inverate_actual");
	}

	public void setAmount_acc_min(java.math.BigDecimal amount_acc_min) {
		set("amount_acc_min", amount_acc_min);
	}

	public java.math.BigDecimal getAmount_acc_min() {
		return get("amount_acc_min");
	}

	public void setAmount_acc_max(java.math.BigDecimal amount_acc_max) {
		set("amount_acc_max", amount_acc_max);
	}

	public java.math.BigDecimal getAmount_acc_max() {
		return get("amount_acc_max");
	}

	public void setAmount_redeem_min(java.math.BigDecimal amount_redeem_min) {
		set("amount_redeem_min", amount_redeem_min);
	}

	public java.math.BigDecimal getAmount_redeem_min() {
		return get("amount_redeem_min");
	}

	public void setAmount_redeem_max(java.math.BigDecimal amount_redeem_max) {
		set("amount_redeem_max", amount_redeem_max);
	}

	public java.math.BigDecimal getAmount_redeem_max() {
		return get("amount_redeem_max");
	}

	public void setAmount_total_min(java.math.BigDecimal amount_total_min) {
		set("amount_total_min", amount_total_min);
	}

	public java.math.BigDecimal getAmount_total_min() {
		return get("amount_total_min");
	}

	public void setAmount_total_max(java.math.BigDecimal amount_total_max) {
		set("amount_total_max", amount_total_max);
	}

	public java.math.BigDecimal getAmount_total_max() {
		return get("amount_total_max");
	}

	public void setRaise_start_date(java.util.Date raise_start_date) {
		set("raise_start_date", raise_start_date);
	}

	public java.util.Date getRaise_start_date() {
		return get("raise_start_date");
	}

	public void setRaise_end_date(java.util.Date raise_end_date) {
		set("raise_end_date", raise_end_date);
	}

	public java.util.Date getRaise_end_date() {
		return get("raise_end_date");
	}

	public void setStart_date(java.util.Date start_date) {
		set("start_date", start_date);
	}

	public java.util.Date getStart_date() {
		return get("start_date");
	}

	public void setEnd_date(java.util.Date end_date) {
		set("end_date", end_date);
	}

	public java.util.Date getEnd_date() {
		return get("end_date");
	}

	public void setTerm(java.lang.String term) {
		set("term", term);
	}

	public java.lang.String getTerm() {
		return getStr("term");
	}

	public void setExpire_end_date(java.util.Date expire_end_date) {
		set("expire_end_date", expire_end_date);
	}

	public java.util.Date getExpire_end_date() {
		return get("expire_end_date");
	}

	public void setThird_status(java.lang.String third_status) {
		set("third_status", third_status);
	}

	public java.lang.String getThird_status() {
		return getStr("third_status");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setShare_count(java.lang.Integer share_count) {
		set("share_count", share_count);
	}

	public java.lang.Integer getShare_count() {
		return getInt("share_count");
	}

	public void setClick_count(java.lang.Integer click_count) {
		set("click_count", click_count);
	}

	public java.lang.Integer getClick_count() {
		return getInt("click_count");
	}

	public void setBuy_count(java.lang.Integer buy_count) {
		set("buy_count", buy_count);
	}

	public java.lang.Integer getBuy_count() {
		return getInt("buy_count");
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
