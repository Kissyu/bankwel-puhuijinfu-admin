<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/compent/jstl/webTag-Bootstrap.tld" prefix="webTag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>系统</title>
    <jsp:include page="/WEB-INF/jsp/pub/basePhjfInclude.jsp" flush="true"/>
</head>
<body >
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>敏感词管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>敏感词管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>添加敏感词</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/sensitiveWords/save" enctype ="multipart/form-data" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="language" name="language" displayLable="语言:" parentCode="sys_language"  />
                </div>
                <div class="row">
                    <div style="width: 100%;height: 40px;margin-left: 160px;color: red;">
                        小贴士:填入多个敏感词请用分割字符分开（分割字符：英文逗号(,)），例如：北冥有鱼,闪联,智商。<br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        当然你也可以装入文本文件（.txt）中大批量的敏感词导入。
                    </div>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="words" name="words" displayLable="敏感词:" rows="10" value='' maxlength="1000"/>
                </div>
                <div class="row">
                    <div style="width: 100%;height: 30px;margin-left: 86px;">
                        批量导入：&nbsp;&nbsp;&nbsp;&nbsp;<input type="file" name="file" id="file" />
                    </div>
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存</button>
                <%--<a href="/phjfht/api/v1/sensitiveWords/bulkImportBtn" class="btn btn-danger">
                    <i class="icon-inbox icon-white"></i>批量导入
                </a>--%>
                <a class="btn btn-danger" href="/phjfht/api/v1/sensitiveWords/indexMainPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
            </div>
        </form>
    </div>
    <!-- 数据区 end -->
</div>
<!-- 		底部高度填充块 -->
<div class="zeoBottom"></div>
<!-- 		底部高度填充块 结束-->
</body>
</html>
