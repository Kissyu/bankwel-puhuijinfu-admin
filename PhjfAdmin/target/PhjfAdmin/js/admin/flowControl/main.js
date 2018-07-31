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
		id:'delKey',
		text:'删除',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要操作的限制！');
				return;
			}
            delKey(row);
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
			$CL = $('#phjfManagepointInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/flow/queryKey',
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
			    idField: 'key',
			    pagination: true,
			    pageSize: 25,
			    pageList: [25, 30, 50],
			    columns:[[
                    {field:'key', title:'关键字',width:200}
					,{field:'status', title:'状态',width:80}
                    ,{field:'value',title:'值',width:80}
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
                data.flag = "1";
                if(!data.mobilephone || data.mobilephone === ""){
                	alert("请填写手机号");
				}else if(!data.rule || data.rule === ""){
                    alert("请选择限制类型");
				}else if(!data.systemCode || data.systemCode === ""){
                    alert("请选择系统编码");
                }
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

//清理key
function delKey(row){
	var msgTxt = "确定解锁:\n\r[" + row.key +"]吗？";
	confirm(msgTxt,{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/flow/delKey'
				,{key:row.key}
				,function(result){
					if(result.code != 1){
						alert(result.msg);
						return;
					}
					reloadGrid();
			    }
			);
		}
	});		
}

//重新加载数据
function reloadGrid(){
    $('#phjfManagepointInfo_grid').datagrid("clearChecked");
    $('#phjfManagepointInfo_grid').datagrid("clearSelections");
    $('#phjfManagepointInfo_grid').datagrid('reload');
}
