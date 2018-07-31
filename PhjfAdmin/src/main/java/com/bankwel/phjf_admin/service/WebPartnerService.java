package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.EnvUtil;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.WebNavVO;
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
public class WebPartnerService {
    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryPartnerSearchList() throws MsgBusinessException{
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
     * 合作伙伴管理 - 合作伙伴列表
     * @param language
     * @param name
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryPartnerByPage(String language, String name,String status,PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = WebPartner.dao.queryPartnerByPage(language, name,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 导航管理 - 添加导航
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebPartner addPartner(AuthOperator opt, WebPartnerVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getPartner_name())){
            throw new MsgBusinessException("名称不能为空");
        }
        WebPartner wn = WebPartner.dao.findByPartnerName(vo.getPartner_name());
        if(F.validate.isNotEmpty(wn)){
            throw new MsgBusinessException("该导航已经存在");
        }
        if(F.validate.isEmpty(vo.getPartner_id())){
            WebPartner model = new WebPartner();
            F.transKit.copyProperties(model, vo);
            WebPartner webPartner1 = model.saveOrUpdate(opt);
            return webPartner1;
        }else {
            return this.modifyPartner(opt,vo);
        }
    }
    /**
     * 导航管理 - 修改导航
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebPartner modifyPartner(AuthOperator opt, WebPartnerVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getPartner_name())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(vo.getPicture())){
            throw new MsgBusinessException("图片不能为空");
        }
        Boolean bool = WebPartner.dao.isHavePartnerName(vo.getSeq_uuid(),vo.getPartner_name());
        if(bool){
            throw new MsgBusinessException("该名称已存在");
        }
        WebPartner model = WebPartner.dao.findByPartnerId(vo.getSeq_uuid());
        model.setPartner_name(vo.getPartner_name());
        model.setPicture(vo.getPicture());
        model.setOrders(vo.getOrders());
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * 合作伙伴管理 - 查找合作伙伴
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findPartnerById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("合作伙伴id不能为空");
        }
        WebPartner model = WebPartner.dao.findByPartnerId(seq_uuid);
        Map data = outputPartnerInfo(model);
        return data;
    }
    /**
     * 导航管理 - 输出导航信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputPartnerInfo(WebPartner model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"partner_id",model.getPartner_id()
                ,"partner_code",model.getPartner_code()
                ,"language",model.getLanguage()
                ,"picture",model.getPicture()
                ,"language_show",model.findLanguage_show()
                ,"partner_name",model.getPartner_name()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"orders",model.getOrders()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 合作伙伴管理 - 获取维语合作伙伴
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyPartnerInfo(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("合作伙伴编码不能为空");
        }
        WebPartner webPartner = WebPartner.dao.findByPartnerId(seq_uuid);
        WebPartner ueyPartner = WebPartner.dao.findPartnerByCode(webPartner.getPartner_code(),language);
        if(F.validate.isEmpty(ueyPartner)){
            WebPartner webPartner1 = new WebPartner();
            webPartner1.setSeq_uuid(null);
            webPartner1.setPartner_id(null);
            webPartner1.setPicture(null);
            webPartner1.setLanguage(language);
            webPartner1.setPicture(webPartner.getPicture());
            return outputPartnerInfo(webPartner1);
        }
        return outputPartnerInfo(ueyPartner);
    }
    /**
     * 合作伙伴管理 - 删除合作伙伴
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deletePartner(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("合作伙伴id不能为空");
        }
        WebPartner webPartner = WebPartner.dao.findByPartnerId(seq_uuid);
        List<WebPartner> partnerList = WebPartner.dao.queryByCode(webPartner.getPartner_code());
        for(WebPartner p : partnerList) {
            p.setStatus("4");
            p.setIs_show("N");
            p.saveOrUpdate(opt);
        }
    }

    public void generatePartners(String language) throws MsgBusinessException {
        String lan = "";
        if(language.equals(AdminConstants.LanguageType.ZH_SIMP.value)) {
            lan = "zh";
        }else {
            lan = "ug";
        }
        Map data = F.transKit.asMap();
        //获取合作伙伴
        List<Map> partnerList = new ArrayList<Map>();
        List<WebPartner> webPartners = WebPartner.dao.queryPartnerList(language);
        for(WebPartner webPartner:webPartners) {
            partnerList.add(F.transKit.asMap("partner_name",webPartner.getPartner_name(),"picture",webPartner.getPicture()));
        }
        data.put("partners",partnerList);
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
        Template t = gt.getTemplate(lan+"/index/indexPartners.html");
        t.binding("data", data);
        String str = t.render();
        System.out.println(str);
        try {
//            String dirPath = PathKit.getWebRootPath() + PropKit.use("config.properties").get("cms.generateStaticFile_path");
            String dirPath = PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
            EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
            FileUtils.writeStringToFile(new File(dirPath+"/index/indexPartners.html"), str,"UTF-8");
            EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
