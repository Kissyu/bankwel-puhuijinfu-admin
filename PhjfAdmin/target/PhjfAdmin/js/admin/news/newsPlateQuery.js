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
			top.changeTab("新闻版块管理", "新闻版块管理","", path + '/phjfht/api/v1/news/goAddSimpNewsPlatePage',false);
		}
	},'-',{
		id:'addRow',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要添加的新闻版块！');
				return;
			}
			top.changeTab("新闻版块管理","新闻版块管理","icon-content", path + '/phjfht/api/v1/news/goAddUeyNewsPlatePage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的新闻版块！');
				return;
			}
			top.changeTab("新闻版块管理","新闻版块管理","", path + '/phjfht/api/v1/news/goModifyNewsPlatePage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要失效的新闻版块！');
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
			$CL = $('#phjfNewsPlate_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:path + '/phjfht/api/v1/news/newsPlateQuery',
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
					{field:'plate_id', hidden:true}
					,{field:'plate_code', title:'版块编号',width:40}
					,{field:'name', title:'版块名称',width:60}
					,{field:'order_num', title:'排序号',width:40}
					,{field:'remark', title:'备注',width:40}
					,{field:'is_show_name', title:'终端是否展现',width:40}
					,{field:'status_show', title:'状态',width:40}
					,{field:'create_opt_name', title:'创建人',width:40}
					,{field:'create_time', title:'创建时间',width:40}
				]],
			    toolbar: _tools
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
	$('#phjfNewsPlate_grid').datagrid("clearChecked");
	$('#phjfNewsPlate_grid').datagrid("clearSelections");
	$('#phjfNewsPlate_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定失效['+names+']吗？',{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/news/deleteNewsPlate',{seq_uuid:ids},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}


