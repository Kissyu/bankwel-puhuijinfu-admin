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
        <span class=mrl14><i class="icon-list icon-red"></i>惠民政策版块管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>惠民政策版块管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>分配</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/policy/allocationPolicyPlate" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="code" name="plate_code" displayLable="版块编码" value='${data.plate_code}'  readonly="true" maxlength="100" />
                    <webTag:Text id="name" name="name" displayLable="版块名称" value='${data.name}' isRequired="true" maxlength="100"  readonly="true"/>
                </div>
                <div class="row">
                    <webTag:ProvincesSelectTag id="province_id" name="province_id" displayLable="省" value='${data.province_id}' onchange="provinceChange();" />
                    <webTag:Text id="order_num" name="order_num" displayLable="排序号" value='${data.order_num}' isInt="true" maxlength="11" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/policy/goPolicyPlateQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
