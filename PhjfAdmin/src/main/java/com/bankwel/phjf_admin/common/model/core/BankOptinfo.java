package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.base.BaseBankOptinfo;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheBankOptinfo;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.validator.Msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class BankOptinfo extends CacheBankOptinfo<BankOptinfo> {
	public static final BankOptinfo dao = new BankOptinfo().dao();

	private BankInfo bankInfo;

	/**
	 * 新增或修改银行账户
	 * @param opt
	 * @return
	 */
	public BankOptinfo saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
		this.checkModelItem();
		if (F.validate.isNotEmpty(this.getOpt_id()+"")&&!(this.getOpt_id().equals(0))){
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			this.update();
		} else {
			this.setSeq_uuid(UUID.randomUUID().toString());
			this.setIs_show("Y");
			this.setStatus("1");
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
		this.checkBank_code("银行网点编码");
		this.checkUser_id("用户id");
		this.checkName("银行网点操作人员姓名");
		this.checkMobilephone("移动电话");
		this.checkCerti_type("银行网点操作人员证件类型");
		this.checkCerti_no("银行网点操作人员证件号码");
		this.checkTelephone("银行网点操作人员固定电话");
		this.checkGender("银行网点操作人员性别");
		this.checkEmail("银行网点操作人员邮箱");
		this.checkAddress("银行网点操作人员地址");
		this.checkRemark("银行网点操作人员备注");
	}
	/**
	 * 获取银行账户列表
	 * @param bank_name
	 * @param name
	 * @param user_name
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryBankOptInfoByPage(String bank_name, String name, String user_name, String status, PageKit page){
		String sql = "select bankopt.seq_uuid," +
				"            bankopt.opt_id," +
				"            bankopt.bank_code," +
				"            bankopt.name," +
				"            bankopt.user_id," +
				"            bankopt.mobilephone," +
				"            bankopt.telephone," +
				"            bankopt.certi_type," +
				"            bankopt.certi_no," +
				"            bankopt.email," +
				"            bankopt.address," +
				"            bankopt.status," +
				"            bankopt.create_time," +
				"            user.user_name user_name," +
				"            bankopt.gender," +
				"            status.name status_show," +
				"            isShow.name is_show_name," +
				"            gender.name gender_show," +
				"            idType.name idType_show," +
				"            bank.name bank_name," +
				"            bt.name bt_name," +
				"            opt.operate_name creat_opt_name" +
				"       from phjf_bank_optinfo bankopt" +
				"            left join phjf_user_info user on bankopt.user_id = user.user_id" +
				"            left join phjf_user_appauth auth on bankopt.user_id = auth.user_id and auth.app_code = 'phjf_merchclient'" +
				"            left join sys_datalibrary status on status.parent_code = 'userAuth_status' and status.code = auth.status" +
				"            left join sys_datalibrary isShow on isShow.parent_code = 'sys_isShow' and isShow.code = bankopt.is_show" +
				"            left join sys_datalibrary gender on gender.parent_code = 'sys_gender' and gender.code = bankopt.gender" +
				"            left join sys_datalibrary idType on idType.parent_code = 'sys_idType' and idType.code = bankopt.certi_type" +
				"            left join phjf_bank_info bank on bankopt.bank_code = bank.bank_code and bank.language = 'ZH_SIMP'" +
				"            left join phjf_bank_type bt on bank.bt_code = bt.bt_code and bt.language = 'ZH_SIMP'" +
				"            left join auth_operator opt on opt.seq_id = bankopt.create_opt" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(bank_name)){
			sql += " and (bank.name LIKE concat('%',?,'%') or bankopt.bank_code = ?) ";
			params.add(bank_name);
			params.add(bank_name);
		}
		if (F.validate.isNotEmpty(name)){
			sql += " and (bankopt.name LIKE concat('%',?,'%') or user.nickname LIKE concat('%',?,'%'))";
			params.add(name);
			params.add(name);
		}
		if (F.validate.isNotEmpty(user_name)){
			sql += " and user.user_name = ? ";
			params.add(user_name);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and bankopt.status = ? ";
			params.add(status);
		}
		sql += " order by bankopt.create_time desc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取银行账户
	 * @param seq_uuid
	 * @return
	 */
	public BankOptinfo findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_bank_optinfo " +
				"      where seq_uuid = ? " +
				"      limit 1";
		BankOptinfo data = BankOptinfo.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new BankOptinfo();
		}
		return data;
	}

	/**
	 * 获取银行机构
	 * @return
	 */
	public BankInfo findBankInfo(){
		if (F.validate.isEmpty(bankInfo)){
			bankInfo = BankInfo.dao.findBankInfo(this.getBank_code(), AdminConstants.LanguageType.ZH_SIMP.value);
		}
		return bankInfo;
	}

	/**
	 * 通过用户ID获取银行账户
	 * @param user_id
	 * @return
	 */
	public BankOptinfo findBankOptByUserId(String user_id){
		String sql = "select * " +
				"       from phjf_bank_optinfo " +
				"      where user_id = ?" +
				"        and status = 1" +
				"      limit 1";
		BankOptinfo data = BankOptinfo.dao.use("DBPublic").findFirst(sql,user_id);
		if(F.validate.isEmpty(data)){
			data = new BankOptinfo();
		}
		return data;
	}
}
