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
			$CL = $('#phjfUserBankcardAppointment_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/user/queryUserBankcardAppointmentByPage',
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
					{field:'uba_id', checkbox:true}
					,{field:'bank_name', title:'开户行',width:40}
                    ,{field:'mp_code', title:'办理点编号',width:40}
					,{field:'mp_name', title:'办理点名称',width:40}
                    ,{field:'user_name', title:'会员账户',width:40}
					,{field:'true_name', title:'真实姓名',width:40}
					,{field:'mobilephone', title:'手机号',width:40}
					,{field:'am_time', title:'预约时间',width:40}
					,{field:'bank_accept_time', title:'银行接受时间',width:40}
					,{field:'bank_refuse_time', title:'银行拒绝时间',width:40}
					,{field:'bank_opt_name', title:'银行办理人',width:40}
					,{field:'status_show', title:'状态',width:40}
					,{field:'is_show', title:'终端是否展现',width:40}
                    ,{field:'create_time', title:'创建时间',width:40}
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
			//表格右键
			$('#right_menu').menu({
				onClick:function(item){
					me[item.id]();
				}
			});
		},
		informBank:function(){
			$('#informBank').trigger('click');
		},
		viewItem:function(){
			$('#viewRow').trigger('click');
		}
	};
}();

function reloadGrid(){
	$('#phjfUserBankcardAppointment_grid').datagrid("clearChecked");
	$('#phjfUserBankcardAppointment_grid').datagrid("clearSelections");
	$('#phjfUserBankcardAppointment_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON(ctx + '/phjfUserBankcardAppointment/delete.htm',{ids:ids},function(result){
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
function getBaStatus(value) {
	var status = ["","已提交","银行处理中","已预约","退回","已完成","取消"];
	return status[value];
}

