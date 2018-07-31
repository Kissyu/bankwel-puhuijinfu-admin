/**
 * Created by bankwel on 2017/9/25.
 */
$.ajaxSetup({
	cache : false
});
$(function(){
	$PC.init();
	$('.sky').hide();
});

var PageCtrl = $PC = function(){
	var operatorId = null;
	var me = null, $CL = null, curNode = null, preNode = null;
	var uesrLoanId = null;
	return{
		/**
		 * 页面初始化
		 */
		init:function(){
			this.initVars();
			this.renderUI();
			this.bindUI();
		},
		/**
		 * 变量初始化
		 */
		initVars:function(){
			me = this;
			uesrLoanId = $('#uesrLoanId').val();
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
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
						tree.expandAll(true);
					}
				}
			};
			var bankTreeObj = $.fn.zTree.init($('#mainBanksTree'), zTreeMainSetting);

			//点击保存发送银行
			$('#saveSendToBank').unbind("click").click(function (e) {
				var bankNodes = bankTreeObj.getCheckedNodes(true);
				var bankArray = [];
				if(bankNodes.length==0) {
					alert("请选择要发送的银行");
				}else{
					for(var i = 0 ;i<bankNodes.length;i++) {
						bankArray.push(bankNodes[i].bank_code);
					}
				}
				//提交
				$.ajax({
				 url: '/phjfht/api/v1/user/saveLoanSendToBanks',
				 async: false,
				 data: {
				 seq_uuid: $('#uesrLoanId').val(),
				 user_id:$('#loanUserId').val(),
				 bankArray:bankArray
				 },
				 dataType: 'json',
				 contentType:"application/json",
				 success: function(result) {
				 removeLoad();
				 if(result.code == 1) {
				 top.changeTab('贷款申请管理',"贷款申请管理","",'/phjfht/api/v1/user/goUserLoanQueryPage',false);
				 }else{
				 alert(result.message);
				 }
				 }
				 });
			});
		},
		/**
		 * 绑定事件和数据
		 */
		bindUI:function(){
		}
	};
}();
function reloadGrid(opId,brId){
	$('#roleManage_grid').datagrid("clearChecked");
	$('#roleManage_grid').datagrid("clearSelections");
}
