package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.GeoHashKit;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseManagepointInfo;
import com.bankwel.phjf_admin.common.util.RansomCodeUtil;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheManagepointInfo;
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
public class ManagepointInfo extends CacheManagepointInfo<ManagepointInfo> {
	public static final ManagepointInfo dao = new ManagepointInfo().dao();

	/**
	 * 保存或更新办理点
	 * @param opt
	 * @return
     */
	public ManagepointInfo saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
		this.checkModelItem();
		this.setGeohash(GeoHashKit.encode(this.getLat(), this.getLng()));
		if (F.validate.isNotEmpty(this.getMp_id()+"")&&!(this.getMp_id().equals(0))){
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			if(F.validate.isEmpty(this.getIs_show())){
				this.setIs_show("Y");
			}
			this.update();
		} else {
			if (F.validate.isEmpty(this.getMp_code())){
				this.setMp_code(SysSeq.dao.generatorManagepointCode());
			}
			if(F.validate.isEmpty(this.getIs_withdraw())){
				this.setIs_withdraw("Y");
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
		this.checkName("办理点名称");
		this.checkContact("办理点联系人");
		this.checkMobilephone("办理点手机号");
		this.checkTelephone("办理点固定电话");
		this.checkOpen_hours("办理点开放时间");
		this.checkProvince("办理点所属省份");
		this.checkCity("办理点所属城市");
		this.checkArea("办理点所属地区");
		this.checkLat("办理点纬度");
		this.checkLng("办理点经度");
		this.checkDay_total_amount("办理点每日总额度");
		this.checkAddress("办理点地址");
		this.checkRemark("办理点备注");

	}

	/**
	 * 获取办理点列表
	 * @param language
	 * @param name
	 * @param contact
	 * @param mobilephone
	 * @param status
     * @param isConfirm
     *@param page  @return
     */
	public Pair<List<Record>,PageKit<Record>> queryManagepointByPage(String language, String name, String contact, String mobilephone, String status, String isConfirm, PageKit page){
		String sql = "select mp.seq_uuid," +
				"            mp.mp_id," +
				"            mp.name," +
				"            mp.telephone," +
				"            mp.lng," +
				"            mp.lat," +
				"            mp.mobilephone," +
				"            mp.contact," +
				"            mp.open_hours," +
				"            mp.address," +
				"            mp.province," +
				"            mp.city," +
				"            mp.area," +
				"            mp.day_total_amount," +
				"            mp.mp_code," +
				"            mp.is_withdraw," +
				"            mp.status," +
				"            mp.is_confirm," +
				"            mp.create_time," +
				"            lib1.name status_show," +
				"            lib2.name is_show_name," +
				"            lib3.name is_withdraw_name," +
				"            isConfirm.name isConfirm_show," +
				"            pro.province province_show," +
				"            city.city city_show," +
				"            area.area area_show" +
				"       from phjf_managepoint_info mp" +
				"            left join sys_datalibrary lib1 on lib1.parent_code = 'sys_status' and lib1.code = mp.status" +
				"            left join sys_datalibrary lib2 on lib2.parent_code = 'sys_isShow' and lib2.code = mp.is_show" +
				"            left join sys_datalibrary lib3 on lib3.parent_code = 'sys_isWithdraw' and lib3.code = mp.is_withdraw" +
				"            left join sys_datalibrary isConfirm on isConfirm.parent_code = 'sys_isConfirm' and isConfirm.code = mp.is_confirm" +
				"            left join sys_provinces pro on mp.province = pro.province_id" +
				"            left join sys_cities city on mp.city = city.city_id" +
				"            left join sys_areas area on mp.area = area.area_id";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(language)){
			sql += " where mp.language = ? ";
			params.add(language);
		}
		if (F.validate.isNotEmpty(name)){
			sql += " and (mp.name LIKE concat('%',?,'%') or mp.mp_code = ?) ";
			params.add(name);
			params.add(name);
		}
		if (F.validate.isNotEmpty(contact)){
			sql += " and mp.contact LIKE concat('%',?,'%') ";
			params.add(contact);
		}
		if (F.validate.isNotEmpty(mobilephone)){
			sql += " and (mp.mobilephone = ? or mp.telephone = ?)";
			params.add(mobilephone);
			params.add(mobilephone);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and mp.status = ? ";
			params.add(status);
		}
		if (F.validate.isNotEmpty(isConfirm)){
			sql += " and mp.is_confirm = ? ";
			params.add(isConfirm);
		}

		sql += " order by mp.create_time desc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取办理点
	 * @param seq_uuid
	 * @return
     */
	public ManagepointInfo findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_managepoint_info " +
				"      where seq_uuid = ? " +
				"      limit 1";
		ManagepointInfo data = ManagepointInfo.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new ManagepointInfo();
		}
		return data;
	}

	/**
	 * 获取办理点
	 * @param mp_code
	 * @param language
     * @return
     */
	public ManagepointInfo findManagepoint(String mp_code, String language) {
		String sql = "select * " +
				"       from phjf_managepoint_info " +
				"      where mp_code = ? " +
				"        and language = ? " +
				"        and status = 1 " +
				"      limit 1 ";
		ManagepointInfo data = ManagepointInfo.dao.use("DBPublic").findFirst(sql, mp_code, language);
		return data;
	}

	/**
	 * 通过mp_code获取办理点
	 * @param mp_code
	 * @return
	 */
	public List<ManagepointInfo> findByMapCode(String mp_code){
		String sql = "select * " +
				"       from phjf_managepoint_info " +
				"      where mp_code = ? " +
				"        and status = 1 ";
		return ManagepointInfo.dao.use("DBPublic").find(sql,mp_code);
	}

	/**
	 * 通过办理点名称获取办理点
	 * @param name
	 * @return
	 */
	public ManagepointInfo findByMapName(String name){
		String sql = "select * " +
				"       from phjf_managepoint_info " +
				"      where name = ? " +
				"        and status = 1 ";
		return ManagepointInfo.dao.use("DBPublic").findFirst(sql,name);
	}

	/**
	 * 判断该办理点名称是否存在
	 * @param seq_uuid
	 * @param name
	 * @return
	 */
	public Boolean isHaveMpName(String seq_uuid,String name){
		String sql = "select * " +
				"       from phjf_managepoint_info " +
				"      where seq_uuid != ?" +
				"        and name = ? " +
				"        and status = 1 ";
		ManagepointInfo mp = ManagepointInfo.dao.use("DBPublic").findFirst(sql,seq_uuid,name);
		if(F.validate.isEmpty(mp)){
			return false;
		}
		return true;
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
	 * 通过ID获取市名
	 * @return
     */
	public String findProvice_show(){
		SysProvinces province = SysProvinces.dao.findProvinceById(this.getProvince());
		return province.getProvince();
	}

	/**
	 * 通过ID获取市名
	 * @return
     */
	public String findCity_show(){
		SysCities city = SysCities.dao.findCityById(this.getCity());
		return city.getCity();
	}

	/**
	 * 通过ID获取区名
	 * @return
     */
	public String findArea_show(){
		SysAreas area = SysAreas.dao.findAreaById(this.getArea());
		return area.getArea();
	}

}
