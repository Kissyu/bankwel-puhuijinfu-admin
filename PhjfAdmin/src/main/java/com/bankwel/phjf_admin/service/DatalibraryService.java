package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
public class DatalibraryService {

    /**
     * 根据类型获取字典
     * @param sys_id
     * @param parentCode
     * @return
     */
    public List<Map> queryByParentCode(String sys_id, String parentCode){
        List<SysDatalibrary> datalibraryList = SysDatalibrary.dao.querySysDatalibrary(sys_id,parentCode);
        List<Map> dataList = new ArrayList<Map>();
        for (SysDatalibrary lib : datalibraryList){
            dataList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return dataList;
    }
}
