package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * C13_14 - Hidden Input Tag Component
 * 隐藏域WebTag组件
 * @author szl
 */
public class HiddenInputTag extends BodyTagSupport {

	private static final long serialVersionUID = -1266067343512203617L;
	private String name;
	private String id;
	private String value;
	private String displayLable;
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
	/**
	 * Tag驱动程序
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer sbf = new StringBuffer(); 
//			TransHelper.stringBufferAppender(sbf,"<div>");
			TransHelper.stringBufferAppender(sbf,"    <input type='hidden'  name='",this.getName(),"' id='",this.getId(),"' value='",this.getValue(),"'/>");
//			TransHelper.stringBufferAppender(sbf,"</div>");
			pageContext.getOut().write(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
