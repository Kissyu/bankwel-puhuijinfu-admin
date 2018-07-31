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
        <span><i class="icon-list icon-red"></i>预约开户</span><span class="divider">/</span>
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
                    <webTag:Text id="bt_code" name="bt_code" displayLable="开户行" value='${data.bt_code}' readonly="true"/>
                    <webTag:Text id="mp_code" name="mp_code" displayLable="办理点名称" value='${data.mp_code}' readonly="true" maxlength="32"/>
                    <webTag:Text id="user_id" name="user_id" displayLable="用户名" value='${data.user_id}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="true_name" name="true_name" displayLable="真实姓名" value='${data.true_name}' readonly="true"/>
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="手机号" value='${data.mobilephone}' readonly="true" maxlength="32"/>
                    <webTag:Date id="am_time" name="am_time" displayLable="预约时间" value='${data.am_time}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Date id="bank_accept_time" name="bank_accept_time" displayLable="银行接受时间" value='${data.bank_accept_time}' readonly="true" />
                    <webTag:Date id="bank_refuse_time" name="bank_refuse_time" displayLable="银行拒绝时间" value='${data.bank_refuse_time}' readonly="true" />
                    <webTag:Text id="bank_opt" name="bank_opt" displayLable="银行办理人" value='${data.bank_opt}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="sys_status" value='${data.status}' readonly="true" />
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255" readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/user/goUserBankcardAppointmentPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
