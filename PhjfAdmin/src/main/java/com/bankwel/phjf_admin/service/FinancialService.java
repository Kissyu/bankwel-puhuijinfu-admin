package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.model.core.*;
import com.bankwel.phjf_admin.webapi.vo.FinancialProductVO;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/17.
 */
public class FinancialService {

    /**
     * @Title:
     * @Description: 修改理财产品信息
     * @author: DukeMr.Chen
     */
    @Before(TTx.class)
    public FinancialProduct modifyFinancialProduct(AuthOperator opt, FinancialProductVO vo) throws MsgBusinessException {
        FinancialProduct model = FinancialProduct.dao.findById(vo.getSeq_uuid());
        model.setIs_recom(vo.getIs_recom());
        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 理财产品信息 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryFPSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysFpTypeList = SysDatalibrary.dao.querySysDatalibrary("phjf","fpType");
        List<Map> fpTypeList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysFpTypeList){
            fpTypeList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysRiskLevelList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_FinRiskLevel");
        List<Map> riskLevelList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysRiskLevelList){
            riskLevelList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
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
        return F.transKit.asMap("fpTypeList",fpTypeList,"riskLevelList",riskLevelList,"statusList",statusList,"languageList",languageList);
    }

    /**
     * 理财产品信息 - 理财产品信息列表
     * @param name
     * @param risk_level
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryFPByPage(String name,String risk_level,String language,String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = FinancialProduct.dao.queryFpByPage(name, risk_level,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 理财产品信息 - 通过ID查找理财产品
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findFpById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("理财产品编号不能为空");
        }
        FinancialProduct model = FinancialProduct.dao.findById(seq_uuid);
        Map data = outputFp(model);
        return data;
    }

    /**
     * 理财产品信息 - 输出理财产品信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputFp(FinancialProduct model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "fp_code",model.getFp_code()
                ,"seq_uuid",model.getSeq_uuid()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"fp_type",model.getFp_type()
                ,"bt_code",model.getBt_code()
                ,"bt_name",model.findBank_name()
                ,"name",model.getName()
                ,"third_code",model.getThird_code()
                ,"currency",model.getCurrency()
                ,"open_type",model.getOpen_type()
                ,"risk_level",model.getRisk_level()
                ,"inverate_expect_min",model.getInverate_expect_min()
                ,"inverate_expect_max",model.getInverate_expect_max()
                ,"inverate_actual",model.getInverate_actual()
                ,"amount_acc_min",model.getAmount_acc_min()
                ,"amount_acc_max",model.getAmount_acc_max()
                ,"amount_redeem_min",model.getAmount_redeem_min()
                ,"amount_redeem_max",model.getAmount_redeem_max()
                ,"amount_total_min",model.getAmount_total_min()
                ,"amount_total_max",model.getAmount_total_max()
                ,"raise_start_date",model.getRaise_start_date()
                ,"raise_end_date",model.getRaise_end_date()
                ,"start_date",model.getStart_date()
                ,"end_date",model.getEnd_date()
                ,"expire_end_date",model.getExpire_end_date()
                ,"third_status",model.getThird_status()
                ,"remark",model.getRemark()
                ,"share_count",model.getShare_count()
                ,"click_count",model.getClick_count()
                ,"buy_count",model.getBuy_count()
                ,"order_num",model.getOrder_num()
                ,"publish_date",model.getPublish_date()
                ,"under_date",model.getUnder_date()
                ,"remark",model.getRemark()
                ,"is_recom",model.getIs_recom()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus());
        return data;
    }
    /**
     * 理财产品信息 - 下架
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void unPublishFP(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("理财产品编号不能为空");
        }
        FinancialProduct financialProduct = FinancialProduct.dao.findById(seq_uuid);
        List<FinancialProduct> FpList = FinancialProduct.dao.findByFpCode(financialProduct.getFp_code());
        for (FinancialProduct fp : FpList){
            fp.setStatus("3");
            fp.saveOrUpdate(opt);
        }

    }

    /**
     * 货币基金产品信息 - 货币基金产品信息列表
     * @param title
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryFinancialFundByPage(String title,String language,String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = FinancialFund.dao.queryFinancialFundByPage(title,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 货币基金产品信息 - 通过ID查找理财产品
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findFinancialFundById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("理财产品编号不能为空");
        }
        FinancialFund model = FinancialFund.dao.findById(seq_uuid);
        Map data = outputFinancialFund(model);
        return data;
    }

    /**
     * 货币基金产品信息 - 输出货币基金产品信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputFinancialFund(FinancialFund model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "fp_code",model.getFp_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"fp_type",model.getFp_type()
                ,"bt_code",model.getBt_code()
                ,"bt_name",model.findBank_name()
                ,"title",model.getTitle()
                ,"fin_code",model.getFin_code()
                ,"currency",model.getCurrency()
                ,"fin_nav",model.getFin_nav()
                ,"fin_nav_total",model.getFin_nav_total()
                ,"fin_income",model.getFin_income()
                ,"fin_income_unit",model.getFin_income_unit()
                ,"fin_income_rate",model.getFin_income_rate()
                ,"amount_min",model.getAmount_min()
                ,"remark",model.getRemark()
                ,"share_count",model.getShare_count()
                ,"click_count",model.getClick_count()
                ,"buy_count",model.getBuy_count()
                ,"order_num",model.getOrder_num()
                ,"publish_date",model.getPublish_date()
                ,"under_date",model.getUnder_date()
                ,"download_time",model.getDownload_time()
                ,"is_recom",model.getIs_recom()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus());
        return data;
    }
    /**
     * 货币基金产品信息 - 下架
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void unPublishFinancialFund(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("理财产品编号不能为空");
        }
        FinancialFund financialProduct = FinancialFund.dao.findById(seq_uuid);
        List<FinancialFund> FfList = FinancialFund.dao.findByFfCode(financialProduct.getFp_code());
        for (FinancialFund ff : FfList){
            ff.setIs_show("N");
            ff.setStatus("3");
            ff.saveOrUpdate(opt);
        }

    }
}
