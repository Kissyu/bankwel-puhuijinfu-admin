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
        <span class=mrl14><i class="icon-list icon-red"></i>银行信息管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行账号管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>修改</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/bankinfo/modifyBankOptinfo" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="bank_code" name="bank_code" displayLable="银行机构编号" value='${data.bank_code}' isRequired="true" readonly="true" />
                    <webTag:Text id="bank_name" name="bank_name" displayLable="银行机构名称" value='${data.bank_name}' readonly="true" />
                    <webTag:Text id="bank_contact" name="bank_contact" displayLable="银行操作人员" value='${data.bank_contact}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="name" name="name" displayLable="姓名" value='${data.name}' isRequired="true" maxlength="32" />
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="移动电话" value='${data.mobilephone}' isMobile="true" isRequired="true" readonly="true" maxlength="11"/>
                    <webTag:Text id="telephone" name="telephone" displayLable="固定电话" value='${data.telephone}' isPhone="true" maxlength="32" />
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="certi_type" name="certi_type" displayLable="证件类型" parentCode="sys_idType" value='${data.certi_type}' />
                    <webTag:Text id="certi_no" name="certi_no" displayLable="证件号码" value='${data.certi_no}' maxlength="32"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="gender" name="gender" displayLable="性别" parentCode="sys_gender" value='${data.gender}' />
                </div>
                <div class="row">
                    <webTag:Text id="email" name="email" displayLable="邮箱" value='${data.email}' isEmail="true" maxlength="100"/>
                    <webTag:PasswordTag id="password" name="password" value='${data.password}' displayLable="登录密码" minlength="8" readonly="true"/>
                    <webTag:PasswordTag id="confirmPassword" name="confirmPassword" value='${data.password}' displayLable="确认密码" minlength="8" readonly="true"/>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="address" name="address" displayLable="地址:" rows="5" value='${data.address}' maxlength="255"/>
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="终端是否展现" parentCode="sys_isShow" value='${data.is_show}'  />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goBankOptQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
