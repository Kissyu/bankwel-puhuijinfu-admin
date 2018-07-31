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
		text:'添加简体中文',
		iconCls:'icon-add',
		handler:function(){
			top.changeTab("助农办理点管理", "助农办理点管理","", path + '/phjfht/api/v1/managepoint/goAddSimpManagepointPage',false);
		}
	},'-',{
		id:'addRow',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要维护的助农办理点！');
				return;
			}else if(row.status!=1){
				alert('请选中有效的助农办理点！');
				return;
			}
			top.changeTab("助农办理点管理", "助农办理点管理","", path + '/phjfht/api/v1/managepoint/goAddUeyManagepointPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的助农办理点！');
				return;
			}else if(row.status!=1){
				alert('请选中有效的助农办理点！');
				return;
			}
			top.changeTab("助农办理点管理", "助农办理点管理","", path + '/phjfht/api/v1/managepoint/goModifyManagepointPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'viewRow',
		text:'查看',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要查看的助农办理点！');
				return;
			}
			top.changeTab("助农办理点管理", "助农办理点管理","", path + '/phjfht/api/v1/managepoint/goViewManagepointPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要删除的助农办理点！');
				return;
			}
			deleteAll(row.seq_uuid,row.name);
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
			$CL = $('#phjfManagepointInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/managepoint/queryManagepointByPage',
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
					{field:'mp_id',hidden:true}
                    ,{field:'mp_code', title:'办理点编号',width:40}
					,{field:'name', title:'办理点名称',width:80}
					,{field:'contact', title:'联系人',width:40}
					,{field:'mobilephone', title:'移动电话',width:40}
					,{field:'telephone', title:'固定电话',width:40}
					,{field:'open_hours', title:'营业时间',width:40}
					,{field:'province_show', title:'省',width:40}
					,{field:'city_show', title:'市',width:40}
					,{field:'area_show', title:'区',width:40}
					,{field:'day_total_amount', title:'每日总额度',width:40}
					,{field:'is_withdraw_name', title:'是否可预约取款',width:40}
					,{field:'address', title:'地址',width:80}
                    ,{field:'isConfirm_show', title:'是否确认',width:60}
					,{field:'status_show', title:'状态',width:20}
					,{field:'is_show_name', title:'终端是否展现',width:40}
                    ,{field:'create_time', title:'创建时间',width:40}
				]],
			    toolbar: _tools,
			    onClickRow: function(rowIndex, rowData) {
			        curRow = rowData;
			        lastIndex = rowIndex;
			    },
			    // onRowContextMenu: function(e, rowIndex, rowData) {
			    //     e.preventDefault();
			    //     $CL.datagrid('selectRow', rowIndex);
					// $CL.datagrid('options').onClickRow(rowIndex,rowData);
			    //     $('#right_menu').menu('show', {
			    //         left: e.pageX,
			    //         top: e.pageY
			    //     });
			    // },
			    // onDblClickRow: function(rowIndex, rowData) {
			    //     $("#updateRow").trigger("click");
			    // },
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
	$('#phjfManagepointInfo_grid').datagrid("clearChecked");
	$('#phjfManagepointInfo_grid').datagrid("clearSelections");
	$('#phjfManagepointInfo_grid').datagrid('reload');
}


function deleteAll(id,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/managepoint/deleteManagepoint',{seq_uuid:id},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});		
}
function getLanguage(value) {
	return "ZH_SIMP"==value ? '中文简体':'维吾尔语';
}
