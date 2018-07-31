package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.EnvUtil;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.NewsArticleVO;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.bankwel.phjf_admin.webapi.vo.WebNewsInfoVO;
import com.bankwel.phjf_admin.webapi.vo.WebNewsPlateVO;
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
 * Created by Administrator on 2017/11/15.
 */
public class WebNewsService {
    /**
     * 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNewsPlateSearchList() throws MsgBusinessException{
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
     * 新闻版块管理 - 获取新闻版块列表
     * @param name
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNewsPlateByPage(String name, String language, String status, PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = WebNewsPlate.dao.queryNewsPlateByPage(name,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 新闻版块管理 - 获取新闻版块类型及位置列表
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    public Map queryTypeAndLocationList(String language) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Map result = F.transKit.asMap();
        List<SysDatalibrary> typeList = SysDatalibrary.dao.querySysDatalibrarys("phjf",language,"web_newsModule");
        List<Map> _typeList = new ArrayList<Map>();
        for (SysDatalibrary lib1 : typeList){
            _typeList.add(F.transKit.asMap("code",lib1.getCode(),"name",lib1.getName()));
        }
        result.put("typeList",_typeList);
        List<SysDatalibrary> locationList = SysDatalibrary.dao.querySysDatalibrarys("phjf",language,"web_newsLocation");
        List<Map> _locationList = new ArrayList<Map>();
        for (SysDatalibrary lib2 : locationList){
            _locationList.add(F.transKit.asMap("code",lib2.getCode(),"name",lib2.getName()));
        }
        result.put("locationList",_locationList);
        List<WebNav> navList = WebNav.dao.queryAll(language);
        List<Map> _navList = new ArrayList<Map>();
        for (WebNav nav : navList){
            _navList.add(F.transKit.asMap("nav_code",nav.getNav_code(),"nav_name",nav.getNav_name()));
        }
        result.put("navList",_locationList);
        return result;
    }

    /**
     * 新闻版块管理 - 添加新闻版块
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNewsPlate addNewsPlate(AuthOperator opt, WebNewsPlateVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getNp_id()+"")) {
            checkNewsPlateModel(vo);
            WebNewsPlate newsPlate = WebNewsPlate.dao.findByPlateName(vo.getNp_name());
            if(F.validate.isNotEmpty(newsPlate.getNp_id())){
                throw new MsgBusinessException("该新闻版块名称已存在");
            }
            WebNewsPlate model = new WebNewsPlate();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyNewsPlate(opt,vo);
        }
    }

    /**
     * 新闻版块管理 - 修改新闻版块
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNewsPlate modifyNewsPlate(AuthOperator opt, WebNewsPlateVO vo) throws MsgBusinessException {
        checkNewsPlateModel(vo);
        Boolean bool = WebNewsPlate.dao.isHavePlate(vo.getSeq_uuid(),vo.getNp_name());
        if(bool){
            throw new MsgBusinessException("该新闻版块名称已存在");
        }
        WebNewsPlate model = WebNewsPlate.dao.findById(vo.getSeq_uuid());
        model.setNp_name(vo.getNp_name());
        model.setType(vo.getType());
        model.setLocation(vo.getLocation());
        model.setNav_code(vo.getNav_code());
        model.setOrder_num(vo.getOrder_num());
        model.setIs_show(vo.getIs_show());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 新闻版块管理 - 删除新闻版块
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteNewsPlate(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        WebNewsPlate newsPlate = WebNewsPlate.dao.findById(seq_uuid);
        List<WebNewsPlate> newsPlateList = WebNewsPlate.dao.findByPlateCode(newsPlate.getNp_code());
        for (WebNewsPlate plate : newsPlateList){
            plate.setStatus("4");
            plate.setIs_show("N");
            plate.saveOrUpdate(opt);
        }
        List<WebNewsInfo> newsArticleList = WebNewsInfo.dao.findByPlateCode(newsPlate.getNp_code());
        for (WebNewsInfo article : newsArticleList){
            article.setStatus("3");
            article.saveOrUpdate(opt);
        }
    }
    /**
     * 新闻版块管理 - 获取维语银行新闻版块
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyNewsPlate(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        WebNewsPlate newsPlate = WebNewsPlate.dao.findById(seq_uuid);
        WebNewsPlate UeynewsPlate = WebNewsPlate.dao.findNewsPlate(newsPlate.getNp_code(),language);
        if(F.validate.isEmpty(UeynewsPlate.getNp_id())){
            newsPlate.setSeq_uuid(null);
            newsPlate.setNp_id(null);
            newsPlate.setLanguage(language);
            return outputNewsPlate(newsPlate);
        }
        return outputNewsPlate(UeynewsPlate);
    }

    /**
     * 新闻版块管理 - 查找新闻版块
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findNewsPlateById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        WebNewsPlate model = WebNewsPlate.dao.findById(seq_uuid);
        Map data = outputNewsPlate(model);
        return data;
    }

    /**
     * 新闻版块管理 - 输出新闻版块管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputNewsPlate(WebNewsPlate model) throws MsgBusinessException {
        String type = SysDatalibrary.dao.findSysDatalibrary(AdminConstants.LanguageType.ZH_SIMP.value,"web_newsModule",model.getType()).getName();
        String location = SysDatalibrary.dao.findSysDatalibrary(AdminConstants.LanguageType.ZH_SIMP.value,"web_newsLocation",model.getLocation()).getName();
        String nav_name = WebNav.dao.findNavByCode(model.getNav_code(),AdminConstants.LanguageType.ZH_SIMP.value).getNav_name();
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"np_id",model.getNp_id()
                ,"np_code",model.getNp_code()
                ,"np_name",model.getNp_name()
                ,"nav_name",nav_name
                ,"type",model.getType()
                ,"location",model.getLocation()
                ,"nav_code",model.getNav_code()
                ,"type_show",type
                ,"location_show",location
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"order_num",model.getOrder_num()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 新闻版块管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkNewsPlateModel(WebNewsPlateVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getNp_name())){
            throw new MsgBusinessException("版块名称不能为空");
        }
        if (F.validate.isEmpty(vo.getType())){
            throw new MsgBusinessException("版块类型不能为空");
        }
        if (F.validate.isEmpty(vo.getLocation())){
            throw new MsgBusinessException("版块位置不能为空");
        }
        if (F.validate.isEmpty(vo.getNav_code())){
            throw new MsgBusinessException("所属导航不能为空");
        }
    }

    /**
     * 新闻版块管理 - 通过plate_code和语言获取版块
     * @param plate_code
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    public Map findNewsPlateByCode(String plate_code,String language) throws MsgBusinessException{
        WebNewsPlate newsPlate = WebNewsPlate.dao.findNewsPlate(plate_code,language);
        Map map = F.transKit.asMap("seq_uuid", newsPlate.getSeq_uuid()
                ,"name", newsPlate.getNp_name());
        return map;
    }

    /**
     * 新闻文章管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNewsArticleSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_language");
        List<Map> languageList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLanguageList){
            languageList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_articleStatus");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList,"languageList",languageList);
    }

    /**
     * 新闻文章管理 - 获取新闻文章列表
     * @param np_name
     * @param title
     * @param publish_date
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNewsArticleByPage(String np_name, String title, String publish_date, String language, String status, PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = WebNewsInfo.dao.queryNewsArticleByPage(np_name,title,publish_date,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 新闻文章管理 - 添加新闻文章
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNewsInfo addNewsArticle(AuthOperator opt, WebNewsInfoVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getNi_id()+"")) {
            checkNewsArticleModel(vo);
            WebNewsInfo newsArticle = WebNewsInfo.dao.findByArticleTitle(vo.getNp_code(),vo.getTitle());
            if(F.validate.isNotEmpty(newsArticle.getNi_id())){
                throw new MsgBusinessException("该新闻版块中该文章标题已存在");
            }
            WebNewsInfo model = new WebNewsInfo();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyNewsArticle(opt,vo);
        }

    }

    /**
     * 新闻文章管理 - 获取维语新闻文章
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyNewsArticle(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        WebNewsInfo newsArticle = WebNewsInfo.dao.findById(seq_uuid);
        WebNewsInfo UeynewsArticle = WebNewsInfo.dao.findNewsArticle(newsArticle.getNi_code(),language);
        if(F.validate.isEmpty(UeynewsArticle.getNi_id())){
            newsArticle.setSeq_uuid(null);
            newsArticle.setNi_id(null);
            newsArticle.setLanguage(language);
            return outputNewsArticle(newsArticle);
        }
        return outputNewsArticle(UeynewsArticle);
    }

    /**
     * 新闻文章管理 - 修改新闻文章
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public WebNewsInfo modifyNewsArticle(AuthOperator opt, WebNewsInfoVO vo) throws MsgBusinessException {
        checkNewsArticleModel(vo);
        Boolean bool = WebNewsInfo.dao.isHaveArticle(vo.getSeq_uuid(),vo.getNp_code(),vo.getTitle());
        if(bool){
            throw new MsgBusinessException("该文章标题已存在");
        }
        WebNewsInfo model = WebNewsInfo.dao.findById(vo.getSeq_uuid());
        model.setTitle(vo.getTitle());
        model.setLogo(vo.getLogo());
        model.setTags(vo.getTags());
        model.setSource(vo.getSource());
        model.setContent(vo.getContent());
        model.setPublish_date(vo.getPublish_date());
        model.setAuto_publish_date(vo.getAuto_publish_date());
        model.setIs_top(vo.getIs_top());
        model.setIs_recom(vo.getIs_recom());
        model.setIs_show(vo.getIs_show());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 新闻文章管理 - 下架
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map unPublishArticle(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻文章编号不能为空");
        }
        WebNewsInfo newsArticle = WebNewsInfo.dao.findById(seq_uuid);
        List<WebNewsInfo> newsArticleList = WebNewsInfo.dao.queryByArticleCode(newsArticle.getNi_code());
        for (WebNewsInfo article : newsArticleList){
            article.setStatus("3");
            article.setIs_show("N");
            article.saveOrUpdate(opt);
        }
        WebNewsInfo UeynewsArticle = WebNewsInfo.dao.findUnPublishArticle(newsArticle.getNi_code(),newsArticle.getLanguage());
        return outputNewsArticle(UeynewsArticle);
    }

    /**
     * 新闻文章管理 - 发布
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map publishArticle(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻文章编号不能为空");
        }
        WebNewsInfo newsArticle = WebNewsInfo.dao.findById(seq_uuid);
        newsArticle.setStatus("2");
        newsArticle.saveOrUpdate(opt);
        WebNewsInfo UeynewsArticle = WebNewsInfo.dao.findNewsArticle(newsArticle.getNi_code(),newsArticle.getLanguage());
        return outputNewsArticle(UeynewsArticle);
    }


    /**
     * 新闻文章管理 - 查找新闻文章
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findNewsArticleById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻文章编号不能为空");
        }
        WebNewsInfo model = WebNewsInfo.dao.findById(seq_uuid);
        Map data = outputNewsArticle(model);
        return data;
    }

    /**
     * 新闻文章管理 - 输出新闻文章管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputNewsArticle(WebNewsInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"ni_id",model.getNi_id()
                ,"ni_code",model.getNi_code()
                ,"np_code",model.getNp_code()
                ,"np_name",model.findNewsPlate().getNp_name()
                ,"type",model.findNewsPlate().getType()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"title",model.getTitle()
                ,"logo",model.getLogo()
                ,"publish_date",model.getPublish_date()
                ,"auto_publish_date",model.getAuto_publish_date()
                ,"tags",model.getTags()
                ,"source",model.getSource()
                ,"content",model.getContent()
                ,"is_recom",model.getIs_recom()
                ,"is_top",model.getIs_top()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 新闻文章管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkNewsArticleModel(WebNewsInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getNp_code())){
            vo.setNp_code("9");
//            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        if (F.validate.isEmpty(vo.getTitle())){
            throw new MsgBusinessException("文章标题不能为空");
        }
        if (F.validate.isEmpty(vo.getContent())){
            throw new MsgBusinessException("文章内容不能为空");
        }
    }
    public void generateNews(String type,String is_recome,String language,String np_code,String ni_code,String is_publish) {
        if(type.equals("1")&&is_recome.equals("Y")) {
            String lan = "";
            if(language.equals(AdminConstants.LanguageType.ZH_SIMP.value)) {
                lan = "zh";
            }else {
                lan = "ug";
            }
            Map result = F.transKit.asMap();
            List<WebNewsInfo> indexNews = WebNewsInfo.dao.queryIndexNews(language,np_code);
            List<Map> newsList = new ArrayList<Map>();
            for(WebNewsInfo news : indexNews) {
                newsList.add(F.transKit.asMap("title",news.getTitle(),"content",news.getContent(),"publish_date",news.getPublish_date(),"logo",news.getLogo(),"url","/html/"+lan+"/news/articles"+news.getNi_id()+".html"));
            }
            result.put("indexNews",newsList);
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
            Template t = gt.getTemplate(lan+"/index/indexNews.html");
            t.binding("data", result);
            String str = t.render();
            System.out.println(str);
            try {
                String dirPath =PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
                EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
                FileUtils.writeStringToFile(new File(dirPath+"/index/indexNews.html"), str,"UTF-8");
                EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(is_publish.equals("publish")) {
                WebNewsInfo newsInfo =WebNewsInfo.dao.findNewsArticle(ni_code,language);
                GroupTemplate Gt = new GroupTemplate(resourceLoader, cfg);
                Template tem = Gt.getTemplate(lan + "/news/newsInfo.html");
                tem.binding("data", newsInfo);
                String stri = tem.render();
                System.out.println(stri);
                try {
                    String dirPath = PropKit.use("config.properties").get("cms.generateStaticFile_path")+"/"+lan;
                    EnvUtil.exec(dirPath + "/sh/svnUpdate.sh");
                    FileUtils.writeStringToFile(new File(dirPath + "/news/articles" + newsInfo.getNi_id() + ".html"), stri, "UTF-8");
                    EnvUtil.exec(dirPath + "/sh/svnAddCommit.sh");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
