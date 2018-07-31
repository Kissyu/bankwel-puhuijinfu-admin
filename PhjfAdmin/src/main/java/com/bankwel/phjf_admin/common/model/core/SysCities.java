package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.base.BaseSysCities;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.DynamicSelectData;
import com.bankwel.phjf_admin.component.c13webtag.bootstrap.IDynamicSelectData;
import com.bankwel.phjf_baseModel.common.model.phjf.CacheSysCities;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysCities extends CacheSysCities<SysCities> {
	public static final SysCities dao = new SysCities().dao();

	public List<IDynamicSelectData> querySelectData(String province_id){
		String sql = "select * " +
				"       from sys_cities " +
				"      where is_show = 'Y' " +
				"        and province_id = ? ";
		List<SysCities> list = dao.use("DBPublic").find(sql, province_id);
		List<IDynamicSelectData> selectData = new ArrayList<IDynamicSelectData>();
		for (SysCities city : list){
			IDynamicSelectData sd = new DynamicSelectData();
			sd.setId(city.getCity_id());
			sd.setName(city.getCity());
			selectData.add(sd);
		}
		return selectData;
	}

	public SysCities findCityById(String city_id){
		String sql = "select * " +
				"       from sys_cities " +
				"      where city_id = ? " +
				"        and status = 1 ";
		SysCities city = dao.use("DBPublic").findFirst(sql, city_id);
		if(F.validate.isEmpty(city)){
			city = new SysCities();
		}
		return city;
	}
}