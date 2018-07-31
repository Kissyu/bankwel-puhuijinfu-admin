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
    //     id:'viewRow',
    //     text:'查看',
    //     iconCls:'icon-look',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选中要查看的用户！');
    //             return;
    //         }
    //         top.changeTab("会员列表", "会员列表","", path + '/phjfht/api/v1/user/goViewUserInfoPage?seq_uuid='+row.seq_uuid,false);
    //     }
    // },'-',{
    //     id:'deleteRow',
    //     text:'冻结',
    //     iconCls:'icon-remove',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选择要冻结的用户！');
    //             return;
    //         }
    //         deleteAll(row.seq_uuid,row.user_name);
    //     }
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
            $CL = $('#phjfUserLoanBankProcess_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/user/queryULBankProcessByPage',
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
                    {field:'blua_id', hidden:true}
                    ,{field:'user_name', title:'会员账户',width:40}
                    ,{field:'true_name', title:'真实姓名',width:40}
                    ,{field:'mobilephone', title:'手机号',width:40}
                    ,{field:'idType_show', title:'证件类型',width:40}
                    ,{field:'id_num', title:'证件号',width:40}
                    ,{field:'bt_name', title:'银行类型名称',width:40}
                    ,{field:'bank_name', title:'银行机构名称',width:40}
                    ,{field:'loanType_show', title:'贷款用途',width:40}
                    ,{field:'loan_amount', title:'贷款金额',width:40}
                    ,{field:'loan_use_term', title:'贷款使用期限',width:40}
                    ,{field:'apply_remark', title:'申请人备注',width:40}
                    ,{field:'platform_remark', title:'平台备注',width:40}
                    ,{field:'accept_time', title:'受理时间',width:40}
                    ,{field:'collection_time', title:'收集材料时间',width:40}
                    ,{field:'submit_time', title:'上报时间',width:40}
                    ,{field:'approval_time', title:'审批通过时间',width:40}
                    ,{field:'lending_time', title:'放款时间',width:40}
                    ,{field:'pass_time', title:'驳回时间',width:40}
                    ,{field:'status_show', title:'状态',width:30}
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
    $('#phjfUserLoanBankProcess_grid').datagrid("clearChecked");
    $('#phjfUserLoanBankProcess_grid').datagrid("clearSelections");
    $('#phjfUserLoanBankProcess_grid').datagrid('reload');
}


function deleteAll(ids,names){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON('/phjfht/api/v1/managepoint/deleteUserInfo',{seq_uuid:ids},function(result){
                if(result.code != 1){
                    alert(result.msg);
                    return;
                }
                reloadGrid();
            });
        }
    });
}