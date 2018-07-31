package com.bankwel.phjf_admin.common.model.core;

import com.bankwel.phjf_admin.webapi.vo.PolicyArticleVO;
import com.bankwel.phjf_baseModel.common.model.phjf.CachePolicyArticlePlate;
import com.jfinal.plugin.activerecord.Db;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PolicyArticlePlate extends CachePolicyArticlePlate<PolicyArticlePlate> {
    public final static PolicyArticlePlate dao = new PolicyArticlePlate().dao();

    public void addBatch(AuthOperator opt, List<PolicyArticlePlate> dataList) {
        String sql = "insert into phjf_policy_article_plate(seq_uuid, article_code,plate_code) values(?, ?, ?)";
        int[] result = Db.use("DBPublic").batch(sql, "seq_uuid,article_code,plate_code", dataList, 500);
        //flashCatch(this);
    }

    public void deleteByArticleCode(String article_code) {
        String sql = "DELETE FROM  phjf_policy_article_plate WHERE article_code = ?";
        Db.use("DBPublic").update(sql, article_code);
        //flashCatch(this);
    }

    public List<PolicyArticlePlate> findByArticleCode(String article_code) {
        String sql = "Select * FROM  phjf_policy_article_plate WHERE article_code = ?";
        return PolicyArticlePlate.dao.find(sql, article_code);
    }

    public void dealWithRelation(AuthOperator opt, String articleCode, PolicyArticleVO vo) {
        String policy_plate = vo.getPolicy_plate();
        if(StringUtils.isNotBlank(policy_plate)){
            List<PolicyArticlePlate> batchList = new ArrayList<PolicyArticlePlate>();
            String[] array = policy_plate.split(",");
            for (String plate_code : array) {
                PolicyArticlePlate policyArticlePlate= new PolicyArticlePlate();
                policyArticlePlate.setSeq_uuid(UUID.randomUUID().toString());
                policyArticlePlate.setArticle_code(articleCode);
                policyArticlePlate.setPlate_code(plate_code);
                batchList.add(policyArticlePlate);
            }
            this.deleteByArticleCode(articleCode);
            this.addBatch(opt, batchList);
        }
    }
}
