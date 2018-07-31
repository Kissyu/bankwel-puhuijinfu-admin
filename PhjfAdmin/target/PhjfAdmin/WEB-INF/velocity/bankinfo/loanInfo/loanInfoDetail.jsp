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
            var con_description = new baidu.editor.ui.Editor({
                initialFrameWidth:650,
                initialFrameHeight:150,
                autoHeightEnabled:false,//全屏再缩小后仍然保持原来尺寸
                toolbars:toolbars
            });
            var mat_description = new baidu.editor.ui.Editor({
                initialFrameWidth:650,
                initialFrameHeight:150,
                autoHeightEnabled:false,//全屏再缩小后仍然保持原来尺寸
                toolbars:toolbars
            });
            var item_description = new baidu.editor.ui.Editor({
                initialFrameWidth:650,
                initialFrameHeight:150,
                autoHeightEnabled:false,//全屏再缩小后仍然保持原来尺寸
                toolbars:toolbars
            });
            desc_description.render("loan_desc");
            con_description.render("apply_condition");
            mat_description.render("apply_need_materials");
            item_description.render("apply_need_item");
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
        <span><i class="icon-list icon-red"></i>贷款产品管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/newsManage/addNewsPlate" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言:" value='${data.language_show}' readonly="true" maxlength="32"/>
                    <webTag:BankTypeSelectTag id="bt_code" name="bt_code" displayLable="银行类型" value='${data.bt_code}' readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="loan_plate" name="loan_plate" displayLable="所属版块" parentCode="loan_plate" value='${data.loan_plate}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="loan_use_type" name="loan_use_type" displayLable="用途" parentCode="loan_useType" value='${data.loan_use_type}' readonly="true" />
                    <webTag:Text id="name" name="name" displayLable="产品名称" value='${data.name}' readonly="true"/>
                    <webTag:Text id="tags" name="tags" displayLable="产品关键字" value='${data.tags}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:ImgUploadTag id="logo" name="logo" displayLable="宣传图片" value="${data.logo}" isShowImg="true" readonly="true"/>
                    <webTag:Text id="loan_term_lower" name="loan_term_lower" displayLable="期限下限（月）" value='${data.loan_term_lower}' readonly="true" />
                    <webTag:Text id="loan_term_upper" name="loan_term_upper" displayLable="期限上限（月）" value='${data.loan_term_upper}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="loan_amount_lower" name="loan_amount_lower" displayLable="额度下限（万元）" value='${data.loan_amount_lower}' readonly="true" />
                    <webTag:Text id="loan_amount_upper" name="loan_amount_upper" displayLable="额度上限（万元）" value='${data.loan_amount_upper}' readonly="true"  maxlength="32"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="source" name="source" displayLable="来源" parentCode="loan_source" value='${data.source}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="loan_rate_lower" name="loan_rate_lower" displayLable="利率下限（年）" value='${data.loan_rate_lower}'  maxlength="32" readonly="true"/>
                    <webTag:Text id="loan_rate_upper" name="loan_rate_upper" displayLable="利率上限（年）" value='${data.loan_rate_upper}' readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_recom" name="is_recom" displayLable="是否推荐" parentCode="sys_isRecom" value='${data.is_recom}' readonly="true"  />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="loan_status" value='${data.status}' readonly="true" />
                    <webTag:Text id="order_num" name="order_num" displayLable="排序号" value='${data.order_num}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:HtmlTextTag id="loan_desc" name="loan_desc" displayLable="产品说明:" value="${data.loan_desc}" style="span10"/>
                </div>
                <div class="row">
                    <webTag:HtmlTextTag id="apply_condition" name="apply_condition" displayLable="申请条件:" value="${data.apply_condition}" style="span10"/>
                </div>
                <div class="row">
                    <webTag:HtmlTextTag id="apply_need_materials" name="apply_need_materials" displayLable="所需材料:" value="${data.apply_need_materials}" style="span10"/>
                </div>
                <div class="row">
                    <div class="control-group span8">
                        <label class="control-label" for="loan_rate_lower">申请信息项:</label>
                        <div class="controls" style="height: 28px;line-height: 28px;">
                            <input type="checkbox" name="apply_need_item" value="" id="Checkbox1" disabled="disabled" checked>真实姓名&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="apply_need_item" value="" id="Checkbox2" disabled="disabled" checked>身份证号&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="apply_need_item" value="" id="Checkbox3" disabled="disabled" checked>贷款金额&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="apply_need_item" value="" id="Checkbox4" disabled="disabled" checked>贷款期限
                        </div>
                    </div>                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goLoanQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
