package com.bankwel.phjf_admin.common.model.core;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _PhjfMappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _PhjfMappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("auth_branch", "seq_id", AuthBranch.class);
		arp.addMapping("auth_branch_apply", "seq_id", AuthBranchApply.class);
		arp.addMapping("auth_operator", "seq_id", AuthOperator.class);
		arp.addMapping("auth_operator_auth", "seq_id", AuthOperatorAuth.class);
		arp.addMapping("auth_resource", "seq_id", AuthResource.class);
		arp.addMapping("auth_resource_service", "seq_id", AuthResourceService.class);
		arp.addMapping("auth_role", "seq_id", AuthRole.class);
		arp.addMapping("auth_role_resource", "seq_id", AuthRoleResource.class);
		arp.addMapping("auth_service", "seq_id", AuthService.class);
		arp.addMapping("phjf_app_info", "app_id", AppInfo.class);
		arp.addMapping("phjf_app_version_info", "avi_id", AppVersionInfo.class);
		arp.addMapping("phjf_appointment_time", "seq_id", AppointmentTime.class);
		arp.addMapping("phjf_bank_atm", "bank_atm_id", BankAtm.class);
		arp.addMapping("phjf_bank_bankcard_tradelog", "seq_id", BankBankcardTradelog.class);
		arp.addMapping("phjf_bank_business_config", "seq_id", BankBusinessConfig.class);
		arp.addMapping("phjf_bank_business_setting", "seq_id", BankBusinessSetting.class);
		arp.addMapping("phjf_bank_deposit_info", "bdi_id", BankDepositInfo.class);
		arp.addMapping("phjf_bank_info", "bank_id", BankInfo.class);
		arp.addMapping("phjf_bank_optinfo", "opt_id", BankOptinfo.class);
		arp.addMapping("phjf_bank_type", "bt_id", BankType.class);
		arp.addMapping("phjf_banner_info", "banner_id", BannerInfo.class);
		arp.addMapping("phjf_financial_fund", "fp_id", FinancialFund.class);
		arp.addMapping("phjf_financial_product", "fp_id", FinancialProduct.class);
		arp.addMapping("phjf_loan_info", "loan_id", LoanInfo.class);
		arp.addMapping("phjf_managepoint_bank", "mb_id", ManagepointBank.class);
		arp.addMapping("phjf_managepoint_info", "mp_id", ManagepointInfo.class);
		arp.addMapping("phjf_managepoint_optinfo", "opt_id", ManagepointOptinfo.class);
		arp.addMapping("phjf_news_article", "na_id", NewsArticle.class);
		arp.addMapping("phjf_news_plate", "plate_id", NewsPlate.class);
		arp.addMapping("phjf_policy_article", "pa_id", PolicyArticle.class);
		arp.addMapping("phjf_policy_article_plate", "pap_id", PolicyArticlePlate.class);
		arp.addMapping("phjf_policy_article_search", "pak_id", PolicyArticleSearch.class);
		arp.addMapping("phjf_policy_plate", "pp_id", PolicyPlate.class);
		arp.addMapping("phjf_policy_plate_city", "ppc_id", PolicyPlateCity.class);
		arp.addMapping("phjf_search_terms", "st_id", SearchTerms.class);
		arp.addMapping("phjf_sms_code", "code_id", SmsCode.class);
		arp.addMapping("phjf_sms_log", "log_id", SmsLog.class);
		arp.addMapping("phjf_user_appauth", "uad_id", UserAppauth.class);
		arp.addMapping("phjf_user_bankcard", "ubc_id", UserBankcard.class);
		arp.addMapping("phjf_user_bankcard_appointment", "uba_id", UserBankcardAppointment.class);
		arp.addMapping("phjf_user_feedback", "fb_id", UserFeedback.class);
		arp.addMapping("phjf_user_info", "user_id", UserInfo.class);
		arp.addMapping("phjf_user_loan_apply", "ulp_id", UserLoanApply.class);
		arp.addMapping("phjf_user_loan_bank_process", "blua_id", UserLoanBankProcess.class);
		arp.addMapping("phjf_user_login_log", "seq_id", UserLoginLog.class);
		arp.addMapping("phjf_user_withdraw_appointment", "uwa_id", UserWithdrawAppointment.class);
		arp.addMapping("phjf_web_about_us", "au_id", WebAboutUs.class);
		arp.addMapping("phjf_web_app_download_info", "adi_id", WebAppDownloadInfo.class);
		arp.addMapping("phjf_web_banner", "banner_id", WebBanner.class);
		arp.addMapping("phjf_web_links", "links_id", WebLinks.class);
		arp.addMapping("phjf_web_nav", "nav_id", WebNav.class);
		arp.addMapping("phjf_web_news_info", "ni_id", WebNewsInfo.class);
		arp.addMapping("phjf_web_news_plate", "np_id", WebNewsPlate.class);
		arp.addMapping("phjf_web_partner", "partner_id", WebPartner.class);
		arp.addMapping("sys_apply_center", "seq_id", SysApplyCenter.class);
		arp.addMapping("sys_areas", "seq_id", SysAreas.class);
		arp.addMapping("sys_cities", "seq_id", SysCities.class);
		arp.addMapping("sys_datalibrary", "seq_id", SysDatalibrary.class);
		arp.addMapping("sys_param", "param_id", SysParam.class);
		arp.addMapping("sys_provinces", "seq_id", SysProvinces.class);
		arp.addMapping("sys_sensitiveWords_library", "sw_id", SysSensitivewordsLibrary.class);
		arp.addMapping("sys_seq", "seq_id", SysSeq.class);
		arp.addMapping("sys_monitor_buslog", "seq_id", SysMonitorBuslog.class);
		arp.addMapping("auth_operator_login_log", "seq_id", AuthOperatorLoginLog.class);
	}
}
