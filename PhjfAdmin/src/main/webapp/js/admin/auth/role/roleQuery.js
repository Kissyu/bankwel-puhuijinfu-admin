$.ajaxSetup({
    cache : false,
    traditional : true
});
$(function(){
    $PC.init();
    $('.sky').hide();
});
var PageCtrl = $PC = function(){
    var me = null, $CL = null, treeObj =null,roleId =null;

    var _tools = [{
        id:'addRow',
        text:'添加角色',
        iconCls:'icon-add',
        handler:function(){
            top.addTab("添加角色","", '/phjfht/api/v1/auth/goAddRolePage',false);

        }
    },'-',{
        id:'allocateResource',
        text:'分配资源',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请先选择角色！');
                return;
            }else{
                roleId = row.seq_id;
                $.getJSON('/phjfht/api/v1/auth/isHaveAllotModiAuth',{role_seq_id:roleId},function (result) {
                    if(result.code==1){
                        if(!result.data){
                            alert('您没有给该角色分配资源的权限！');
                        }else {
                            $('#branchTreeWin').window('open');
                            $("#resourceBtn").show();
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
                                    url : "/phjfht/api/v1/auth/queryResourceByRole",//Ajax 获取数据的 URL 地址
                                    otherParam:["roleId",roleId]
                                },
                                callback: {
                                    onAsyncSuccess:function (event, treeId, treeNode, msg) {
                                        var tree = $.fn.zTree.getZTreeObj(treeId);
                                        tree.expandAll(true);
                                    }
                                }
                            };
                            treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
                        }
                    }else{
                        alert(result.msg);
                    }
                });
            }
        }
    },'-',{
        id:'updateRow',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要修改的角色！');
                return;
            }
            var roleUuid = row.seq_uuid;
            $.getJSON('/phjfht/api/v1/auth/isHaveAllotModiAuth',{role_seq_id:row.seq_id},function (result) {
                if(result.code==1){
                    if(!result.data){
                        alert('您没有修改该角色的权限！');
                    }else {
                        top.addTab("修改角色","", '/phjfht/api/v1/auth/goModifyRolePage?seq_uuid='+roleUuid,true);
                    }
                }else{
                    alert(result.msg);
                }
            });
        }
    },'-',{
        id:'updateRow',
        text:'查看已分配资源',
        iconCls:'icon-look',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要查看的角色！');
                return;
            }
            roleId = row.seq_id;
            $('#branchTreeWin').window('open');
            $("#resourceBtn").hide();
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
                    url : "/phjfht/api/v1/auth/queryResourceByRole",//Ajax 获取数据的 URL 地址
                    otherParam:["roleId",roleId]
                },
                callback: {
                    onAsyncSuccess:function (event, treeId, treeNode, msg) {
                        var tree = $.fn.zTree.getZTreeObj(treeId);
                        tree.expandAll(true);
                        var nodes = tree.transformToArray(tree.getNodes());
                        for(var i =0;i<nodes.length;i++){
                            tree.setChkDisabled(nodes[i], true);
                        }
                    }
                }
            };
            treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
        }
    },'-',{
        id:'deleteRow',
        text:'删除',
        iconCls:'icon-remove',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要删除的角色！');
                return;
            }
            deleteRole(row.seq_uuid,row.name,row.seq_id);
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
            $CL = $('#roleManage_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/auth/queryRoleByPage',
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
                    {field:'seq_id', checkbox:true,hidden:true}
                    ,{field:'name', title:'角色名称',width:80}
                    ,{field:'remark', title:'备注',width:80}
                    ,{field:'status_show', title:'状态',width:80}
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
                    $("#viewRow").trigger("click");
                },
            });
            $('#branchTreeWin').window('move',{left:300,top:50});
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
            $('#btn_ok').click(function(){
                var clickBtn = $('#btn_ok');
                if(!clickBtn.hasClass("submitted")){
                    clickBtn.addClass("submitted");
                    var selectedIds = treeObj.getCheckedNodes(true);
                    Load();
                    var ids=[];
                    $.each(selectedIds,function(i,e){
                        ids.push(e.seq_id);
                    });
                    $.ajax({
                        url: '/phjfht/api/v1/auth/allotResource',
                        data: {
                            roleId:roleId,
                            resourceIds:ids
                        },
                        dataType: 'json',
                        contentType:"application/json",
                        success: function(result) {
                            removeLoad();
                            if(result.code != 1){
                                alert(result.msg);
                                return;
                            }
                            reloadGrid();
                            clickBtn.removeClass("submitted");
                            $('#btn_cancel').trigger('click');
                        }
                    });
                }
            });
            $('#btn_cancel').click(function(e){
                $('#branchTreeWin').window('close');
            });
        },
        updateItem:function(){
            $('#updateRow').trigger('click');
        },
        addItem:function () {
            $('#addRow').trigger('click');
        }

    };
}();

function reloadGrid(){
    $('#roleManage_grid').datagrid("clearChecked");
    $('#roleManage_grid').datagrid("clearSelections");
    $('#roleManage_grid').datagrid('reload');
}
function deleteRole(ids,names,roleId){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON('/phjfht/api/v1/auth/deleteRoleById', {seq_uuid:ids,seq_id:roleId}, function (result) {
                if(result.code != 1){
                    alert(result.msg || '您没有此操作的权限，请联系管理员！');
                    return;
                }
                reloadGrid();
            });
        }
    });
}
