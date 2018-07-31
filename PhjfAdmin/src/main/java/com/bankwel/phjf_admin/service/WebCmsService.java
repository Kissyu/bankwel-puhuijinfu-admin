package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.EnvUtil;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.WebAboutUsVO;
import com.bankwel.phjf_admin.webapi.vo.WebAppDownloadInfoVO;
import com.bankwel.phjf_admin.webapi.vo.WebBannerVO;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/11.
 */
public class WebCmsService {
    /**
     * 广告位管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBannerSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_language");
        List<Map> languageList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLanguageList){
            languageList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList,"languageList",languageList);
    }

    /**
     * 广告位管理 - 获取广告列表
     * @param contents
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBannerByPage(String contents, String language, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = WebBanner.dao.queryBannerByPage(contents,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 广告位管理 - 添加广告位
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebBanner addBanner(AuthOperator opt, WebBannerVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getBanner_id()+"")) {
            checkBannerModel(vo);
            WebBanner model = new WebBanner();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBanner(opt,vo);
        }
    }

    /**
     * 广告位管理 - 获取维语广告位
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyBanner(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("广告位编号不能为空");
        }
        WebBanner webBanner = WebBanner.dao.findById(seq_uuid);
        WebBanner UeybannerInfo = WebBanner.dao.findWebBanner(webBanner.getBanner_code(),language);
        if(F.validate.isEmpty(UeybannerInfo.getBanner_id())){
            webBanner.setSeq_uuid(null);
            webBanner.setBanner_id(null);
            webBanner.setLanguage(language);
            return outputWebBanner(webBanner);
        }
        return outputWebBanner(UeybannerInfo);
    }

    /**
     * 广告位管理 - 修改广告位
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebBanner modifyBanner(AuthOperator opt, WebBannerVO vo) throws MsgBusinessException {
        checkBannerModel(vo);
        WebBanner model = WebBanner.dao.findById(vo.getSeq_uuid());
        model.setImg_url(vo.getImg_url());
        model.setContents(vo.getContents());
        model.setUrl(vo.getUrl());
        model.setOrders(vo.getOrders());
        model.setIs_show(vo.getIs_show());

        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 广告位管理 - 删除广告位
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteBanner(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("广告位编号不能为空");
        }
        WebBanner bannerInfo = WebBanner.dao.findById(seq_uuid);
        if("4".equals(bannerInfo.getStatus())){
            throw new MsgBusinessException("该广告位已经失效");
        }
        List<WebBanner> bannerList = WebBanner.dao.findByBannerCode(bannerInfo.getBanner_code());
        for (WebBanner banner : bannerList){
            banner.setStatus("4");
            banner.setIs_show("N");
            banner.saveOrUpdate(opt);
        }
    }

    /**
     * 广告位管理 - 查找广告位
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBannerById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("广告位编号不能为空");
        }
        WebBanner model = WebBanner.dao.findById(seq_uuid);
        Map data = outputWebBanner(model);
        return data;
    }

    /**
     * 广告位管理 - 输出广告位管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputWebBanner(WebBanner model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"banner_id",model.getBanner_id()
                ,"banner_code",model.getBanner_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"contents",model.getContents()
                ,"img_url",model.getImg_url()
                ,"url",model.getUrl()
                ,"orders",model.getOrders()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 广告位管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBannerModel(WebBannerVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getContents())){
            throw new MsgBusinessException("文字叙述不能为空");
        }
        if (F.validate.isEmpty(vo.getUrl())){
            throw new MsgBusinessException("链接地址不能为空");
        }
        if(F.validate.isEmpty(vo.getImg_url())){
            throw new MsgBusinessException("图片不能为空");
        }
    }
    /*
    * 广告位管理 - 输出到页面
    * */
    public Map queryBannersInfo(String language)throws MsgBusinessException {
        Map result = F.transKit.asMap();
        List<Map> bannerList = new ArrayList<Map>();
        List<WebBanner> webBanners = WebBanner.dao.queryBannerList(language);
        for(WebBanner banner:webBanners) {
            bannerList.add(F.transKit.asMap("img_url",banner.getImg_url(),"url",banner.getUrl(),"contents",banner.getContents()));
        }
        result.put("banners",bannerList);
        return result;
    }
    /*
    * 广告位管理 - 生成页面
    * */
    public void generateBannerHtml(String language) throws MsgBusinessException{
        Map banners = queryBannersInfo(language);
        //更新网站模板
        String root = PathKit.getWebRootPath() + PropKit.use("config.properties").get("cms.readTempFile_path");
        System.out.println("classPath="+PathKit.getWebRootPath()+"     "+ root);
        FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        String lan = null;
        if(language.equals(AdminConstants.ZH_SIMP)){
            lan = "zh";
        }else {
            lan = "ug";
        }
        //注册全局共享变量
//            gt.setSharedVars(partners);
        Template t = gt.getTemplate(lan+"/index/banner.html");
        t.binding("data", banners);
        String str = t.render();
        System.out.println(str);
        try {
            String dirPath =PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
            String msg =  EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
            System.out.println("msg="+msg);
            FileUtils.writeStringToFile(new File(dirPath + "/index/banner.html"), str,"UTF-8");
            String msgs = EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
            System.out.println("msgs="+msgs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 关于我们管理 - 获取广告列表
     * @param contents
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAboutUsByPage(String contents, String language, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = WebAboutUs.dao.queryAboutUsByPage(contents,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 关于我们管理 - 添加关于我们
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebAboutUs addAboutUs(AuthOperator opt, WebAboutUsVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getAu_id()+"")) {
            Boolean navBool = WebAboutUs.dao.isExistNav(vo.getNav_code(),vo.getLanguage());
            if(navBool){
                throw new MsgBusinessException("该导航已经添加过内容。");
            }
            checkAboutUsModel(vo);
            WebAboutUs model = new WebAboutUs();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyAboutUs(opt,vo);
        }
    }

    /**
     * 关于我们管理 - 获取维语关于我们
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyAboutUs(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("关于我们编号不能为空");
        }
        WebAboutUs webAboutUs = WebAboutUs.dao.findById(seq_uuid);
        WebAboutUs UeybannerInfo = WebAboutUs.dao.findWebAboutUs(webAboutUs.getAu_code(),language);
        if(F.validate.isEmpty(UeybannerInfo.getAu_id())){
            webAboutUs.setSeq_uuid(null);
            webAboutUs.setAu_id(null);
            webAboutUs.setLanguage(language);
            return outputWebAboutUs(webAboutUs);
        }
        return outputWebAboutUs(UeybannerInfo);
    }

    /**
     * 关于我们管理 - 修改关于我们
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebAboutUs modifyAboutUs(AuthOperator opt, WebAboutUsVO vo) throws MsgBusinessException {
        checkAboutUsModel(vo);
        WebAboutUs model = WebAboutUs.dao.findById(vo.getSeq_uuid());
        model.setContent(vo.getContent());
        model.setOrders(vo.getOrders());
        model.setIs_show(vo.getIs_show());

        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 关于我们管理 - 删除关于我们
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteAboutUs(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("关于我们编号不能为空");
        }
        WebAboutUs aboutUs = WebAboutUs.dao.findById(seq_uuid);
        if("4".equals(aboutUs.getStatus())){
            throw new MsgBusinessException("该内容已经失效");
        }
        List<WebAboutUs> aboutUsList = WebAboutUs.dao.findByAboutUsCode(aboutUs.getAu_code());
        for (WebAboutUs au : aboutUsList){
            au.setStatus("4");
            au.setIs_show("N");
            au.saveOrUpdate(opt);
        }
    }

    /**
     * 关于我们管理 - 查找关于我们
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findAboutUsById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("关于我们编号不能为空");
        }
        WebAboutUs model = WebAboutUs.dao.findById(seq_uuid);
        Map data = outputWebAboutUs(model);
        return data;
    }

    /**
     * 关于我们管理 - 输出关于我们管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputWebAboutUs(WebAboutUs model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"au_id",model.getAu_id()
                ,"au_code",model.getAu_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"nav_name",model.getNav_name()
                ,"content",model.getContent()
                ,"nav_code",model.getNav_code()
                ,"orders",model.getOrders()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 关于我们管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkAboutUsModel(WebAboutUsVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getNav_name())){
            throw new MsgBusinessException("所属导航名称不能为空");
        }
        if(F.validate.isEmpty(vo.getNav_code())){
            throw new MsgBusinessException("所属导航不能为空");
        }
    }

    /*
    * 关于我们管理 - 输出到页面
    * */
    public Map queryAboutUsCmsInfo(String seq_uuid, String language)throws MsgBusinessException {
        Map result = F.transKit.asMap();
        List<WebNav> webAboutUsList = WebAboutUs.dao.queryAboutUsCmsList(language);
        result.put("aboutUsNavList",webAboutUsList);
        result.put("curAboutUs",WebAboutUs.dao.findById(seq_uuid));
        return result;
    }
    /*
    * 关于我们管理 - 生成页面
    * */
    public void generateAboutHtml(String seq_uuid, String language) throws MsgBusinessException{
        Map aboutUsCms = queryAboutUsCmsInfo(seq_uuid,language);
        //更新网站模板
        String root = PathKit.getWebRootPath() + PropKit.use("config.properties").get("cms.readTempFile_path");
        System.out.println("classPath="+PathKit.getWebRootPath()+"     "+ root);
        FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        String lan = null;
        if(language.equals(AdminConstants.ZH_SIMP)){
            lan = "zh";
        }else {
            lan = "ug";
        }
        //注册全局共享变量
//            gt.setSharedVars(partners);
        Template t = gt.getTemplate(lan+"/about/aboutUs.html");
        t.binding("data", aboutUsCms);
        String str = t.render();
        System.out.println(str);
        Template navT = gt.getTemplate(lan+"/about/aboutNav.html");
        navT.binding("data", aboutUsCms);
        String navTstr = navT.render();
        try {
            String dirPath = PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
            String[] urlArr = WebAboutUs.dao.findWebAboutUsInfo(seq_uuid).getUrl().split("/");
            String aboutUsName = urlArr[urlArr.length - 1].split(".html")[0];
            EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
            FileUtils.writeStringToFile(new File(dirPath+"/about/"+aboutUsName+".html"), str,"UTF-8");
            EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");

            EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
            FileUtils.writeStringToFile(new File(dirPath+"/about/aboutNav.html"), navTstr,"UTF-8");
            EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * APP下载信息管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAppSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysDeviceTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_deviceType");
        List<Map> deviceTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysDeviceTypeList){
            deviceTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysAppTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_appType");
        List<Map> appTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysAppTypeList){
            appTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_language");
        List<Map> languageList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLanguageList){
            languageList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList,"languageList",languageList,"deviceTypeList",deviceTypeList,"appTypeList",appTypeList);
    }

    /**
     * APP下载信息管理 - 获取广告列表
     * @param user_type
     * @param app_type
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryAppByPage(String user_type,String app_type, String language, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = WebAppDownloadInfo.dao.queryAppByPage(user_type,app_type,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * APP下载信息管理 - 添加APP下载信息
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebAppDownloadInfo addApp(AuthOperator opt, WebAppDownloadInfoVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getAdi_id()+"")) {
            checkAppModel(vo);
            WebAppDownloadInfo model = new WebAppDownloadInfo();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyApp(opt,vo);
        }
    }

    /**
     * APP下载信息管理 - 获取维语APP下载信息
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyApp(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("APP下载信息编号不能为空");
        }
        WebAppDownloadInfo webApp = WebAppDownloadInfo.dao.findById(seq_uuid);
        WebAppDownloadInfo UeyappInfo = WebAppDownloadInfo.dao.findWebApp(webApp.getAdi_code(),language);
        if(F.validate.isEmpty(UeyappInfo.getAdi_id())){
            webApp.setSeq_uuid(null);
            webApp.setAdi_id(null);
            webApp.setLanguage(language);
            return outputWebApp(webApp);
        }
        return outputWebApp(UeyappInfo);
    }

    /**
     * APP下载信息管理 - 修改APP下载信息
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebAppDownloadInfo modifyApp(AuthOperator opt, WebAppDownloadInfoVO vo) throws MsgBusinessException {
        checkAppModel(vo);
        WebAppDownloadInfo model = WebAppDownloadInfo.dao.findById(vo.getSeq_uuid());
        model.setQr_code_url(vo.getQr_code_url());
        model.setDescription(vo.getDescription());
        model.setOrders(vo.getOrders());
        model.setIs_show(vo.getIs_show());

        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * APP下载信息管理 - 删除APP下载信息
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteApp(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("APP下载信息编号不能为空");
        }
        WebAppDownloadInfo webAppDownloadInfo = WebAppDownloadInfo.dao.findById(seq_uuid);
        List<WebAppDownloadInfo> appList = WebAppDownloadInfo.dao.findByAppCode(webAppDownloadInfo.getAdi_code());
        for (WebAppDownloadInfo adi : appList){
            adi.setStatus("4");
            adi.setIs_show("N");
            adi.saveOrUpdate(opt);
        }
    }

    /**
     * APP下载信息管理 - 查找APP下载信息
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findAppById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("APP下载信息编号不能为空");
        }
        WebAppDownloadInfo model = WebAppDownloadInfo.dao.findById(seq_uuid);
        Map data = outputWebApp(model);
        return data;
    }

    /**
     * APP下载信息管理 - 输出APP下载信息管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputWebApp(WebAppDownloadInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"adi_id",model.getAdi_id()
                ,"adi_code",model.getAdi_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"user_type",model.getUser_type()
                ,"app_type",model.getApp_type()
                ,"qr_code_url",model.getQr_code_url()
                ,"description",model.getDescription()
                ,"orders",model.getOrders()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * APP下载信息管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkAppModel(WebAppDownloadInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getUser_type())){
            throw new MsgBusinessException("设备类型不能为空");
        }
        if (F.validate.isEmpty(vo.getApp_type())){
            throw new MsgBusinessException("APP类型不能为空");
        }
        if(F.validate.isEmpty(vo.getQr_code_url())){
            throw new MsgBusinessException("二维码图片不能为空");
        }
    }
    /*
    * APP下载信息管理 - 输出到页面
    * */
    public Map queryAppsInfo(String language)throws MsgBusinessException {
        Map result = F.transKit.asMap();
        List<Map> appList = new ArrayList<Map>();
        List<SysDatalibrary> deviceTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_deviceType");
        List<SysDatalibrary> appTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_appType");
        for(SysDatalibrary deviceType : deviceTypeList){
            List<Map> appCodeList = new ArrayList<Map>();
            for (SysDatalibrary appType : appTypeList){
                WebAppDownloadInfo app = WebAppDownloadInfo.dao.queryAppList(language,deviceType.getCode(),appType.getCode());
                String qrCodeUrl = "";
                if(F.validate.isNotEmpty(app)){
                    qrCodeUrl = app.getQr_code_url();
                }
                appCodeList.add(F.transKit.asMap("appType",appType.getName(),"qrCodeUrl",qrCodeUrl));
            }
            appList.add(F.transKit.asMap("deviceType", deviceType.getName(),"codeList",appCodeList));
        }
        result.put("apps",appList);
        return result;
    }
    /*
    * APP下载信息管理 - 生成页面
    * */
    public void generateAppHtml(String language) throws MsgBusinessException{
        Map apps = queryAppsInfo(language);
        //更新网站模板
        String root = PathKit.getWebRootPath() + PropKit.use("config.properties").get("cms.readTempFile_path");
        System.out.println("classPath="+PathKit.getWebRootPath()+"     "+ root);
        FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        String lan = null;
        if(language.equals(AdminConstants.ZH_SIMP)){
            lan = "zh";
        }else {
            lan = "ug";
        }
        //注册全局共享变量
//            gt.setSharedVars(partners);
        Template t = gt.getTemplate(lan+"/index/qrCode.html");
        t.binding("data", apps);
        String str = t.render();
        System.out.println(str);
        try {
            String dirPath = PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
            EnvUtil.exec(dirPath + "/" + lan + "/sh/svnUpdate.sh");
            FileUtils.writeStringToFile(new File(dirPath+"/index/qrCode.html"), str,"UTF-8");
            EnvUtil.exec(dirPath + "/" + lan + "/sh/svnAddCommit.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
