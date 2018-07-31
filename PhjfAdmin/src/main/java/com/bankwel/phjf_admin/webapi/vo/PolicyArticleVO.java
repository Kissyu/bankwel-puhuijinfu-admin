package com.bankwel.phjf_admin.webapi.vo;

import com.bankwel.phjf_admin.common.model.core.PolicyArticle;

public class PolicyArticleVO extends PolicyArticle {
    private String search_terms;
    private String policy_plate;
    private String language_show;

    public String getLanguage_show() {
        return language_show;
    }

    public void setLanguage_show(String language_show) {
        this.language_show = language_show;
    }

    public String getSearch_terms() {
        return search_terms;
    }

    public void setSearch_terms(String search_terms) {
        this.search_terms = search_terms;
    }

    public String getPolicy_plate() {
        return policy_plate;
    }

    public void setPolicy_plate(String policy_plate) {
        this.policy_plate = policy_plate;
    }

}
