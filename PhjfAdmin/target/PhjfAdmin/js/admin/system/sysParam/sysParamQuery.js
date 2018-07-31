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
            top.changeTab("系统参数维护", "系统参数维护","", path + '/phjfht/api/v1/system/goAddSysParamPage',false);
        }
    },'-',{
        id:'updateRow',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要修改的系统参数！');
                return;
            }
            top.changeTab("系统参数维护", "系统参数维护","", path + '/phjfht/api/v1/system/goModifySysParamPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'deleteRow',
        text:'失效',
        iconCls:'icon-remove',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选择要删除的系统参数！');
                return;
            }
            deleteAll(row.seq_uuid,row.param_name);
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
            $CL = $('#sysParam_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/system/querysysParamByPage',
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
                    {field:'param_id', hidden:true}
                    ,{field:'param_code', title:'系统参数编号',width:40}
                    ,{field:'param_name', title:'系统参数名称',width:40}
                    ,{field:'param_value', title:'系统参数值',width:40}
                    ,{field:'remark', title:'备注',width:40}
                    ,{field:'is_show_name', title:'终端是否展现',width:40}
                    ,{field:'status_show', title:'状态',width:20}
                    ,{field:'create_time', title:'创建时间',width:40}
                ]],
                toolbar: _tools,
                onClickRow: function(rowIndex, rowData) {
                    curRow = rowData;
                    lastIndex = rowIndex;
                }
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
    $('#sysParam_grid').datagrid("clearChecked");
    $('#sysParam_grid').datagrid("clearSelections");
    $('#sysParam_grid').datagrid('reload');
}


function deleteAll(ids,names){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON('/phjfht/api/v1/system/deleteSysParam',{seq_uuid:ids},function(result){
                if(result.code != 1){
                    alert(result.msg);
                    return;
                }
                reloadGrid();
            });
        }
    });
}

