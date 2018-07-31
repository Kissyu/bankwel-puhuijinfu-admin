package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * C13_3 - Date Tag Component
 * 日期输入框WebTag组件
 * @author sln
 *
 */
public class DateTag extends BodyTagSupport {

	private static final long serialVersionUID = -1266067343512203617L;
	private String name;
	private String id;
	private String value;
	private String displayLable;
	private String readonly;
//	private String isdisplay;
	private String isRequired;
	private String iclass;
    private String dateFormat;
	public String getIclass() {
		return iclass;
	}
	public void setIclass(String iclass) {
		this.iclass = iclass;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDisplayLable() {
		return displayLable;
	}
	public void setDisplayLable(String displayLable) {
		this.displayLable = displayLable;
	}
	
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Tag驱动程序
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer sbf = new StringBuffer();
			TransHelper.stringBufferAppender(sbf,"<div class='control-group span4'>");
			if(this.getIsRequired()=="true"){
				TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(),"(<font color=\"red\">*</font>):</label>");
			}else{
				TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(),":</label>");
			}
			if(this.getReadonly()==null||this.getReadonly()=="false"){
                if (this.getDateFormat() != null && !"".equals(this.getDateFormat())) {
                    TransHelper.stringBufferAppender(sbf,"    <div class='controls'><input type='text' class='Wdate input-medium' onClick='WdatePicker({dateFmt: \"", this.getDateFormat(), "\"})' name='",this.getName(),"' id='",this.getId(),"' value='",this.getValue(),"'  ></div>");
                } else {
                    TransHelper.stringBufferAppender(sbf,"    <div class='controls'><input type='text' class='Wdate input-medium' onClick='WdatePicker()' name='",this.getName(),"' id='",this.getId(),"' value='",this.getValue(),"'  ></div>");
                }
			}else{
				TransHelper.stringBufferAppender(sbf,"    <div class='controls'><input type='text' class='Wdate input-medium' name='",this.getName(),"' id='",this.getId(),"' value='",this.getValue(),"' readonly='",this.getReadonly(),"' ></div>");
				
			}
			TransHelper.stringBufferAppender(sbf,"</div>");
			pageContext.getOut().write(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
