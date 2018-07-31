package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.SysProvinces;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */
public class ProvincesSelectTag extends SelectTag  {

    private String onchange = "";

    public String getOnchange() {
        return onchange;
    }
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }


    public String printSelect(){
        StringBuffer result = new StringBuffer();
        List<IDynamicSelectData> selectDataList= SysProvinces.dao.querySelectData();
        TransHelper.stringBufferAppender(result,"<select class='input-medium "+this.getIclass()+"' name='"+this.getName()+"' id='"+this.getId()+"' "+this.printReadOnly()+this.printIsRequired()+ this.printOnchange()+" >");
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
