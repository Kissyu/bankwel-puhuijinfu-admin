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
        <span class=mrl14><i class="icon-list icon-red"></i>办理点管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>办理点账户管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/managepoint/addMpOptInfo" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <div class="row">
                    <webTag:Text id="mp_code" name="mp_code" displayLable="办理点代码" value='${data.mp_code}' isRequired="true" readonly="true"/>
                    <webTag:Text id="mp_name" name="mp_name" displayLable="办理点名称" value='${data.mp_name}' readonly="true" />
                    <webTag:Text id="mp_contact" name="mp_contact" displayLable="办理点联系人" value='${data.mp_contact}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="name" name="name" displayLable="姓名" value='${data.name}' isRequired="true" readonly="true" />
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="移动电话" value='${data.mobilephone}' isMobile="true" isRequired="true" readonly="true"/>
                    <webTag:Text id="telephone" name="telephone" displayLable="固定电话" value='${data.telephone}' isPhone="true" readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="certi_type" name="certi_type" displayLable="证件类型" parentCode="sys_idType" value='${data.certi_type}' readonly="true" />
                    <webTag:Text id="certi_no" name="certi_no" displayLable="证件号码" value='${data.certi_no}' maxlength="32" readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="gender" name="gender" displayLable="性别" parentCode="sys_gender" value='${data.gender}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="email" name="email" displayLable="邮箱" value='${data.email}' isEmail="true" readonly="true"/>
                    <webTag:PasswordTag id="password" name="password" value='${data.password}' displayLable="登录密码" minlength="8" maxlength="20" readonly="true"/>
                    <webTag:PasswordTag id="confirmPassword" name="confirmPassword" value='${data.password}' displayLable="确认密码" minlength="8" maxlength="20" readonly="true"/>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="address" name="address" displayLable="地址:" rows="5" value='${data.address}' maxlength="255" readonly="true"/>
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255" readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/managepoint/goAddMpOptInfoPage"><i class="icon-inbox icon-white"></i>录入下一个</a>
                <a class="btn btn-danger" href="/phjfht/api/v1/managepoint/goMpOptQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
