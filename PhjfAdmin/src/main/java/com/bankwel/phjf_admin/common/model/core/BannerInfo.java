package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseBannerInfo;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheBannerInfo;
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
public class BannerInfo extends CacheBannerInfo<BannerInfo> {
	public static final BannerInfo dao = new BannerInfo().dao();

	/**
	 * 新增或修改广告位
	 * @param opt
	 * @return
	 */
	public BannerInfo saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
		this.checkModelItem();
		if (F.validate.isNotEmpty(this.getBanner_id()+"")&&!(this.getBanner_id().equals(0))){
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			if(F.validate.isEmpty(this.getIs_show())){
				this.setIs_show("Y");
			}
			this.update();
		} else {
			if (F.validate.isEmpty(this.getBanner_code())){
				this.setBanner_code(SysSeq.dao.generatorBannerCode());
			}
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
		this.checkLanguage("语言");
		this.checkOrder_num("轮播图序号");
		this.checkLocation("轮播图位置");
		this.checkContent_type("内容类型");
		this.checkOpen_type("打开类型");
		this.checkImg_url("轮播图片");
		this.checkHttp_url("网页链接地址");
		this.checkParams("app链接地址");
		this.checkClick_count("点击次数");
		this.checkRemark("轮播图备注");
	}
	/**
	 * 获取广告位列表
	 * @param location
	 * @param language
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryBannerByPage(String location, String language, String status, PageKit page){
		String sql = "select banner.seq_uuid," +
				"            banner.banner_id," +
				"            banner.banner_code," +
				"            banner.language," +
				"            banner.location," +
				"            banner.content_type," +
				"            banner.open_type," +
				"            banner.img_url," +
				"            banner.http_url," +
				"            banner.params," +
				"            banner.click_count," +
				"            banner.order_num," +
				"            banner.remark," +
				"            banner.status," +
				"            banner.create_time," +
				"            status.name status_show," +
				"            is_show.name is_show_name," +
				"            location.name location_show," +
				"            contentType.name contentType_show," +
				"            openType.name openType_show" +
				"       from phjf_banner_info banner" +
				"            left join sys_datalibrary status on status.parent_code = 'sys_status' and status.code = banner.status" +
				"            left join sys_datalibrary is_show on is_show.parent_code = 'sys_isShow' and is_show.code = banner.is_show" +
				"            left join sys_datalibrary location on location.parent_code = 'sys_location' and location.code = banner.location" +
				"            left join sys_datalibrary contentType on contentType.parent_code = 'sys_contentType' and contentType.code = banner.content_type" +
				"            left join sys_datalibrary openType on openType.parent_code = 'sys_openType' and openType.code = banner.open_type" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(location)){
			sql += " and banner.location = ? ";
			params.add(location);
		}
		if (F.validate.isNotEmpty(language)){
			sql += " and banner.language = ? ";
			params.add(language);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and banner.status = ? ";
			params.add(status);
		}
		sql += " order by banner.location asc, banner.order_num asc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取广告位
	 * @param seq_uuid
	 * @return
	 */
	public BannerInfo findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_banner_info " +
				"      where seq_uuid = ? " +
				"      limit 1";
		BannerInfo data = BannerInfo.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new BannerInfo();
		}
		return data;
	}

	/**
	 *通过banner_code获取广告位
	 * @param banner_code
	 * @return
	 */
	public List<BannerInfo> findByBannerCode(String banner_code){
		String sql = "select * " +
				"       from phjf_banner_info " +
				"      where banner_code = ? " +
				"        and status = 1 ";
		return BannerInfo.dao.use("DBPublic").find(sql,banner_code);
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
	 * 通过code和语言获取广告位
	 * @param banner_code
	 * @param language
	 * @return
	 */
	public BannerInfo findBannerInfo(String banner_code, String language){
		String sql = "select * " +
				"       from phjf_banner_info " +
				"      where banner_code = ? " +
				"        and language = ?" +
				"        and status = 1" +
				"      limit 1";
		BannerInfo data = BannerInfo.dao.use("DBPublic").findFirst(sql,banner_code,language);
		if(F.validate.isEmpty(data)){
			data = new BannerInfo();
		}
		return data;
	}
}