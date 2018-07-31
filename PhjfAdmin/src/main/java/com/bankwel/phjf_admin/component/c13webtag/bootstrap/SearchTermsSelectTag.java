package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.SearchTerms;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import java.util.List;

/**
 * @ClassName: SearchTermsSelectTag
 * @Description: 搜索条件标签
 * @author: DukeMr.Chen
 * @date: 2018/4/27 16:12
 * @version: V1.0
 *
 */
public class SearchTermsSelectTag extends SelectTag  {
    private static final long serialVersionUID = 1L;

    private String language = "ZH_SIMP";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String printSelect(){
        StringBuffer result = new StringBuffer();
        List<IDynamicSelectData> selectDataList= SearchTerms.dao.querySelectData(this.language);
        TransHelper.stringBufferAppender(result,"<select class='ui-choose " + this.getIclass() + "' multiple='multiple' id='" + this.getId() +"' >");
        TransHelper.stringBufferAppender(result,printOption(selectDataList));
        TransHelper.stringBufferAppender(result,"</select>");
        TransHelper.stringBufferAppender(result,"<input type='hidden'  name='", this.getName() , "' id='", this.getId(),"X' value='", this.getValue() ,"'/>");
        return result.toString();
    }

    public String printOption(List<IDynamicSelectData> selectDataList){
        StringBuffer result = new StringBuffer();
        String selectedId = F.validate.isNotEmpty(this.getValue())?this.getValue():this.getDefaultValue();
        //TransHelper.stringBufferAppender(result,printEmpty());
        if(selectDataList!=null && !selectDataList.isEmpty()){
            for(IDynamicSelectData selectData : selectDataList){
                if(selectData.getId().equals(selectedId)){
                    TransHelper.stringBufferAppender(result,"<option id='",selectData.getId(),"' value='",selectData.getId(),"' selected>",selectData.getName(),"</option>");
                }else{
                    TransHelper.stringBufferAppender(result,"<option id='",selectData.getId(),"' value='",selectData.getId(),"' >",selectData.getName(),"</option>");
                }
            }
        }
        return result.toString();
    }

    public String printIsRequired(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsRequired())){
            if("true".equals(this.getIsRequired().toLowerCase())){
                result = " required ";
            }
        }
        return result;
    }
}
