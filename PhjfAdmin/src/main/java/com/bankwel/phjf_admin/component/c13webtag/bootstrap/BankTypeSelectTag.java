package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.BankType;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import java.util.List;

/**
 * Created by admin on 2017/12/6.
 */
public class BankTypeSelectTag extends SelectTag {

    public String printSelect(){
        StringBuffer result = new StringBuffer();
        List<IDynamicSelectData> selectDataList= BankType.dao.querySelectData();
        TransHelper.stringBufferAppender(result,"<select class='input-medium "+this.getIclass()+"' name='"+this.getName()+"' id='"+this.getId()+"' "+this.printReadOnly()+ this.printOnchange()+" >");
        TransHelper.stringBufferAppender(result,printOption(selectDataList));
        TransHelper.stringBufferAppender(result,"</select>");
        return result.toString();
    }

    public String printOption(List<IDynamicSelectData> selectDataList){
        StringBuffer result = new StringBuffer();
        String selectedId = F.validate.isNotEmpty(this.getValue())?this.getValue():this.getDefaultValue();
        TransHelper.stringBufferAppender(result,printEmpty());
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

}
