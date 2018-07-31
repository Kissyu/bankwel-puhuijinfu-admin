<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
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

            $('#amTime_format').on('change',function () {
                if($(this).val()=='yyyy-MM-dd HH:mm:ss'){
                    $('.timeBox').hide();
                    $('#format1').show();
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').attr("isRequired","false");
                    $('#start_time1,#end_time1').attr("isRequired","true");
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').removeAttr("name");
                    $('#start_time1').attr("name","start_time");
                    $('#end_time1').attr("name","end_time");
                }else if($(this).val()=='yyyy-MM-dd') {
                    $('.timeBox').hide();
                    $('#format2').show();
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').attr("isRequired","false");
                    $('#start_time2,#end_time2').attr("isRequired","true");
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').removeAttr("name");
                    $('#start_time2').attr("name","start_time");
                    $('#end_time2').attr("name","end_time");
                }else if($(this).val()=='HH:mm:ss') {
                    $('.timeBox').hide();
                    $('#format3').show();
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').attr("isRequired","false");
                    $('#start_time3,#end_time3').attr("isRequired","true");
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').removeAttr("name");
                    $('#start_time3').attr("name","start_time");
                    $('#end_time3').attr("name","end_time");
                }else {
                    $('.timeBox').hide();
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').attr("isRequired","false");
                    $('#start_time1,#end_time1,#start_time2,#end_time2,#start_time3,#end_time3').removeAttr("name");
                }
            });
        });
    </script>
    <style>
        .timeBox .control-group{
            float: right;
        }
    </style>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>银行预约时间管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行预约时间管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/appointmentTime/addBankAmTime" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="seq_id" name="seq_id" value="${data.seq_id}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="bt_code" name="bt_code" displayLable="银行类型代码" value='${data.institution_code}' isRequired="true" maxlength="32"/>
                    <webTag:Text id="bt_name" name="bt_name" displayLable="银行类型名称" value='${data.bt_name}' readonly="true" maxlength="32"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="bank_function" name="param_code" displayLable="银行业务" parentCode="bank_function" value='${data.param_code}' isRequired="true"  />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="amTime_format" name="time_format" displayLable="业务关闭时间格式" parentCode="amTime_format" value='${data.time_format}' isRequired="true"  />
                    <div id="format1" class="row hide timeBox">
                        <webTag:Date id="start_time1" name="start_time" displayLable="关闭开始时间" dateFormat="yyyy-MM-dd HH:mm:ss" value='${data.start_time}' />
                        <webTag:Date id="end_time1" name="end_time" displayLable="关闭结束时间"  dateFormat="yyyy-MM-dd HH:mm:ss" value='${data.end_time}'/>
                    </div>
                    <div id="format2" class="row hide timeBox">
                        <webTag:Date id="start_time2" name="start_time" displayLable="关闭开始时间" dateFormat="yyyy-MM-dd" value='${data.start_time}'/>
                        <webTag:Date id="end_time2" name="end_time" displayLable="关闭结束时间" dateFormat="yyyy-MM-dd" value='${data.end_time}' />
                    </div>
                    <div id="format3" class="row hide timeBox">
                        <webTag:Date id="start_time3" name="start_time" displayLable="关闭开始时间" dateFormat="HH:mm:ss" value='${data.start_time}' />
                        <webTag:Date id="end_time3" name="end_time" displayLable="关闭结束时间" dateFormat="HH:mm:ss" value='${data.end_time}' />
                    </div>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="describe" name="describe" displayLable="描述" rows="5" value='${data.describe}' maxlength="255" isRequired="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_isShow" name="is_show" displayLable="是否显示" parentCode="sys_isShow" value='${data.is_show}'  />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="sysStatus" name="status" displayLable="状态" parentCode="sys_status" value='${data.status}'  />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/appointmentTime/goQueryBankAppointmentTimePage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
