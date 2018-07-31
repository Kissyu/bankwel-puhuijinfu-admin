package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.EnvUtil;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.common.model.core.WebLinks;
import com.bankwel.phjf_admin.common.model.core.WebPartner;
import com.bankwel.phjf_admin.webapi.vo.WebLinksVO;
import com.bankwel.phjf_admin.webapi.vo.WebPartnerVO;
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
 * Created by admin on 2017/11/9.
 */
public class WebLinksService {
    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryLinksSearchList() throws MsgBusinessException{
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
     * 友情链接管理 - 友情链接列表
     * @param language
     * @param name
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryLinksByPage(String language, String name,String status,PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = WebLinks.dao.queryLinksByPage(language, name, status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 友情链接管理 - 添加友情链接
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebLinks addLInks(AuthOperator opt, WebLinksVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getLinks_name())){
            throw new MsgBusinessException("名称不能为空");
        }
        WebLinks wl = WebLinks.dao.findByLinksName(vo.getLinks_name());
        if(F.validate.isNotEmpty(wl)){
            throw new MsgBusinessException("该数据已经存在");
        }
        if(F.validate.isEmpty(vo.getLinks_id())){
            WebLinks model = new WebLinks();
            F.transKit.copyProperties(model, vo);
            WebLinks webLinks = model.saveOrUpdate(opt);
            return webLinks;
        }else {
            return this.modifyLinks(opt,vo);
        }
    }
    /**
     * 友情链接管理 - 修改友情链接
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebLinks modifyLinks(AuthOperator opt, WebLinksVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getLinks_name())){
            throw new MsgBusinessException("名称不能为空");
        }
        Boolean bool = WebLinks.dao.isHaveLinksName(vo.getSeq_uuid(),vo.getLinks_name());
        if(bool){
            throw new MsgBusinessException("该名称已存在");
        }
        WebLinks model = WebLinks.dao.findByLinksId(vo.getSeq_uuid());
        model.setLinks_name(vo.getLinks_name());
        model.setUrl(vo.getUrl());
        model.setOrders(vo.getOrders());
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * 友情链接管理 - 查找友情链接
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findLInksById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("友情链接id不能为空");
        }
        WebLinks model = WebLinks.dao.findByLinksId(seq_uuid);
        Map data = outputLInksInfo(model);
        return data;
    }

    /**
     * 友情链接管理 - 输出友情链接信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputLInksInfo(WebLinks model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"links_id",model.getLinks_id()
                ,"links_code",model.getLinks_code()
                ,"language",model.getLanguage()
                ,"url",model.getUrl()
                ,"language_show",model.findLanguage_show()
                ,"links_name",model.getLinks_name()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"orders",model.getOrders()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 友情链接管理 - 获取维语友情链接
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyLinksInfo(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("合作伙伴编码不能为空");
        }
        WebLinks webLinks = WebLinks.dao.findByLinksId(seq_uuid);
        WebLinks ueyLinks = WebLinks.dao.findLinksByCode(webLinks.getLinks_code(),language);
        if(F.validate.isEmpty(ueyLinks)){
            WebLinks webLinks1 = new WebLinks();
            webLinks1.setSeq_uuid(null);
            webLinks1.setLinks_id(null);
            webLinks1.setLanguage(language);
            webLinks1.setUrl(webLinks.getUrl());
            return outputLInksInfo(webLinks1);
        }
        return outputLInksInfo(ueyLinks);
    }
    /**
     * 友情链接管理 - 删除友情链接
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteLinks(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("友情链接id不能为空");
        }
        WebLinks webLinks = WebLinks.dao.findByLinksId(seq_uuid);
        List<WebLinks> links = WebLinks.dao.queryByCode(webLinks.getLinks_code());
        for(WebLinks link : links) {
            link.setStatus("4");
            link.setIs_show("N");
            link.saveOrUpdate(opt);
        }
    }
    public void generateLinks(String language) {
        String lan = "";
        if(language.equals(AdminConstants.LanguageType.ZH_SIMP.value)) {
            lan = "zh";
        }else {
            lan = "ug";
        }
        Map result = F.transKit.asMap();
        //获取合作伙伴
        List<Map> linkList = new ArrayList<Map>();
        List<WebLinks> webLinks = WebLinks.dao.queryLinkList(language);
        for(WebLinks link:webLinks) {
            linkList.add(F.transKit.asMap("link_name",link.getLinks_name(),"url",link.getUrl()));
        }
        result.put("links",linkList);
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
        //注册全局共享变量
//            gt.setSharedVars(partners);
        Template t = gt.getTemplate(lan+"/index/indexLinks.html");
        t.binding("data", result);
        String str = t.render();
        System.out.println(str);
        try {
            String dirPath =PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
            EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
            FileUtils.writeStringToFile(new File(dirPath+"/index/indexLinks.html"), str,"UTF-8");
            EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
