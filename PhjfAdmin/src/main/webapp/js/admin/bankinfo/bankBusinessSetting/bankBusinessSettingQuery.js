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
		id:'addRow',
		text:'添加',
		iconCls:'icon-add',
		handler:function(){
			top.changeTab("银行业务管理", "银行业务管理","", path + '/phjfht/api/v1/bankinfo/goAddSimpBankBusinessSettingPage',false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中一个要修改的银行业务！');
				return;
			}
			top.changeTab("银行业务管理", "银行业务管理","", path + '/phjfht/api/v1/bankinfo/goModifyBankBusinessSettingPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'viewRow',
		text:'查看',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中一个要查看的银行业务！');
				return;
			}
			top.changeTab("银行业务管理", "银行业务管理","", path + '/phjfht/api/v1/bankinfo/goViewBankBusinessSettingPage?seq_uuid='+row.seq_uuid,false);
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
			$CL = $('#bankType_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/bankinfo/queryBankBusinessSettingByPage',
   				queryParams: {
					language:"ZH_SIMP"
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
					{field:'seq_id', hidden:true}
					,{field:'bt_code', title:'银行类型编号',width:20}
					,{field:'bt_name', title:'银行类型名称',width:40}
					,{field:'type_name', title:'银行业务类型',width:40}
					,{field:'business_code_name', title:'银行业务',width:40}
					,{field:'status_name', title:'状态',width:20}
					,{field:'is_show_name', title:'是否显示',width:20}
					,{field:'create_time', title:'创建时间',width:30}
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
	$('#bankType_grid').datagrid("clearChecked");
	$('#bankType_grid').datagrid("clearSelections");
	$('#bankType_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON(ctx + '/phjfBankBusinessSetting/delete.htm',{ids:ids},function(result){
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

function getLanguage(value) {
	return "ZH_SIMP"==value ? '中文简体':'维吾尔语';
}

function getShow(value) {
	return 1==value ? '显示':'不显示';
}

function getStatus(value) {
	return 1==value ? '有效':'无效';
}