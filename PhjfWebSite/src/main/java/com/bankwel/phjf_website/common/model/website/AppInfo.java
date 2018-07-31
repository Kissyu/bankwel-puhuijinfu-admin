package com.bankwel.phjf_website.common.model.website;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_website.common.model.website.base.BaseAppInfo;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class AppInfo extends BaseAppInfo<AppInfo> {
	public static final AppInfo dao = new AppInfo().dao();
	public AppInfo findByAppCode(String language,String app_code){
		String sql = "select * " +
				"       from phjf_app_info " +
				"      where language = ? " +
				"        and app_code = ? " +
				"        and status = 1" +
				"        and is_show = 'Y' ";
		return AppInfo.dao.use("DBPublic").findFirst(sql,language,app_code);
	}

	public void setShareCount() {
		this.setShare_count(this.getShare_count()+1);
		saveOrUpdate();
	}
	public void setDownloadAppCount() {
		this.setDownload_count(this.getDownload_count()+1);
		saveOrUpdate();
	}
	public void saveOrUpdate() {
		if(this.isEmpty()){
			this.setSeq_uuid(F.randomKit.randomUUID());
			this.save();
		}else{
			this.update();
		}
	}
	public boolean isEmpty(){
		if(F.validate.isEmpty(this.getSeq_uuid())){
			return true;
		}
		return false;
	}
}
