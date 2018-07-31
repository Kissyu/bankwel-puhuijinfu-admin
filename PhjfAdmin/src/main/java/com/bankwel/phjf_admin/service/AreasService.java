package com.bankwel.phjf_admin.service;

import com.bankwel.phjf_admin.common.model.core.SysAreas;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;

import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */
public class AreasService {

    /**
     * 根据城市ID获取区信息
     * @param city_id
     * @return
     */
    public List<IDynamicSelectData> querySelectData(String city_id){
        return SysAreas.dao.querySelectData(city_id);
    }
}
