package com.bankwel.phjf_website.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.phjf_website.common.model.website.NewsArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bankwel on 2016/11/8.
 */
public class NewsService {

    /**
     * 获取新闻资讯详情
     * @return
     */
    public static Map findArticle(String article_code,String language ) throws MsgBusinessException {
        Map result = F.transKit.asMap();
        if(F.validate.isEmpty(article_code)){
            throw  new MsgBusinessException("资讯CODE为空");
        }
        NewsArticle article = NewsArticle.dao.findByCode(article_code,language);
        if (F.validate.isNotEmpty(article)) {
            result.put("content",F.validate.isEmpty(article.getContent())?"":article.getContent());
            result.put("title",article.getTitle());
            result.put("source",F.validate.isEmpty(article.getSource())?"":article.getSource());
            result.put("publish_date",F.validate.isEmpty(article.getPublish_date())?"":article.getPublish_date());
        }
        return result;
    }

}
