package com.bankwel.phjf_admin.service;

import com.bankwel.phjf_admin.common.model.core.SysCities;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;

import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */
public class CitiesService {

    /**
     * 根据省ID获取城市信息
     * @param province_id
     * @return
     */
    public List<IDynamicSelectData> querySelectData(String province_id){
        return SysCities.dao.querySelectData(province_id);
    }
}
