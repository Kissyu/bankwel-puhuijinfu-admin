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
			top.changeTab("办理点账户管理", "办理点账户管理","", path + '/phjfht/api/v1/managepoint/goAddMpOptInfoPage',false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的助农办理点账户信息！');
				return;
			}
			top.changeTab("办理点账户管理", "办理点账户管理","", path + '/phjfht/api/v1/managepoint/goModifyMpOptInfoPage?seq_uuid='+row.seq_uuid,false);
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
			$CL = $('#phjfManagepointOptinfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/managepoint/queryMpOptInfoByPage',
   				queryParams: {
                    mpCode:mpCode
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
					{field:'opt_id', hidden:true}
                    ,{field:'mp_code', title:'办理点编号',width:40}
					,{field:'mp_name', title:'办理点名称',width:80}
					,{field:'name', title:'姓名',width:40}
					,{field:'user_name', title:'会员账户',width:40}
					,{field:'mobilephone', title:'移动电话',width:40}
					,{field:'telephone', title:'固定电话',width:40}
                    ,{field:'idType_show', title:'证件类型',width:40}
                    ,{field:'certi_no', title:'证件号',width:40}
                    ,{field:'gender_show', title:'性别',width:20}
					,{field:'email', title:'邮箱',width:40}
					,{field:'status_show', title:'状态',width:20}
					,{field:'is_show_name', title:'终端是否展现',width:40}
					,{field:'creat_opt_name', title:'创建人',width:40}
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
	$('#phjfManagepointOptinfo_grid').datagrid("clearChecked");
	$('#phjfManagepointOptinfo_grid').datagrid("clearSelections");
	$('#phjfManagepointOptinfo_grid').datagrid('reload');
}


