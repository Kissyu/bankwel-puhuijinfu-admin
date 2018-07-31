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
		text:'添加敏感词',
		iconCls:'icon-add',
		handler:function(){
			top.changeTab("敏感词管理", "敏感词管理","", path + '/phjfht/api/v1/sensitiveWords/goAddPage',false);
		}
	},'-',{
		id:'setWordsStatus',
		text:'失效/有效',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要操作的敏感词！');
				return;
			}
            setWordsStatus(row);
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
    			url:'/phjfht/api/v1/sensitiveWords/querySensitiveByPage',
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
					{field:'sw_id',hidden:true}
                    ,{field:'status',hidden:true}
                    ,{field:'words', title:'敏感词',width:200}
					,{field:'status_show', title:'状态',width:80}
                    ,{field:'create_time', title:'创建时间',width:100}
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
				data.state = 1;
				$CL.datagrid('load',data);
			});
            //导出表格
            $('#exportBtn').click(function(e){
                //导出代码
                var data = $('#searchForm').serializeObject();
                if(data.language === ""){
                    alert("请选择导出的敏感词语言");
                    return;
                }
                window.location.href= path + '/phjfht/api/v1/sensitiveWords/bulkExportBtn?words='+data.words+'&language='+data.language+'&status='+data.status;
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

//设置敏感词状态
function setWordsStatus(row){
	var msgTxt = row.status === "1" ? "确定失效[" + row.words +"]吗？" : "确定生效[" + row.words +"]吗？";
	confirm(msgTxt,{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/sensitiveWords/setWordsStatus'
				,{sw_id:row.sw_id
					,status:row.status}
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
