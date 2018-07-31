package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.phjf_admin.webapi.vo.PolicyArticleVO;
import com.bankwel.phjf_baseModel.common.model.phjf.CachePolicyArticleSearch;
import com.jfinal.plugin.activerecord.Db;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PolicyArticleSearch extends CachePolicyArticleSearch<PolicyArticleSearch>{
    public final static PolicyArticleSearch dao = new PolicyArticleSearch().dao();

    public void addBatch(AuthOperator opt, List<PolicyArticleSearch> dataList) {
        String sql = "insert into phjf_policy_article_search(seq_uuid, article_code,search_code) values(?, ?, ?)";
        int[] result = Db.use("DBPublic").batch(sql, "seq_uuid,article_code,search_code", dataList, 500);
        //flashCatch(this);
    }

    public void deleteByArticleCode(String article_code) {
        String sql = "DELETE FROM  phjf_policy_article_search WHERE article_code = ?";
        Db.use("DBPublic").update(sql, article_code);
        //flashCatch(this);
    }

    public List<PolicyArticleSearch> findByArticleCode(String article_code) {
        String sql = "Select * FROM  phjf_policy_article_search WHERE article_code = ?";
       return PolicyArticleSearch.dao.find(sql, article_code);
    }

    public void dealWithRelation(AuthOperator opt, String articleCode, PolicyArticleVO vo) {
        String search_terms = vo.getSearch_terms();
        if(StringUtils.isNotBlank(search_terms)){
            List<PolicyArticleSearch> batchList = new ArrayList<PolicyArticleSearch>();
            String[] array = search_terms.split(",");
            for (String search_code : array) {
                PolicyArticleSearch policyArticleSearch= new PolicyArticleSearch();
                policyArticleSearch.setSeq_uuid(UUID.randomUUID().toString());
                policyArticleSearch.setArticle_code(articleCode);
                policyArticleSearch.setSearch_code(search_code);
                batchList.add(policyArticleSearch);
            }
            this.deleteByArticleCode(articleCode);
            this.addBatch(opt, batchList);
        }
    }
}
