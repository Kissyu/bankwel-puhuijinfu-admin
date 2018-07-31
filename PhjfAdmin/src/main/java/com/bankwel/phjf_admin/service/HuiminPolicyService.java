package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyArticleVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyPlateCityVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyPlateVO;
import com.bankwel.phjf_admin.webapi.vo.SearchTermVO;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.mail.search.SearchTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: HuiminPolicyService
 * @Description: 惠民政策 服务
 * @author: DukeMr.Chen
 * @date: 2018/4/27 14:54
 * @version: V1.0
 *
 */
public class HuiminPolicyService {
    NewsService newsService = Duang.duang(NewsService.class);

    /**
     * @Title:
     * @Description: 查询维语版惠民政策文章
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public Map findUeyPolicyArticle(String pa_id, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(pa_id)){
            throw new MsgBusinessException("文章编号不能为空");
        }
        PolicyArticle policyArticle = PolicyArticle.dao.findByPaId(pa_id);
        PolicyArticle UeyPolicyArticle = PolicyArticle.dao.findByArticleCode(policyArticle.getArticle_code(),language);
        if(F.validate.isEmpty(UeyPolicyArticle.getPa_id())){
            policyArticle.setSeq_uuid(null);
            policyArticle.setPa_id(null);
            policyArticle.setLanguage(language);
            return outputHuiminPolicyArticle(policyArticle);
        }
        return outputHuiminPolicyArticle(UeyPolicyArticle);
    }

    /**
     * @Title:
     * @Description: 组装返回页面视图的数据map
     * @author: DukeMr.Chen
     */
    public Map outputHuiminPolicyArticle(PolicyArticle model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"pa_id",model.getPa_id()
                ,"article_code",model.getArticle_code()
                ,"language",model.getLanguage()
                ,"language_show", model.findLanguage_show()
                ,"title",model.getTitle()
                ,"logo",model.getLogo()
                ,"publish_date", model.getPublish_date() != null ? F.dateKit.translate2Str(model.getPublish_date(), "yyyy-MM-dd") : ""
                ,"auto_publish_date", model.getAuto_publish_date() != null ? F.dateKit.translate2Str(model.getAuto_publish_date(), "yyyy-MM-dd") : ""
                ,"source",model.getSource()
                ,"source_url",model.getSource_url()
                ,"plate_list",model.queryPlateListShow(AdminConstants.ZH_SIMP)
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
                ,"create_time",model.getCreate_time()
                ,"province",model.getProvince()
                ,"city",model.getCity()
                ,"area",model.getArea());
        return data;
    }

    /**
     * @Title:
     * @Description: 获取条件列表
     * @author: DukeMr.Chen
     */
    public Map queryHuiminPolicySearchList() {
        return newsService.queryNewsArticleSearchList();
    }

    /**
     * @Title:
     * @Description: 查询惠民政策文章 分页
     * @author: DukeMr.Chen
     */
    public Map queryHuiminPolicyByPage(String plate_name, String title, String publish_date, String language, String status, PageKit page) {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>,PageKit<Record>> pair  =  PolicyArticle.dao.queryPolicyArticleIdByPage(plate_name, title, publish_date, language, status, page);
        List<Record> idList = pair.getLeft();
        List<Map> processList = new ArrayList<Map>();
        for(Record r : idList){
            PolicyArticle policyArticle = PolicyArticle.dao.findByPaId(""+r.get("pa_id"));
            processList.add(F.transKit.asMap(
                    "pa_id", policyArticle.getPa_id()
                    , "article_code", policyArticle.getArticle_code()
                    ,"plate_list",policyArticle.queryPlateListShow(language)
                    , "title", policyArticle.getTitle()
                    , "publish_date", policyArticle.getPublish_date()
                    , "source", policyArticle.getSource()
                    , "share_count", policyArticle.getShare_count()
                    , "click_count", policyArticle.getClick_count()
                    , "status", policyArticle.getStatus()
                    , "is_banner_show", policyArticle.getIsBannerShow(language)
                    , "is_top_show", policyArticle.getIsTopShow(language)
                    , "is_recom_show", policyArticle.getIsRecomShow(language)
                    , "is_show_name", policyArticle.getIsShowName(language)
                    , "status_show", policyArticle.getStatusShow(language)
            ));
        }
        return F.transKit.asMap("rows", processList, "total", pair.getRight().getTotal());
    }

    /**
     * @Title:
     * @Description: 添加惠民政策
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public PolicyArticle addPolicyArticle(AuthOperator opt, PolicyArticleVO vo) {
        PolicyArticle retObject = null;
        if(F.validate.isEmpty(vo.getPa_id())) {
            checkModel(vo);
            PolicyArticle policyArticle = PolicyArticle.dao.findByArticleTitle(vo.getTitle());
            if(F.validate.isNotEmpty(policyArticle.getPa_id())){
                throw new MsgBusinessException("该政策文章标题已存在");
            }
            PolicyArticle model = new PolicyArticle();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            //插入标签关系
            dealWith(model, opt, vo);
            return model;

        }else {
            return this.modifyPolicyArticle(opt,vo);
        }
    }

    /**
     * @Title:
     * @Description: 修改政策文章
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public PolicyArticle modifyPolicyArticle(AuthOperator opt, PolicyArticleVO vo) throws MsgBusinessException {
        checkModel(vo);
        PolicyArticle model = PolicyArticle.dao.findById(vo.getPa_id() + "");
        model.setTitle(vo.getTitle());
        model.setLogo(vo.getLogo());
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
        model.setProvince(vo.getProvince());
        model.setCity(vo.getCity());
        model.setArea(vo.getArea());

        model.saveOrUpdate(opt);

        //插入标签关系
        dealWith(model, opt, vo);

        return model;
    }

    public void dealWith(PolicyArticle model, AuthOperator opt, PolicyArticleVO vo){
        String articleCode = model.getArticle_code();
        //1 phjf_policy_article_search
        PolicyArticleSearch.dao.dealWithRelation(opt, articleCode, vo);

        //2 phjf_policy_article_plate
        PolicyArticlePlate.dao.dealWithRelation(opt, articleCode, vo);
    }

    /**
     * @Title:
     * @Description: 检查视图对象
     * @author: DukeMr.Chen
     */
    public void checkModel(PolicyArticleVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getTitle())){
            throw new MsgBusinessException("文章标题不能为空");
        }
        if (F.validate.isEmpty(vo.getContent())){
            throw new MsgBusinessException("文章内容不能为空");
        }
    }

    /**
     * @Title:
     * @Description: 通过pa_id查文章
     * @author: DukeMr.Chen
     */
    public Map findPolicyArticleById(String pa_id) {
        if(F.validate.isEmpty(pa_id)){
            throw new MsgBusinessException("政策文章编号不能为空");
        }
        PolicyArticle model = PolicyArticle.dao.findByPaId(pa_id);
        Map data = outputHuiminPolicyArticle(model);
        //添加搜索关系
        List <PolicyArticleSearch> searchList = PolicyArticleSearch.dao.findByArticleCode(model.getArticle_code());
        StringBuilder sbS = new StringBuilder();
        for (PolicyArticleSearch obj : searchList) {
            sbS.append(",").append(obj.getSearch_code());
        }
        data.put("search_terms", sbS.length() > 1 ? sbS.toString().substring(1) : "");
        //添加板块大类
        List <PolicyArticlePlate> plateList = PolicyArticlePlate.dao.findByArticleCode(model.getArticle_code());
        StringBuilder sbP = new StringBuilder();
        for (PolicyArticlePlate obj : plateList) {
            sbP.append(",").append(obj.getPlate_code());
        }
        data.put("policy_plate", sbP.length() > 1 ? sbP.toString().substring(1) : "");
        return data;
    }

    /**
     * @Title:
     * @Description: 发布、下架
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public void publishOrUnArticle(AuthOperator opt, String pa_id, String status) throws MsgBusinessException {
        if(F.validate.isEmpty(pa_id)){
            throw new MsgBusinessException("政策文章编号不能为空");
        }
        PolicyArticle newsArticle = PolicyArticle.dao.findByPaId(pa_id);
        newsArticle.setStatus(status);
        newsArticle.saveOrUpdate(opt);
    }
    //  惠民政策版块管理
    /**
     * 惠民政策版块管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryHuiminPolicyPlateSearchList() throws MsgBusinessException {
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("phjf", "sys_language");
        List<Map> languageList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLanguageList) {
            languageList.add(F.transKit.asMap("code", lib.getCode(), "name", lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf", "sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList) {
            statusList.add(F.transKit.asMap("code", lib.getCode(), "name", lib.getName()));
        }
        List<SysProvinces> sysProvincesList = SysProvinces.dao.queryProvinceById();
        List<Map> provincesList = new ArrayList<Map>();
        for (SysProvinces lib : sysProvincesList) {
            provincesList.add(F.transKit.asMap("province_id", lib.getProvince_id(), "province", lib.getProvince()));
        }
        return F.transKit.asMap("statusList", statusList, "languageList", languageList,"provincesList",provincesList);
    }
    /**
     * 惠民政策版块管理 - 获取惠民政策版块列表
     * @param name
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryPolicyPlateByPage(String name, String language, String status,String province, PageKit page) throws MsgBusinessException {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>, PageKit<Record>> pair = PolicyPlate.dao.queryPolicyPlateByPage(name,language,status,province, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }
    /**
     * 惠民政策版块管理 - 输出惠民政策版块管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputPolicyPlate(PolicyPlate model) throws MsgBusinessException {

        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"pp_id",model.getPp_id()
                ,"plate_code",model.getPlate_code()
                ,"name",model.getName()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"logo",model.getLogo()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }
    /**
     * 惠民政策版块管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkPolicyPlateModel(PolicyPlateVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("版块名称不能为空");
        }

    }
    /**
     * 惠民政策版块管理 - 添加惠民政策版块
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public PolicyPlate addPolicyPlate(AuthOperator opt, PolicyPlateVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getPp_id()+"")) {
            checkPolicyPlateModel(vo);
            PolicyPlate policyPlate = PolicyPlate.dao.findByPlateName(vo.getName());
            if(F.validate.isNotEmpty(policyPlate.getPp_id())){
                throw new MsgBusinessException("该惠民政策版块名称已存在");
            }
            PolicyPlate model = new PolicyPlate();
            F.transKit.copyProperties(model, vo);
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyPolicyPlate(opt,vo);
        }
    }
    /**
     * 惠民政策版块管理 - 获取维语惠民政策版块
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyPolicyPlate(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("新闻版块编号不能为空");
        }
        PolicyPlate policyPlate = PolicyPlate.dao.findById(seq_uuid);
        PolicyPlate UeypolicyPlate = PolicyPlate.dao.findPolicyPlate(policyPlate.getPlate_code(),language);
        if(F.validate.isEmpty(UeypolicyPlate.getPp_id())){
            policyPlate.setSeq_uuid(null);
            policyPlate.setPp_id(null);
            policyPlate.setLanguage(language);
            return outputPolicyPlate(policyPlate);
        }
        return outputPolicyPlate(UeypolicyPlate);
    }
    /**
     * 惠民政策版块管理 - 查找惠民政策版块
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findPolicyPlateById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("惠民政策版块编号不能为空");
        }
        PolicyPlate model = PolicyPlate.dao.findById(seq_uuid);
        Map data = outputPolicyPlate(model);
        return data;
    }
    /**
     * 惠民政策版块管理 - 修改惠民政策版块
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public PolicyPlate modifyPolicyPlate(AuthOperator opt, PolicyPlateVO vo) throws MsgBusinessException {
        checkPolicyPlateModel(vo);
        Boolean bool = PolicyPlate.dao.isHavePlate(vo.getSeq_uuid(),vo.getName());
        if(bool){
            throw new MsgBusinessException("该惠民政策版块名称已存在");
        }
        PolicyPlate model = PolicyPlate.dao.findById(vo.getSeq_uuid());
        model.setName(vo.getName());
        model.setLogo(vo.getLogo());
        model.setRemark(vo.getRemark());
        model.setIs_show(vo.getIs_show());

        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * 惠民政策版块管理 - 删除惠民政策版块
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deletePolicyPlate(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("惠民政策版块编号不能为空");
        }
        PolicyPlate policyPlate = PolicyPlate.dao.findById(seq_uuid);
        policyPlate.setStatus("4");
        policyPlate.setIs_show("N");
        policyPlate.saveOrUpdate(opt);
    }
    /**
     * 惠民政策版块管理 - 分配惠民政策版块
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public PolicyPlateCity allocationPolicyPlate(AuthOperator opt, PolicyPlateCityVO vo) throws MsgBusinessException {
        checkPolicyPlateCityModel(vo);
        PolicyPlateCity policyPlateCity = PolicyPlateCity.dao.findByPlateCode(vo.getPlate_code());
        if(F.validate.isNotEmpty(policyPlateCity.getPpc_id())){
            throw new MsgBusinessException("该惠民政策版块名称已存在");
        }
//        Boolean bool = PolicyPlateCity.dao.isHavePlate(vo.getSeq_uuid(),vo.getPlate_code());
//        if(bool){
//            throw new MsgBusinessException("该惠民政策版块名称已存在");
//        }
        PolicyPlateCity model = new PolicyPlateCity();
        F.transKit.copyProperties(model, vo);
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * 分配惠民政策版块管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkPolicyPlateCityModel(PolicyPlateCityVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getPlate_code())){
            throw new MsgBusinessException("版块名称不能为空");
        }
        if (F.validate.isEmpty(vo.getProvince_id())){
            throw new MsgBusinessException("省份不能为空");
        }
        if (F.validate.isEmpty(vo.getOrder_num())){
            throw new MsgBusinessException("排序号不能为空");
        }
    }
    /**
     * 惠民政策版块管理 - 查找惠民政策版块分配
     * @return
     * @throws MsgBusinessException
     */
    public Map findPolicyPlateCityById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("惠民政策版块编号不能为空");
        }
        PolicyPlateCity model = PolicyPlateCity.dao.findById(seq_uuid);
        Map data = outputPolicyPlateCity(model);
        return data;
    }
    /**
     * 惠民政策版块管理 - 查找惠民政策版块分配
     * @return
     * @throws MsgBusinessException
     */
    public Map findPolicyPlateCityByCode(String plate_code, String province_id) throws MsgBusinessException {
        if(F.validate.isEmpty(plate_code)){
            throw new MsgBusinessException("惠民政策版块编号不能为空");
        }
        PolicyPlateCity model = PolicyPlateCity.dao.findByPlateInfo(plate_code,province_id);
        Map data = outputPolicyPlateCity(model);
        return data;
    }
    /**
     * 惠民政策版块管理 - 输出分配惠民政策版块管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputPolicyPlateCity(PolicyPlateCity model) throws MsgBusinessException {
        PolicyPlate palteName = PolicyPlate.dao.findByPlateCode(model.getPlate_code());

        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"ppc_id",model.getPpc_id()
                ,"plate_code",model.getPlate_code()
                ,"name",palteName.getName()
                ,"language",palteName.getLanguage()
                ,"language_show",palteName.findLanguage_show()
                ,"province_id",model.getProvince_id()
                ,"order_num",model.getOrder_num()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }
    /**
     * 惠民政策版块管理 - 修改惠民政策版块分配
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public PolicyPlateCity modifyPolicyPlateCity(AuthOperator opt, PolicyPlateCityVO vo) throws MsgBusinessException {
        checkPolicyPlateCityModel(vo);
        Boolean bool = PolicyPlateCity.dao.isHavePlate(vo.getSeq_uuid(),vo.getPlate_code());
        if(bool){
            throw new MsgBusinessException("该惠民政策版块名称已存在");
        }
        PolicyPlateCity model = PolicyPlateCity.dao.findById(vo.getSeq_uuid());
        model.setOrder_num(vo.getOrder_num());
        model.setIs_show(vo.getIs_show());
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * @Title:
     * @Description: 获取条件列表
     * @author: DukeMr.Chen
     */
    public Map querySearchTermList() {
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
        List<SysDatalibrary> sysSearchTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_policySearchType");
        return F.transKit.asMap("statusList",statusList,"languageList",languageList,"searchTypeList",sysSearchTypeList);
    }
    /**
     * @Title:
     * @Description: 查询惠民政策搜索条件 分页
     * @author: DukeMr.Chen
     */
    public Map queryPolicySearchTermByPage(String name, String type, String language, String status, PageKit page) {
        if(F.validate.isEmpty(language)){
            language = AdminConstants.LanguageType.ZH_SIMP.value;
        }
        Pair<List<Record>,PageKit<Record>> pair  =  SearchTerms.dao.querySearchTermByPage(name, type, language, status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());

    }
    /**
     * @Title:
     * @Description: 添加搜索条件
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public SearchTerms addSearchTerm(AuthOperator opt, SearchTermVO vo) {
        if(F.validate.isEmpty(vo.getSt_id())) {
            checkSearchTerm(vo);
            SearchTerms searchTerms = SearchTerms.dao.findSearchTermByName(vo.getName(),vo.getLanguage());
            if(F.validate.isNotEmpty(searchTerms.getSt_id())){
                throw new MsgBusinessException("该名称已存在");
            }
            SearchTerms model = new SearchTerms();
            F.transKit.copyProperties(model, vo);
            SearchTerms stModel = model.saveOrUpdate(opt);
            SearchTerms stByUUId = SearchTerms.dao.findStByUUId(stModel.getSeq_uuid());
            if(F.validate.isEmpty(stByUUId.getParent_st_id())) {
                stByUUId.setTreepath(stByUUId.getSt_id()+"");
                stByUUId.saveOrUpdate(opt);
            }else{
                SearchTerms st = SearchTerms.dao.findStById(stByUUId.getParent_st_id()+"");
                if(F.validate.isNotEmpty(st.getSeq_uuid())) {
                    stByUUId.setTreepath(st.getTreepath()+"-"+stByUUId.getSt_id());
                    stByUUId.saveOrUpdate(opt);
                }
            }
            return stByUUId;

        }else {
            return this.modifySearchTerm(opt,vo);
        }
    }

    /**
     * @Title:
     * @Description: 组装返回页面视图的数据map
     * @author: DukeMr.Chen
     */
    public Map outputSearchTerm(SearchTerms model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"st_id",model.getSt_id()
                ,"search_code",model.getSearch_code()
                ,"language",model.getLanguage()
                ,"language_show", model.findLanguage_show()
                ,"name",model.getName()
                ,"logo",model.getLogo()
                ,"type",model.getType()
                ,"type_name",model.findType_show()
                ,"is_child",model.getIs_child()
                ,"is_child_name",model.findIsChild_show()
                ,"parent_st_id",model.getParent_st_id()
                ,"parent_st_name",F.validate.isNotEmpty(model.getParent_st_id())?model.findStById(model.getParent_st_id()+"").getName():""
                ,"treepath",model.getTreepath()
                ,"order_no",model.getOrder_no()
                ,"province",model.findProvinceById(model.getProvince()).getProvince_id()
                ,"province_name",model.findProvinceById(model.getProvince()).getProvince()
        );
        return data;
    }

    /**
     * @Title:
     * @Description: 检查视图对象
     * @author: DukeMr.Chen
     */
    public void checkSearchTerm(SearchTermVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        if (F.validate.isEmpty(vo.getIs_child())){
            throw new MsgBusinessException("是否为子节点不能为空");
        }
        if (F.validate.isEmpty(vo.getType())){
            throw new MsgBusinessException("类型不能为空");
        }
        if (F.validate.isEmpty(vo.getOrder_no())){
            throw new MsgBusinessException("序号不能为空");
        }
    }
    /**
     * @Title:
     * @Description: 查询维语版搜索条件
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public Map findUeySearchTerm(String st_id, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(st_id)){
            throw new MsgBusinessException("文章编号不能为空");
        }
        SearchTerms st = SearchTerms.dao.findStById(st_id);
        SearchTerms UeySt = SearchTerms.dao.findBySearchCode(st.getSearch_code(),language);
        if(F.validate.isEmpty(UeySt.getSt_id())){
            st.setSeq_uuid(null);
            st.setSt_id(null);
            st.setParent_st_id(st.getParent_st_id());
            st.setLanguage(language);
            return outputSearchTerm(st);
        }
        return outputSearchTerm(UeySt);
    }

    /**
     * @Title:
     * @Description: 通过st_id查搜索条件
     * @author: DukeMr.Chen
     */
    public Map findSearchTermById(String st_id) {
        if(F.validate.isEmpty(st_id)){
            throw new MsgBusinessException("编号不能为空");
        }
        SearchTerms model = SearchTerms.dao.findStById(st_id);
        Map data = outputSearchTerm(model);
        return data;
    }
    /**
     * @Title:
     * @Description: 修改搜索条件
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public SearchTerms modifySearchTerm(AuthOperator opt, SearchTermVO vo) throws MsgBusinessException {
        checkSearchTerm(vo);
        SearchTerms model = SearchTerms.dao.findById(vo.getSt_id() + "");
        model.setName(vo.getName());
        model.setLogo(vo.getLogo());
        model.setLanguage(vo.getLanguage());
        model.setOrder_no(vo.getOrder_no());
        model.setProvince(vo.getProvince());
        model.setIs_child(vo.getIs_child());
        model.setParent_st_id(vo.getParent_st_id());
        model.saveOrUpdate(opt);
        return model;
    }
    /**
     * @Title:
     * @Description: 失效
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public void unPublishSearchTerm(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("编号不能为空");
        }
        SearchTerms searchTerms = SearchTerms.dao.findStByUUId(seq_uuid);
        searchTerms.setStatus("4");
        searchTerms.setIs_show("N");
        searchTerms.saveOrUpdate(opt);
    }
}
