package com.bankwel.phjf_website.common.model.website;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_website.common.model.website.base.BaseBankType;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class BankType extends BaseBankType<BankType> {
	public static final BankType dao = new BankType().dao();

	/**
	 * 获取银行类型列表
	 * @return
	 */
	public List<BankType> queryBankTypeList(){
		String sql = "select bt.bt_code, bt.name " +
				"       from phjf_bank_type bt " +
				"      where bt.status = '1' " +
				"        and is_show = 'Y' ";
		return dao.use("DBPublic").find(sql);
	}


}
