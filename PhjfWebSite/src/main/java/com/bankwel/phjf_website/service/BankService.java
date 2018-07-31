package com.bankwel.phjf_website.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.GeoHashKit;
import com.bankwel.phjf_website.common.model.website.BankAtm;
import com.bankwel.phjf_website.common.model.website.BankInfo;
import com.bankwel.phjf_website.common.model.website.BankType;
import com.bankwel.phjf_website.common.model.website.ManagepointInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/12/2.
 */
public class BankService {
    /**
     * 银行类型列表
     * @return
     * @throws MsgBusinessException
     */
    public List<Map> queryBankTypeList() throws MsgBusinessException {
        List<BankType> list = BankType.dao.queryBankTypeList();
        List<Map> resultList = new ArrayList<Map>();
        Map map = null;
        for (int i = 0; i < list.size(); i++){
            map = F.transKit.asMap("bt_code", list.get(i).getBt_code()
                    ,"bt_name", list.get(i).getName());
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 根据银行类型编码获取银行机构列表
     * @return
     * @throws MsgBusinessException
     */
    public List<Map> queryBankList(String bt_code, String lat, String lng){
        if (F.validate.isEmpty(lat)){
            throw new MsgBusinessException("纬度不能为空");
        }if (F.validate.isEmpty(lng)){
            throw new MsgBusinessException("经度不能为空");
        }
        String geohash = GeoHashKit.encode(Double.parseDouble(lat), Double.parseDouble(lng), 4);
        List<BankInfo> list = BankInfo.dao.queryBankList(bt_code, geohash,lat,lng);
        List<Map> resultList = new ArrayList<Map>();
        Map map = null;
        for (int i = 0; i < list.size(); i++){
            map = F.transKit.asMap("lat", list.get(i).getLat()
                    ,"lng", list.get(i).getLng()
                    ,"name", list.get(i).getName());
            resultList.add(map);
        }
        return resultList;
    }


    /**
     * 根据银行类型编码获取银行ATM列表
     * @return
     * @throws MsgBusinessException
     */
    public List<Map> queryBankAtmList(String bt_code, String lat, String lng){
        if (F.validate.isEmpty(lat)){
            throw new MsgBusinessException("纬度不能为空");
        }if (F.validate.isEmpty(lng)){
            throw new MsgBusinessException("经度不能为空");
        }
        String geohash = GeoHashKit.encode(Double.parseDouble(lat), Double.parseDouble(lng), 4);
        List<BankAtm> list = BankAtm.dao.queryBankAtmListAllName(bt_code, geohash,lat,lng);
        List<Map> resultList = new ArrayList<Map>();
        Map map = null;
        for (int i = 0; i < list.size(); i++){
            map = F.transKit.asMap("lat", list.get(i).getLat()
                    ,"lng", list.get(i).getLng()
                    ,"name", list.get(i).getName()
                    ,"telephone", list.get(i).getTelephone()
                    ,"address", list.get(i).getAddress());
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 根据银行类型编码获取银行网点列表
     * @return
     * @throws MsgBusinessException
     */
    public List<Map> queryBankInfoList(String bt_code, String lat, String lng){
        if (F.validate.isEmpty(lat)){
            throw new MsgBusinessException("纬度不能为空");
        }if (F.validate.isEmpty(lng)){
            throw new MsgBusinessException("经度不能为空");
        }
        String geohash = GeoHashKit.encode(Double.parseDouble(lat), Double.parseDouble(lng), 4);
        List<BankInfo> list = BankInfo.dao.queryBankListAllName(bt_code, geohash,lat,lng);
        List<Map> resultList = new ArrayList<Map>();
        Map map = null;
        for (int i = 0; i < list.size(); i++){
            map = F.transKit.asMap("lat", list.get(i).getLat()
                    ,"lng", list.get(i).getLng()
                    ,"name", list.get(i).getName()
                    ,"telephone", list.get(i).getTelephone()
                    ,"address", list.get(i).getAddress());
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 根据银行类型编码获取银行网点列表
     * @return
     * @throws MsgBusinessException
     */
    public List<Map> querymanagerPointList(String lat, String lng){
        if (F.validate.isEmpty(lat)){
            throw new MsgBusinessException("纬度不能为空");
        }if (F.validate.isEmpty(lng)){
            throw new MsgBusinessException("经度不能为空");
        }
        String geohash = GeoHashKit.encode(Double.parseDouble(lat), Double.parseDouble(lng), 4);
        List<ManagepointInfo> list = ManagepointInfo.dao.queryManagepointInfoList(geohash,lat,lng);
        List<Map> resultList = new ArrayList<Map>();
        Map map = null;
        for (int i = 0; i < list.size(); i++){
            map = F.transKit.asMap("lat", list.get(i).getLat()
                    ,"lng", list.get(i).getLng()
                    ,"name", list.get(i).getName()
                    ,"telephone", list.get(i).getTelephone()
                    ,"address", list.get(i).getAddress());
            resultList.add(map);
        }
        return resultList;
    }
}
