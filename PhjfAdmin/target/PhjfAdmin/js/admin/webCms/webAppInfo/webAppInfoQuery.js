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
			top.changeTab("APP下载信息管理", "APP下载信息管理","", path + '/phjfwebht/api/v1/webCms/goAddSimpAppPage',false);
		}
	},'-',{
		id:'addRow',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要维护的APP下载信息！');
				return;
			}
			top.changeTab("APP下载信息管理", "APP下载信息管理","", path + '/phjfwebht/api/v1/webCms/goAddUeyAppPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的APP下载信息！');
				return;
			}
			top.changeTab("APP下载信息管理", "APP下载信息管理","", path + '/phjfwebht/api/v1/webCms/goModifyAppPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要失效的APP下载信息！');
				return;
			}
			deleteAll(row.seq_uuid,row.adi_code);
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
			$CL = $('#phjfAppInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
				url:'/phjfwebht/api/v1/webCms/queryAppByPage',
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
					{field:'adi_id', hidden:true}
					,{field:'adi_code', title:'app编号',width:40}
					,{field:'userType_show', title:'设备类型',width:40}
					,{field:'appType_show', title:'app类型',width:40}
					,{field:'qr_code_url', title:'二维码图片',width:40,formatter:getLogo}
					,{field:'description', title:'介绍',width:40}
					,{field:'orders', title:'排序号',width:40}
					,{field:'status_show', title:'状态',width:20}
					,{field:'is_show_name', title:'终端是否展现',width:40}
					,{field:'create_time', title:'创建时间',width:40}
				]],
				toolbar: _tools,
				onClickRow: function(rowIndex, rowData) {
					curRow = rowData;
					lastIndex = rowIndex;
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
	$('#phjfAppInfo_grid').datagrid("clearChecked");
	$('#phjfAppInfo_grid').datagrid("clearSelections");
	$('#phjfAppInfo_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON('/phjfwebht/api/v1/webCms/deleteApp',{seq_uuid:ids},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}


