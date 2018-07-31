package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.base.BaseSmsLog;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheSmsLog;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SmsLog extends CacheSmsLog<SmsLog> {
	public static final SmsLog dao = new SmsLog().dao();

	/**
	 * 获取短信发送历史列表
	 * @param type
	 * @param mobilephone
	 * @param result_type
	 * @param status
	 * @param page
	 * @return
	 */
	public Pair<List<Record>,PageKit<Record>> querySmsLogByPage(String type, String mobilephone, String result_type,String status, PageKit page){
		String sql = "select log.seq_uuid," +
				"            log.log_id," +
				"            log.type," +
				"            log.mobilephone," +
				"            log.content," +
				"            log.result_type," +
				"            log.status," +
				"            log.create_time," +
				"            status.name status_show," +
				"            smsType.name smsType_show," +
				"            resultType.name resultType_show" +
				"       from phjf_sms_log log " +
				"            left join sys_datalibrary smsType on smsType.parent_code = 'sys_smsCodeType' and smsType.code = log.type" +
				"            left join sys_datalibrary status on status.parent_code = 'sys_status' and status.code = log.status" +
				"            left join sys_datalibrary resultType on resultType.parent_code = 'sys_smsResultType' and resultType.code = log.result_type" +
				"      where log.type = 'gfb'";
		List params = new ArrayList();
//		if (F.validate.isNotEmpty(type)){
//			sql += " and log.type = ? ";
//			params.add(type);
//		}
		if (F.validate.isNotEmpty(mobilephone)){
			sql += " and log.mobilephone = ? ";
			params.add(mobilephone);
		}
		if (F.validate.isNotEmpty(result_type)){
			sql += " and log.result_type = ? ";
			params.add(result_type);
		}
		if (F.validate.isNotEmpty(status)){
			sql += " and log.status = ? ";
			params.add(status);
		}
		sql += " order by log.create_time desc";
		return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
	}

	/**
	 * 通过ID获取用户
	 * @param seq_uuid
	 * @return
	 */
	public SmsLog findById(String seq_uuid){
		String sql = "select * " +
				"       from phjf_sms_log " +
				"      where seq_uuid = ? " +
				"      limit 1";
		SmsLog data = SmsLog.dao.use("DBPublic").findFirst(sql,seq_uuid);
		if(F.validate.isEmpty(data)){
			data = new SmsLog();
		}
		return data;
	}

	public SmsLog addSmslog(String type, String mobilephone, String content) throws MsgBusinessException {
		SmsLog smsLog = new SmsLog();
		smsLog.setType(type);
		smsLog.setMobilephone(mobilephone);
		smsLog.setContent(content);
		smsLog.saveOrUpdate();
		return smsLog;
	}

	public SmsLog saveOrUpdate() {
		if(F.validate.isEmpty(this.getSeq_uuid())) {
			this.setSeq_uuid(F.randomKit.randomUUID());
			this.save();
		} else {
			this.update();
		}
		return this;
	}
}
