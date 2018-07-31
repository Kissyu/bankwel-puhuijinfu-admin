package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseUserLoanBankProcess;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheUserLoanBankProcess;
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
public class UserLoanBankProcess extends CacheUserLoanBankProcess<UserLoanBankProcess> {
	public static final UserLoanBankProcess dao = new UserLoanBankProcess().dao();
	/**
	 * 获取用户列表
	 * @param user_name
	 * @param bt_code
	 * @param loan_use_type
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryULBankProcessByPage(String user_name, String bt_code, String loan_use_type, String status, PageKit page){
		String sql = "select ulbp.seq_uuid," +
				"            ulbp.blua_id," +
				"            ulbp.ulp_id," +
				"            ulbp.user_id," +
				"            ulbp.mobilephone," +
				"            ulbp.true_name," +
				"            ulbp.id_type," +
				"            ulbp.id_num," +
				"            ulbp.bt_code," +
				"            ulbp.bank_code," +
				"            ulbp.loan_use_type," +
				"            ulbp.loan_amount," +
				"            ulbp.loan_use_term," +
				"            ulbp.apply_remark," +
                "            ulbp.platform_remark," +
				"            ulbp.accept_time," +
				"            ulbp.collection_time," +
				"            ulbp.submit_time," +
				"            ulbp.approval_time," +
				"            ulbp.lending_time," +
				"            ulbp.pass_time," +
				"            ulbp.status," +
				"            ulbp.create_time," +
				"            status.name status_show," +
				"            isShow.name is_show_name," +
				"            loanType.name loanType_show," +
				"            idType.name idType_show," +
				"            user.user_name user_name," +
				"            bt.name bt_name," +
				"            bank.name bank_name" +
				"       from phjf_user_loan_bank_process ulbp " +
				"            left join sys_datalibrary status on status.parent_code = 'loan_bank_processStatus' and status.code = ulbp.status" +
				"            left join sys_datalibrary isShow on isShow.parent_code = 'sys_isShow' and isShow.code = ulbp.is_show" +
				"            left join sys_datalibrary loanType on loanType.parent_code = 'loan_useType' and loanType.code = ulbp.loan_use_type" +
				"            left join sys_datalibrary idType on idType.parent_code = 'sys_idType' and idType.code = ulbp.id_type" +
				"            left join phjf_user_info user on user.user_id = ulbp.user_id" +
				"            left join phjf_bank_type bt on bt.bt_code = ulbp.bt_code and bt.language = 'ZH_SIMP'" +
				"            left join phjf_bank_info bank on bank.bank_code = ulbp.bank_code and bank.language = 'ZH_SIMP'" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(user_name)){
			sql += " and user.user_name = ? ";
			params.add(user_name);
		}
		if (F.validate.isNotEmpty(bt_code)){
			sql += " and( ulbp.bt_code = ? or bt.name LIKE concat('%',?,'%') )";
			params.add(bt_code);
			params.add(bt_code);
		}
		if (F.validate.isNotEmpty(loan_use_type)){
			sql += " and ulbp.loan_use_type = ? ";
			params.add(loan_use_type);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and ulbp.status = ? ";
			params.add(status);
		}
		sql += " order by ulbp.create_time desc, ulbp.user_id asc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	public List<UserLoanBankProcess> queryUserloanBankById(String ulpId){
		String sql = "select DISTINCT ulbp.bank_code " +
				"          , ulbp.status " +
				"       from phjf_user_loan_bank_process ulbp , " +
				"            phjf_bank_info bi " +
				"      where ulbp.bank_code = bi.bank_code " +
				"        and ulbp.ulp_id = ? " +
				"        and bi.status = 1 " +
				"        and bi.is_show = 'Y' " ;
		List<UserLoanBankProcess> data = UserLoanBankProcess.dao.use("DBPublic").find(sql,ulpId);
		if (F.validate.isEmpty(data)) {
			data = new ArrayList<UserLoanBankProcess>();
		}
		return data;
	}
	public void saveLoanSendToBanks(UserInfo user,UserLoanApply userLoan,BankInfo bi,String remark) throws MsgBusinessException {
		UserLoanBankProcess ulbp = new UserLoanBankProcess();
		ulbp.setUser_id(user.getUser_id());
		ulbp.setUlp_id(userLoan.getUlp_id());
		ulbp.setId_type(userLoan.getId_type());
		ulbp.setBt_code(bi.getBt_code());
		ulbp.setBank_code(bi.getBank_code());
		ulbp.setTrue_name(userLoan.getTrue_name());
		ulbp.setId_num(userLoan.getId_num());
		ulbp.setMobilephone(user.getMobilephone());
		ulbp.setLoan_use_type(userLoan.getLoan_use_type());
		ulbp.setLoan_amount(userLoan.getLoan_amount());
		ulbp.setLoan_use_term(userLoan.getLoan_use_term());
        ulbp.setApply_remark(userLoan.getRemark());
		ulbp.setStatus(("1"));
		ulbp.setPlatform_remark(remark);
		ulbp.saveOrUpdate(ulbp);
	}

	public void saveOrUpdate(UserLoanBankProcess ulbp) throws MsgBusinessException{
		ulbp.checkModelItem(ulbp);
		if (F.validate.isNotEmpty(ulbp.getSeq_uuid())){
			ulbp.update();
		} else {
			ulbp.setSeq_uuid(UUID.randomUUID().toString());
			ulbp.save();
		}
		flashCatch(ulbp);
	}
	public void checkModelItem(UserLoanBankProcess ulbp){
		ulbp.checkUlp_id("贷款申请ID");
		ulbp.checkUser_id("用户ID");
		ulbp.checkBt_code("银行类型编码");
		ulbp.checkBank_code("银行编码");
		ulbp.checkTrue_name("真实姓名");
		ulbp.checkId_type("证件类型");
		ulbp.checkId_num("证件号");
		ulbp.checkMobilephone("手机号");
		ulbp.checkLoan_use_type("贷款用途");
		ulbp.checkLoan_amount("贷款金额");
		ulbp.checkLoan_use_term("贷款使用期限");
		ulbp.checkApply_remark("申请人备注");
		ulbp.checkPlatform_remark("平台备注");
		ulbp.checkAccept_time("受理时间");
		ulbp.checkCollection_time("收集材料时间");
		ulbp.checkSubmit_time("上报时间");
		ulbp.checkApproval_time("审批通过时间");
		ulbp.checkLending_time("放款时间");
		ulbp.checkPass_time("驳回时间");
	}
	public UserLoanBankProcess findselfRemark(String ulp_id) {
		String sql = "select ulbp.*  " +
				"       from phjf_user_loan_bank_process ulbp " +
				"      where ulbp.ulp_id = ? " +
				"            order by ulbp.create_time DESC " +
				"         limit 1  " ;
		UserLoanBankProcess data = UserLoanBankProcess.dao.use("DBPublic").findFirst(sql,ulp_id);
		if (F.validate.isEmpty(data)) {
			data = new UserLoanBankProcess();
		}
		return data;
	}

}
