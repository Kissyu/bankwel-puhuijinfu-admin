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
            $("#mainForm").validate();
        })
    </script>
</head>
<body>
<div class="container-fluid">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>办理点管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>办理点银行管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>修改</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/managepoint/ModifyMpBankType" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="mp_code" name="mp_code" displayLable="办理点代码" value='${data.mp_code}' readonly="true" />
                    <webTag:Text id="mp_name" name="mp_name" displayLable="办理点名称" value='${data.mp_name}' readonly="true" />
                    <webTag:Text id="mp_contact" name="mp_contact" displayLable="办理点联系人" value='${data.mp_contact}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="bank_code" name="bank_code" displayLable="银行代码" value='${data.bank_code}' readonly="true" />
                    <webTag:Text id="bank_name" name="bank_name" displayLable="银行名称" value='${data.bank_name}' readonly="true" />
                    <webTag:Text id="bank_contact" name="bank_contact" displayLable="银行操作人员" value='${data.bank_contact}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_open_account" name="is_open_account" displayLable="是否可预约开户" parentCode="is_open_account" value='${data.is_open_account}'  />
                    <webTag:Date id="start_date" name="start_date" displayLable="业务建立日期" value='${data.start_date}' />
                    <webTag:Date id="end_date" name="end_date" displayLable="业务终止日期" value='${data.end_date}' />
                </div>
                <div class="row">
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="终端是否展现" parentCode="sys_isShow" value='${data.is_show}'  />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/managepoint/goMpBankTypePage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
