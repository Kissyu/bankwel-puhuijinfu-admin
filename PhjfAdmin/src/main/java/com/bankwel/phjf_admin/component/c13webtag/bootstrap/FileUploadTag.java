package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.component.c11assistant.TransHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Created by admin on 2017/11/3.
 */
public class FileUploadTag extends BodyTagSupport {

    private String name;
    private String id;
    private String value;
    private String defaultValue = "";
    private String displayLable = "";
    private String readonly;
    private String isRequired;


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
            TransHelper.stringBufferAppender(sbf,"    <label class='control-label' for='",this.getId(),"'>",this.getDisplayLable(), printRequiredLable(),"</label>");
            TransHelper.stringBufferAppender(sbf,"    <div class='controls' style='width: 260px;'>");
            TransHelper.stringBufferAppender(sbf,"        <input type='text' class='input-medium ' id='",this.getId(),"' name='",this.getId(),"' value='",this.value,"' ", this.printReadOnly(),this.printIsRequired()," >");
            TransHelper.stringBufferAppender(sbf,"        <span style='display: inline-block;display: ",this.printUpload(),"'> ");
            TransHelper.stringBufferAppender(sbf,"            <input type='file' class='input-medium ' id='",this.getId(),"_file' name='",this.getId(),"_file' >");
            TransHelper.stringBufferAppender(sbf,"            <div id=\"fileQueue\"></div>");
            TransHelper.stringBufferAppender(sbf,"        </span>");
            TransHelper.stringBufferAppender(sbf,"    </div>");
            TransHelper.stringBufferAppender(sbf,"</div>");
            pageContext.getOut().write(sbf.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public String printUpload(){
        String result = "";
        if(F.validate.isNotEmpty(this.getReadonly())){
            if("true".equals(this.getReadonly().toLowerCase())){
                result = " none ";
            } else {
                result = "  ";
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

    public String printRequiredLable(){
        String result = "";
        if(F.validate.isNotEmpty(this.getIsRequired())){
            if("true".equals(this.getIsRequired().toLowerCase())){
                result = "(<font color='red'>*</font>)";
            }
        }
        return result;
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
