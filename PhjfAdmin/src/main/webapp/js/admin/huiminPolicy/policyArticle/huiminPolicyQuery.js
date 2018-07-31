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
			top.changeTab("惠民政策管理", "惠民政策管理","", path + '/phjfht/api/v1/policy/goAddSimpHuiminPolicyPage',false);
		}
	},'-',{
		id:'addRowOther',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要添加的新闻频道！');
				return;
			}
			top.changeTab("新闻文章管理","新闻文章管理","icon-content", path + '/phjfht/api/v1/policy/goAddUeyPolicyArticlePage?pa_id='+row.pa_id,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的文章内容！');
				return;
			}
			if(row.status != 2){
				top.changeTab("惠民政策管理","惠民政策管理","", path + '/phjfht/api/v1/policy/goModifyPolicyArticlePage?pa_id='+row.pa_id,false);
			}else{
				alert("状态为已发布的文章不能修改，请先下架！");
			}
		}
	// },'-',{
	// 	id:'deleteRow',
	// 	text:'失效',
	// 	iconCls:'icon-remove',
	// 	handler:function(){
	// 		var row = $CL.datagrid('getSelected');
	// 		if(!row){
	// 			alert('请选择要失效的新闻内容！');
	// 			return;
	// 		}
	// 		if(row.status != 2){
	// 			deleteAll(row.articleId,row.title);
    //
	// 		}else{
	// 			alert("状态为已发布的不能失效！");
	// 		}
    //
	// 	}
	},'-',{
		id:'viewRow',
		text:'预览',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要查看的政策文章内容！');
				return;
			}
			top.changeTab("惠民政策管理","惠民政策管理","",path + '/phjfht/api/v1/policy/goPolicyArticleViewPage?pa_id='+ row.pa_id, false);
		}
	},'-',{
		id:'deployRow',
		text:'发布',
		iconCls:'icon-upload',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要发布的政策文章！');
				return;
			}
			deployAll(row.pa_id,'确定要发布['+row.title+']','publishArticle');

		}
	},'-',{
		id:'deployRow',
		text:'下架',
		iconCls:'icon-down',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要发布的新闻内容！');
				return;
			}
			deployAll(row.pa_id,'确定要下架['+row.title+']','unPublishArticle');
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
    			url:path + '/phjfht/api/v1/policy/huiminPolicyQuery',
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
			    idField: 'pa_id',
			    pagination: true,
			    pageSize: 25,
			    pageList: [25, 30, 50],
			    columns:[[
					{field:'pa_id', hidden:true}
					,{field:'article_code', title:'文章编号',width:30}
					,{field:'plate_list', title:'所属板块',width:50}
					,{field:'title', title:'标题',width:60}
					,{field:'publish_date', title:'发表日期',width:40,formatter:formateDateYYMMDD}
					,{field:'source', title:'来源',width:40}
					// ,{field:'share_count', title:'分享次数',width:40}
					// ,{field:'click_count', title:'点击次数',width:40}
					,{field:'is_banner_show', title:'是否轮播',width:30}
					,{field:'is_top_show', title:'是否置顶',width:30}
					,{field:'is_recom_show', title:'是否推荐',width:30}
					,{field:'is_show_name', title:'终端是否展现',width:40}
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

function deployAll(pa_id,title,url){
	confirm(title,{
		onOK:function(){
			$.getJSON("/phjfht/api/v1/policy/"+ url,{pa_id:pa_id},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}