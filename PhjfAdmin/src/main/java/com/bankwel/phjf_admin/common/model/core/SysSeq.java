package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.base.BaseSysSeq;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheSysSeq;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysSeq extends CacheSysSeq<SysSeq> {
	public static final SysSeq dao = new SysSeq().dao();

	private String findNextSeq(String type){
		String result = "";
		String sql = "UPDATE sys_seq " +
				"	     SET seqno = LAST_INSERT_ID(case when seqno >= maxno then 1 else seqno+1 end) " +
				"      where type = ?;";
		Db.use("DBPublic").update(sql,type);
		Record data = Db.use("DBPublic").findFirst("SELECT LAST_INSERT_ID() seqno");
		if (F.validate.isNotEmpty(data)){
			result = data.getStr("seqno");
		}
		return result;
	}

	/**
	 * 生成银行类型编码
	 * @return
	 */
	public String generatorBankTypeCode(){
		String result = F.strKit.f("%s%s","BT", this.findNextSeq("BankType"));
		return result;
	}

	/**
	 * 生成银行编码
	 * @return
	 */
	public String generatorBankCode(){
		String result = F.strKit.f("%s%s","B", this.findNextSeq("Bank"));
		return result;
	}

	/**
	 * 生成办理点编码
	 * @return
	 */
	public String generatorManagepointCode(){
		String result = F.strKit.f("%s%s","MP", this.findNextSeq("Managepoint"));
		return result;
	}

	/**
	 * 生成办理点银行编码
	 * @return
	 */
	public String generatorMpBankCode(){
		String result = F.strKit.f("%s%s","MB", this.findNextSeq("MpBank"));
		return result;
	}

	public String generatorLoanCode(){
		String result = F.strKit.f("%s%s","L", this.findNextSeq("Loan"));
		return result;
	}

	/**
	 * 生成新闻版块编码
	 * @return
	 */
	public String generatorNewsPlateCode(){
		String result = F.strKit.f("%s%s","NP", this.findNextSeq("NewsPlate"));
		return result;
	}

	/**
	 * 生成新闻编码
	 * @return
	 */
	public String generatorNewsCode(){
		String result = F.strKit.f("%s%s","N", this.findNextSeq("News"));
		return result;
	}

	/**
	 * 生成新闻编码
	 * @return
	 */
	public String generatorPolicyCode(){
		String result = F.strKit.f("%s%s","P", this.findNextSeq("Policy"));
		return result;
	}

	/**
	 * 生成轮播图编码
	 * @return
	 */
	public String generatorBannerCode(){
		String result = F.strKit.f("%s%s","BN", this.findNextSeq("Banner"));
		return result;
	}

	/**
	 * 生成理财编码
	 * @return
	 */
	public String generatorFinancialProductCode(){
		String result = F.strKit.f("%s%s","FP", this.findNextSeq("FinancialProduct"));
		return result;
	}

	/**
	 * 生成办理点编码
	 * @return
	 */
	public String generatorATMCode(){
		String result = F.strKit.f("%s%s","ATM", this.findNextSeq("ATM"));
		return result;
	}
	/**
	 * 生成app编码
	 * @return
	 */
	public String generatorAPPCode(){
		String result = F.strKit.f("%s%s","APP", this.findNextSeq("APP"));
		return result;
	}
	/**
	 * 生成系统参数编码
	 * @return
	 */
	public String generatorSysParamCode(){
		String result = F.strKit.f("%s%s","SP", this.findNextSeq("SysParam"));
		return result;
	}

	//-----------------------官网-------------------------
	/**
	 * 生成网站导航编码
	 * @return
	 */
	public String generatorWebNavCode(){
		String result = F.strKit.f("%s%s","WN", this.findNextSeq("WebNav"));
		return result;
	}

	/**
	 * 生成网站合作伙伴编码
	 * @return
	 */
	public String generatorWebPartnerCode(){
		String result = F.strKit.f("%s%s","WP", this.findNextSeq("WebPartner"));
		return result;
	}
	/**
	 * 生成网站banner编码
	 * @return
	 */
	public String generatorWebBannerCode(){
		String result = F.strKit.f("%s%s","WB", this.findNextSeq("WebBanner"));
		return result;
	}
	/**
	 * 生成网站友情链接编码
	 * @return
	 */
	public String generatorWebLinksCode(){
		String result = F.strKit.f("%s%s","WL", this.findNextSeq("WebLinks"));
		return result;
	}
	/**
	 * 生成网站关于我们编码
	 * @return
	 */
	public String generatorWebAboutUsCode(){
		String result = F.strKit.f("%s%s","WAU", this.findNextSeq("AboutUs"));
		return result;
	}
	/**
	 * 生成网站APP下载信息编码
	 * @return
	 */
	public String generatorWebAdiCode(){
		String result = F.strKit.f("%s%s","WADI", this.findNextSeq("AppDownloadInfo"));
		return result;
	}

	/**
	 * 生成新闻版块编码
	 * @return
	 */
	public String generatorWebNPCode(){
		String result = F.strKit.f("%s%s","WNP", this.findNextSeq("WebNewsPlate"));
		return result;
	}
	/**
	 * 生成网站新闻编码
	 * @return
	 */
	public String generatorNiCode(){
		String result = F.strKit.f("%s%s","WNI", this.findNextSeq("WebNewsInfo"));
		return result;
	}
	/**
	 * 生成搜索条件编码
	 * @return
	 */
	public String generatorSearchTermCode(){
		String result = F.strKit.f("%s%s","ST", this.findNextSeq("SearchTerm"));
		return result;
	}
	/**
	 * 生成惠民政策版块编码
	 * @return
	 */
	public String generatorPolicyPlateCode(){
		String result = F.strKit.f("%s%s","pp", this.findNextSeq("PolicyPlate"));
		return result;
	}

}
