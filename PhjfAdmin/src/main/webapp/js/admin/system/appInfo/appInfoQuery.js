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
			top.changeTab("app信息维护", "app信息维护","", path + '/phjfht/api/v1/system/goAddSimpAppInfoPage',false);
		}
	// },'-',{
	// 	id:'addRowOther',
	// 	text:'维护其他语言信息',
	// 	iconCls:'icon-add',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选择要添加的银行app！');
	// 			return;
	// 		}
	// 		bk.openFullHieghtScreenWin('/phjfAppInfo/addOtherLanguage.htm?appId='+row.appId, 800,'添加银行app超市管理',true);
	// 	}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的银行app！');
				return;
			}
			top.changeTab("app信息维护", "app信息维护","", path + '/phjfht/api/v1/system/goModifyAppInfoPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要删除的银行app！');
				return;
			}
			deleteAll(row.seq_uuid,row.name);
		}
	// },'-',{
	// 	id:'viewRow',
	// 	text:'查看',
	// 	iconCls:'icon-look',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选中要查看的银行app！');
	// 			return;
	// 		}
	// 		bk.openFullHieghtScreenWin('/phjfAppInfo/info.htm?id='+row.appId,850,'查看银行app超市管理',true);
	// 	}
	// },'-',{
	// 	id:'releaseRow',
	// 	text:'发布',
	// 	iconCls:'icon-upload',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选择要发布的银行app！');
	// 			return;
	// 		}
	// 			deployAll(row.appId,'确定要发布'+row.name,'deploy');
	// 	}
	// },'-',{
	// 	id:'downRow',
	// 	text:'下架',
	// 	iconCls:'icon-down',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选择要下架的银行app！');
	// 			return;
	// 		}
	// 		if(row.status == 2){
	// 			deployAll(row.appId,'确定要下架'+row.name,'undeploy');
	// 		}else{
	// 			alert('请选择状态为已发布的银行app!');
	// 		}
    //
	// 	}
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
    			url:'/phjfht/api/v1/system/queryAppInfoByPage',
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
					{field:'app_id', hidden:true}
                    ,{field:'name', title:'名称',width:40}
					,{field:'app_code', title:'app编号',width:40}
					,{field:'package_name', title:'包名',width:60}
					,{field:'remark', title:'备注',width:40}
					,{field:'is_show_name', title:'终端是否展现',width:40}
					,{field:'status_show', title:'状态',width:20}
					,{field:'create_time', title:'创建时间',width:40}

				]],
			    toolbar: _tools,
			    onClickRow: function(rowIndex, rowData) {
			        curRow = rowData;
			        lastIndex = rowIndex;
			    }
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
	$('#phjfAppInfo_grid').datagrid("clearChecked");
	$('#phjfAppInfo_grid').datagrid("clearSelections");
	$('#phjfAppInfo_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/system/deleteAppInfo',{seq_uuid:ids},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}

