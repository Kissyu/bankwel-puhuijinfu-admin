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
            if($("#open_type").val()=="app"){
                $("#params").parents(".control-group").show();
                $("#http_url").parents(".control-group").hide();
            } else if($("#open_type").val()=="http"){
                $("#params").parents(".control-group").hide();
                $("#http_url").parents(".control-group").show();
            } else if($("#open_type").val()=="none"){
                $("#params").parents(".control-group").hide();
                $("#http_url").parents(".control-group").hide();
            }
        })
    </script>
</head>
<body>
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>内容管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>广告位管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/cms/addBanner" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="location" name="location" displayLable="展现位置" parentCode="sys_location" value='${data.location}' isRequired="true" readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="content_type" name="content_type" displayLable="内容类型" parentCode="sys_contentType" value='${data.content_type}' isRequired="true" readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="open_type" name="open_type" displayLable="打开类型" parentCode="sys_openType" value='${data.open_type}' isRequired="true" readonly="true" />
                    <webTag:Text id="params" name="params" displayLable="移动端链接地址" value='${data.params}' readonly="true" />
                    <webTag:Text id="http_url" name="http_url" displayLable="网页链接地址" value='${data.http_url}' readonly="true"/>
                    <webTag:Text id="order_num" name="order_num" displayLable="排序号" value='${data.order_num}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:ImgUploadTag id="img_url" name="img_url" displayLable="图片" value="${data.img_url}" isRequired="true" readonly="true" isShowImg="true" />
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255" readonly="true"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <c:if test="${data.language == 'ZH_SIMP'}">
                    <a class="btn btn-danger" href="/phjfht/api/v1/cms/goAddSimpBannerPage"><i class="icon-inbox icon-white"></i>录入下一个</a>
                </c:if>
                <a class="btn btn-danger" href="/phjfht/api/v1/cms/goBannerQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
