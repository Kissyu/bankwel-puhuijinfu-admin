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
    <script>
        $(function(){
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)});
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)});
            };
        })

    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>货基产品管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>货基产品信息</span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="fp_code" name="fp_code" displayLable="货基产品编号" value='${data.fp_code}' readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="fp_type" name="fp_type" displayLable="理财产品类型" parentCode="fpType" value='${data.fp_type}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="bt_name" name="bt_name" displayLable="产品所属银行" value='${data.bt_name}' readonly="true" maxlength="32"/>
                    <webTag:Text id="name" name="name" displayLable="货基产品名称" value='${data.title}' readonly="true"/>
                    <webTag:Text id="fin_code" name="fin_code" displayLable="第三方产品代码" value='${data.fin_code}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="currency" name="currency" displayLable="币种" parentCode="sys_currencyType" value='${data.currency}' readonly="true" />
                    <webTag:Text id="fin_nav" name="fin_nav" displayLable="产品净值" value='${data.fin_nav}' readonly="true" />
                    <webTag:Text id="fin_nav_total" name="fin_nav_total" displayLable="产品累计净值" value='${data.fin_nav_total}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="fin_income" name="fin_income" displayLable="昨日收益" value='${data.fin_income}' readonly="true"/>
                    <webTag:Text id="fin_income_unit" name="fin_income_unit" displayLable="万份产品单位收益" value='${data.fin_income_unit}' readonly="true" />
                    <webTag:Text id="fin_income_rate" name="fin_income_rate" displayLable="产品收益率" value='${data.fin_income_rate}' readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Text id="amount_min" name="amount_min" displayLable="起售金额" value='${data.amount_min}' readonly="true"/>
                    <webTag:Date id="download_time" name="download_time" displayLable="最后下载或更新时间" value='${data.download_time}' readonly="true" />
                    <webTag:Text id="share_count" name="share_count" displayLable="分享次数" value='${data.share_count}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="click_count" name="click_count" displayLable="点击次数" value='${data.click_count}' readonly="true"/>
                    <webTag:Text id="buy_count" name="buy_count" displayLable="购买次数" value='${data.buy_count}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_recom" name="is_recom" displayLable="是否推荐" parentCode="sys_isRecom" value='${data.is_recom}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="order_num" name="order_num" displayLable="排序号" value='${data.order_num}' readonly="true"/>
                    <webTag:Date id="publish_date" name="publish_date" displayLable="起售日期" value='${data.publish_date}' readonly="true" />
                    <webTag:Date id="under_date" name="under_date" displayLable="下架日期" value='${data.under_date}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="显示状态" parentCode="sys_isShow" value='${data.is_show}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="sys_articleStatus" value='${data.status}' readonly="true" />
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255" readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/financial/goFinancialFundQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
