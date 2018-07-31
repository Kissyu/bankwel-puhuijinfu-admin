package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * C13_6 - Password Tag Component
 * 密码框WebTag组件
 * @author szl
 *
 */
public class PasswordTag extends BodyTagSupport {

	private static final long serialVersionUID = -1806140401781937606L;
	private String name;
	private String id;
	private String value;
	private String displayLable;
	private String readonly= "false";
	private String isRequired;
	private String minlength;
	private String maxlength;
	
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
		return this.displayLable;
	}

	public void setDisplayLable(String displayLable) {
		this.displayLable = displayLable;
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

	/**
	 * Tag驱动程序
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer sbf = new StringBuffer();
			TransHelper.stringBufferAppender(sbf,"<div class='control-group span4'>");
			TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(),printRequired(),"</label>");
			TransHelper.stringBufferAppender(sbf,"    <div class='controls'><input type='password' class='input-medium'  name='",this.getName(),"' id='",this.getId(),"' value='",this.getValue(),"' ",printMinlength(),printMaxlength(),printReadOnly()," ></div>");
			TransHelper.stringBufferAppender(sbf,"</div>");
			pageContext.getOut().write(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String printRequired(){
		String result = "";
		if(F.validate.isNotEmpty(this.getIsRequired())){
			if("true".equals(this.getIsRequired().toLowerCase())){
				result = "(<font color=\"red\">*</font>)";
			}
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
}
