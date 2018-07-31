package com.bankwel.phjf_website.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.phjf_website.common.model.website.AppInfo;


/**
 * Created by admin on 2017/12/2.
 */
public class AppInfoService {
    /**
     * 分享
     * @param language
     * @param app_code
     * @throws MsgBusinessException
     */
    public void setShare(String language,String app_code)throws MsgBusinessException {
        //获取APPInfo信息
        AppInfo appInfo = AppInfo.dao.findByAppCode(language,app_code);
        if(F.validate.isNotEmpty(appInfo)) {
            appInfo.setShareCount();
        }
    }

    /**
     * 下载
     * @param language
     * @param app_code
     * @throws MsgBusinessException
     */
    public void setDownloadAppCount(String language,String app_code)throws MsgBusinessException {
        //获取APPInfo信息
        AppInfo appInfo = AppInfo.dao.findByAppCode(language,app_code);
        if(F.validate.isNotEmpty(appInfo)) {
            appInfo.setDownloadAppCount();
        }

    }
}
