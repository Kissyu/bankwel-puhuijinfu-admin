package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.EnvUtil;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.ManagepointInfoVO;
import com.bankwel.phjf_admin.webapi.vo.WebNavVO;
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
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/11/9.
 */
public class WebNavService {
    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNavSearchList() throws MsgBusinessException{
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
     * 导航管理 - 导航列表
     * @param language
     * @param name
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNavByPage(String language, String name,String status,PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = WebNav.dao.queryNavByPage(language, name,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 导航管理 - 添加导航
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNav addManagepoint(AuthOperator opt, WebNavVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getParent_nav_code())){
            throw new MsgBusinessException("上级导航不能为空");
        }
        if (F.validate.isEmpty(vo.getNav_name())){
            throw new MsgBusinessException("名称不能为空");
        }
        WebNav wn = WebNav.dao.findByNavName(vo.getNav_name());
        if(F.validate.isNotEmpty(wn)){
            throw new MsgBusinessException("该导航已经存在");
        }
        if(F.validate.isEmpty(vo.getNav_id())){
            WebNav model = new WebNav();
            F.transKit.copyProperties(model, vo);
            WebNav webNav1 = model.saveOrUpdate(opt);
            return webNav1;
        }else {
            return this.modifyNav(opt,vo);
        }
    }

    /**
     * 导航管理 - 保存路径
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNav savePath(AuthOperator opt,String p_code,String language,String nav_id,String code) throws MsgBusinessException {
        String path = "";
        if(p_code.equals("0")) {
            path="0";
        }else {
            WebNav nav = WebNav.dao.findParentNavpath(language,p_code);
            path = nav.getNav_path();
        }
        WebNav webNav = WebNav.dao.findNavByCode(code,language);
        webNav.setNav_path(path+"/"+webNav.getNav_code());
        webNav.saveOrUpdate(opt);
        return webNav;
    }

    /**
     * 导航管理 - 修改导航
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNav modifyNav(AuthOperator opt, WebNavVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getParent_nav_code())){
            throw new MsgBusinessException("上级导航不能为空");
        }
        if (F.validate.isEmpty(vo.getNav_name())){
            throw new MsgBusinessException("名称不能为空");
        }
        Boolean bool = WebNav.dao.isHaveNavName(vo.getSeq_uuid(),vo.getNav_name());
        if(bool){
            throw new MsgBusinessException("该名称已存在");
        }
        WebNav model = WebNav.dao.findByNavId(vo.getSeq_uuid());
        model.setNav_name(vo.getNav_name());
        model.setUrl(vo.getUrl());
        model.setOrders(vo.getOrders());
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * 导航管理 - 查找导航
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findNavById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("导航id不能为空");
        }
        WebNav model = WebNav.dao.findByNavId(seq_uuid);
        Map data = outputNavInfo(model);
        return data;
    }

    /**
     * 导航管理 - 输出导航信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputNavInfo(WebNav model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"nav_id",model.getNav_id()
                ,"nav_code",model.getNav_code()
                ,"parent_nav_code",model.getParent_nav_code()
                ,"language",model.getLanguage()
                ,"url",model.getUrl()
                ,"language_show",model.findLanguage_show()
                ,"nav_name",model.getNav_name()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"orders",model.getOrders()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 导航管理 - 获取维语导航
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyNavInfo(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("导航id不能为空");
        }
        WebNav webNav = WebNav.dao.findByNavId(seq_uuid);
        WebNav ueyNav = WebNav.dao.findNavByCode(webNav.getNav_code(),language);
        if(F.validate.isEmpty(ueyNav)){
            WebNav webNav1 = new WebNav();
            webNav1.setSeq_uuid(null);
            webNav1.setNav_id(null);
            webNav1.setParent_nav_code(webNav.getParent_nav_code());
            webNav1.setUrl(webNav.getUrl());
            webNav1.setLanguage(language);
            return outputNavInfo(webNav1);
        }
        return outputNavInfo(webNav);
    }
    /**
     * 导航管理 - 删除导航
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteNav(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("导航id不能为空");
        }
        WebNav webNav = WebNav.dao.findByNavId(seq_uuid);
        //根据path获取删除的数据
        List<WebNav> navList = WebNav.dao.findByNavPath(webNav.getNav_path());
        if (F.validate.isNotEmpty(navList)) {
            for (WebNav nav : navList){
                nav.setStatus("4");
                nav.setIs_show("N");
                nav.saveOrUpdate(opt);
            }
        }
    }

    /**
     * 导航管理 - 通过nav_code获取关于我们子导航
     * @param nav_code
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    public Map findAboutUsNav(String nav_code,String language) throws MsgBusinessException{
        WebNav webNav = WebNav.dao.findNavByCode(nav_code,language);
        Map map = F.transKit.asMap("seq_uuid", webNav.getSeq_uuid()
                ,"nav_name",webNav.getNav_name());
        return map;
    }
    public void generateNavs(String language) {
        String lan = "";
        if(language.equals(AdminConstants.LanguageType.ZH_SIMP.value)) {
            lan = "zh";
        }else {
            lan = "ug";
        }
        Map result = F.transKit.asMap();
        //获取顶部导航
        List<Map> topNavList = new ArrayList<Map>();
        List<WebNav> _topNavList = WebNav.dao.queryTopNavs(language,"0");
        if(F.validate.isNotEmpty(_topNavList)){
            for (WebNav topNav: _topNavList) {
                List<Map> subTopNavs = new ArrayList<Map>();
                List<WebNav> webNav1 = WebNav.dao.queryTopNavs(language,topNav.getNav_code());
                for (WebNav subNav:webNav1) {
                    subTopNavs.add(F.transKit.asMap("nav_code",subNav.getNav_code(),"nav_name",subNav.getNav_name(),"url",subNav.getUrl()));
                }
                topNavList.add(F.transKit.asMap("nav_name",topNav.getNav_name()
                        ,"url",topNav.getUrl()
                        ,"nav_code",topNav.getNav_code()
                        ,"subNavList",subTopNavs));
            }
        }
        result.put("topNavs",topNavList);
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
        //注册全局共享变量
//            gt.setSharedVars(partners);
        Template t = gt.getTemplate(lan+"/index/nav.html");
        t.binding("data", result);
        String str = t.render();
        System.out.println(str);
        try {
            String dirPath = PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
            EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
            FileUtils.writeStringToFile(new File(dirPath+"/index/nav.html"), str,"UTF-8");
            EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
