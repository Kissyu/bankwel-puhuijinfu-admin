package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.JFinalDbKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.DynamicSelectData;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;
import com.bankwel.phjf_admin.webapi.vo.NewsPlateVO;
import com.bankwel.phjf_admin.webapi.vo.PolicyPlateVO;
import com.bankwel.phjf_baseModel.common.model.phjf.CachePolicyPlate;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.ApiNewsPlateEnum;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class PolicyPlate extends CachePolicyPlate<PolicyPlate>{
    public static final PolicyPlate dao = new PolicyPlate().dao();

    /**
     * 新增或修改惠民政策版块
     * @param opt
     * @return
     */
    public PolicyPlate saveOrUpdate(AuthOperator opt) throws MsgBusinessException{
        this.checkModelItem();
        if (F.validate.isNotEmpty(this.getPp_id()+"")&&!(this.getPp_id().equals(0))){
            this.setModify_opt(opt.getSeq_id()+"");
            this.setModify_time(new Date());
            if(F.validate.isEmpty(this.getIs_show())){
                this.setIs_show("Y");
            }
            this.update();
        } else {
            if (F.validate.isEmpty(this.getPlate_code())){
                this.setPlate_code(SysSeq.dao.generatorPolicyPlateCode());
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
        String key1 =F.strKit.f(ApiNewsPlateEnum.CK_Phjf_Model_NewsPlate_KEY1.getKey(),this.getLanguage());
        flashCatch(this);
        return this;
    }

    public void checkModelItem() {
        this.checkName("板块名称");
//        this.checkOrder_num("板块序号");
        this.checkRemark("板块备注");
    }

    public List<IDynamicSelectData> querySelectData(String language){
        String sql = "select * from phjf_policy_plate where is_show = 'Y' and status = '1' and  language = ?";
        List<PolicyPlate> list = dao.use("DBPublic").find(sql, language);
        List<IDynamicSelectData> selectData = new ArrayList<IDynamicSelectData>();
        for (PolicyPlate item : list){
            IDynamicSelectData sd = new DynamicSelectData();
            sd.setId(item.getPlate_code());
            sd.setName(item.getName());
            selectData.add(sd);
        }
        return selectData;
    }
    /**
     * 获取惠民政策版块列表
     * @param name
     * @param language
     * @param status
     * @param page
     * @return
     */
    public Pair<List<Record>,PageKit<Record>> queryPolicyPlateByPage(String name, String language, String status, String province, PageKit page){
        String sql = "select plate.seq_uuid," +
                "            plate.pp_id," +
                "            plate.plate_code," +
                "            plate.language," +
                "            plate.name," +
                "            plate.logo," +
                "            ppc.order_num order_num," +
                "            plate.remark," +
                "            plate.status," +
                "            plate.create_time," +
                "            status.name status_show," +
                "            is_show.name is_show_name," +
                "            province.province_id province_id," +
                "            province.province province_name," +
                "            opt.operate_name create_opt_name" +
                "       from phjf_policy_plate plate" +
                "            left join phjf_policy_plate_city ppc on ppc.plate_code = plate.plate_code" +
                "            left join sys_datalibrary status on status.parent_code = 'sys_status' and status.code = plate.status" +
                "            left join sys_datalibrary is_show on is_show.parent_code = 'sys_isShow' and is_show.code = plate.is_show" +
                "            left join sys_provinces province on province.province_id = ppc.province_id" +
                "            left join auth_operator opt on opt.seq_id = plate.create_opt" +
                "      where 1=1";
        List params = new ArrayList();
        if (F.validate.isNotEmpty(name)){
            sql += " and (plate.name = ? or plate.plate_code = ?)";
            params.add(name);
        }
        if (F.validate.isNotEmpty(language)){
            sql += " and plate.language = ? ";
            params.add(language);
        }
        if (F.validate.isNotEmpty(status)){
            sql += " and plate.status = ? ";
            params.add(status);
        }
        if (F.validate.isNotEmpty(province)){
            sql += " and ppc.province_id = ? ";
            params.add(province);
        }
        sql += " order by ppc.order_num asc, ppc.province_id asc, plate.plate_code asc";
        return JFinalDbKit.paginate(Db.use("DBPublic"), page.getNowPage(), page.getRowsPerPage(), sql, params.toArray());
    }

    /**
     *通过版块名称获取惠民政策版块
     * @param name
     * @return
     */
    public PolicyPlate findByPlateName(String name){
        String sql = "select * " +
                "       from phjf_policy_plate " +
                "      where name = ? " +
                "        and status = 1 ";
        PolicyPlate data = PolicyPlate.dao.use("DBPublic").findFirst(sql,name);
        if(F.validate.isEmpty(data)){
            data = new PolicyPlate();
        }
        return data;
    }

    /**
     * 通过ID获取惠民政策版块
     * @param seq_uuid
     * @return
     */
    public PolicyPlate findById(String seq_uuid){
        String sql = "select * " +
                "       from phjf_policy_plate " +
                "      where seq_uuid = ? " +
                "      limit 1";
        PolicyPlate data = PolicyPlate.dao.use("DBPublic").findFirst(sql,seq_uuid);
        if(F.validate.isEmpty(data)){
            data = new PolicyPlate();
        }
        return data;
    }
    /**
     * 修改前判断该惠民政策版块名称是否存在
     * @param seq_uuid
     * @param name
     * @return
     */
    public Boolean isHavePlate(String seq_uuid,String name){
        String sql = "select * " +
                "       from phjf_policy_plate " +
                "      where seq_uuid !=? " +
                "        and name = ?" +
                "        and status = 1 ";
        PolicyPlate data = PolicyPlate.dao.use("DBPublic").findFirst(sql,seq_uuid,name);
        if(F.validate.isEmpty(data)){
            return false;
        }
        return true;
    }
    /**
     *通过plate_code获取惠民政策版块
     * @param plate_code
     * @return
     */
    public PolicyPlate findByPlateCode(String plate_code){
        String sql = "select * " +
                "       from phjf_policy_plate " +
                "      where plate_code = ? " +
                "        and status = 1 " +
                "        and is_show = 'Y' ";
        PolicyPlate data = PolicyPlate.dao.use("DBPublic").findFirst(sql,plate_code);
        if(F.validate.isEmpty(data)){
            data = new PolicyPlate();
        }
        return data;
    }
    /**
     * 获取新闻版块
     * @param plate_code
     * @param language
     * @return
     */
    public PolicyPlate findPolicyPlate(String plate_code, String language) {
        String sql = "select * " +
                "       from phjf_policy_plate " +
                "      where plate_code = ? " +
                "        and language = ? " +
                "        and status = 1 " +
                "      limit 1 ";
        PolicyPlate data = PolicyPlate.dao.use("DBPublic").findFirst(sql, plate_code, language);
        if(F.validate.isEmpty(data)){
            data = new PolicyPlate();
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
}
