$.ajaxSetup({
	cache : false,
	traditional : true
});
$(function(){
	$PC.init();
	$('.sky').hide();
});
var PageCtrl = $PC = function(){
	
	var me = null, $CL = null;
	
	var _tools = [];
	
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
			$CL = $('#phjfUserLoan_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/user/queryUserFeedbackByPage',
   				queryParams: {
        		},
    			fit: true,
			    fitColumns: true,
			    striped: true,
			    rownumbers: true,
			    singleSelect: true,
			    checkOnSelect: false,
			    selectOnCheck: false,
			    nowrap: false,
			    idField: 'seq_uuid',
			    pagination: true,
			    pageSize: 25,
			    pageList: [25, 30, 50],
			    columns:[[
					{field:'seq_uuid', hidden:true}
					,{field:'true_name', title:'姓名',width:40}
					,{field:'mobilephone', title:'手机号',width:40}
					,{field:'content', title:'反馈信息',width:40}
					,{field:'recontent', title:'回馈信息',width:40}
					,{field:'result_status_show', title:'回馈状态',width:40}
					,{field:'create_time', title:'申请时间',width:40}
				]],
			    toolbar: _tools,
			    onClickRow: function(rowIndex, rowData) {
			        curRow = rowData;
			        lastIndex = rowIndex;
			    },
			    onRowContextMenu: function(e, rowIndex, rowData) {
			        e.preventDefault();
			        $CL.datagrid('selectRow', rowIndex);
					$CL.datagrid('options').onClickRow(rowIndex,rowData);
			        $('#right_menu').menu('show', {
			            left: e.pageX,
			            top: e.pageY
			        });
			    },
			    onDblClickRow: function(rowIndex, rowData) {
			        $("#updateRow").trigger("click");
			    },
			});
		},
		/**
		 * 绑定事件和数据
		 */
		bindUI:function(){
			//查询
			$('#searchBtn').click(function(e){
				var data = $('#searchForm').serializeObject();
				data.state = 1;
				$CL.datagrid('load',data);
			});
			//清空查询值
			$('#clearBtn').click(function(e){
				$('#searchForm').form('clear');
			});

	
		}
	};
}();

function reloadGrid(){
	$('#phjfUserLoan_grid').datagrid("clearChecked");
	$('#phjfUserLoan_grid').datagrid("clearSelections");
	$('#phjfUserLoan_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON(ctx + '/phjfLoanInfo/delete.htm',{ids:ids},function(result){
				if(result.code == bk.ResultCode.SERVER_ERR){
					alert('删除出错！');
					return;
				}else{
					if(result.code ==bk.ResultCode.DISABLE_OPERATE){
						alert(result.message || '您没有此操作的权限，请联系管理员！');
						return;
					}
				}
				reloadGrid();
			});
		}
	});		
}

function getProductType(value) {
	var types = ['','个人贷款','扶贫贷款','企业贷款'];
	return types[value];
}
function getLoanType(value) {
	var types = ['','个人信用贷款','个人房产抵押贷款'];
	return types[value];
}

function getProudctStatus(value){
	var status = ['无效','待发布','已发布','已下架'];
	return status[value];
}

function deployAll(loanId,title,type){
	confirm(title,{
		onOK:function(){
			$.getJSON(ctx + '/phjfLoanInfo/'+type+'/deplpyLoanProduct.htm',{loanId:loanId},function(result){
				if(result.code == bk.ResultCode.SERVER_ERR){
					alert('发布出错！');
					return;
				}else{
					if(result.code ==bk.ResultCode.DISABLE_OPERATE){
						alert(result.message || '您没有此操作的权限，请联系管理员！');
						return;
					}
				}
				reloadGrid();
			});
		}
	});
}

