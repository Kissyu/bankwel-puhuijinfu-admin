package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * C13_4_1 - Static Select Tag Component
 * 静态下拉列表框WebTag组件
 * @author sln
 *
 */
public class SelectTag extends BodyTagSupport {

	private static final long serialVersionUID = -1266067343512203617L;
	
	private String id;
	private String name;
	private String value;
	private String displayLable;
	private String style;
	private String defaultValue;
	private String isBank;
	private String isEmptyOption = "true";
	private String readonly="";
	private String onmousemove="";
	private String onmouseout="";
	private String onfocus="";
	private String isRequired = "";
	private String iclass;  //校验及样式
	private String onkeydown;//键盘事件
	private String onchange = "";
	private String topStyle = "";

	public String getTopStyle() {
		return topStyle;
	}

	public void setTopStyle(String topStyle) {
		this.topStyle = topStyle;
	}

	public String getOnkeydown() {
		return onkeydown;
	}
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}
	public String getIclass() {
		return iclass;
	}
	public void setIclass(String iclass) {
		this.iclass = iclass;
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
		if(F.validate.isNotEmpty(displayLable)
				&& !displayLable.contains(":")){
			return displayLable+":";
		}
		return displayLable;
	}
	public void setDisplayLable(String displayLable) {
		this.displayLable = displayLable;
	}
	public String getIsEmptyOption() {
		if(this.isEmptyOption==null||"".equals(this.isEmptyOption)){this.isEmptyOption="true";}
		return isEmptyOption;
	}
	public void setIsEmptyOption(String isEmptyOption) {
		this.isEmptyOption = isEmptyOption;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getIsBank() {
		return isBank;
	}
	public void setIsBank(String isBank) {
		this.isBank = isBank;
	}
	
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
 
	public String getOnmousemove() {
		return onmousemove;
	}
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}
	public String getOnmouseout() {
		return onmouseout;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	/**
	 * 渲染请选择
	 * @return
	 */
	public String printEmpty(){
		if("true".equals(this.getIsEmptyOption())){
			return "<option value=''>---请选择---</option>";
		}
		return "";
	}
	
	/**
	 * Tag驱动程序
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer sbf = new StringBuffer();
			TransHelper.stringBufferAppender(sbf,"<div class='control-group span4' style='" + this.topStyle + "' >");
			TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(),this.printRequired(),"</label>");
			TransHelper.stringBufferAppender(sbf,"    <div class='controls'>",printSelect(),"</div>");
 			TransHelper.stringBufferAppender(sbf,"</div>");
			pageContext.getOut().write(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	/**
	 * 渲染下拉列表框
	 * @return
	 */
	public String printSelect(){
		String spanstart="";
		String spanend="";
		
		if(!"".equals( this.getOnmousemove())&& !"".equals( this.getOnmouseout())&&!"".equals( this.getOnfocus())&&!"".equals( this.getOnkeydown())){
			spanstart ="<span onmousemove='"+this.getOnmousemove()+"' onkeydown='"+this.getOnkeydown()+"' onmouseout='"+this.getOnmouseout()+"' onfocus='"+this.getOnfocus()+"'>";
			spanend="</span>";
		}
		StringBuffer sbf = new StringBuffer();
		TransHelper.stringBufferAppender(sbf,"        <select class='input-medium ",this.getIclass(),"' name='",this.getName(),"' id='",this.getId(),"' "+this.printReadOnly()+" "+this.printOnchange()+" >");
		TransHelper.stringBufferAppender(sbf,printEmpty());
//		TransHelper.stringBufferAppender(sbf,this.getBodyContent()+"");
		TransHelper.stringBufferAppender(sbf,"        </select>");
		StringBuffer select = new StringBuffer();
		TransHelper.stringBufferAppender(select,spanstart,sbf.toString(),spanend);
		return select.toString();
	}


	public String printReadOnly(){
		String result = "";
		if(F.validate.isNotEmpty(this.getReadonly())){
			if("true".equals(this.getReadonly().toLowerCase())){
				result = " readonly='true' onchange='this.selectedIndex=this.defaultIndex;' onfocus='this.defaultIndex=this.selectedIndex;' ";
			}
		}
		return result;
	}

	public String printOnchange(){
		String result = "";
		if(F.validate.isNotEmpty(this.getOnchange())){
			result = " onchange='"+this.getOnchange()+"' ";
		}
		return result;
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

}
