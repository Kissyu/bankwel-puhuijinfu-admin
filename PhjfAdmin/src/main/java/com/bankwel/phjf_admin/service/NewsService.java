package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.NewsArticle;
import com.bankwel.phjf_admin.common.model.core.NewsPlate;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.webapi.vo.NewsArticleVO;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/15.
 */
public class NewsService {
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
        Pair<List<Record>, PageKit<Record>> pair = NewsPlate.dao.queryNewsPlateByPage(name,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 新闻版块管理 - 添加新闻版块
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public NewsPlate addNewsPlate(AuthOperator opt, NewsPlateVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getPlate_id()+"")) {
            checkNewsPlateModel(vo);
            NewsPlate newsPlate = NewsPlate.dao.findByPlateName(vo.getName());
            if(F.validate.isNotEmpty(newsPlate.getPlate_id())){
                throw new MsgBusinessException("该新闻版块名称已存在");
            }
            NewsPlate model = new NewsPlate();
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
    public NewsPlate modifyNewsPlate(AuthOperator opt, NewsPlateVO vo) throws MsgBusinessException {
        checkNewsPlateModel(vo);
        Boolean bool = NewsPlate.dao.isHavePlate(vo.getSeq_uuid(),vo.getName());
        if(bool){
            throw new MsgBusinessException("该新闻版块名称已存在");
        }
        NewsPlate model = NewsPlate.dao.findById(vo.getSeq_uuid());
        model.setName(vo.getName());
        model.setOrder_num(vo.getOrder_num());
        model.setRemark(vo.getRemark());
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
        NewsPlate newsPlate = NewsPlate.dao.findById(seq_uuid);
        List<NewsPlate> newsPlateList = NewsPlate.dao.findByPlateCode(newsPlate.getPlate_code());
        for (NewsPlate plate : newsPlateList){
            plate.setStatus("4");
            plate.setIs_show("N");
            plate.saveOrUpdate(opt);
        }
        List<NewsArticle> newsArticleList = NewsArticle.dao.findByPlateCode(newsPlate.getPlate_code());
        for (NewsArticle article : newsArticleList){
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
        NewsPlate newsPlate = NewsPlate.dao.findById(seq_uuid);
        NewsPlate UeynewsPlate = NewsPlate.dao.findNewsPlate(newsPlate.getPlate_code(),language);
        if(F.validate.isEmpty(UeynewsPlate.getPlate_id())){
            newsPlate.setSeq_uuid(null);
            newsPlate.setPlate_id(null);
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
        NewsPlate model = NewsPlate.dao.findById(seq_uuid);
        Map data = outputNewsPlate(model);
        return data;
    }

    /**
     * 新闻版块管理 - 输出新闻版块管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputNewsPlate(NewsPlate model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"plate_id",model.getPlate_id()
                ,"plate_code",model.getPlate_code()
                ,"name",model.getName()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"order_num",model.getOrder_num()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 新闻版块管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkNewsPlateModel(NewsPlateVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("版块名称不能为空");
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
        NewsPlate newsPlate = NewsPlate.dao.findNewsPlate(plate_code,language);
        Map map = F.transKit.asMap("seq_uuid", newsPlate.getSeq_uuid()
                ,"name", newsPlate.getName());
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
     * @param plate_name
     * @param title
     * @param publish_date
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryNewsArticleByPage(String plate_name, String title, String publish_date, String language, String status, PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = NewsArticle.dao.queryNewsArticleByPage(plate_name,title,publish_date,language,status, page);
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
    public NewsArticle addNewsArticle(AuthOperator opt, NewsArticleVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getNa_id()+"")) {
            checkNewsArticleModel(vo);
            NewsArticle newsArticle = NewsArticle.dao.findByArticleTitle(vo.getPlate_code(),vo.getTitle());
            if(F.validate.isNotEmpty(newsArticle.getNa_id())){
                throw new MsgBusinessException("该新闻版块中该文章标题已存在");
            }
            NewsArticle model = new NewsArticle();
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
        NewsArticle newsArticle = NewsArticle.dao.findById(seq_uuid);
        NewsArticle UeynewsArticle = NewsArticle.dao.findNewsArticle(newsArticle.getArticle_code(),language);
        if(F.validate.isEmpty(UeynewsArticle.getNa_id())){
            newsArticle.setSeq_uuid(null);
            newsArticle.setNa_id(null);
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
    public NewsArticle modifyNewsArticle(AuthOperator opt, NewsArticleVO vo) throws MsgBusinessException {
        checkNewsArticleModel(vo);
        Boolean bool = NewsArticle.dao.isHaveArticle(vo.getSeq_uuid(),vo.getPlate_code(),vo.getTitle());
        if(bool){
            throw new MsgBusinessException("该文章标题已存在");
        }
        NewsArticle model = NewsArticle.dao.findById(vo.getSeq_uuid());
        model.setTitle(vo.getTitle());
        model.setLogo(vo.getLogo());
        model.setTags(vo.getTags());
        model.setSource(vo.getSource());
        model.setSource_url(vo.getSource_url());
        model.setContent(vo.getContent());
        model.setPublish_date(vo.getPublish_date());
        model.setAuto_publish_date(vo.getAuto_publish_date());
        model.setIs_top(vo.getIs_top());
        model.setIs_recom(vo.getIs_recom());
        model.setRemark(vo.getRemark());
        model.setIs_show(vo.getIs_show());
        model.setIs_banner(vo.getIs_banner());
        model.setBanner_logo(vo.getBanner_logo());
        model.setIs_banner(vo.getIs_banner());
        model.setBanner_logo(vo.getBanner_logo());

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
    public void unPublishArticle(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻文章编号不能为空");
        }
        NewsArticle newsArticle = NewsArticle.dao.findById(seq_uuid);
        List<NewsArticle> newsArticleList = NewsArticle.dao.findByArticleCode(newsArticle.getArticle_code());
        for (NewsArticle article : newsArticleList){
            article.setStatus("3");
            article.saveOrUpdate(opt);
        }
    }

    /**
     * 新闻文章管理 - 发布
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void publishArticle(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻文章编号不能为空");
        }
        NewsArticle newsArticle = NewsArticle.dao.findById(seq_uuid);
        newsArticle.setStatus("2");
        newsArticle.saveOrUpdate(opt);
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
        NewsArticle model = NewsArticle.dao.findById(seq_uuid);
        Map data = outputNewsArticle(model);
        return data;
    }

    /**
     * 新闻文章管理 - 输出新闻文章管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputNewsArticle(NewsArticle model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"na_id",model.getNa_id()
                ,"article_code",model.getArticle_code()
                ,"plate_code",model.getPlate_code()
                ,"plate_name",model.findNewsPlate().getName()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"title",model.getTitle()
                ,"logo",model.getLogo()
                ,"publish_date",model.getPublish_date()
                ,"auto_publish_date",model.getAuto_publish_date()
                ,"tags",model.getTags()
                ,"source",model.getSource()
                ,"source_url",model.getSource_url()
                ,"content",model.getContent()
                ,"share_count",model.getShare_count()
                ,"click_count",model.getClick_count()
                ,"is_recom",model.getIs_recom()
                ,"is_top",model.getIs_top()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"is_banner",model.getIs_banner()
                ,"banner_logo",model.getBanner_logo()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 新闻文章管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkNewsArticleModel(NewsArticleVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getPlate_code())){
            vo.setPlate_code("9");
//            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        if (F.validate.isEmpty(vo.getTitle())){
            throw new MsgBusinessException("文章标题不能为空");
        }
        if (F.validate.isEmpty(vo.getContent())){
            throw new MsgBusinessException("文章内容不能为空");
        }
        if (F.validate.isNotEmpty(vo.getSource())){
            if (F.validate.isEmpty(vo.getSource_url())){
                throw new MsgBusinessException("来源链接地址不能为空");
            }
        }
    }

}
