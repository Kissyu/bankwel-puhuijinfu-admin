<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
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
    <script>
        $(function(){
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };
        });
    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>银行预约时间管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行预约时间管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <div class="row">
                    <webTag:Text id="bt_code" name="bt_code" displayLable="银行类型代码" value='${data.bt_code}'  readonly="true" maxlength="32"/>
                    <webTag:Text id="bt_name" name="bt_name" displayLable="银行类型名称" value='${data.bt_name}' readonly="true" maxlength="32"/>
                    <webTag:Text id="business_code_name" name="business_code_name" displayLable="业务" value='${data.business_code_name}' readonly="true" maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:Text id="time_format" name="time_format" displayLable="时间格式" value='${data.time_format_name}' readonly="true" maxlength="32"/>
                    <webTag:Text id="start_time" name="start_time" displayLable="业务关闭开始时间" value='${data.start_time}' readonly="true" maxlength="32"/>
                    <webTag:Text id="end_time" name="end_time" displayLable="业务关闭结束时间" value='${data.end_time}' readonly="true" maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="describe" name="describe" displayLable="描述" rows="5" value='${data.describe}'  readonly="true"  maxlength="255" isRequired="true"/>
                    <webTag:Text id="is_show" name="is_show" displayLable="是否显示"  value='${data.is_show_name}'  readonly="true" isRequired="true"/>
                    <webTag:Text id="status_name" name="status_name" displayLable="状态"  value='${data.status_name}'  readonly="true" isRequired="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/appointmentTime/goAddSimpBankAmTimePage"><i class="icon-inbox icon-white"></i>录入下一个</a>
                <a class="btn btn-danger" href="/phjfht/api/v1/appointmentTime/goQueryBankAppointmentTimePage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
