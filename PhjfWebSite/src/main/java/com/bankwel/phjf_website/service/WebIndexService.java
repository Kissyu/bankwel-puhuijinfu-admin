package com.bankwel.phjf_website.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.phjf_website.common.model.website.WebLinks;
import com.bankwel.phjf_website.common.model.website.WebNav;
import com.bankwel.phjf_website.common.model.website.WebNewsInfo;
import com.bankwel.phjf_website.common.model.website.WebPartner;
import com.bankwel.phjf_website.common.utils.ConstStr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bankwel on 2018/1/5.
 */
public class WebIndexService {

    /**
     * 获取首页的各栏目详细信息
     * @param language
     * @return
     * @throws MsgBusinessException
     */
    public static Map queryIndexInfo(String language)throws MsgBusinessException {
        Map result = F.transKit.asMap();
        //获取顶部导航
        List<Map> topNavList = new ArrayList<Map>();
        List<WebNav> _topNavList = WebNav.dao.queryTopNavs(language,"0");
        if(F.validate.isNotEmpty(_topNavList)){
            for (WebNav topNav: _topNavList) {
                List<Map> subTopNavs = new ArrayList<Map>();
                List<WebNav> webNav1 = WebNav.dao.queryTopNavs(language,topNav.getNav_code());
                for (WebNav subNav:webNav1) {
                    subTopNavs.add(F.transKit.asMap("nav_code",subNav.getNav_code(),"nav_name",subNav.getNav_name(),"url",subNav.getUrl()));
                }
                topNavList.add(F.transKit.asMap("nav_name",topNav.getNav_name()
                        ,"url",topNav.getUrl()
                        ,"nav_code",topNav.getNav_code()
                        ,"subNavList",subTopNavs));
            }
        }
        result.put("topNavs",topNavList);
        //获取合作伙伴
        List<Map> partnerList = new ArrayList<Map>();
        List<WebPartner> webPartners = WebPartner.dao.queryPartnerList(language);
        for(WebPartner webPartner:webPartners) {
            partnerList.add(F.transKit.asMap("partner_name",webPartner.getPartner_name(),"picture",webPartner.getPicture()));
        }
        result.put("partners",partnerList);
        //友情链接
        List<Map> linksList = new ArrayList<Map>();
        List<WebLinks> webLinkses = WebLinks.dao.queryLinksList(language);
        for(WebLinks link:webLinkses) {
            linksList.add(F.transKit.asMap("link_name",link.getLinks_name(),"url",link.getUrl()));
        }
        result.put("links",linksList);
        return result;
    }
}
