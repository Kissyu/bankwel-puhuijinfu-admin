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
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/compent/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script>
        $(function(){
            var toolbars = [
                ['fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', 'formatmatch', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                    'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                    'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                    'simpleupload', 'insertvideo', 'attachment',  '|',
                    'horizontal', 'date', 'time', 'spechars', 'wordimage', '|',
                    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|',
                    'preview', 'searchreplace', 'help']
            ];

            var desc_description = new baidu.editor.ui.Editor({
                initialFrameWidth:650,
                initialFrameHeight:150,
                autoHeightEnabled:false,//全屏再缩小后仍然保持原来尺寸
                toolbars:toolbars
            });
            desc_description.render("opencard_need_material");
            $("#mainForm").validate();
            upload_img({id:'bt_card_bgimg_file',callback:function(result){
                $('#bt_card_bgimg').val(result.data.url);
                $("#bt_card_bgimg_show").show();
                $('#bt_card_bgimg_show').attr('src',result.data.url);
            }});
            upload_img({id:'bt_logo_file',callback:function(result){
                $('#bt_logo').val(result.data.url);
                $("#bt_logo_show").show();
                $('#bt_logo_show').attr('src',result.data.url);
            }});
            upload_img({id:'bt_card_img_file',callback:function(result){
                $('#bt_card_img').val(result.data.url);
                $("#bt_card_img_show").show();
                $('#bt_card_img_show').attr('src',result.data.url);
            }});
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };
        })
    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>银行信息管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行类型管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/bankinfo/addBankType" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="bt_id" name="bt_id" value="${data.bt_id}"/>
            <webTag:HiddenInputTag id="bt_code" name="bt_code" value="${data.bt_code}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言:" value='${data.language_show}' readonly="true" maxlength="32"/>
                    <webTag:Text id="name" name="name" displayLable="名称" value='${data.name}' isRequired="true" maxlength="100"/>
                    <webTag:Text id="service_telephone" name="service_telephone" displayLable="客服电话" value='${data.service_telephone}' maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:ImgUploadTag id="bt_logo" name="bt_logo" displayLable="LOGO图片" value="${data.bt_logo}" isRequired="true" />
                    <webTag:ImgUploadTag id="bt_card_bgimg" name="bt_card_bgimg" displayLable="二类户银行卡背景图片" value="${data.bt_card_bgimg}" />
                    <webTag:ImgUploadTag id="bt_card_img" name="bt_card_img" displayLable="二类户银行卡开户图片" value="${data.bt_card_img}" />
                </div>
                <div class="row">
                    <webTag:TextareaTag id="opencard_need_material" name="opencard_need_material" rows="16" displayLable="开户所需材料:" value="${data.opencard_need_material}"/>
                </div>
                <div class="row">
                    <webTag:Text id="order_num" name="order_num" displayLable="排序号" value='${data.order_num}' isInt="true" maxlength="11"/>
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255"/>
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goBankTypeQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
