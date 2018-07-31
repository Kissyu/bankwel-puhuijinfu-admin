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
    <link href="${path}/compent/uichose/src/ui-choose.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/uichose/src/ui-choose.js"></script>
    <script>
        var ueditorURL = '/';
        var plate_code;
        $(function(){
            var search_terms = $('#search_terms').ui_choose();
            var strS = '${data.search_terms}';
            search_terms.val(strS.split(","));
            search_terms.change = function(value, item) {
                //console.log('change', value);
                $('#search_termsX').val(value);
            };

            var policy_plate = $('#policy_plate').ui_choose();
            var strP = '${data.policy_plate}';
            policy_plate.val(strP.split(","));
            policy_plate.change = function(value, item) {
                //console.log('change', value);
                $('#policy_plateX').val(value);
            };

            var toolbars = [
                ['fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', 'formatmatch', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                    'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                    'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                    'simpleupload',  '|',
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
            upload_img({id:'logo_file',callback:function(result){
                $('#logo').val(result.data.url);
                $("#logo_show").show();
                $('#logo_show').attr('src',result.data.url);
            }});

            upload_img({id:'banner_logo_file',callback:function(result){
                $('#banner_logo').val(result.data.url);
                $("#banner_logo_show").show();
                $('#banner_logo_show').attr('src',result.data.url);
            }});

            $("#mainForm").validate({
//                rules: {
//                    plate_code: {
//                        getNewsPlate: []
//                    }
//                },
//                onkeyup: false
            });

            //新闻版块信息自动带出
            jQuery.validator.addMethod("getNewsPlate",function(value,element) {
                var plateFlag = true;
                if (plate_code == value) {
                    return plateFlag;
                }
                var newsPlate = CommonPubAjax.getNewsPlate(value);

                $("#plate_name").val(CommonPubAjax.isEmpty(newsPlate) ? "" : newsPlate.name);
                plate_code = value;
                plateFlag = CommonPubAjax.isEmpty(newsPlate) ? false : true;
                return plateFlag;
            },"新闻版块编号不存在，请重新录入。");

        });

    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>惠民政策管理</span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>惠民政策管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/policy/addHuiminPolicy" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="pa_id" name="pa_id" value="${data.pa_id}"/>
            <webTag:HiddenInputTag id="article_code" name="article_code" value="${data.article_code}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言" value='${data.language_show}' readonly="true"/>
                    <webTag:Text id="title" name="title" displayLable="标题" value='${data.title}' isRequired="true" maxlength="255"/>
                    <webTag:Text id="source" name="source" displayLable="来源" value='${data.source}' maxlength="50" />
                </div>
                <div class="row">
                    <webTag:Text id="source_url" name="source_url" displayLable="来源链接" value='${data.source_url}' maxlength="255" />
                    <webTag:Date id="publish_date" name="publish_date" displayLable="发表日期" value='${data.publish_date}' />
                    <webTag:Date id="auto_publish_date" name="auto_publish_date" displayLable="自动发布日期" value='${data.auto_publish_date}' />
                </div>
                <div class="row">
                    <webTag:ProvincesSelectTag id="province" name="province" displayLable="省" value='${data.province}' onchange="provinceChange();" isRequired="true"/>
                    <webTag:CitiesSelectTag id="city" name="city" displayLable="市" value='${data.city}' provinceId='${data.province}' onchange="cityChange();"/>
                    <webTag:AreasSelectTag id="area" name="area" displayLable="区" value='${data.area}' cityId="${data.city}"/>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="content" name="content" rows="16" displayLable="内容:" value="${data.content}" isRequired="true"/>
                </div>
                <div class="row">
                    <webTag:ImgUploadTag id="logo" name="logo" displayLable="LOGO" value="${data.logo}" />
                    <webTag:ImgUploadTag id="banner_logo" name="banner_logo" displayLable="轮播图" value="${data.banner_logo}" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_recom" name="is_recom" displayLable="是否推荐" parentCode="sys_isRecom" value='${data.is_recom}'  />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_banner" name="is_banner" displayLable="是否轮播" parentCode="sys_isBanner" value='${data.is_banner}'  />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_top" name="is_top" displayLable="是否置顶" parentCode="sys_yesNo" value='${data.is_top}'  />
                </div>
                <div class="row">
                    <%--动态标签：板块大类、搜索条件：户籍类型、所在地区、身份、筛选--%>
                    <webTag:PolicyPlateSelectTag id="policy_plate" name="policy_plate" displayLable="板块大类标签" language = '${data.language}' value='${data.policy_plate}' topStyle="width: 90%;"/>
                </div>
                <div class="row">
                    <%--动态标签：板块大类、搜索条件：户籍类型、所在地区、身份、筛选--%>
                    <webTag:SearchTermsSelectTag id="search_terms" name="search_terms" displayLable="搜索条件标签" language = '${data.language}' value='${data.search_terms}' topStyle="width: 90%;"/>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/policy/goHuiminPolicyQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
