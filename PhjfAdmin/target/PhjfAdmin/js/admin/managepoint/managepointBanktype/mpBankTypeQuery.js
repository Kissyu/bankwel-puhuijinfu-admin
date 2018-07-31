$.ajaxSetup({
	cache : false,
	traditional : true
});
$(function(){
	$PC.init();
	$('.sky').hide();
});
var PageCtrl = $PC = function(){
	
	var me = null, $CL = null, mpCode = null;
	
	var _tools = [{
		id:'addRow',
		text:'添加',
		iconCls:'icon-add',
		handler:function(){
			top.changeTab("办理点银行管理", "办理点银行管理","", path + '/phjfht/api/v1/managepoint/goAddMpBankTypePage',false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			 if(!row){
			 	alert('请选中要修改的记录！');
			 	return;
			 }
			top.changeTab("办理点银行管理", "办理点银行管理","", path + '/phjfht/api/v1/managepoint/goModifyMpBankPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要删除的助农办理点与银行关系！');
				return;
			}
			deleteAll(row.seq_uuid);
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
			$CL = $('#mpBanktype_grid');
			mpCode = $("#mp_code").val();
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:path+'/phjfht/api/v1/managepoint/queryMpBankByPage',
   				queryParams: {
					mp_code:mpCode
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
                    ,{field:'mp_code', title:'办理点代码',width:40}
					,{field:'mp_name', title:'办理点名称',width:130}
					,{field:'bank_code', title:'银行代码',width:30}
					,{field:'bank_name', title:'银行名称',width:110}
					,{field:'is_open_account_show', title:'是否可预约开户',width:50}
					,{field:'start_date', title:'业务建立日期',width:40,formatter:formateDateYYMMDD}
					,{field:'end_date', title:'业务终止日期',width:40,formatter:formateDateYYMMDD}
					,{field:'status_show', title:'状态',width:20}
					,{field:'is_show', title:'终端是否展现',width:40}
                    ,{field:'create_time', title:'创建时间',width:60}
				]],
			    toolbar: _tools,
			    onClickRow: function(rowIndex, rowData) {
			        curRow = rowData;
			        lastIndex = rowIndex;
			    }
			});
			$('#bank_info').combotree({
                url:'/phjfBankInfo/getBankTree.htm'
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
				$('#mpCode').val(mpCode);
			});
			//表格右键
			$('#right_menu').menu({
				onClick:function(item){
					me[item.id]();
				}
			});
	
		},
		addItem:function(){
			$('#addRow').trigger('click');
		},
		updateItem:function(){
			$('#updateRow').trigger('click');
		},
		mutiDelete:function(){
			$('#mutiDeleteRow').trigger('click');
		},
		singleDelete:function(){
			$('#deleteRow').trigger('click');
		},
		viewItem:function(){
			$('#viewRow').trigger('click');
		}
	};
}();

function reloadGrid(){
	$('#mpBanktype_grid').datagrid("clearChecked");
	$('#mpBanktype_grid').datagrid("clearSelections");
	$('#mpBanktype_grid').datagrid('reload');
}


function deleteAll(seq_uuid){
	confirm('确定失效吗？',{
		onOK:function(){
			$.getJSON(path + '/phjfht/api/v1/managepoint/deleteMpBank',{seq_uuid:seq_uuid},function(result){
				if(result.code != '1'){
					alert(result.msg);
					return;
				} else {
					alert(result.msg);
				}
				reloadGrid();
			});
		}
	});		
}

