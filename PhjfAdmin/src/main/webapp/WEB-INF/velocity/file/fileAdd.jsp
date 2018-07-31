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
        $(document).ready(function () {
            $("#file").uploadify({ //上传
                'swf': '<%=basePath %>/uploadify/uploadify.swf', //swf 文件路径
                'uploader': '<%=basePath %>/phjfht/api/v1/uploadFileByPath',    //调用方法进行xlsx文件的导入
                'script': '<%=basePath %>/phjfht/api/v1/uploadFileByPath',//后台处理的请求,对应Controller
                'fileObjName': 'fileupload',      //文件上传的名称
                'folder': 'upload',       //您想将文件保存到的路径
                'queueID': 'fileQueue',//与下面的id对应
                'queueSizeLimit': 20,
                'width': 80,
                'height' : 25,
                'buttonClass': 'buttonCss',/*  btn btn-danger*/
                'removeCompleted': true,
                'fileTypeDesc': 'Excel Files',    //文件类型的说明
                'fileTypeExts': '*.xls;*.apk;*.pdf;*.jpg;*.png',    //指定允许上传的文件类型
                'method': 'post', //发送方式，默认post
                'auto': true, //是否自动上传，设成true后，自动执行uploadify('upload','*') 方法
                'multi': true,// 是否支持多文件上传
                'progressData': 'speed', //文件上传时显示的数据，显示'speed' 速度，'percentage' 显示百分比
                'simUploadLimit': 20,
                'removeTimeout': '1',  //上传成功后进度条消失时间,秒
                'requeueErrors': false,  //若设置为True，那么在上传过程中因为出错导致上传失败的文件将被重新加入队列。
                'fileSizeLimit': '100MB', //上传文件大小限制，默认单位是KB 限制2M
                'buttonText': '上传',
                'onFallback': function () { //无Flash时触发
                    alert('没有支持的Flash版本，请更新Flash版本!');
                },
                'onUploadSuccess': function (file, data, response) { // 每个文件文件上传成功时都会触发的事件
                    try {
                        var result = $.parseJSON(data);
                        if (result.code == '1') {
                            $("#upMsg").text(result.data.url);
                            alertInfo($('form'), 'success', '上传成功! ');
                        }else{
                            alertInfo($('form'), 'error', '上传失败');
                            $("#upMsg").text(result.msg);
                        }
                    } catch (e) {}
                },
                'onUploadError': function (file, errorCode, errorMsg, errorString) {
                    //alert(errorMsg);
                },
                'onSelectError': function (file) {

                },
                'onUploadStart': function (file) { //动态参数设置
                    var uploadPath = $("#uploadPath").val();
                    $("#file").uploadify("settings", "formData", { 'uploadPath': uploadPath });
                        //在onUploadStart事件中，也就是上传之前，把参数写好传递到后台。

                }
            });

            function alertInfo(alertTarget, level, msg) {
                $("#msg").remove();

                alertTarget.prepend("<div class='alert alert-"+level+"' id ='msg' name='msg'><strong>提示信息：</strong> " + msg + "</div>");

            }
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>文件管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <%--<div class="row-fluid">--%>
        <form enctype ="multipart/form-data" action="" method="POST">

            <webTag:HiddenInputTag id="path" name="path" value='<%=basePath %>'/>
            <div class="overAuto row-fluid">
                <table class='table table-striped table-bordered bootstrap-datatable datatable'>
                    <tr>
                        <td width="50px"><i class="icon-file icon-red"></i> 路径</td>
                        <td ><input type="text" name="uploadPath" id="uploadPath" />
                        </td>
                    </tr>
                    <tr>
                        <td width="50px"><i class="icon-file icon-red"></i> 导入</td>
                        <td ><input type="file" name="file" id="file" />
                            <div id="fileQueue"></div>
                        </td>
                    </tr>
                    <tr>
                        <td><i class="icon-exclamation-sign icon-red"></i> 上传URL</td>
                        <td colspan="3">
                            <textarea id="upMsg" rows="15" name="upMsg" style="width: 800px;" readonly="readonly"></textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    <%--</div>--%>
    <!-- 数据区 end -->
</div>
<!-- 		底部高度填充块 -->
<div class="zeoBottom"></div>
<!-- 		底部高度填充块 结束-->
</body>
</html>
