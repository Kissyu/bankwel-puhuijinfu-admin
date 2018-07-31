package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.util.DataLoader;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_baseModel.common.model.phjf.CachePolicyArticle;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.AdminPolicyArticleEnum;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.tuple.Pair;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: PolicyArticle
 * @Description: 政策文章
 * @author: DukeMr.Chen
 * @date: 2018/4/27 14:45
 * @version: V1.0
 *
 */
public class PolicyArticle extends CachePolicyArticle<PolicyArticle> {
    public static final PolicyArticle dao = new PolicyArticle().dao();

    /**
     * 获取语言的中文名
     * @return
     */
    public String findLanguage_show(){
        SysDatalibrary library = SysDatalibrary.dao.queryDatalibrary("Phjf", "sys_language", this.getLanguage());
        return library.getName();
    }

    /**
     * @Title:
     * @Description: 根据文章内部编码和语言 查询文章
     * @author: DukeMr.Chen
     */
    public PolicyArticle findByArticleCode(String article_code, String language) {
        String sql = "select * " +
                "       from phjf_policy_article " +
                "      where article_code = ? " +
                "        and language = ? ";
        PolicyArticle data = PolicyArticle.dao.use("DBPublic").findFirst(sql, article_code, language);
        if(F.validate.isEmpty(data)){
            data = new PolicyArticle();
        }
        return data;
    }

    /** 
     * @Title:
     * @Description: 获取政策文章的 页 ids
     * @author: DukeMr.Chen
     */
    public Pair<List<Record>,PageKit<Record>> queryPolicyArticleIdByPage(final String plate_name, final String title, final String publish_date, final String language, final String status, PageKit pageIn) {
        ArrayList<Record> _ids = RedisClient.getClient().get(F.strKit.f(AdminPolicyArticleEnum.CK_Phjf_Model_PolicyArticle_KEY1.getKey(), plate_name, title, publish_date, language, status)
                , AdminPolicyArticleEnum.CK_Phjf_Model_PolicyArticle_KEY1.getTime()
                , new DataLoader(){
                public Object load() throws  Exception{
                    String sql = "SELECT DISTINCT pa.pa_id" +
                            " FROM phjf_policy_article pa " +
                            " LEFT JOIN phjf_policy_article_plate pap on pap.article_code = pa.article_code\n" +
                            " LEFT JOIN phjf_policy_plate pp on pp.plate_code = pap.plate_code " +
                            "WHERE 1 = 1 ";
                    List params = new ArrayList();
                    if (F.validate.isNotEmpty(plate_name)){
                        sql += " and (pp.plate_code = ? or pp.name LIKE concat('%',?,'%'))";
                        params.add(plate_name);
                        params.add(plate_name);
                    }
                    if (F.validate.isNotEmpty(title)){
                        sql += " and (pa.title LIKE concat('%',?,'%') or pa.article_code = ?)";
                        params.add(title);
                        params.add(title);
                    }
                    if (F.validate.isNotEmpty(publish_date)){
                        sql += " and pa.publish_date = ?";
                        params.add(publish_date);
                    }
                    if (F.validate.isNotEmpty(language)){
                        sql += " and pa.language = ? ";
                        params.add(language);
                    }
                    if (F.validate.isNotEmpty(status)){
                        sql += " and pa.status = ? ";
                        params.add(status);
                    }
                    sql += " order by pa.create_time desc";

                    return Db.use("DBPublic").find(sql, params.toArray());
                }
        });
        if(F.validate.isEmpty(_ids)) {
            _ids = new ArrayList<Record>();
        }
        //分页计算
        PageKit<Record> page = new PageKit<Record>(pageIn.getNowPage(),_ids, pageIn.getRowsPerPage());
        java.util.List<Record> selectIds = page.calcPagelist(_ids);
        return Pair.of(selectIds,page);
    }

    /**
     * @Title:
     * @Description: 根据主键查询政策文章
     * @author: DukeMr.Chen
     */
    public PolicyArticle findByPaId(final String pa_id) {
        PolicyArticle data = RedisClient.getClient().get(F.strKit.f(AdminPolicyArticleEnum.CK_Phjf_Model_PolicyArticle_KEY2.getKey(), pa_id)
                , AdminPolicyArticleEnum.CK_Phjf_Model_PolicyArticle_KEY2.getTime()
                , PolicyArticle.class
                , new DataLoader(){
                public Object load() throws Exception{
                    return PolicyArticle.dao.findById(pa_id);
                }
        });
        if(F.validate.isEmpty(data)) {
            data = new PolicyArticle();
        }
        return data;
    }

    /**
     * @Title:
     * @Description: 是否轮播
     * @author: DukeMr.Chen
     */
    public String getIsBannerShow(String language) {
        SysDatalibrary sysData = SysDatalibrary.dao.findSysDatalibrary(language, AdminConstants.Datalibrary_SYS_isBanner, this.getIs_banner());
        return F.validate.isEmpty(sysData) ? "" : sysData.getName();
    }

    /**
     * @Title:
     * @Description: 是否置顶
     * @author: DukeMr.Chen
     */
    public String getIsTopShow(String language) {
        SysDatalibrary sysData = SysDatalibrary.dao.findSysDatalibrary(language, AdminConstants.Datalibrary_SYS_yesNo, this.getIs_top());
        return F.validate.isEmpty(sysData) ? "" : sysData.getName();
    }

    /**
     * @Title:
     * @Description: 是否推荐
     * @author: DukeMr.Chen
     */
    public String getIsRecomShow(String language) {
        SysDatalibrary sysData = SysDatalibrary.dao.findSysDatalibrary(language, AdminConstants.Datalibrary_SYS_isRecom, this.getIs_recom());
        return F.validate.isEmpty(sysData) ? "" : sysData.getName();
    }

    /**
     * @Title:
     * @Description: 是否显示
     * @author: DukeMr.Chen
     */
    public String getIsShowName(String language) {
        SysDatalibrary sysData = SysDatalibrary.dao.findSysDatalibrary(language, AdminConstants.Datalibrary_SYS_isShow, this.getIs_show());
        return F.validate.isEmpty(sysData) ? "" : sysData.getName();
    }

    /**
     * @Title:
     * @Description: 状态
     * @author: DukeMr.Chen
     */
    public String getStatusShow(String language) {
        SysDatalibrary sysData = SysDatalibrary.dao.findSysDatalibrary(language, AdminConstants.Datalibrary_SYS_articleStatus, this.getStatus());
        return F.validate.isEmpty(sysData) ? "" : sysData.getName();
    }

    /**
     * @Title:
     * @Description: 根据标题查询文章
     * @author: DukeMr.Chen
     */
    public PolicyArticle findByArticleTitle(String title) {
        String sql = "select * " +
                "       from phjf_policy_article " +
                "      where title = ? " ;
        PolicyArticle data = PolicyArticle.dao.use("DBPublic").findFirst(sql, title);
        if(F.validate.isEmpty(data)){
            data = new PolicyArticle();
        }
        return data;
    }

    /**
     * @Title:
     * @Description: 保存或更新
     * @author: DukeMr.Chen
     */
    public PolicyArticle saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
        this.checkModelItem();
        if (F.validate.isNotEmpty(this.getPa_id()+"")&&!(this.getPa_id().equals(0))){
            this.setModify_opt(opt.getSeq_id()+"");
            this.setModify_time(new Date());
            if(F.validate.isEmpty(this.getIs_show())){
                this.setIs_show("Y");
            }
            this.ridNull();
            this.update();
        } else {
            if (F.validate.isEmpty(this.getArticle_code())){
                this.setArticle_code(SysSeq.dao.generatorPolicyCode());
            }
            if(F.validate.isEmpty(this.getIs_recom())){
                this.setIs_recom("N");
            }
            if(F.validate.isEmpty(this.getIs_top())){
                this.setIs_top("N");
            }
            if(F.validate.isEmpty(this.getIs_banner())){
                this.setIs_banner("N");
            }
            this.setSeq_uuid(UUID.randomUUID().toString());
            this.setIs_show("Y");
            this.setStatus("1");
            this.setCreate_opt(opt.getSeq_id()+"");
            this.setCreate_time(new Date());
            this.setModify_opt(opt.getSeq_id()+"");
            this.setModify_time(new Date());
            this.ridNull();
            this.save();
        }
        flashCatch(this);
        return this;
    }

    public void checkModelItem() {
        this.checkTitle("标题");
        this.checkLogo("图标");
        this.checkSource("政策来源");
        this.checkSource_url("政策来源链接");
        this.checkContent("内容");
        this.checkPublish_date("发布日期");
        this.checkAuto_publish_date("自动发布日期");
        this.checkBanner_logo("轮播图片");
        this.checkProvince("省份");
        this.checkCity("城市");
        this.checkArea("地区");
    }

    public void ridNull(){
        BeanUtilsBean bean = BeanUtilsBean.getInstance();
        PropertyDescriptor[] origDescriptors = bean.getPropertyUtils().getPropertyDescriptors(this);
        for(int i = 0; i < origDescriptors.length; ++i) {
            String name = origDescriptors[i].getName();
            Class T = origDescriptors[i].getPropertyType();
            if (!"class".equals(name) && bean.getPropertyUtils().isReadable(this, name) ) try {
                Object value = bean.getPropertyUtils().getSimpleProperty(this, name);
                if (value == null) {
                    if (Date.class.equals(T)){
                        bean.copyProperty(this, name, new Date());
                    }else if (String.class.equals(T)) {
                        bean.copyProperty(this, name, "");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @Title:
     * @Description: 获取文章所属板块列表
     * @author: DukeMr.Chen
     */
    public String queryPlateListShow(String language) {
        String plateList = "";
        List<PolicyArticlePlate> paps = PolicyArticlePlate.dao.findByArticleCode(this.getArticle_code());
        if(paps.size()>0) {
            Integer i = 0;
            String tag = "";
            for (PolicyArticlePlate pap : paps){
                i++;
                if(i.equals(1)) {
                    tag ="";
                }else{
                    tag = ",";
                }
                PolicyPlate pp = PolicyPlate.dao.findPolicyPlate(pap.getPlate_code(),language);
                plateList = plateList+tag+pp.getName();
            }
        }
        return plateList;
    }
}
