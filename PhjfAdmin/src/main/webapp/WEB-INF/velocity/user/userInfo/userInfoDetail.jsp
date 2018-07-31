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
<body>
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>会员管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>会员列表</span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/newsManage/addNewsPlate" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="user_name" name="user_name" displayLable="账户名" value='${data.user_name}' readonly="true"/>
                    <webTag:Text id="true_name" name="true_name" displayLable="姓名" value='${data.true_name}' readonly="true"/>
                    <webTag:Text id="nickname" name="nickname" displayLable="昵称" value='${data.nickname}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="手机号" value='${data.mobilephone}' readonly="true" maxlength="32"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="id_type" name="id_type" displayLable="证件类型" parentCode="sys_idType" value='${data.id_type}' readonly="true" />
                    <webTag:Text id="id_num" name="id_num" displayLable="证件号" value='${data.id_num}' readonly="true" maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="gender" name="gender" displayLable="性别" parentCode="sys_gender" value='${data.gender}' readonly="true" />
                    <webTag:Date id="reg_time" name="reg_time" displayLable="注册时间" value='${data.reg_time}' readonly="true" />
                    <webTag:Date id="frozen_time" name="frozen_time" displayLable="账户冻结时间" value='${data.frozen_time}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:ImgUploadTag id="head_img" name="head_img" displayLable="头像" value="${data.head_img}" isShowImg="true" readonly="true" />
                    <webTag:TextareaTag id="address" name="address" displayLable="常住地" rows="5" value='${data.address}' maxlength="255" readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/user/goUserQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
