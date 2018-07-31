package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseWebPartner;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheWebPartner;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class WebPartner extends CacheWebPartner<WebPartner> {
	public static final WebPartner dao = new WebPartner().dao();
	/**
	 * 获取合作伙伴列表
	 * @param language
	 * @param name
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryPartnerByPage(String language, String name, String status,PageKit page){
		String sql = "select p.*,  " +
				"            status.name status_show," +
				"            isShow.name is_show_name" +
				"       from phjf_web_partner p " +
				"       left join sys_datalibrary isShow on isShow.parent_code = 'sys_isShow' and isShow.code = p.is_show  " +
				"       left join sys_datalibrary status on status.parent_code = 'sys_status' and status.code = p.status  " ;
		List params = new ArrayList();
		if (F.validate.isNotEmpty(language)){
			sql += " where p.language = ? ";
			params.add(language);
		}
		if (F.validate.isNotEmpty(name)){
			sql += " and p.partner_name LIKE concat('%',?,'%') ";
			params.add(name);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and p.status = ? ";
			params.add(status);
		}
		sql += " order by p.orders ";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}
	/**
	 * 获取合作伙伴
	 * @param partner_code
	 * @param language
	 * @return
	 */
	public WebPartner findPartnerByCode(String partner_code, String language) {
		String sql = "select * " +
				"       from phjf_web_partner " +
				"      where partner_code = ? " +
				"        and language = ? " +
				"        and status = 1 " +
				"      limit 1 ";
		WebPartner data = WebPartner.dao.use("DBPublic").findFirst(sql, partner_code, language);
		return data;
	}
	/**
	 * 获取语言的中文名
	 * @return
	 */
	public String findLanguage_show(){
		SysDatalibrary library = SysDatalibrary.dao.queryDatalibrary("Phjf", "sys_language", this.getLanguage());
		return library.getName();
	}
	/**
	 * 通过ID获取合作伙伴
	 * @param seq_uuid
	 * @return
	 */
	public WebPartner findByPartnerId(String seq_uuid){
		String sql = "select * " +
				"       from phjf_web_partner " +
				"      where seq_uuid = ? " +
				"      limit 1";
		WebPartner data = WebPartner.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new WebPartner();
		}
		return data;
	}
	/**
	 * 通过名称获取数据
	 * @param name
	 * @return
	 */
	public WebPartner findByPartnerName(String name){
		String sql = "select * " +
				"       from phjf_web_partner " +
				"      where partner_name = ? " +
				"        and status = 1 ";
		return WebPartner.dao.use("DBPublic").findFirst(sql,name);
	}
	/**
	 * 保存或更新
	 * @param opt
	 * @return
	 */
	public WebPartner saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
		this.checkModelItem();
		if (F.validate.isNotEmpty(this.getPartner_id()) && !(this.getPartner_id().equals(0))) {
			this.setModify_user(opt.getSeq_id() + "");
			this.setModify_time(new Date());
			this.update();
		} else {
			if (F.validate.isEmpty(this.getPartner_code())) {
				this.setPartner_code(SysSeq.dao.generatorWebPartnerCode());
			}
			this.setSeq_uuid(UUID.randomUUID().toString());
			this.setIs_show("Y");
			this.setStatus("1");
			this.setCreate_user(opt.getSeq_id() + "");
			this.setCreate_time(new Date());
			this.setModify_user(opt.getSeq_id() + "");
			this.setModify_time(new Date());
			this.save();
		}
		flashCatch(this);
		return this;
	}
	public void checkModelItem() {
		this.checkPartner_id("合作伙伴ID标识");
		this.checkPartner_code("合作伙伴标识码");
		this.checkLanguage("语言");
		this.checkPartner_name("合作伙伴名称");
		this.checkPicture("合作伙伴图片");
		this.checkOrders("合作伙伴排序");
	}
	/**
	 * 判断该名称是否存在
	 * @param seq_uuid
	 * @param name
	 * @return
	 */
	public Boolean isHavePartnerName(String seq_uuid,String name){
		String sql = "select * " +
				"       from phjf_web_partner " +
				"      where seq_uuid != ?" +
				"        and partner_name = ? " +
				"        and status = 1 ";
		WebPartner mp = WebPartner.dao.use("DBPublic").findFirst(sql,seq_uuid,name);
		if(F.validate.isEmpty(mp)){
			return false;
		}
		return true;
	}
	/**
	 * 获取导航列表
	 * @return
	 */
	public List<WebPartner> queryPartnerList(String language){
		String sql = "select * " +
				"       from phjf_web_partner " +
				"      where language = ? " +
				"        and is_show = 'Y'" +
				"        and status = '1' order by orders ";
		return WebPartner.dao.use("DBPublic").find(sql,language);
	}
	public List<WebPartner> queryByCode(String partner_code){
		String sql = "select * " +
				"       from phjf_web_partner " +
				"      where partner_code = ? " +
				"        and is_show = 'Y'" +
				"        and status = '1' order by orders ";
		return WebPartner.dao.use("DBPublic").find(sql,partner_code);
	}
}