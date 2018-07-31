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

	var _tools = [{
		id:'viewRow',
		text:'查看',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要查看的用户预约取款信息表！');
				return;
			}
			top.changeTab("预约取款", "预约取款","", path + '/phjfht/api/v1/user/goViewUserWithdrawAmPage?seq_uuid='+row.seq_uuid,false);
		}
	}];

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
			$CL = $('#phjfUserWithdrawAppointment_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
				url:'/phjfht/api/v1/user/queryUserWithdrawAmByPage',
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
					{field:'uwa_id', hidden:true}
                    ,{field:'mp_code', title:'办理点编号',width:40}
					,{field:'mp_name', title:'办理点名称',width:40}
					,{field:'user_name', title:'会员账户',width:40}
					,{field:'true_name', title:'预约取款人姓名',width:40}
					,{field:'nickname', title:'昵称',width:40}
					,{field:'mobilephone', title:'手机号',width:40}
					,{field:'amount', title:'取款金额',width:40}
					,{field:'am_date', title:'预约办理时间',width:40}
					,{field:'accept_time', title:'接受时间',width:40}
					,{field:'refuse_time', title:'拒绝时间',width:40}
					,{field:'cancel_time', title:'取消时间',width:40}
					,{field:'mp_opt', title:'办理点办理人',width:40}
					,{field:'remark', title:'备注',width:40}
					,{field:'status_show', title:'状态',width:40}
					,{field:'is_show_name', title:'终端是否展现',width:40}
                    ,{field:'create_time', title:'创建时间',width:40}

				]],
				toolbar: _tools,
				onClickRow: function(rowIndex, rowData) {
					curRow = rowData;
					lastIndex = rowIndex;
				},
				// onRowContextMenu: function(e, rowIndex, rowData) {
				// 	e.preventDefault();
				// 	$CL.datagrid('selectRow', rowIndex);
				// 	$CL.datagrid('options').onClickRow(rowIndex,rowData);
				// 	$('#right_menu').menu('show', {
				// 		left: e.pageX,
				// 		top: e.pageY
				// 	});
				// },
				// onDblClickRow: function(rowIndex, rowData) {
				// 	$("#updateRow").trigger("click");
				// }
			});
		},
		/**
		 * 绑定事件和数据
		 */
		bindUI:function(){
			//查询
			$('#searchBtn').click(function(e){
				var data = $('#searchForm').serializeObject();
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

		},viewItem:function(){
			$('#viewRow').trigger('click');
		}
	};
}();

function reloadGrid(){
	$('#phjfUserWithdrawAppointment_grid').datagrid("clearChecked");
	$('#phjfUserWithdrawAppointment_grid').datagrid("clearSelections");
	$('#phjfUserWithdrawAppointment_grid').datagrid('reload');
}

