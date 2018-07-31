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
		id:'lookRow',
		text:'查看',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			// if(!row){
			// 	alert('请选中要查看的银行储蓄产品！');
			// 	return;
			// }
			top.changeTab("储蓄产品管理", "储蓄产品管理","", path + '/phjfht/api/v1/bankinfo/goViewBankDepositInfoPage',false);
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
			$CL = $('#phjfBankDepositInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/bankinfo/queryBankDepositInfoByPage',
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
					{field:'bdi_id', checkbox:true}
					,{field:'bt_name', title:'银行类型名称',width:40}
					,{field:'term_type', title:'储蓄产品类型',width:40}
					,{field:'term', title:'期限',width:40,formatter:getTerm}
					,{field:'seven_rate', title:'七日年化利率',width:40}
					,{field:'year_rate', title:'年化利率',width:40}
					,{field:'min_amount', title:'起存金额',width:40}
					,{field:'currency', title:'币种',width:40}
					,{field:'order_num', title:'排序号',width:40}
					,{field:'is_recom', title:'是否推荐',width:40}
					,{field:'is_redeem', title:'是否可赎回',width:40}
					,{field:'is_top', title:'是否置顶',width:40}
					,{field:'is_show', title:'终端是否展现',width:40}
					,{field:'status', title:'状态',width:40}
					,{field:'create_time', title:'创建时间',width:40}
				]],
			    toolbar: _tools

			});
		},
		/**
		 * 绑定事件和数据
		 */
		bindUI:function(){
			//初始化选择银行
			$("#bank_info").combotree({
				url: '/phjfBankInfo/getBankTree.htm'
			});

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
	$('#phjfBankDepositInfo_grid').datagrid("clearChecked");
	$('#phjfBankDepositInfo_grid').datagrid("clearSelections");
	$('#phjfBankDepositInfo_grid').datagrid('reload');
}


function deleteAll(ids,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON(ctx + '/phjfBankDepositInfo/delete.htm',{ids:ids},function(result){
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

function getDespotType(){
	return '定期';
}

function getDespotStatus(value){
	var status = ['无效','待发布','已发布','已下架'];

	return status[value];
}

function getTerm(value){
	var term='';
	if(value == 10){
		term = '3个月';
	}else if(value == 20){
		term = '6个月';
	} else if(value == 30){
		term = '1年';
	}else if(value == 40){
		term = '2年';
	}else if(value == 50){
		term = '3年';
	}else if(value == 60){
		term = '5年';
	}
	return term;
}


function deployAll(bdId,title,type){
	confirm(title,{
		onOK:function(){
			$.getJSON(ctx + '/phjfBankDepositInfo/'+type+'/deplpyBankDepositInfo.htm',{bdId:bdId},function(result){
				if(result.code == bk.ResultCode.SERVER_ERR){
					alert('发布出错！');
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

