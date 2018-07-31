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
		id:'viewRow',
		text:'查看',
		iconCls:'icon-look',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要查看的用户！');
				return;
			}
			top.changeTab("会员列表", "会员列表","", path + '/phjfht/api/v1/user/goViewUserInfoPage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'冻结',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要冻结的用户！');
				return;
			}
			deleteAll(row.seq_uuid,row.app_code,'freezeUserInfo','冻结['+row.user_name+']');
		}
	},'-', {
		id: 'deleteRow',
		text: '解冻',
		iconCls: 'icon-redo',
		handler: function () {
			var row = $CL.datagrid('getSelected');
			if (!row) {
				alert('请选择要解冻的用户！');
				return;
			}
			deleteAll(row.seq_uuid,row.app_code,'unfreezeUserInfo', '解冻['+row.user_name+']');
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
			$CL = $('#phjfUserInfo_grid');
		},
		/**
		 * 渲染页面控件
		 */
		renderUI:function(){
			$CL.datagrid({
    			url:'/phjfht/api/v1/user/queryUserInfoByPage',
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
					{field:'app_name', title:'app名称',width:40}
					,{field:'user_name', title:'会员账户',width:40}
                    ,{field:'user_id', title:'用户ID',width:40}
					,{field:'true_name', title:'姓名',width:40}
					,{field:'nickname', title:'昵称',width:40}
					,{field:'gender_show', title:'性别',width:20}
					,{field:'mobilephone', title:'手机号',width:40}
					,{field:'idType_show', title:'证件类型',width:40}
					,{field:'id_num', title:'证件号',width:40}
					,{field:'reg_time', title:'注册时间',width:40}
					,{field:'frozen_time', title:'账户冻结时间',width:40}
					,{field:'status_show', title:'状态',width:20}
					,{field:'is_show_name', title:'终端是否展现',width:40}
					,{field:'creat_opt_name', title:'创建人',width:40}
					,{field:'create_time', title:'创建时间',width:40}
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
	
		},viewItem:function(){
			$('#viewRow').trigger('click');
		}
	};
}();

function reloadGrid(){
	$('#phjfUserInfo_grid').datagrid("clearChecked");
	$('#phjfUserInfo_grid').datagrid("clearSelections");
	$('#phjfUserInfo_grid').datagrid('reload');
}


function deleteAll(ids,app_code,url,names){
	confirm('确定'+names+'吗？',{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/user/'+url+'?app_code='+app_code,{seq_uuid:ids},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}