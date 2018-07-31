package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * C13_2 - Text Tag Component
 * 文本输入框WebTag组件
 * @author sln
 *
 */
public class TextTag extends BodyTagSupport {

	private static final long serialVersionUID = -1266067343512203617L;
	private String name;
	private String id;
	private String value;
    private String defaultValue = "";
	private String displayLable = "";
	private String readonly;
	private String onclick;
	private String isBank;
	private String isRequired;
    private String minlength = "";
    private String maxlength = "";
    private String isEmail = "";
    private String isPhone = "";
    private String isMobile = "";
    private String isTelephone = "";
    private String isDecimal = "";
    private String isInt = "";

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
        String result = this.value;
        if(F.validate.isEmpty(result)){
            result = this.getDefaultValue();
        }
		return result;
	}
	public void setValue(String value) {
		this.value = value;
	}

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDisplayLable() {
		String result = "";
		if(F.validate.isNotEmpty(this.displayLable) && !this.displayLable.contains(":")){
			result = this.displayLable+":";
		} else {
			result = this.displayLable;
		}
		return result;
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
	
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
	public String getIsBank() {
		return isBank;
	}
	public void setIsBank(String isBank) {
		this.isBank = isBank;
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

    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    public String getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }

    public String getIsTelephone() {
        return isTelephone;
    }

    public void setIsTelephone(String isTelephone) {
        this.isTelephone = isTelephone;
    }

    public String getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(String isPhone) {
        this.isPhone = isPhone;
    }

    public String getIsDecimal() {
        return isDecimal;
    }

    public void setIsDecimal(String isDecimal) {
        this.isDecimal = isDecimal;
    }

    public String getIsInt() {
        return isInt;
    }

    public void setIsInt(String isInt) {
        this.isInt = isInt;
    }

    /**
	 * Tag驱动程序
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer sbf = new StringBuffer();
			TransHelper.stringBufferAppender(sbf,"<div class='control-group span4'>");
			TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(), printRequiredLable(),"</label>");
			TransHelper.stringBufferAppender(sbf,"    <div class='controls'>");
            TransHelper.stringBufferAppender(sbf,"        <input type='text' class='input-medium ' iclass='' name='",this.getName(),"' id='",this.getId(),"' value='",this.getValue(),"' onclick='",this.getOnclick(),"' ",printMinlength(),printMaxlength(),printRequired(),printIsEmail(),printIsPhone(),printIsMobile(),printIsTelephone(),printIsDecimal(),printIsInt(),printReadOnly()+" />");
            TransHelper.stringBufferAppender(sbf,"    </div>");
            TransHelper.stringBufferAppender(sbf,"</div>");
			pageContext.getOut().write(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
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

    public String printIsEmail(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsEmail())){
            if("true".equals(this.getIsEmail().toLowerCase())){
                result = " email='true' ";
            }
        }
        return result;
    }

    public String printIsMobile(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsMobile())){
            if("true".equals(this.getIsMobile().toLowerCase())){
                result = " mobile='true' ";
            }
        }
        return result;
    }

    public String printIsTelephone(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsTelephone())){
            if("true".equals(this.getIsTelephone().toLowerCase())){
                result = " telephone='true' ";
            }
        }
        return result;
    }
    public String printIsPhone(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsPhone())){
            if("true".equals(this.getIsPhone().toLowerCase())){
                result = " phone='true' ";
            }
        }
        return result;
    }

    public String printIsDecimal(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsDecimal())){
            if("true".equals(this.getIsDecimal().toLowerCase())){
                result = " decimal='true' ";
            }
        }
        return result;
    }

    public String printIsInt(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsInt())){
            if("true".equals(this.getIsInt().toLowerCase())){
                result = " integer='true' ";
            }
        }
        return result;
    }
}
