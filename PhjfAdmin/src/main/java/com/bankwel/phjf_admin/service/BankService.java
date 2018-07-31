package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.BankInfo;
import com.bankwel.phjf_admin.common.model.core.BankType;
import com.bankwel.phjf_admin.webapi.vo.BankTypeVO;

import java.util.Map;

/**
 * Created by admin on 2017/11/8.
 */
public class BankService {

    /**
     * 新增银行类型
     * @param opt
     * @param vo
     * @return
     */
    public BankType addBankType(AuthOperator opt, BankTypeVO vo){
        if (F.validate.isEmpty(vo.getName())){
            throw new MsgBusinessException("名称不能为空");
        }
        BankType bankType = new BankType();
        F.transKit.copyProperties(bankType, vo);
        bankType.saveOrUpdate(opt);
        return bankType;
    }


    /**
     * 通过银行代码获取银行
     * @param bank_code
     * @param language
     * @return
     */
    public Map findBankByCode(String bank_code, String language){
        BankInfo bankInfo = BankInfo.dao.findByCode(bank_code, language);
        Map map = F.transKit.asMap("seq_uuid", bankInfo.getSeq_uuid()
                ,"name", bankInfo.getName()
                ,"bt_code", bankInfo.getBt_code()
                ,"contact", bankInfo.getContact());
        return map;
    }

    /**
     * 通过银行类型代码获取银行类型
     * @param bt_code
     * @param language
     * @return
     */
    public Map findBankTypeByCode(String bt_code, String language){
        BankType bankType = BankType.dao.findByCode(bt_code, language);
        Map map = F.transKit.asMap("seq_uuid", bankType.getSeq_uuid()
                ,"name", bankType.getName());
        return map;
    }

}
