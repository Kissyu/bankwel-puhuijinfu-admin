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
    <link href="${path}/compent/uichose/src/ui-choose.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/uichose/src/ui-choose.js"></script>
    <script>
        var ueditorURL = '/';
        var plate_code;
        $(function(){
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };
            upload_img({id:'logo_file',callback:function(result){
                $('#logo').val(result.data.url);
                $("#logo_show").show();
                $('#logo_show').attr('src',result.data.url);
            }});
        });

    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>惠民政策搜索条件管理</span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>惠民政策搜索条件管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>修改</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/policy/modifySearchTerm" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="search_code" name="search_code" value="${data.search_code}"/>
            <webTag:HiddenInputTag id="st_id" name="st_id" value="${data.st_id}"/>
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="parent_st_id" name="parent_st_id" value="${data.parent_st_id}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="parent_st_name" name="parent_st_name" displayLable="父节点" value='${data.parent_st_name}' readonly="true"/>
                    <webTag:Text id="name" name="name" displayLable="名称" value='${data.name}' isRequired="true" maxlength="32"/>
                </div>
                <div class="row">
                    <%--<webTag:Text id="type" name="type" displayLable="类型" value='${data.type}' isRequired="true" readonly="true"/>--%>
                <webTag:DataDictionarySelectTag sys_id="Phjf" id="type" name="type" displayLable="类型" parentCode="sys_policySearchType" value='${data.type}' isRequired="true" readonly="true" />
                    <webTag:ProvincesSelectTag id="province" name="province" displayLable="省" value='${data.province}' onchange="provinceChange();" />
                    <webTag:ImgUploadTag id="logo" name="logo" displayLable="LOGO" value="${data.logo}" />
                </div>
                <div class="row">
                    <webTag:Text id="treepath" name="treepath" displayLable="路径" value='${data.treepath}' isRequired="true" readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_child" name="is_child" displayLable="是否为子节点" parentCode="sys_policySearchIsChild" value='${data.is_child}' isRequired="true"  />
                    <webTag:Text id="order_no" name="order_no" displayLable="序号" value='${data.order_no}' isRequired="true" isInt="true" maxlength="10"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/policy/goPolicySearchQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
