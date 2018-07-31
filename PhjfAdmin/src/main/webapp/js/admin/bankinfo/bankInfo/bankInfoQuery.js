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
            if(row){
                top.changeTab("银行机构管理", "银行机构管理","", path + '/phjfht/api/v1/bankinfo/goAddSimpBankInfoPage?parent_bank_code='+row.bank_code,false);
            }else {
                top.changeTab("银行机构管理", "银行机构管理","", path + '/phjfht/api/v1/bankinfo/goAddSimpBankInfoPage',false);
            }
        }
    },'-',{
        id:'addRows',
        text:'维护其他语言信息',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
            	alert('请选中一个要添加的银行网点！');
            	return;
            }
            top.changeTab("银行机构管理", "银行机构管理","", path + '/phjfht/api/v1/bankinfo/goAddUeyBankInfoPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'updateRow',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
            	alert('请选中一个要修改的银行机构！');
            	return;
            }
            top.changeTab("银行机构管理", "银行机构管理","", path + '/phjfht/api/v1/bankinfo/goModifyBankInfoPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'viewRow',
        text:'查看',
        iconCls:'icon-look',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
            	alert('请选中一个要查看的银行机构！');
            	return;
            }
            top.changeTab("银行机构管理", "银行机构管理","", path + '/phjfht/api/v1/bankinfo/goViewBankInfoPage?seq_uuid='+row.seq_uuid,false);
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
            $CL = $('#phjfBankInfo_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/bankinfo/queryBankInfoByPage',
                queryParams: {
                    language:"ZH_SIMP"
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
                    {field:'bank_id', title:'',hidden:true}
                    ,{field:'bt_code', title:'银行类型编号',width:40}
                    ,{field:'bt_name', title:'银行类型名称',width:60}
                    ,{field:'bank_code', title:'银行机构编号',width:40}
                    ,{field:'third_bank_code', title:'银行机构第三方编号',width:40}
                    ,{field:'name', title:'银行机构名称',width:60}
                    ,{field:'parent_bank_name', title:'上级银行机构名称',width:60}
                    ,{field:'contact', title:'联系人',width:40}
                    ,{field:'mobilephone', title:'移动电话',width:40}
                    ,{field:'telephone', title:'固定电话',width:40}
                    ,{field:'email', title:'邮箱',width:40}
                    ,{field:'address', title:'地址',width:40}
                    ,{field:'remark', title:'备注',width:40}
                    ,{field:'isConfirm_show', title:'是否确认',width:40}
                    ,{field:'status_show', title:'状态',width:20}
                    ,{field:'is_show_name', title:'终端是否展现',width:40}
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

function reloadGrid(){
    $('#phjfBankInfo_grid').datagrid("clearChecked");
    $('#phjfBankInfo_grid').datagrid("clearSelections");
    $('#phjfBankInfo_grid').datagrid('reload');
}


function deleteAll(ids,names){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON(ctx + '/phjfBankType/delete.htm',{ids:ids},function(result){
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

function getLanguage(value) {
    return "ZH_SIMP"==value ? '中文简体':'维吾尔语';
}

function getShow(value) {
    return 1==value ? '显示':'不显示';
}

function getStatus(value) {
    return 1==value ? '有效':'无效';
}