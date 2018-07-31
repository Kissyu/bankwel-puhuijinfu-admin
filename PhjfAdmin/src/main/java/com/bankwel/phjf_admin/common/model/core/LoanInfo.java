package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseLoanInfo;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheLoanInfo;
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
public class LoanInfo extends CacheLoanInfo<LoanInfo> {
	public static final LoanInfo dao = new LoanInfo().dao();
    private BankType bankType;

	/**
	 * 新增或修改
	 * @param opt
	 * @return
	 */
	public LoanInfo saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
		this.checkModelItem();
		if (F.validate.isNotEmpty(this.getSeq_uuid()+"")&&!(this.getSeq_uuid().equals(0))){
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			this.update();
		} else {
			this.setSeq_uuid(UUID.randomUUID().toString());
			this.setLoan_code(SysSeq.dao.generatorLoanCode());
			if(F.validate.isEmpty(this.getIs_recom())){
				this.setIs_recom("N");
			}
			if(F.validate.isEmpty(this.getSource())){
				this.setSource("bank");
			}
			this.setIs_show("Y");
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
		this.checkLanguage("语言");
		this.checkBt_code("银行网点所属银行");
		this.checkName("贷款产品名称");
		this.checkTags("贷款产品关键字标签");
		this.checkLogo("贷款产品宣传图片");
		this.checkLoan_plate("贷款大类");
		this.checkLoan_use_type("贷款用途");
		this.checkLoan_term_lower("每月贷款期限下限");
		this.checkLoan_term_upper("每月贷款期限上限");
		this.checkLoan_rate_lower("每月贷款利率上限");
		this.checkLoan_rate_upper("每月贷款利率下限");
		this.checkLoan_amount_upper("贷款产品发放额度上限");
		this.checkLoan_amount_lower("贷款产品发放额度下限");
		this.checkSource("贷款产品来源");
		this.checkIs_recom("贷款产品是否推荐");
		this.checkOrder_num("贷款产品序号");
	}
	/**
	 * 查询贷款信息
	 * @param bt_name
	 * @param loan_plate
	 * @param name
	 * @param loan_use_type
	 * @param language
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryLoanInfoByPage(String bt_name, String loan_plate, String name, String loan_use_type, String is_recom, String status, String language, PageKit page){
		String sql = "select loan.seq_uuid " +
				"            , bankType.name bt_name " +
				"            , loan.name " +
				"            , loan.loan_code " +
				"            , loan.language " +
				"            , loanPlate.name loanPlate_show" +
				"            , useType.name useType_show " +
				"            , loan.logo " +
				"            , loan.loan_term_upper " +
				"            , loan.loan_term_lower " +
				"            , loan.loan_rate_upper " +
				"            , loan.loan_rate_lower " +
				"            , loan.loan_amount_upper " +
				"            , loan.loan_amount_lower " +
				"            , loanSource.name loanSource_show " +
				"            , loan.is_recom " +
				"            , loan.tags " +
				"            , isRecom.name is_recom_show " +
				"            , status.name status_show " +
				"            , isShow.name is_show " +
				"            , loan.create_time " +
				"       from phjf_loan_info loan " +
				"            left join phjf_bank_type bankType on bankType.bt_code = loan.bt_code and bankType.language = ? " +
				"            left join sys_datalibrary loanPlate on loanPlate.parent_code = 'loan_plate' and loanPlate.code = loan.loan_plate " +
				"            left join sys_datalibrary useType on useType.parent_code = 'loan_useType' and useType.code = loan.loan_use_type " +
				"            left join sys_datalibrary loanSource on loanSource.parent_code = 'loan_source' and loanSource.code = loan.source " +
				"            left join sys_datalibrary status on status.parent_code = 'sys_articleStatus' and status.code = loan.status " +
				"            left join sys_datalibrary isRecom on isRecom.parent_code = 'sys_isRecom' and isRecom.code = loan.is_recom " +
				"            left join sys_datalibrary isShow on isShow.parent_code = 'sys_isShow' and isShow.code = loan.is_show " +
				"      where 1 = 1 ";
		List params = new ArrayList();
		params.add(language);
		if (F.validate.isNotEmpty(bt_name)){
			sql += " and (bankType.name like concat('%',?,'%') or bankType.bt_code = ?) ";
			params.add(bt_name);
			params.add(bt_name);
		}
		if (F.validate.isNotEmpty(loan_plate)){
			sql += " and loan.loan_plate = ? ";
			params.add(loan_plate);
		}
		if (F.validate.isNotEmpty(name)){
			sql += " and loan.name like concat('%',?,'%') ";
			params.add(name);
		}
		if (F.validate.isNotEmpty(loan_use_type)){
			sql += " and loan.loan_use_type = ? ";
			params.add(loan_use_type);
		}
		if (F.validate.isNotEmpty(is_recom)){
			sql += " and loan.is_recom = ? ";
			params.add(is_recom);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and loan.status = ? ";
			params.add(status);
		}

		sql += " order by loan.create_time desc ";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 根据贷款代码获取贷款信息
	 * @param seq_uuid
	 * @return
     */
	public LoanInfo findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_loan_info " +
				"      where seq_uuid = ? " +
				"      limit 1 ";
		LoanInfo loanInfo = dao.use("DBPublic").findFirst(sql, seq_uuid);
		if (F.validate.isEmpty(loanInfo)){
			loanInfo = new LoanInfo();
		}
		return loanInfo;
	}

	/**
	 * 通过loan_code获取贷款产品
	 * @param loan_code
	 * @return
	 */
	public List<LoanInfo> findByLoanCode(String loan_code){
		String sql = "select * " +
				"       from phjf_loan_info " +
				"      where loan_code = ? " +
				"        and status != 3";
		return  dao.use("DBPublic").find(sql, loan_code);
	}

	/**
	 * 获取银行类型
	 * @param language
	 * @return
	 */
	public BankType findBankType(String language){
		if (F.validate.isEmpty(bankType)){
			bankType = BankType.dao.findByCode(this.getBt_code(), language);
		}
		return bankType;
	}

	/**
	 * 获取语言的中文名
	 * @return
	 */
	public String findLanguage_show(){
		SysDatalibrary library = SysDatalibrary.dao.queryDatalibrary("Phjf", "sys_language", this.getLanguage());
		return library.getName();
	}
}