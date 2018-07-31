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
            upload_img({id:'img_url_file',callback:function(result){
                $('#img_url').val(result.data.url);
                $("#img_url_show").show();
                $('#img_url_show').attr('src',result.data.url);
            }});
            $("#mainForm").validate();
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
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfwebht/api/v1/webCms/addBanner" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="banner_id" name="banner_id" value="${data.banner_id}"/>
            <webTag:HiddenInputTag id="banner_code" name="banner_code" value="${data.banner_code}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="contents" name="contents" displayLable="文字叙述" value='${data.contents}' maxlength="255" isRequired="true" />
                    <webTag:Text id="url" name="url" displayLable="链接地址" value='${data.url}' maxlength="255" isRequired="true" />
                </div>
                <div class="row">
                    <webTag:ImgUploadTag id="img_url" name="img_url" displayLable="图片" value="${data.img_url}" isRequired="true" isShowImg="true" />
                    <webTag:Text id="orders" name="orders" displayLable="排序号" value='${data.orders}' isInt="true" maxlength="11" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfwebht/api/v1/webCms/goBannerQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
