package com.bankwel.phjf_website.common.model.website;

import com.bankwel.phjf_website.common.model.website.base.BaseWebNewsInfo;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class WebNewsInfo extends BaseWebNewsInfo<WebNewsInfo> {
	public static final WebNewsInfo dao = new WebNewsInfo().dao();
	/**
	 * 获取首页列表
	 * @return
	 */
	public List<WebNewsInfo> queryIndexNews(String type,String language){
		String sql = "select * " +
				"       from phjf_web_news_info " +
				"      where type = ? " +
				"        and language = ? " +
				"        and  is_show = 'Y' " +
				"        and  status = '1' and is_recom = 'Y' order by orders ";
		return WebNewsInfo.dao.use("DBPublic").find(sql,type,language);
	}
}
