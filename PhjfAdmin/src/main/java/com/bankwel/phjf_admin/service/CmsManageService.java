package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.support.jfinal.TTx;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.BannerInfo;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.webapi.vo.BannerInfoVO;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14.
 */
public class CmsManageService {
    /**
     * 广告位管理 - 获取查询项的数据列表
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBannerSearchList() throws MsgBusinessException{
        List<SysDatalibrary> sysLocationList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_location");
        List<Map> locationList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLocationList){
            locationList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_language");
        List<Map> languageList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysLanguageList){
            languageList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("phjf","sys_status");
        List<Map> statusList = new ArrayList<Map>();
        for (SysDatalibrary lib : sysStatusList){
            statusList.add(F.transKit.asMap("code",lib.getCode(),"name",lib.getName()));
        }
        return F.transKit.asMap("statusList",statusList,"languageList",languageList,"locationList",locationList);
    }

    /**
     * 广告位管理 - 获取广告列表
     * @param location
     * @param language
     * @param status
     * @param page
     * @return
     * @throws MsgBusinessException
     */
    public Map queryBannerByPage(String location, String language, String status, PageKit page) throws MsgBusinessException {
        Pair<List<Record>, PageKit<Record>> pair = BannerInfo.dao.queryBannerByPage(location,language,status, page);
        return F.transKit.asMap("rows", pair.getLeft(), "total", pair.getRight().getTotal());
    }

    /**
     * 广告位管理 - 添加广告位
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BannerInfo addBanner(AuthOperator opt, BannerInfoVO vo) throws MsgBusinessException {
        if(F.validate.isEmpty(vo.getBanner_id()+"")) {
            checkBannerModel(vo);
            BannerInfo model = new BannerInfo();
            F.transKit.copyProperties(model, vo);
            if("app".equals(vo.getOpen_type())){
                model.setParams(vo.getParams());
                model.setHttp_url("");
            }else if("http".equals(vo.getOpen_type())){
                model.setHttp_url(vo.getHttp_url());
                model.setParams("");
            }
            model.saveOrUpdate(opt);
            return model;
        }else {
            return this.modifyBanner(opt,vo);
        }
    }

    /**
     * 广告位管理 - 获取维语广告位
     * @param seq_uuid
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public Map findUeyBanner(String seq_uuid, String language) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("广告位编号不能为空");
        }
        BannerInfo bannerInfo = BannerInfo.dao.findById(seq_uuid);
        BannerInfo UeybannerInfo = BannerInfo.dao.findBannerInfo(bannerInfo.getBanner_code(),language);
        if(F.validate.isEmpty(UeybannerInfo.getBanner_id())){
            bannerInfo.setSeq_uuid(null);
            bannerInfo.setBanner_id(null);
            bannerInfo.setLanguage(language);
            return outputBannerInfo(bannerInfo);
        }
        return outputBannerInfo(UeybannerInfo);
    }

    /**
     * 广告位管理 - 修改广告位
     * @param opt
     * @param vo
     * @return
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public BannerInfo modifyBanner(AuthOperator opt, BannerInfoVO vo) throws MsgBusinessException {
        checkBannerModel(vo);
        BannerInfo model = BannerInfo.dao.findById(vo.getSeq_uuid());
        model.setLocation(vo.getLocation());
        model.setContent_type(vo.getContent_type());
        model.setOpen_type(vo.getOpen_type());
        if("app".equals(vo.getOpen_type())){
            model.setParams(vo.getParams());
            model.setHttp_url("");
        }else if("http".equals(vo.getOpen_type())){
            model.setHttp_url(vo.getHttp_url());
            model.setParams("");
        }
        model.setImg_url(vo.getImg_url());
        model.setOrder_num(vo.getOrder_num());
        model.setRemark(vo.getRemark());
        model.setIs_show(vo.getIs_show());

        model.saveOrUpdate(opt);
        return model;
    }

    /**
     * 广告位管理 - 删除广告位
     * @param opt
     * @param seq_uuid
     * @throws MsgBusinessException
     */
    @Before(TTx.class)
    public void deleteBanner(AuthOperator opt, String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("广告位编号不能为空");
        }
        BannerInfo bannerInfo = BannerInfo.dao.findById(seq_uuid);
        List<BannerInfo> bannerList = BannerInfo.dao.findByBannerCode(bannerInfo.getBanner_code());
        for (BannerInfo banner : bannerList){
            banner.setStatus("4");
            banner.setIs_show("N");
            banner.saveOrUpdate(opt);
        }
    }

    /**
     * 广告位管理 - 查找广告位
     * @param seq_uuid
     * @return
     * @throws MsgBusinessException
     */
    public Map findBannerById(String seq_uuid) throws MsgBusinessException {
        if(F.validate.isEmpty(seq_uuid)){
            throw new MsgBusinessException("广告位编号不能为空");
        }
        BannerInfo model = BannerInfo.dao.findById(seq_uuid);
        Map data = outputBannerInfo(model);
        return data;
    }

    /**
     * 广告位管理 - 输出广告位管理信息
     * @param model
     * @return
     * @throws MsgBusinessException
     */
    public Map outputBannerInfo(BannerInfo model) throws MsgBusinessException {
        Map data = F.transKit.asMap(
                "seq_uuid",model.getSeq_uuid()
                ,"banner_id",model.getBanner_id()
                ,"banner_code",model.getBanner_code()
                ,"language",model.getLanguage()
                ,"language_show",model.findLanguage_show()
                ,"location",model.getLocation()
                ,"content_type",model.getContent_type()
                ,"open_type",model.getOpen_type()
                ,"img_url",model.getImg_url()
                ,"http_url",model.getHttp_url()
                ,"params",model.getParams()
                ,"click_count",model.getClick_count()
                ,"order_num",model.getOrder_num()
                ,"remark",model.getRemark()
                ,"is_show",model.getIs_show()
                ,"status",model.getStatus()
                ,"create_time",model.getCreate_time());
        return data;
    }

    /**
     * 广告位管理 - 检查输入完整性
     * @param vo
     * @throws MsgBusinessException
     */
    public void checkBannerModel(BannerInfoVO vo) throws MsgBusinessException {
        if (F.validate.isEmpty(vo.getLocation())){
            throw new MsgBusinessException("展现位置不能为空");
        }
        if (F.validate.isEmpty(vo.getContent_type())){
            throw new MsgBusinessException("内容类型不能为空");
        }
        if (F.validate.isEmpty(vo.getOpen_type())){
            throw new MsgBusinessException("打开类型不能为空");
        }
//        if("app".equals(vo.getOpen_type())&&F.validate.isEmpty(vo.getParams())){
//            throw new MsgBusinessException("移动端链接地址不能为空");
//        }
//        if("http".equals(vo.getOpen_type())&&F.validate.isEmpty(vo.getHttp_url())){
//            throw new MsgBusinessException("网页端链接地址不能为空");
//        }
        if(F.validate.isEmpty(vo.getImg_url())){
            throw new MsgBusinessException("图片不能为空");
        }
    }
}
