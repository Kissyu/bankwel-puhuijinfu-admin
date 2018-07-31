$.ajaxSetup({
    cache : false,
    traditional : true
});
$(function(){
    $PC.init();
    $('.sky').hide();
});
var PageCtrl = $PC = function(){
    var branchName = null;
    var branchId = null;
    var me = null, $CL = null;
    var _tools = [{
        id:'authItem',
        text:'授权',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row) {
                alert('请选中要授权的账户！');
            }else {
                top.addTab("账户授权","icon-content", "/phjfht/api/v1/auth/goOperatorAuthPage?operator_seq_id="+row.seq_id,true);
            }
        }
    },'-',{
        id:'addItem',
        text:'添加',
        iconCls:'icon-add',
        handler:function(){
            top.addTab("添加账户","icon-content", "/phjfht/api/v1/auth/goAddOperatorPage",true);
        }
    },'-',{
        id:'updateItem',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row) {
                alert('请选中要修改的数据！');
            }else {
                var seq_uuid = row.seq_uuid;
                top.addTab("修改账户","icon-content", "/phjfht/api/v1/auth/goModifyOperatorPage?seq_uuid="+seq_uuid,true);
            }
        }
    },'-',{
        id:'singleDelete',
        text:'删除账户',
        iconCls:'icon-remove',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要删除数据！');
                return;
            }
            deleteAll(row.seq_uuid);
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
            $('#parentIdWin').window('close');
        },
        /**
         * 变量初始化
         */
        initVars:function(){
            me = this;
            $CL = $('#operator_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url: '/phjfht/api/v1/auth/queryOperatorByPage',
                queryParams: {
                    "branch_id":localStorage.getItem("branchId")
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
                    ,{field:'seq_id', hidden:true}
                    ,{field:'operate_name', title:'账户名',align:"center",width:40}
                    ,{field:'true_name', title:'真实姓名',align:"center",width:40}
                    ,{field:'email', title:'邮箱',align:"center",width:40}
                    ,{field:'mobile', title:'电话',align:"center",width:40}
                    ,{field:'branch_name', title:'所属机构',align:"center",width:40}
                    ,{field:'opt_type', title:'账户类型',align:"center",width:40,formatter:getType}
                ]],
                toolbar: _tools,
                onClickRow: function(rowIndex, rowData) {
                    curRow = rowData;
                    lastIndex = rowIndex;
                },
                rowStyler: function (index, row) {
                    if ($('#userid').val()!='1') {
                        if (row.seq_id == '1'|| row.seq_id == $('#userid').val()) {
                            return 'display:none';
                        }
                    }
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
                    $("#viewRowInfo").trigger("click");
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

            //树形控件展现设置
            var zTreeMainSetting = {
                data: {
                    simpleData: {enable:true, idKey: "seq_id", pIdKey: "parent_seq_id", nameKey:"name"}
                },async : {
                    enable : true,//设置 zTree 是否开启异步加载模式
                    url : "/phjfht/api/v1/auth/queryBranchList"//Ajax 获取数据的 URL 地址
                },
                callback: {
                    beforeClick: function(treeId, treeNode) {
                        branchId =treeNode.seq_id;
                        branchName = treeNode.name;
                    }
                }
            };
            var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
            var nodes = treeObj.getNodes();
            if(nodes.length > 0){
                treeObj.expandNode(nodes[0], true, true, true);
            }
            //父节点树的获取
            $('#branchName').click(function (e) {
                $('#parentIdWin').window('open'); // open a window
            });

            //确定获取treepath
            $('#getTreepath_ok').click(function (e) {
                if(branchId) {
                    $('#branch_seq_id').val(branchId);
                    $('#branchName').val(branchName);
                }
                var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
                var nodes = treeObj.getNodes();
                if(nodes.length > 0){
                    treeObj.expandNode(nodes[0], true, true, true);
                }
                $('#parentIdWin').window('close');
            });
            //取消获取treepath
            $('#getTreepath_cancel').click(function (e) {
                branchId = '';
                branchName = '';
                $('#branchName').val(branchName);
                $('#branch_seq_id').val(branchId);
                var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
                var nodes = treeObj.getNodes();
                if(nodes.length > 0){
                    treeObj.expandNode(nodes[0], true, true, true);
                }
                $('#parentIdWin').window('close');
            });
        },
        authItem:function(){
            $('#authRow').trigger('click');
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
    $('#operator_grid').datagrid("clearChecked");
    $('#operator_grid').datagrid("clearSelections");
    $('#operator_grid').datagrid('reload');
}


function deleteAll(ids){
    confirm('确定删除这条数据吗？',{
        onOK:function(){
            $.getJSON('/phjfht/api/v1/auth/deleteOperatorById',{seq_uuid:ids},function(result){
                if(result.code==1 ){
                    reloadGrid();
                }else{
                    alert(result.message || '您没有此操作的权限，请联系管理员！');
                    return;
                }

            });
        }
    });
}
function getType(type) {
    var typeName = null;
    if(type == ""){
        return;
    }
    switch(type){
        case 'admin':
            typeName = "管理员";
            break;
        case 'normal':
            typeName = "普通账户";
            break;
    }
    return typeName;
}
