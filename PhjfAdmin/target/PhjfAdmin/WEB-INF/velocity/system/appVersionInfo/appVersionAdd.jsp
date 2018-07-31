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

    <link href="/uploadify/uploadify.css" rel="stylesheet" type="text/css">
    <link href="/uploadify/uploadimg.css" rel="stylesheet" type="text/css">
    <script src="/uploadify/swfobject.js" type="text/javascript"></script>
    <script src="/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
    <script>
        var app_id;
        $(function(){
            $("#mainForm").validate({
                rules: {
                    app_id: {
                        getApp : []
                    }
                },
                onkeyup: false
            });

            //app信息自动带出
            jQuery.validator.addMethod("getApp",function(value,element) {
                var appFlag = true;
                if (app_id == value) {
                    return appFlag;
                }
                var app = CommonPubAjax.getApp(value);
                $("#app_name").val(CommonPubAjax.isEmpty(app) ? "" : app.app_name);
                $("#package_name").val(CommonPubAjax.isEmpty(app) ? "" : app.package_name);
                app_id = value;
                appFlag = CommonPubAjax.isEmpty(app) ? false : true;
                return appFlag;
            },"app编号不存在，请重新录入。");

            $("#app_url_file").uploadify({ //上传
                'swf': '<%=basePath %>/uploadify/uploadify.swf', //swf 文件路径
                'uploader': '<%=basePath %>/phjfht/api/v1/uploadFile',    //调用方法进行xlsx文件的导入
                'script': '<%=basePath %>/phjfht/api/v1/uploadFile',//后台处理的请求,对应Controller
                'fileObjName': 'fileupload',      //文件上传的名称
                'folder': '/upload',       //您想将文件保存到的路径
                'queueID': 'fileQueue',//与下面的id对应
                'queueSizeLimit': 20,
                'width': 55,
                'height' : 25,
                'buttonClass': 'buttonCss',/*  btn btn-danger*/
                'removeCompleted': true,
                'fileTypeDesc': 'Excel Files',    //文件类型的说明
                'fileTypeExts': '*.xls;*.apk',    //指定允许上传的文件类型
                'method': 'post', //发送方式，默认post
                'auto': true, //是否自动上传，设成true后，自动执行uploadify('upload','*') 方法
                'multi': true,// 是否支持多文件上传
                'progressData': 'speed', //文件上传时显示的数据，显示'speed' 速度，'percentage' 显示百分比
                'simUploadLimit': 20,
                'removeTimeout': '1',  //上传成功后进度条消失时间,秒
                'requeueErrors': false,  //若设置为True，那么在上传过程中因为出错导致上传失败的文件将被重新加入队列。
                'fileSizeLimit': '100MB', //上传文件大小限制，默认单位是KB 限制2M
                'successTimeout': 300,
                'buttonText': '上传',
                'onFallback': function () { //无Flash时触发
                    alert('没有支持的Flash版本，请更新Flash版本!');
                },
                'onUploadSuccess': function (file, data, response) { // 每个文件文件上传成功时都会触发的事件
                    try {
                        var result = $.parseJSON(data);
                        if (result.code == '1') {
                            $("#app_url").val(result.data.url);
                        }else{
                            $("#app_url").val("");
                        }
                    } catch (e) {}
                },
                'onUploadError': function (file, errorCode, errorMsg, errorString) {
                    //alert(errorMsg);
                },
                'onSelectError': function (file) {

                },
                'onUploadStart': function (file) { //动态参数设置

                }
            });

        })
    </script>
</head>
<body >
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>系统管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>app管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>app发布管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/system/addAppVersion" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <div class="row">
                    <webTag:Text id="app_id" name="app_id" displayLable="app编号" value='${data.app_id}' isRequired="true" maxlength="11" isInt="true"/>
                    <webTag:Text id="app_name" name="app_name" displayLable="app名称" value='${data.app_name}' readonly="true" />
                    <webTag:Text id="package_name" name="package_name" displayLable="包名" value='${data.package_name}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="device_type" name="device_type" displayLable="设备类型" parentCode="sys_deviceType" value='${data.device_type}' isRequired="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="channel" name="channel" displayLable="渠道" parentCode="sys_channel" value='${data.channel}' isRequired="true" />
                    <webTag:Text id="app_version" name="app_version" displayLable="版本" value='${data.app_version}' isRequired="true" maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_update" name="is_update" displayLable="是否强制更新" parentCode="sys_isUpdate" value='${data.is_update}' isRequired="true" />
                    <webTag:Text id="h5_url" name="h5_url" displayLable="H5下载地址" value='${data.h5_url}' maxlength="255" />
                    <%--<webTag:Text id="app_url" name="app_url" displayLable="安装包下载地址" value='${data.app_url}' />--%>
                    <webTag:FileUploadTag id="app_url" name="app_url" displayLable="安装包下载地址" value="${data.app_url}" />
                </div>
                <div class="row">
                    <webTag:Text id="qr_code_url" name="qr_code_url" displayLable="二维码下载地址" value='${data.qr_code_url}' maxlength="255" />
                    <webTag:TextareaTag id="change_content" name="change_content" displayLable="更新内容:" rows="5" maxlength="255" value='${data.change_content}' />
                    <webTag:Text id="size" name="size" displayLable="安装包大小" value='${data.size}' isDecimal="true" maxlength="11" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/system/goAppVersionQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
