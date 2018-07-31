package com.bankwel.phjf_website.common.model.website;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_website.common.model.website.base.BaseWebNav;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class WebNav extends BaseWebNav<WebNav> {
	public static final WebNav dao = new WebNav().dao();
	/**
	 * 获取导航编码
	 * @return
	 */
	public WebNav findNavCodeByUri(String language,String url){
		String sql = "select * " +
				"       from phjf_web_nav " +
				"      where language = ? " +
				"        and url = ? " +
				"        and status = 1" +
				"        and is_show = 'Y' ";
		return WebNav.dao.use("DBPublic").findFirst(sql,language,url);
	}
	/**
	 * 获取导航列表
	 * @return
	 */
	public List<WebNav> queryTopNavs(String language,String parent_nav_code){
		String sql = "select * " +
				"       from phjf_web_nav " +
				"      where language = ? " +
				"        and parent_nav_code = ? " +
				"        and is_show = 'Y'" +
				"        and status = '1' order by orders ";
		return WebNav.dao.use("DBPublic").find(sql,language,parent_nav_code);
	}
}
