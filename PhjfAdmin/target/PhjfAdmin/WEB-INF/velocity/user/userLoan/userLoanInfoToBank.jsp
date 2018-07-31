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
        var uesrLoanId = null;
        $(function(){
            uesrLoanId = $('#uesrLoanId').val();
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };
            //树形控件展现设置
            var zTreeMainSetting = {
                data: {
                    simpleData: {enable:true, idKey: "bank_code", pIdKey: "parent_bank_code", nameKey:"name"}
                },
                check: {
                    enable: true,
                    chkStyle: "checkbox",
                    radioType: "all"
                }
                ,async : {
                    enable : true,//设置 zTree 是否开启异步加载模式
                    url : "/phjfht/api/v1/user/queryBankByUserLoanId",//Ajax 获取数据的 URL 地址
                    otherParam:["seq_uuid",uesrLoanId]
                },
                callback: {
                    onAsyncSuccess:function (event, treeId, treeNode, msg) {
                        var tree = $.fn.zTree.getZTreeObj(treeId);
                        tree.expandAll(false);
                    }
                }
            };
            var bankTreeObj = $.fn.zTree.init($('#mainBanksTree'), zTreeMainSetting);

            //点击保存发送银行
            $('.saveSendToBank').unbind("click").click(function (e) {
                var bankNodes = bankTreeObj.getCheckedNodes(true);
                var bankArray = [];
                if(bankNodes.length==0) {
                    alert("请选择要发送的银行");
                    return false;
                }else{
                    for(var i = 0 ;i<bankNodes.length;i++) {
                        bankArray.push(bankNodes[i].bank_code);
                    }
                }
                //提交
                $.ajax({
                    url: '/phjfht/api/v1/user/saveLoanSendToBanks',
                    async: true,
                    data: {
                        seq_uuid: $('#uesrLoanId').val(),
                        user_id:$('#loanUserId').val(),
                        bankArray:bankArray,
                        remark:$('#selfRemark').val()
                    },
                    dataType: 'json',
                    contentType:"application/json",
                    success: function(result) {
                        if(result.code == 1) {
                            location.replace(location.href);
                        }else{
                        }
                    }
                });
            });
        })
    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>贷款申请 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>贷款发送银行 </span><span class="divider">/</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <div id="mainForm" class="form-horizontal alert alert-info fade in span12" style="position: relative;">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <fieldset>
                <input type="hidden" value="${data.userInfo.user_id}" id="loanUserId">
                <input type="hidden" value="${data.userLoanInfo.seq_uuid}" id="uesrLoanId">
                <input type="hidden" value="${data.userLoanInfo.ulp_id}" id="uesrLoanUlpId">
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="贷款人姓名:" value='${data.userLoanInfo.true_name}' readonly="true"/>
                    <webTag:Text id="bt_code" name="bt_code" displayLable="贷款人手机号" value='${data.mobilephone}' readonly="true" />
                    <webTag:Text id="loan_plate" name="loan_plate" displayLable="贷款申请时间"  value='${data.loanApplyTime}' readonly="true"  />
                </div>
                <div class="row">
                    <webTag:Text id="loan_use_type" name="loan_use_type" displayLable="贷款金额" value='${data.userLoanInfo.loan_amount}' readonly="true" />
                    <webTag:Text id="name" name="name" displayLable="贷款用途" value='${data.loanUse}' readonly="true"/>
                    <webTag:Text id="tags" name="tags" displayLable="贷款期限" value='${data.userLoanInfo.loan_use_term}' readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="logo" name="logo" displayLable="贷款状态" value="${data.loanStatus}" readonly="true"/>
                    <div style="float: left;width: 168px;padding-top: 5px;text-align: right;font-size: 13px;line-height:18px; color:#817B5A;">银行列表:</div>
                    <ul style="width: 480px;float: left;margin-top: 10px;margin-bottom: 10px;margin-right: 23px; border: 1px solid #cccccc;" class="clearfix">
                        <li style="width: 100%;height: 330px; overflow: auto;margin: 0 auto;">
                            <div class="zTreeDemoBackground">
                                <ul id="mainBanksTree" class="ztree"></ul>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="row" style="position: absolute; left: 0; top: 138px;">
                    <div style="float: left;width: 153px;padding-top: 5px;text-align: right;font-size: 13px;line-height:18px; color:#817B5A;">备注:</div>
                    <textarea id="selfRemark" style="width: 165px;height: 100px;margin-left: 20px;">${data.platform_remark}</textarea>
                </div>
                <div class="row" align="right">
                    <button class="btn btn-danger saveSendToBank" style="margin-left: 30px;"><i class="icon-inbox icon-white"></i>保存</button>
                    <a class="btn btn-danger" id="reloadSendBank" href="/phjfht/api/v1/user/goUserLoanQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
                </div>
            </fieldset>
        </div>
    </div>
    <!-- 数据区 end -->
</div>
<!-- 		底部高度填充块 -->
<div class="zeoBottom"></div>
<!-- 		底部高度填充块 结束-->
</body>
</html>


