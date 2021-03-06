package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.base.BaseUserWithdrawAppointment;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheUserWithdrawAppointment;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class UserWithdrawAppointment extends CacheUserWithdrawAppointment<UserWithdrawAppointment> {
	public static final UserWithdrawAppointment dao = new UserWithdrawAppointment().dao();

	private ManagepointInfo managepointInfo;

	/**
	 * 获取预约取款列表
	 * @param mp_name
	 * @param name
	 * @param user_name
	 * @param mobilephone
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryUserWithdrawAmByPage(String mp_name, String name, String user_name,String mobilephone, String status, PageKit page){
		String sql = "select uwp.seq_uuid," +
				"            uwp.uwa_id," +
				"            uwp.mp_code," +
				"            uwp.user_id," +
				"            uwp.amount," +
				"            uwp.am_date," +
				"            uwp.accept_time," +
				"            uwp.refuse_time," +
				"            uwp.cancel_time," +
				"            uwp.remark," +
				"            uwp.create_time," +
				"            user.true_name," +
				"            user.mobilephone," +
				"            user.nickname," +
				"            user.user_name user_name," +
				"            status.name status_show," +
				"            isShow.name is_show_name," +
				"            mp.name mp_name," +
				"            mpopt.user_name mp_opt" +
				"       from phjf_user_withdraw_appointment uwp" +
				"            left join phjf_user_info user on uwp.user_id = user.user_id" +
				"            left join phjf_user_info mpopt on uwp.mp_opt = mpopt.user_id" +
				"            left join sys_datalibrary status on status.parent_code = 'sys_withdrawStatus' and status.code = uwp.status" +
				"            left join sys_datalibrary isShow on isShow.parent_code = 'sys_isShow' and isShow.code = uwp.is_show" +
				"            left join phjf_managepoint_info mp on uwp.mp_code = mp.mp_code and mp.language = 'ZH_SIMP'" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(mp_name)){
			sql += " and (mp.name LIKE concat('%',?,'%') or uwp.mp_code = ?) ";
			params.add(mp_name);
			params.add(mp_name);
		}
		if (F.validate.isNotEmpty(name)){
			sql += " and (user.true_name LIKE concat('%',?,'%') or user.nickname LIKE concat('%',?,'%'))";
			params.add(name);
			params.add(name);
		}
		if (F.validate.isNotEmpty(user_name)){
			sql += " and user.user_name LIKE concat('%',?,'%') ";
			params.add(user_name);
		}
		if (F.validate.isNotEmpty(mobilephone)){
			sql += " and user.mobilephone = ? ";
			params.add(mobilephone);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and uwp.status = ? ";
			params.add(status);
		}
		sql += " order by uwp.create_time desc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取预约取款
	 * @param seq_uuid
	 * @return
	 */
	public UserWithdrawAppointment findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_user_withdraw_appointment " +
				"      where seq_uuid = ? " +
				"      limit 1";
		UserWithdrawAppointment data = UserWithdrawAppointment.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new UserWithdrawAppointment();
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
	 * 获取用户
	 * @return
	 */
	public UserInfo findUser(String user_id){
		UserInfo user = UserInfo.dao.findByUserId(user_id);
		if (F.validate.isEmpty(user)){
			user = new UserInfo();
		}
		return user;
	}
}
