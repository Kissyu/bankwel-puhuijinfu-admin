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
			top.changeTab("app发布管理", "app发布管理","", path + '/phjfht/api/v1/system/goAddAppVersionPage',false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的APP版本信息！');
				return;
			}
			if(row.status == 2){
				alert("已发布的版本信息，不能进行修改！")
				return ;
			}
			top.changeTab("app发布管理", "app发布管理","", path + '/phjfht/api/v1/system/goModifyAppVersionPage?seq_uuid='+row.seq_uuid,false);
		}
	// },'-',{
	// 	id:'deleteRow',
	// 	text:'删除',
	// 	iconCls:'icon-remove',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选择要删除的APP版本信息表！');
	// 			return;
	// 		}
	// 		if(row.status == 2){
	// 			alert("已发布的APP版本，不允许删除！")
	// 			return ;
	// 		}
	// 		deleteAll(row.aviId,row.appVersion);
	// 	}
	},'-',{
		id:'deployRow',
		text:'发布',
		iconCls:'icon-upload',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要发布的APP版本信息！');
				return;
			}
			if(row.status == 2){
				alert("已发布的APP版本，不能重复发布！")
				return ;
			}
			deployAll(row.seq_uuid,'确定要发布版本['+row.app_version+']','publishAppVersion');
		}
	},'-',{
		id:'deployRow',
		text:'下架',
		iconCls:'icon-down',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要发布的APP版本信息！');
				return;
			}
			deployAll(row.seq_uuid,'确定要下架['+row.app_version+']','unPublishAppVersion');
		}
	// },'-',{
	// 	id:'viewRow',
	// 	text:'查看',
	// 	iconCls:'icon-look',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选中要查看的APP版本信息表！');
	// 			return;
	// 		}
	// 		bk.openWin('/phjfAppVersionInfo/info.htm?aviId='+row.aviId,600,450,'查看APP版本信息表',true);
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
			$CL = $('#phjfAppVersionInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/system/queryAppVersionByPage',
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
					{field:'avi_id', hidden:true}
					,{field:'app_id', title:'app编号',width:30}
					,{field:'app_name', title:'app名称',width:40}
					,{field:'package_name', title:'包名',width:80}
					,{field:'app_version', title:'版本',width:30}
					,{field:'deviceType_show', title:'设备类型',width:30}
					,{field:'isUpdate_show', title:'是否强制更新',width:40}
					,{field:'app_url', title:'安装包下载地址',width:40}
					,{field:'qr_code_url', title:'二维码下载地址',width:40}
					,{field:'h5_url', title:'H5下载地址',width:40}
					,{field:'change_content', title:'更新内容',width:40}
					,{field:'channerl_show', title:'渠道',width:40}
					,{field:'is_show_name', title:'终端是否显示',width:40}
					,{field:'status_show', title:'状态',width:30}
					,{field:'publish_date', title:'发布日期',width:40,formatter:formateDateYYMMDD}
					,{field:'under_date', title:'下架日期',width:40,formatter:formateDateYYMMDD}
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
	$('#phjfAppVersionInfo_grid').datagrid("clearChecked");
	$('#phjfAppVersionInfo_grid').datagrid("clearSelections");
	$('#phjfAppVersionInfo_grid').datagrid('reload');
}


function deployAll(seq_uuid,title,url){
	confirm(title,{
		onOK:function(){
			$.getJSON("/phjfht/api/v1/system/"+url,{seq_uuid:seq_uuid},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}
