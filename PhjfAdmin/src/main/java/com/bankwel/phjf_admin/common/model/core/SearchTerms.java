package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.DataLoader;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.DynamicSelectData;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheSearchTerms;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.AdminPolicyArticleEnum;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SearchTerms extends CacheSearchTerms<SearchTerms> {
    public static final SearchTerms dao = new SearchTerms().dao();

    public List<IDynamicSelectData> querySelectData(String language){
        String sql = "select * from phjf_search_terms where is_show = 'Y' and status = '1' and  language = ? and parent_st_id is not null order by treepath";
        List<SearchTerms> list = dao.use("DBPublic").find(sql, language);
        List<IDynamicSelectData> selectData = new ArrayList<IDynamicSelectData>();
        for (SearchTerms item : list){
            IDynamicSelectData sd = new DynamicSelectData();
            sd.setId(item.getSearch_code());
            sd.setName(item.getName());
            selectData.add(sd);
        }
        return selectData;
    }

    /**
     * 获取新闻版块列表
     * @param name
     * @param language
     * @param status
     * @param page
     * @return
     */
    public Pair<List<Record>,PageKit<Record>> querySearchTermByPage(String name,String type, String language, String status, PageKit page){
        String sql = "select st.seq_uuid," +
                "            st.st_id," +
                "            st.search_code," +
                "            st.language," +
                "            st.name," +
                "            st.order_no," +
                "            st.status," +
                "            st.is_child," +
                "            st.type," +
                "            st.parent_st_id," +
                "            st.treepath," +
                "            st.province," +
                "            st.logo," +
                "            st.create_time," +
                "            status.name status_show," +
                "            is_show.name is_show_name," +
                "            type.name type_name," +
                "            parent_st.name parent_st_name," +
                "            province.province province_name," +
                "            is_child.name is_child_name" +
                "       from phjf_search_terms st" +
                "            left join sys_datalibrary status on status.parent_code = 'sys_status' and status.code = st.status" +
                "            left join sys_datalibrary is_show on is_show.parent_code = 'sys_isShow' and is_show.code = st.is_show" +
                "            left join sys_datalibrary type on type.parent_code = 'sys_policySearchType' and type.code = st.type" +
                "            left join sys_datalibrary is_child on is_child.parent_code = 'sys_policySearchIsChild' and is_child.code = st.is_child" +
                "            left join phjf_search_terms parent_st on parent_st.st_id = st.parent_st_id" +
                "            left join sys_provinces province on province.province_id = st.province" +
                "      where 1=1";
        List params = new ArrayList();
        if (F.validate.isNotEmpty(name)){
            sql += " and (st.name = ? or st.search_code = ?)";
            params.add(name);
            params.add(name);
        }
        if (F.validate.isNotEmpty(type)){
            sql += " and st.type = ? ";
            params.add(type);
        }
        if (F.validate.isNotEmpty(language)){
            sql += " and st.language = ? ";
            params.add(language);
        }

        if (F.validate.isNotEmpty(status)){
            sql += " and st.status = ? ";
            params.add(status);
        }
        sql += " order by st.treepath asc , st.parent_st_id asc";
        return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
    }
    /**
     * @Title:
     * @Description: 根据名称查询
     * @author: DukeMr.Chen
     */
    public SearchTerms findSearchTermByName(String name,String language) {
        String sql = "select * " +
                "       from phjf_search_terms " +
                "      where name = ? and language = ? and status = 1 and is_show = 'Y' " ;
        SearchTerms data = SearchTerms.dao.use("DBPublic").findFirst(sql, name,language);
        if(F.validate.isEmpty(data)){
            data = new SearchTerms();
        }
        return data;
    }
    /**
     * @Title:
     * @Description: 根据id查询
     * @author: DukeMr.Chen
     */
    public SearchTerms findStById(String st_id) {
        String sql = "select * " +
                "       from phjf_search_terms " +
                "      where st_id = ? and status = 1 and is_show = 'Y' " ;
        SearchTerms data = SearchTerms.dao.use("DBPublic").findFirst(sql,st_id);
        if(F.validate.isEmpty(data)){
            data = new SearchTerms();
        }
        return data;
    }
    public SearchTerms findStByUUId(String uuid) {
        String sql = "select * " +
                "       from phjf_search_terms " +
                "      where seq_uuid = ? and status = 1 and is_show = 'Y' " ;
        SearchTerms data = SearchTerms.dao.use("DBPublic").findFirst(sql,uuid);
        if(F.validate.isEmpty(data)){
            data = new SearchTerms();
        }
        return data;
    }
    /**
     * @Title:
     * @Description: 保存或更新
     * @author: DukeMr.Chen
     */
    public SearchTerms saveOrUpdate(AuthOperator opt) throws MsgBusinessException {
        this.checkModelItem();
        if (F.validate.isNotEmpty(this.getSt_id()+"")&&!(this.getSt_id().equals(0))){
            this.setModify_opt(opt.getSeq_id()+"");
            this.setModify_time(new Date());
            if(F.validate.isEmpty(this.getIs_show())){
                this.setIs_show("Y");
                this.setStatus("1");
            }
            this.update();
        } else {
            if (F.validate.isEmpty(this.getSearch_code())){
                this.setSearch_code(SysSeq.dao.generatorSearchTermCode());
            }
            this.setSeq_uuid(UUID.randomUUID().toString());
            this.setIs_show("Y");
            this.setStatus("1");
            this.setCreate_opt(opt.getSeq_id()+"");
            this.setCreate_time(new Date());
            this.setModify_opt(opt.getSeq_id()+"");
            this.setModify_time(new Date());
            this.save();
        }
        flashCatch(this);
        return this;
    }
    public void checkModelItem() {
        this.checkLogo("图标");
        this.checkProvince("省份");
        this.checkName("名称");
    }
    /**
     * @Title:
     * @Description: 根据文章内部编码和语言 查询文章
     * @author: DukeMr.Chen
     */
    public SearchTerms findBySearchCode(String st_code, String language) {
        String sql = "select * " +
                "       from phjf_search_terms " +
                "      where search_code = ? " +
                "        and language = ? ";
        SearchTerms data = SearchTerms.dao.use("DBPublic").findFirst(sql, st_code, language);
        if(F.validate.isEmpty(data)){
            data = new SearchTerms();
        }
        return data;
    }
    /**
     * 获取语言的中文名
     * @return
     */
    public String findLanguage_show(){
        SysDatalibrary library = SysDatalibrary.dao.queryDatalibrary("Phjf", "sys_language", this.getLanguage());
        return library.getName();
    }
    /**
     * 获取省份
     * @return
     */
    public SysProvinces findProvinceById(String province_id){
        SysProvinces province = SysProvinces.dao.findProvinceById(province_id);
        return province;
    }
    /**
     * 获取类型
     * @return
     */
    public String findType_show(){
        SysDatalibrary library = SysDatalibrary.dao.queryDatalibrary(this.getLanguage(), "sys_policySearchType", this.getType());
        return library.getName();
    }
    /**
     * 获取是否为子节点
     * @return
     */
    public String findIsChild_show(){
        SysDatalibrary library = SysDatalibrary.dao.queryDatalibrary(this.getLanguage(), "sys_policySearchIsChild", this.getIs_child());
        return library.getName();
    }
}
