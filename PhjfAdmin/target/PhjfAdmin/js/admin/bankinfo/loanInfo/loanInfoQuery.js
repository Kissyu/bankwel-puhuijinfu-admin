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
		text:'新增',
		iconCls:'icon-add',
		handler:function(){
			top.changeTab("贷款产品管理", "贷款产品管理","", path + '/phjfht/api/v1/bankinfo/goAddLoanInfoPage',false);
		}
	},{
		id:'modifyRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的银行贷款产品！');
				return;
			}
			top.changeTab("贷款产品管理", "贷款产品管理","", path + '/phjfht/api/v1/bankinfo/goModifyLoanInfoPage?seq_uuid='+row.seq_uuid,false);
		}
	},{
		id:'viewRow',
		text:'查看',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要查看的银行贷款产品！');
				return;
			}
			top.changeTab("贷款产品管理", "贷款产品管理","", path + '/phjfht/api/v1/bankinfo/goDetailLoanInfoPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deployRow',
		text:'发布',
		iconCls:'icon-upload',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要发布的内容！');
				return;
			}
			deployAll(row.seq_uuid,'确定要发布['+row.name+']','publishLoan');

		}
	},'-',{
		id:'deployRow',
		text:'下架',
		iconCls:'icon-down',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要发布的内容！');
				return;
			}
			deployAll(row.seq_uuid,'确定要下架['+row.name+']','unPublishLoan');
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
			$CL = $('#phjfLoanInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/bankinfo/queryLoanInfoByPage',
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
					{field:'seq_uuid', hidden:true}
					,{field:'loan_code', title:'产品编号',width:30}
					,{field:'bt_name', title:'银行类型名称',width:40}
					,{field:'name', title:'产品名称',width:40}
					,{field:'loanPlate_show', title:'所属版块',width:30}
					,{field:'useType_show', title:'用途',width:30}
					,{field:'tags', title:'产品关键字',width:40}
					,{field:'loan_term_lower', title:'期限下限(月)',width:40}
					,{field:'loan_term_upper', title:'期限上限(月)',width:40}
					,{field:'loan_rate_lower', title:'利率下限(年)',width:40}
					,{field:'loan_rate_upper', title:'利率上限(年)',width:40}
					,{field:'loan_amount_lower', title:'额度下限(万元)',width:40}
					,{field:'loan_amount_upper', title:'额度上限(万元)',width:40}
					,{field:'loanSource_show', title:'来源',width:20}
					,{field:'is_recom_show', title:'是否推荐',width:30}
					,{field:'status_show', title:'状态',width:30}
					,{field:'is_show', title:'终端是否展现',width:40}
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

	
		}
	};
}();

function reloadGrid(){
	$('#phjfLoanInfo_grid').datagrid("clearChecked");
	$('#phjfLoanInfo_grid').datagrid("clearSelections");
	$('#phjfLoanInfo_grid').datagrid('reload');
}


function deployAll(seq_uuid,title,url){
	confirm(title,{
		onOK:function(){
			$.getJSON("/phjfht/api/v1/bankinfo/"+url,{seq_uuid:seq_uuid},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}

