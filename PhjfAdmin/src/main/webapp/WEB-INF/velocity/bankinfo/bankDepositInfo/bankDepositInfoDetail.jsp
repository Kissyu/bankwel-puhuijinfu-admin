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
        <span class=mrl14><i class="icon-list icon-red"></i>银行信息管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>储蓄产品管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="languageDesc" name="languageDesc" displayLable="语言:" value='${data.languageDesc}' readonly="true" maxlength="32"/>
                    <webTag:Text id="bd_code" name="bd_code" displayLable="储蓄产品编号" value='${data.bd_code}'  readonly="true"/>
                    <webTag:Text id="title" name="title" displayLable="标题" value='${data.title}'  readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="bt_code" name="bt_code" displayLable="银行类型名称" value='${data.bt_code}'  readonly="true"/>
                    <webTag:Text id="term_type" name="term_type" displayLable="类型" value='${data.term_type}' readonly="true" />
                    <webTag:Text id="term" name="term" displayLable="期限" value='${data.term}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="seven_rate" name="seven_rate" displayLable="七日年化利率" value='${data.telephone}' readonly="true" />
                    <webTag:Text id="year_rate" name="year_rate" displayLable="年化利率" value='${data.year_rate}' readonly="true" />
                    <webTag:Text id="min_amount" name="min_amount" displayLable="起存金额" value='${data.min_amount}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="currency" name="currency" displayLable="币种" value='${data.currency}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_redeem" name="is_redeem" displayLable="是否可赎回" parentCode="sys_status" value='${data.is_redeem}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_top" name="is_top" displayLable="是否置顶" parentCode="sys_status" value='${data.is_top}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_recom" name="is_recom" displayLable="是否推荐" parentCode="sys_status" value='${data.is_recom}' readonly="true" />
                    <webTag:Text id="order_num" name="order_num" displayLable="排序优先级" value='${data.order_num}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="sys_status" value='${data.status}' readonly="true" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goBankDepositQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
