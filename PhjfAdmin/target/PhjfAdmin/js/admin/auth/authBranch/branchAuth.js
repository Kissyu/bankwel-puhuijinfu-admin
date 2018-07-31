/**
 * Created by bankwel on 2017/9/25.
 */
$.ajaxSetup({
    cache : false
});
$(function(){
    $PC.init();
    $('.sky').hide();
});

var PageCtrl = $PC = function(){
    var branch_seq_id = null;
    var me = null, $CL = null, curNode = null, preNode = null;
    var branchID = null;
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
            $CL = $('#apply_center_grid');
            branch_seq_id = $('#seq_id').val();
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            //右侧应用列表
            $CL.datagrid({
                url:'/phjfht/api/v1/auth/queryBranchApply',
                queryParams: {
                    branch_seq_id:branch_seq_id,
                },
                fit: true,
                fitColumns: true,
                striped: true,
                singleSelect: false,
                checkOnSelect: false,
                selectOnCheck: true,
                nowrap: false,
                idField: 'seq_id',
                pagination: false,
                columns:[[
                    {field:'seq_id', checkbox:true}
                    ,{field:'name', title:'应用中心名称',width:80}
                ]],
                onLoadSuccess:function(data){//当表格成功加载时执行
                    var rowData = data.rows;
                    $.each(rowData,function(idx,val){//遍历JSON
                        if(rowData[idx].branch_apply_seq_id) {
                            $("#apply_center_grid").datagrid("checkRow", idx);//如果后台传过来的数据表示要选中，则此表示一记载就选中
                        }
                    });
                },
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
                    $("#viewRow").trigger("click");
                },
            });
        },
        /**
         * 绑定事件和数据
         */
        bindUI:function(){
            //点击保存授权
            $('#saveAuth').click(function () {
                var selRows = $('#apply_center_grid').datagrid('getChecked');
                var applySeqIds = [];
                if(selRows.length>0) {
                    for (var i=0;i<selRows.length;i++) {
                        applySeqIds.push(selRows[i].seq_id);
                    }
                }
                //提交
                $.ajax({
                    url: '/phjfht/api/v1/auth/modifyBranchApply',
                    async: false,
                    data: {
                        branch_seq_id:branch_seq_id,
                        apply_seq_ids:applySeqIds
                    },
                    dataType: 'json',
                    contentType:"application/json",
                    success: function(result) {
                        removeLoad();
                        if(result.code == 1) {
                            //alert("保存成功");
                            top.changeTab("机构授权","组织机构管理","","/phjfht/api/v1/auth/goBranchPage",true);
                        }else{
                            alert(result.msg);
                        }
                    }
                });
            });
        }
    };
}();
function reloadGrid(opId,brId){
    $('#apply_center_grid').datagrid("clearChecked");
    $('#apply_center_grid').datagrid("clearSelections");
    $('#apply_center_grid').datagrid('load',{
        "branch_seq_id":brId,
        "operator_seq_id":opId
    });
}
