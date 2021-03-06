package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseUserInfo;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheUserInfo;
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
public class UserInfo extends CacheUserInfo<UserInfo> {
	public static final UserInfo dao = new UserInfo().dao();

	/**
	 * 新增用户
	 * @param opt
	 * @return
	 */
	public UserInfo saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
		this.checkModelItem();
		if (F.validate.isNotEmpty(this.getUser_id()+"")&&!(this.getUser_id().equals(0))){
			this.setModify_opt(opt.getSeq_id()+"");
			this.setModify_time(new Date());
			this.update();
		} else {
			this.setSeq_uuid(UUID.randomUUID().toString());
			this.setReg_time(new Date());
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
		this.checkUser_id("用户ID");
		this.checkUser_name("用户头像");
		this.checkMobilephone("用户手机号");
		this.checkTrue_name("用户真实姓名");
		this.checkId_type("用户证件类型");
		this.checkId_num("用户证件号");
		this.checkNickname("用户昵称");
		this.checkGender("用户性别");
		this.checkAddress("用户常住地");
		this.checkReg_time("用户注册时间");
		this.checkFrozen_time("用户账户冻结时间");
	}
	/**
	 * 获取用户列表
	 * @param user_name
	 * @param id_num
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> queryUserInfoByPage( String user_name, String id_num, String status, PageKit page){
		String sql = "select user.seq_uuid," +
				"            user.user_id," +
				"            user.user_name," +
				"            user.mobilephone," +
				"            user.true_name," +
				"            user.id_type," +
				"            user.id_num," +
				"            user.nickname," +
				"            user.reg_time," +
				"            user.frozen_time," +
				"            user.address," +
				"            user.status," +
				"            user.create_time," +
				"            user.gender," +
				"            status.name status_show," +
				"            isShow.name is_show_name," +
				"            gender.name gender_show," +
				"            idType.name idType_show," +
				"            app.name app_name," +
				"            auth.app_code," +
				"            opt.operate_name creat_opt_name " +
				"       from phjf_user_info user " +
				"            left join phjf_user_appauth auth on user.user_id = auth.user_id" +
				"            left join phjf_app_info app on app.app_code = auth.app_code and app.language = auth.language" +
				"            left join sys_datalibrary status on status.parent_code = 'userAuth_status' and status.code = auth.status" +
				"            left join sys_datalibrary isShow on isShow.parent_code = 'sys_isShow' and isShow.code = user.is_show" +
				"            left join sys_datalibrary gender on gender.parent_code = 'sys_gender' and gender.code = user.gender" +
				"            left join sys_datalibrary idType on idType.parent_code = 'sys_idType' and idType.code = user.id_type" +
				"            left join auth_operator opt on opt.seq_id = user.create_opt" +
				"      where 1=1";
		List params = new ArrayList();
		if (F.validate.isNotEmpty(id_num)){
			sql += " and user.id_num = ? ";
			params.add(id_num);
		}
		if (F.validate.isNotEmpty(user_name)){
			sql += " and user.user_name = ? ";
			params.add(user_name);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and user.status = ? ";
			params.add(status);
		}
		sql += " order by user.create_time desc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取用户
	 * @param user_id
	 * @return
	 */
	public UserInfo findByUserId(String user_id){
		String sql = "select * " +
				"       from phjf_user_info " +
				"      where user_id = ? " +
				"        and status = 1" +
				"      limit 1";
		UserInfo data = UserInfo.dao.use("DBPublic").findFirst(sql,user_id);
		if(F.validate.isEmpty(data)){
			data = new UserInfo();
		}
		return data;
	}

	/**
	 * 通过ID获取用户
	 * @param seq_uuid
	 * @return
	 */
	public UserInfo findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_user_info " +
				"      where seq_uuid = ? " +
				"      limit 1";
		UserInfo data = UserInfo.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new UserInfo();
		}
		return data;
	}
	/**
	 * 通过电话或身份证号码查找用户
	 * @param mobilephone
	 * @param id_num
	 * @return
	 */
	public UserInfo findByPhoneOrId(String mobilephone,String id_num){
		String sql = "select * " +
				"       from phjf_user_info " +
				"      where status = 1 " +
				"        and (mobilephone = ? or id_num = ?)" +
				"      limit 1";
		UserInfo data = UserInfo.dao.use("DBPublic").findFirst(sql,mobilephone,id_num);
		if(F.validate.isEmpty(data)){
			data = new UserInfo();
		}
		return data;
	}
}
