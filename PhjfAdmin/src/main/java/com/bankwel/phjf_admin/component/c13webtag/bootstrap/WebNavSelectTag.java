package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.WebNav;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import java.util.List;

/**
 * Created by admin on 2017/12/6.
 */
public class WebNavSelectTag extends SelectTag {

    private String lan = "";
    public String getLan() {
        return lan;
    }
    public void setLan(String lan) {
        this.lan = lan;
    }

    public String printSelect(){
        StringBuffer result = new StringBuffer();
        List<IDynamicSelectData> navList= WebNav.dao.querySelectData(this.getLan());
        TransHelper.stringBufferAppender(result,"<select class='input-medium "+this.getIclass()+"' name='"+this.getName()+"' id='"+this.getId()+"' "+this.printReadOnly()+ this.printOnchange()+" >");
        TransHelper.stringBufferAppender(result,printOption(navList));
        TransHelper.stringBufferAppender(result,"</select>");
        return result.toString();
    }

    public String printOption(List<IDynamicSelectData> navList){
        StringBuffer result = new StringBuffer();
        String selectedId = F.validate.isNotEmpty(this.getValue())?this.getValue():this.getDefaultValue();
        TransHelper.stringBufferAppender(result,printEmpty());
        if(navList!=null && !navList.isEmpty()){
            for(IDynamicSelectData nav : navList){
                if(nav.getId().equals(selectedId)){
                    TransHelper.stringBufferAppender(result,"<option id='",nav.getId(),"' value='",nav.getId(),"' selected>",nav.getName(),"</option>");
                }else{
                    TransHelper.stringBufferAppender(result,"<option id='",nav.getId(),"' value='",nav.getId(),"' >",nav.getName(),"</option>");
                }
            }
        }
        return result.toString();
    }

}
