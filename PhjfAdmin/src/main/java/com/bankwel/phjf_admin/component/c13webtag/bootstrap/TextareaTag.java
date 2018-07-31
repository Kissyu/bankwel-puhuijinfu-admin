package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * C13_9 - Textarea Tag Component
 * 文本域WebTag组件
 * @author szl
 *
 */
public class TextareaTag extends BodyTagSupport {

	private static final long serialVersionUID = 7725415502112955566L;
	private String name;
	private String id;
	private String value;
	private String rows;
	private String displayLable;
	private String readonly = "false";
	private String isRequired;
	private String minlength = "";
	private String maxlength = "";
	private String textClass = "";

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
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
	
	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getMinlength() {
		return minlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getTextClass() {
		return textClass;
	}

	public void setTextClass(String textClass) {
		this.textClass = textClass;
	}

	/**
	 * Tag驱动程序
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer sbf = new StringBuffer();
			TransHelper.stringBufferAppender(sbf,"<div class='control-group span4'>");
			TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(),printRequiredLable(),"</label>");
			TransHelper.stringBufferAppender(sbf,"    <div class='controls'>");
			TransHelper.stringBufferAppender(sbf,"        <textarea class='",this.printClass(),"' id='",this.getId(),"' name='",this.getName(),"' rows='",this.getRows(),"' ",printRequired(),printMinlength(),printMaxlength(),printReadOnly()," >",this.getValue(),"</textarea>");
			TransHelper.stringBufferAppender(sbf,"    </div>");
			TransHelper.stringBufferAppender(sbf,"</div>");
			pageContext.getOut().write(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String printRequiredLable(){
		String result = "";
		if(F.validate.isNotEmpty(this.getIsRequired())){
			if("true".equals(this.getIsRequired().toLowerCase())){
				result = "(<font color=\"red\">*</font>)";
			}
		}
		return result;
	}

	public String printRequired(){
		String result = "";
		if(F.validate.isNotEmpty(this.getIsRequired())){
			if("true".equals(this.getIsRequired().toLowerCase())){
				result = " required ";
			}
		}
		return result;
	}

	public String printMinlength(){
		String result = "";
		if(F.validate.isNotEmpty(this.getMinlength())){
			result = F.strKit.f("minlength='%s'",this.getMinlength());
		}
		return result;
	}

	public String printMaxlength(){
		String result = "";
		if(F.validate.isNotEmpty(this.getMaxlength())){
			result = F.strKit.f("maxlength='%s'",this.getMaxlength());
		}
		return result;
	}

	public String printReadOnly(){
		String result = "";
		if(F.validate.isNotEmpty(this.getReadonly())){
			if("true".equals(this.getReadonly().toLowerCase())){
				result = " readonly='true' ";
			}
		}
		return result;
	}

	public String printClass(){
		String result = "";
		if (F.validate.isNotEmpty(this.getTextClass())){
			result = this.getTextClass();
		} else {
			result = "input-large";
		}
		return result;
	}

	
}
