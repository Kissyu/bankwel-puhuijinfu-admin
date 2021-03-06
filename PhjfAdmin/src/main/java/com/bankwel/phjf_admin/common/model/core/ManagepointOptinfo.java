package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.base.BaseManagepointOptinfo;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheManagepointOptinfo;
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
public class ManagepointOptinfo extends CacheManagepointOptinfo<ManagepointOptinfo> {
	public static final ManagepointOptinfo dao = new ManagepointOptinfo().dao();

	private ManagepointInfo managepointInfo;
	/**
	 * 新增或修改办理点账户
	 * @param opt
	 * @return
	 */
	public ManagepointOptinfo saveOrUpdate(AuthOperator opt){
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

	/**
	 * 获取办理点账户列表
	 * @param mp_name
	 * @param name
	 * @param user_name
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryMpOptInfoByPage(String mp_name, String name, String user_name,String status, PageKit page){
		String sql = "select optinfo.seq_uuid," +
				"            optinfo.opt_id," +
				"            optinfo.mp_code," +
				"            optinfo.name," +
				"            optinfo.user_id," +
				"            optinfo.mobilephone," +
				"            optinfo.telephone," +
				"            optinfo.certi_type," +
				"            optinfo.certi_no," +
				"            optinfo.email," +
				"            optinfo.address," +
				"            auth.status," +
				"            optinfo.create_time," +
				"            user.user_name user_name," +
				"            optinfo.gender," +
				"            status.name status_show," +
				"            is_show.name is_show_name," +
                "            gender.name gender_show," +
                "            idType.name idType_show," +
				"            mp.name mp_name," +
				"            opt.operate_name creat_opt_name" +
				"       from phjf_managepoint_optinfo optinfo" +
				"            left join phjf_user_info user on optinfo.user_id = user.user_id" +
				"            left join phjf_user_appauth auth on optinfo.user_id = auth.user_id and auth.app_code = 'phjf_merchclient'" +
				"            left join sys_datalibrary status on status.parent_code = 'userAuth_status' and status.code = auth.status" +
				"            left join sys_datalibrary is_show on is_show.parent_code = 'sys_isShow' and is_show.code = optinfo.is_show" +
                "            left join sys_datalibrary gender on gender.parent_code = 'sys_gender' and gender.code = optinfo.gender" +
                "            left join sys_datalibrary idType on idType.parent_code = 'sys_idType' and idType.code = optinfo.certi_type" +
				"            left join phjf_managepoint_info mp on optinfo.mp_code = mp.mp_code and mp.language = 'ZH_SIMP'" +
				"            left join auth_operator opt on opt.seq_id = optinfo.create_opt" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(mp_name)){
			sql += " and (mp.name LIKE concat('%',?,'%') or optinfo.mp_code = ?) ";
			params.add(mp_name);
			params.add(mp_name);
		}
		if (F.validate.isNotEmpty(name)){
			sql += " and (optinfo.name LIKE concat('%',?,'%') or user.nickname LIKE concat('%',?,'%'))";
			params.add(name);
			params.add(name);
		}
		if (F.validate.isNotEmpty(user_name)){
			sql += " and user.user_name = ? ";
			params.add(user_name);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and optinfo.status = ? ";
			params.add(status);
		}
		sql += " order by optinfo.create_time desc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取办理点账户
	 * @param seq_uuid
	 * @return
	 */
	public ManagepointOptinfo findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_managepoint_optinfo " +
				"      where seq_uuid = ? " +
				"      limit 1";
		ManagepointOptinfo data = ManagepointOptinfo.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new ManagepointOptinfo();
		}
		return data;
	}

    /**
     * 获取办理点
     * @return
     */
    public ManagepointInfo findManagepoint(){
        if (F.validate.isEmpty(managepointInfo)){
            managepointInfo = ManagepointInfo.dao.findManagepoint(this.getMp_code(), AdminConstants.LanguageType.ZH_SIMP.value);
        }
        return managepointInfo;
    }

	/**
	 * 通过用户ID获取办理点账户
	 * @param user_id
	 * @return
	 */
	public ManagepointOptinfo findMpOptByUserId(String user_id){
		String sql = "select * " +
				"       from phjf_managepoint_optinfo " +
				"      where user_id = ?" +
				"        and status = 1" +
				"      limit 1";
		ManagepointOptinfo data = ManagepointOptinfo.dao.use("DBPublic").findFirst(sql,user_id);
		if (F.validate.isEmpty(data)){
			data = new ManagepointOptinfo();
		}
		return data;
	}
}
