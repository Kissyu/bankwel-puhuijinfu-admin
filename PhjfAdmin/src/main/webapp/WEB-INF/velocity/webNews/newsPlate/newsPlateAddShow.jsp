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
    <%--<jsp:include page="/WEB-INF/jsp/akc/akccore/pub/backAkcPageHelper.jsp" flush="true"/>--%>

</head>
<body>
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>新闻管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新闻版块管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/news/addNewsPlate" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="np_code" name="np_code" displayLable="版块编号" value='${data.np_code}' readonly="true"/>
                    <webTag:Text id="name" name="name" displayLable="版块名称" value='${data.np_name}' readonly="true" isRequired="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="type_show" name="type_show" displayLable="板块类型" value='${data.type_show}' readonly="true" isRequired="true"/>
                    <webTag:Text id="location_show" name="location_show" displayLable="位置" value='${data.location_show}' readonly="true" isRequired="true"/>
                    <webTag:Text id="nav_name" name="nav_name" displayLable="所属导航" value='${data.nav_name}' readonly="true" isRequired="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="order_num" name="order_num" displayLable="排序号" value='${data.order_num}' readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <c:if test="${data.language == 'ZH_SIMP'}">
                    <a class="btn btn-danger" href="/phjfwebht/api/v1/webNews/goAddSimpNewsPlatePage"><i class="icon-inbox icon-white"></i>录入下一个</a>
                </c:if>
                <a class="btn btn-danger" href="/phjfwebht/api/v1/webNews/goNewsPlatesQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
