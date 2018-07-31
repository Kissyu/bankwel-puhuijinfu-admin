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
            upload_img({id:'qr_code_url_file',callback:function(result){
                $('#qr_code_url').val(result.data.url);
                $("#qr_code_url_show").show();
                $('#qr_code_url_show').attr('src',result.data.url);
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
        <span><i class="icon-list icon-red"></i>APP下载信息管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>修改</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfwebht/api/v1/webCms/modifyApp" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="adi_id" name="adi_id" value="${data.adi_id}"/>
            <webTag:HiddenInputTag id="adi_code" name="adi_code" value="${data.adi_code}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="adi_code" name="adi_code" displayLable="App编号" value='${data.adi_code}' readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="user_type" name="user_type" displayLable="设备类型" parentCode="sys_deviceType" value='${data.user_type}' readonly="true"  />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="app_type" name="app_type" displayLable="APP类型" parentCode="sys_appType" value='${data.app_type}' readonly="true"  />
                    <webTag:ImgUploadTag id="qr_code_url" name="qr_code_url" displayLable="二维码图片" value="${data.qr_code_url}" isRequired="true" isShowImg="true" />
                    <webTag:TextareaTag id="description" name="description" displayLable="介绍" rows="5" value='${data.description}' maxlength="255"/>
                </div>
                <div class="row">
                    <webTag:Text id="orders" name="orders" displayLable="排序号" value='${data.orders}' isInt="true" maxlength="11" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="终端是否展现" parentCode="sys_isShow" value='${data.is_show}'  />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfwebht/api/v1/webCms/goAppQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
