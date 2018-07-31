package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import java.util.List;

/**
 * C13_4_2 - Dynamic Select Tag Component
 * 动态下拉列表框WebTag组件
 * @author sln
 *
 */
public class DataDictionarySelectTag extends SelectTag {

	private static final long serialVersionUID = 7653811678183048846L;
	private String sys_id = "";
	private String parentCode = "";

    public String getSys_id() {
        return sys_id;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
    }

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

    /**
	 * 渲染下拉列表框
	 * @return
	 */
	public String printSelect(){
        StringBuffer result = new StringBuffer();
        List<SysDatalibrary> dataDictionaryList = SysDatalibrary.dao.querySysDatalibrary(this.getSys_id(),this.getParentCode());
        TransHelper.stringBufferAppender(result,"<select class='input-medium "+this.getIclass()+"' name='"+this.getName()+"' id='"+this.getId()+"' "+this.printReadOnly()+this.printIsRequired()+this.printOnchange()+" >");
        TransHelper.stringBufferAppender(result,printOption(dataDictionaryList));
        TransHelper.stringBufferAppender(result,"</select>");
		return result.toString();
	}

	public String printOption(List<SysDatalibrary> dataDictionaryList){
        StringBuffer result = new StringBuffer();
        String selectedId = F.validate.isNotEmpty(this.getValue())?this.getValue():this.getDefaultValue();
        TransHelper.stringBufferAppender(result,printEmpty());
        if(dataDictionaryList!=null && !dataDictionaryList.isEmpty()){
            for(SysDatalibrary dictionary : dataDictionaryList){
                if(dictionary.getCode().equals(selectedId)){
                    TransHelper.stringBufferAppender(result,"<option id='",dictionary.getCode(),"' value='",dictionary.getCode(),"' selected>",dictionary.getName(),"</option>");
                }else{
                    TransHelper.stringBufferAppender(result,"<option id='",dictionary.getCode(),"' value='",dictionary.getCode(),"' >",dictionary.getName(),"</option>");
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
