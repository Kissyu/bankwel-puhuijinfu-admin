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
		text:'添加中文',
		iconCls:'icon-add',
		handler:function(){
			top.changeTab("惠民政策版块管理", "惠民政策版块管理","", path + '/phjfht/api/v1/policy/goAddSimpPolicyPlatePage',false);
		}
	},'-',{
		id:'addRowOther',
		text:'维护其他语言信息',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要添加的惠民政策版块！');
				return;
			}
			top.changeTab("惠民政策版块管理","惠民政策版块管理","icon-content", path + '/phjfht/api/v1/policy/goAddUeyPolicyPlatePage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'updateRow',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的惠民政策版块！');
				return;
			}
			top.changeTab("惠民政策版块管理","惠民政策版块管理","", path + '/phjfht/api/v1/policy/goModifyPolicyPlatePage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'deleteRow',
		text:'失效',
		iconCls:'icon-remove',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要失效的惠民政策版块！');
				return;
			}
			deleteAll(row.seq_uuid,row.name);
		}
	},'-',{
		id:'addPlateRow',
		text:'版块分配',
		iconCls:'icon-add',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选择要分配的惠民政策版块！');
				return;
			}
			top.changeTab("惠民政策版块管理","惠民政策版块管理","icon-content", path + '/phjfht/api/v1/policy/goAllocationPolicyPlatePage?seq_uuid='+row.seq_uuid,false);
		}
	},'-',{
		id:'updatePlateRow',
		text:'修改分配',
		iconCls:'icon-edit',
		handler:function(){
			var row = $CL.datagrid('getSelected');
			if(!row){
				alert('请选中要修改的数据！');
				return;
			}
			if(row.status == '4'){
                alert('请选中有效的数据！');
                return;
            }
			console.log(row.status);
			top.changeTab("惠民政策版块管理","惠民政策版块管理","", path + '/phjfht/api/v1/policy/goModifyAllocationPolicyPlatePage?plate_code='+row.plate_code+"&province_id="+row.province_id,false);
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
				url:path + '/phjfht/api/v1/policy/huiminPolicyPlateQuery',
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
					,{field:'province_id', hidden:true}
					,{field:'plate_code', title:'版块编号',width:40}
					,{field:'name', title:'版块名称',width:40}
					,{field:'logo', title:'图片地址',width:80}
					,{field:'province_name', title:'省份',width:60}
					,{field:'order_num', title:'排序号',width:40}
					,{field:'remark', title:'备注',width:40}
					,{field:'is_show_name', title:'终端是否展现',width:30}
					,{field:'status_show', title:'状态',width:30}
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


function deleteAll(id,names){
	confirm('确定删除['+names+']吗？',{
		onOK:function(){
			$.getJSON('/phjfht/api/v1/policy/deletePolicyPlate',{seq_uuid:id},function(result){
				if(result.code != 1){
					alert(result.msg);
					return;
				}
				reloadGrid();
			});
		}
	});
}
function getLanguage(value) {
	return "ZH_SIMP"==value ? '中文简体':'维吾尔语';
}


