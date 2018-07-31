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
    <%--<jsp:include page="/WEB-INF/jsp/akc/akccore/pub/backAkcPageHelper.jsp" flush="true"/>--%>

    <script>
        var ueditorURL = '/';
        $(function(){

            var toolbars = [
                ['fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', 'formatmatch', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                    'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                    'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                    'simpleupload', '|',
                    'horizontal', 'date', 'time', 'spechars', 'wordimage', '|',
                    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|',
                    'preview', 'searchreplace', 'help']
            ];

            var ue_description = new baidu.editor.ui.Editor({
                initialFrameWidth:650,
                initialFrameHeight:150,
                autoHeightEnabled:false,//全屏再缩小后仍然保持原来尺寸
                toolbars:toolbars
            });
            ue_description.render("content");
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };
        });

    </script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>新闻管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新闻文章管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfwebht/api/v1/webNews/addNewsArticle" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="plate_code" name="np_code" displayLable="版块编号" value='${data.np_code}' readonly="true"/>
                    <webTag:Text id="plate_name" name="np_name" displayLable="版块名称" value='${data.np_name}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="title" name="title" displayLable="标题" value='${data.title}' readonly="true" isRequired="true" />
                    <webTag:Text id="source" name="source" displayLable="来源" value='${data.source}' readonly="true" />
                    <webTag:Date id="publish_date" name="publish_date" displayLable="发表日期" value='${data.publish_date}' />
                </div>
                <div class="row">
                    <webTag:TextareaTag id="content" name="content" rows="16" displayLable="内容:" value="${data.content}" isRequired="true" readonly="true"/>
                </div>
                <div class="row">
                    <webTag:Date id="auto_publish_date" name="auto_publish_date" displayLable="自动发布日期" value='${data.auto_publish_date}' readonly="true" />
                    <webTag:Text id="tags" name="tags" displayLable="标签:" value='${data.tags}' readonly="true" />
                    <webTag:ImgUploadTag id="logo" name="logo" displayLable="LOGO" value="${data.logo}" readonly="true" isShowImg="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_recom" name="is_recom" displayLable="是否推荐" parentCode="sys_isRecom" value='${data.is_recom}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_top" name="is_top" displayLable="是否置顶" parentCode="sys_yesNo" value='${data.is_top}' readonly="true" />
                </div>
                <div class="row">
                </div>
            </fieldset>
            <div class="row" align="right">
                <c:if test="${data.language == 'ZH_SIMP'}">
                    <a class="btn btn-danger" href="/phjfwebht/api/v1/webNews/goAddSimpNewsArticlePage"><i class="icon-inbox icon-white"></i>录入下一个</a>
                </c:if>
                <a class="btn btn-danger" href="/phjfwebht/api/v1/webNews/goNewsInfoQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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