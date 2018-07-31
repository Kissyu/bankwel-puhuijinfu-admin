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
        <span class=mrl14><i class="icon-list icon-red"></i>系统管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>app管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>app发布管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/system/addAppVersion" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <div class="row">
                    <webTag:Text id="app_id" name="app_id" displayLable="app编号" value='${data.app_id}' isRequired="true" maxlength="32" readonly="true"/>
                    <webTag:Text id="app_name" name="app_name" displayLable="app名称" value='${data.app_name}' readonly="true" />
                    <webTag:Text id="package_name" name="package_name" displayLable="包名" value='${data.package_name}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="device_type" name="device_type" displayLable="设备类型" parentCode="sys_deviceType" value='${data.device_type}' isRequired="true" readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="channel" name="channel" displayLable="渠道" parentCode="sys_channel" value='${data.channel}' isRequired="true" readonly="true" />
                    <webTag:Text id="app_version" name="app_version" displayLable="版本" value='${data.app_version}' isRequired="true" readonly="true"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_update" name="is_update" displayLable="是否强制更新" parentCode="sys_isUpdate" value='${data.is_update}' isRequired="true" readonly="true" />
                    <webTag:Text id="h5_url" name="h5_url" displayLable="H5下载地址" value='${data.h5_url}' readonly="true" />
                    <webTag:FileUploadTag id="app_url" name="app_url" displayLable="安装包下载地址" value='${data.app_url}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="qr_code_url" name="qr_code_url" displayLable="二维码下载地址" value='${data.qr_code_url}' readonly="true" />
                    <webTag:TextareaTag id="change_content" name="change_content" displayLable="更新内容:" rows="5" maxlength="255" value='${data.change_content}' readonly="true" />
                    <webTag:Text id="size" name="size" displayLable="安装包大小" value='${data.size}' readonly="true" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/system/goAppVersionQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
