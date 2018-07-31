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
			var row = $CL.datagrid('getSelected');
			var type = "";
			var parent_st_id = "";
			var parent_st_name = "";
			if(row) {
				if(row.type == "c") {
					if(row.parent_st_id) {
						alert('请选择根节点');
						return;
					}else{
						type = row.type;
						parent_st_id = row.st_id;
						parent_st_name = row.name;
					}
				}else if(row.type=="s") {
					type = row.type;
					parent_st_id = row.parent_st_id;
					parent_st_name = row.parent_st_name;
				}
			}
			top.changeTab("惠民政策搜索条件管理", "惠民政策搜索条件管理","", path + '/phjfht/api/v1/policy/goAddSimpSearchTermPage?type='+type+"&parent_st_id="+parent_st_id+"&parent_st_name="+parent_st_name,false);
		}
	},'-',{
		id:'addRowOther',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要添加的数据！');
				return;
			}
			top.changeTab("惠民政策搜索条件管理","惠民政策搜索条件管理","icon-content", path + '/phjfht/api/v1/policy/goAddUeyPolicySearchTermPage?st_id='+row.st_id,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的数据！');
				return;
			}
            top.changeTab("惠民政策搜索条件管理","惠民政策搜索条件管理","", path + '/phjfht/api/v1/policy/goModifySearchTermPage?st_id='+row.st_id,false);

        }
	},'-',{
		id:'delRow',
		text:'失效',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要失效的数据！');
				return;
			}
            deployAll(row.seq_uuid,'确定要失效['+row.name+']','unPublishSearchTerm');
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
			$CL = $('#phjfNewsArticle_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:path + '/phjfht/api/v1/policy/policySearchTermQuery',
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
			    idField: 'st_id',
			    pagination: true,
			    pageSize: 25,
			    pageList: [25, 30, 50],
			    columns:[[
					{field:'st_id', hidden:true}
					,{field:'seq_uuid', hidden:true}
					,{field:'parent_st_id', hidden:true}
					,{field:'search_code', title:'条件编号',width:30}
					,{field:'order_no', title:'序号',width:10}
					,{field:'name', title:'名称',width:40}
					,{field:'is_child_name', title:'是否子节点',width:20}
					,{field:'treepath', title:'路径',width:60}
					,{field:'type_name', title:'类型',width:30}
					,{field:'logo', title:'图标',width:40,formatter:getLogo}
					,{field:'parent_st_name', title:'父节点',width:20}
					,{field:'create_time', title:'创建时间',width:30}
					,{field:'province_name', title:'地区',width:30}
					,{field:'is_show_name', title:'终端是否展现',width:20}
					,{field:'status_show', title:'状态',width:20}
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
	$('#phjfNewsArticle_grid').datagrid("clearChecked");
	$('#phjfNewsArticle_grid').datagrid("clearSelections");
	$('#phjfNewsArticle_grid').datagrid('reload');
}

function deployAll(seq_uuid,title,url){
    confirm(title,{
        onOK:function(){
            $.getJSON("/phjfht/api/v1/policy/"+url,{seq_uuid:seq_uuid},function(result){
                if(result.code != 1){
                    alert(result.msg);
                    return;
                }
                reloadGrid();
            });
        }
    });
}

function getLogo(value){
	if(value){
		return '<img style="display: block;height: auto;" src="'+value+'"/>';
	}
}