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
    var operatorId = null;
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
            $CL = $('#roleManage_grid');
            operatorId = $('#operatorId').val();
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            //树形控件展现设置
            var zTreeMainSetting = {
                data: {
                    simpleData: {enable:true, idKey: "seq_id", pIdKey: "parent_seq_id", nameKey:"name"}
                },
                check: {
                    enable: true,
                    chkStyle: "checkbox",
                    radioType: "all"
                }
                ,async : {
                    enable : true,//设置 zTree 是否开启异步加载模式
                    url : "/phjfht/api/v1/auth/queryOptAuthBranchList",//Ajax 获取数据的 URL 地址
                    otherParam:["operator_seq_id",operatorId]
                },
                callback: {
                    onClick: function(event,treeId, treeNode) {
                        $('#saveAuth').show();
                        branchID = treeNode.seq_id;
                        reloadGrid(operatorId,branchID);
                    },
                    onAsyncSuccess:function (event, treeId, treeNode, msg) {
                        var tree = $.fn.zTree.getZTreeObj(treeId);
                        tree.expandAll(true);
                    }
                }
            };
            var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);

            //右侧角色树
            $CL.datagrid({
                url:'/phjfht/api/v1/auth/queryBranchRoles',
                queryParams: {
                    branch_seq_id:branchID,
                    operator_seq_id:operatorId
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
                    ,{field:'name', title:'角色名称',width:80}
                    ,{field:'apply_center_seq_id', title:'应用中心ID',width:80}
                ]],
                onLoadSuccess:function(data){//当表格成功加载时执行
                    var rowData = data.rows;
                    $.each(rowData,function(idx,val){//遍历JSON
                        if(rowData[idx].checked) {
                            $("#roleManage_grid").datagrid("checkRow", idx);//如果后台传过来的数据表示要选中，则此表示一记载就选中
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
                var selRows = $('#roleManage_grid').datagrid('getChecked');
                var roleIds = [];
                if(selRows.length>0) {
                    for (var i=0;i<selRows.length;i++) {
                        roleIds.push(selRows[i].seq_id);
                    }
                }
                //提交
                $.ajax({
                    url: '/phjfht/api/v1/auth/modifyBranchRoles',
                    async: false,
                    data: {
                        operator_seq_id:operatorId,
                        branch_seq_id:branchID,
                        roleIds:roleIds
                    },
                    dataType: 'json',
                    contentType:"application/json",
                    success: function(result) {
                        removeLoad();
                        if(result.code == 1) {
                            alert("保存成功");
                            //top.changeTab('账户管理',"账户授权","icon-people",'/phjfht/api/v1/auth/goOperatorAuthPage?operator_seq_id='+operatorId,true);
                        }else{
                            alert(result.message);
                        }
                    }
                });
            });
        }
    };
}();
function reloadGrid(opId,brId){
    $('#roleManage_grid').datagrid("clearChecked");
    $('#roleManage_grid').datagrid("clearSelections");
    $('#roleManage_grid').datagrid('load',{
        "branch_seq_id":brId,
        "operator_seq_id":opId
    });
}
