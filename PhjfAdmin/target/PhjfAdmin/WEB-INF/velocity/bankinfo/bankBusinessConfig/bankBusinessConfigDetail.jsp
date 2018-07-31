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
    <script src="http://webapi.amap.com/maps?v=1.3&key=c3ea708c2d00e1a63283b111bf120b8d"></script>
    <script src="//webapi.amap.com/ui/1.0/main.js"></script>
    <script>
        $(function(){
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };

            $("#mainForm").validate({
                rules: {
                    bt_code: {
                        getBankType: []
                    }
                },
                onkeyup: false
            });

            //银行信息自动带出
            jQuery.validator.addMethod("getBankType",function(value,element) {
                var flag = true;
                if (bt_code == value) {
                    return flag;
                }
                var bankType = CommonPubAjax.getBankType(value);
                $("#bt_name").val(CommonPubAjax.isEmpty(bankType) ? "" : bankType.name);
                bt_code = value;
                flag = CommonPubAjax.isEmpty(bankType) ? false : true;
                return flag;
            },"银行类型代码不存在，请重新录入。");

        })
    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>银行业务配置管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行业务配置管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>查看</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" >
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="bt_code" name="bt_code" displayLable="银行类型代码" value='${data.bt_code}' isRequired="true" maxlength="32" readonly="true"/>
                    <webTag:Text id="bt_name" name="bt_name" displayLable="银行类型名称" value='${data.bt_name}' readonly="true" maxlength="32"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="business_code" name="business_code" displayLable="银行业务" parentCode="bank_function" value='${data.business_code}'  isRequired="true"  readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="app_version" name="app_version" displayLable="APP版本号" value='${data.app_version}' isRequired="true" maxlength="32"  readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="device_type" name="device_type" displayLable="设备类型" parentCode="sys_deviceType" value='${data.device_type}'  readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="显示状态" parentCode="sys_isShow" value='${data.is_show}'  isRequired="true"  readonly="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="sys_status" value='${data.status}'  isRequired="true"  readonly="true" />
                </div>
                <div class="row">
                    <webTag:TextareaTag id="value" name="value" displayLable="配置信息" rows="20" value='${data.value}' textClass="input-xxlarge"  readonly="true" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goBankBusinessConfigQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
