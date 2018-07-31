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
        <span><i class="icon-list icon-red"></i>预约取款 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/managepoint/addMpOptinfo" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <div class="row">
                    <webTag:Text id="mp_code" name="mp_code" displayLable="办理点编号" value='${data.mp_code}' readonly="true"/>
                    <webTag:Text id="mp_name" name="mp_name" displayLable="办理点名称" value='${data.mp_name}' readonly="true"/>
                    <webTag:Text id="user_name" name="user_name" displayLable="会员账户" value='${data.user_name}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="name" name="name" displayLable="预约取款人姓名" value='${data.name}' readonly="true" />
                    <webTag:Text id="nickname" name="nickname" displayLable="昵称" value='${data.nickname}' readonly="true"/>
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="手机号" value='${data.mobilephone}' isMobile="true" readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="amount" name="amount" displayLable="取款金额" value='${data.amount}' readonly="true"/>
                    <webTag:Date id="am_date" name="am_date" displayLable="预约办理时间" value='${data.am_date}' readonly="true" />
                    <webTag:Date id="accept_time" name="accept_time" displayLable="接受时间" value='${data.accept_time}' dateFormat="yyyy-MM-dd HH:mm" readonly="true" />
                </div>
                <div class="row">
                    <webTag:Date id="refuse_time" name="refuse_time" displayLable="拒绝时间" value='${data.refuse_time}' dateFormat="yyyy-MM-dd HH:mm:ss" readonly="true" />
                    <webTag:Date id="cancel_time" name="cancel_time" displayLable="取消时间" value='${data.cancel_time}' dateFormat="yyyy-MM-dd HH:mm" readonly="true" />
                    <webTag:Text id="mp_opt" name="mp_opt" displayLable="办理点办理人" value='${data.mp_opt}' isMobile="true" readonly="true"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="终端是否展现" parentCode="sys_isShow" value='${data.is_show}'  readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="sys_withdrawStatus" value='${data.status}'   readonly="true"/>
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="175" readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/user/goUserWithdrawAmPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
