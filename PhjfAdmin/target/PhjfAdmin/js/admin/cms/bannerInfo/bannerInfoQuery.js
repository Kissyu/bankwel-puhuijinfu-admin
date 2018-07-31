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
			top.changeTab("广告位管理", "广告位管理","", path + '/phjfht/api/v1/cms/goAddSimpBannerPage',false);
		}
	},'-',{
		id:'addRow',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要维护的广告位！');
				return;
			}
			top.changeTab("广告位管理", "广告位管理","", path + '/phjfht/api/v1/cms/goAddUeyBannerPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的广告位！');
				return;
			}
			top.changeTab("广告位管理", "广告位管理","", path + '/phjfht/api/v1/cms/goModifyBannerPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要失效的广告位！');
				return;
			}
			deleteAll(row.seq_uuid,row.banner_code);
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
			$CL = $('#phjfBannerInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/cms/queryBannerByPage',
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
					{field:'banner_id', hidden:true}
					,{field:'banner_code', title:'广告编号',width:40}
					,{field:'location_show', title:'展现位置',width:40}
					,{field:'contentType_show', title:'内容类型',width:40}
					,{field:'openType_show', title:'打开类型',width:40}
					,{field:'img_url', title:'图片',width:40,formatter:getLogo}
					,{field:'http_url', title:'网页链接地址',width:40}
					,{field:'params', title:'app链接地址',width:40}
					,{field:'click_count', title:'点击次数',width:40}
					,{field:'order_num', title:'排序号',width:40}
					,{field:'remark', title:'备注',width:40}
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
	$('#phjfBannerInfo_grid').datagrid("clearChecked");
	$('#phjfBannerInfo_grid').datagrid("clearSelections");
	$('#phjfBannerInfo_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/cms/deleteBanner',{seq_uuid:ids},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}


