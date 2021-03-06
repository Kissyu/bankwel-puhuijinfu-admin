package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.DynamicSelectData;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheBankBusinessSetting;
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
public class BankBusinessSetting extends CacheBankBusinessSetting<BankBusinessSetting> {
	public static final BankBusinessSetting dao = new BankBusinessSetting().dao();
	/**
	 * 保存或修改银行业务
	 * @param opt
	 * @return
	 */
	public BankBusinessSetting saveOrUpdate(AuthOperator opt) throws MsgBusinessException {
		this.checkModelItem();
		if (this.getSeq_uuid() != null){
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			if(F.validate.isEmpty(this.getIs_show())){

			}
			this.update();
		} else {
			this.setType("bank_function");
			this.setSeq_uuid(UUID.randomUUID().toString());
			this.setCreate_opt(opt.getSeq_id()+"");
			this.setCreate_time(new Date());
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			this.save();
		}
		flashCatch(this);
		return this;
	}

	public void checkModelItem() {
		//check
		this.checkBt_code("银行类型编码");
//		this.checkType("银行业务归属");
		this.checkBusiness_code("银行业务");
	}

	/**
	 * 获取银行业务列表
	 * @param bt_name
	 * @param type
	 * @param status
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryBankBSByPage(String bt_name,String type,String business_code, String status , PageKit page){
		String sql = "select bbs.seq_uuid," +
				"            bbs.seq_id," +
				"            bbs.bt_code," +
				"            bt.name bt_name, " +
				"            bbs.type," +
				"            bbs.business_code," +
				"            bbs.is_show," +
				"            bbs.status," +
				"            bbs.create_time," +
				"            status.name status_name," +
				"            business_code.name business_code_name," +
				"            type.name type_name," +
				"             is_show.name is_show_name " +
				"       from phjf_bank_business_setting bbs" +
				"            left join phjf_bank_type bt on bt.bt_code = bbs.bt_code " +
				"            left join sys_datalibrary status on status.parent_code = 'sys_status' and status.code = bbs.status" +
				"            left join sys_datalibrary type on type.parent_code = 'bank_business_type' and type.code = bbs.type" +
				"            left join sys_datalibrary business_code on business_code.parent_code = 'bank_function' and business_code.code = bbs.business_code" +
				"            left join sys_datalibrary is_show on is_show.parent_code = 'sys_isShow' and is_show.code = bbs.is_show" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(bt_name)){
			sql += " and (bt.name LIKE concat('%',?,'%') )";
			params.add(bt_name);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and bbs.status = ? ";
			params.add(status);
		}
		if (F.validate.isNotEmpty(type)){
			sql += " and bbs.type = ? ";
			params.add(type);
		}
		if (F.validate.isNotEmpty(business_code)){
			sql += " and bbs.business_code = ? ";
			params.add(business_code);
		}
		sql += " order by bbs.bt_code asc, bbs.type asc, bbs.business_code asc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}
	/**
	 * 通过银行类型代码获取银行业务
	 * @param bt_code
	 * @return
	 */
	public BankBusinessSetting findByCode(String bt_code,String business_code){
		String sql = "select * " +
				"       from phjf_bank_business_setting " +
				"      where bt_code = ? " +
				"        and business_code = ? " +
				"        and status = 1" +
				"        and is_show = 'Y' " +
				"      limit 1 ";
		BankBusinessSetting data = dao.use("DBPublic").findFirst(sql, bt_code,business_code);
		if (F.validate.isEmpty(data)){
			data = new BankBusinessSetting();
		}
		return data;
	}

	/**
	 * 通过ID获取银行业务
	 * @param seq_uuid
	 * @return
	 */
	public BankBusinessSetting findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_bank_business_setting " +
				"      where seq_uuid = ? " +
				"      limit 1 ";
		BankBusinessSetting data = dao.use("DBPublic").findFirst(sql, seq_uuid);
		if (F.validate.isEmpty(data)){
			data = new BankBusinessSetting();
		}
		return data;
	}
	/**
	 * 判断该银行业务名称是否存在
	 * @param seq_uuid
	 * @return
	 */
	public Boolean isHaveBbsName(String seq_uuid,String bt_code,String business_code){
		String sql = "select * " +
				"       from phjf_bank_business_setting " +
				"      where seq_uuid != ?" +
				"        and bt_code = ? " +
				"        and business_code = ? " +
				"        and status = 1 ";
		BankBusinessSetting bt = BankBusinessSetting.dao.use("DBPublic").findFirst(sql,seq_uuid,bt_code,business_code);
		if(F.validate.isEmpty(bt)){
			return false;
		}
		return true;
	}
	/**
	 * 通过银行类型代码获取银行业务
	 * @param bt_code
	 * @return
	 */
	public Boolean findByIsHaveBank(String bt_code,String business_code){
		String sql = "select * " +
				"       from phjf_bank_business_setting " +
				"      where bt_code = ? " +
				"        and business_code = ? " +
				"        and type = 'bank_function' " +
				"        and status = 1" +
				"        and is_show = 'Y' " +
				"      limit 1 ";
		BankBusinessSetting data = dao.use("DBPublic").findFirst(sql, bt_code,business_code);
		if (F.validate.isEmpty(data)){
			return false;
		}
		return true;
	}



}
