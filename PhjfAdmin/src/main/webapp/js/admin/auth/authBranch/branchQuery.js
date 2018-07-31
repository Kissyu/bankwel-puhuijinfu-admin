$.ajaxSetup({
    cache : false,
    traditional : true
});
$(function(){
    $PC.init();
    $('.sky').hide();
});
var PageCtrl = $PC = function(){
    var treePath = '';
    var pName = '';
    var me = null, $CL = null;

    var _tools = [{
        id:'addItem',
        text:'添加',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row) {
                alert("请选择机构进行添加子机构！");
               // top.addTab("添加新机构","icon-content", "/phjfht/api/v1/auth/goAddBranchPage?parent_seq_id=0&treepath=root",true);
            }else {
                var treepath = row.treepath;
                var parent_seq_id = row.seq_id;
                top.addTab("添加新机构","icon-content", "/phjfht/api/v1/auth/goAddBranchPage?parent_seq_id="+parent_seq_id+"&treepath="+treepath,true);
            }
        }
    },'-',{
        id:'addApply',
        text:'机构授权',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row) {
                alert('请选中要修改的数据！');
            }else {
                var seq_uuid = row.seq_uuid;
                top.addTab("机构授权","icon-content", "/phjfht/api/v1/auth/goBranchAuthPage?seq_uuid="+seq_uuid,true);
            }
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
                top.addTab("修改机构","icon-content", "/phjfht/api/v1/auth/goModifyBranchPage?seq_uuid="+seq_uuid,true);
            }
        }
    },'-',{
        id:'singleDelete',
        text:'删除机构',
        iconCls:'icon-remove',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要删除数据！');
                return;
            }
            deleteAll(row.seq_uuid,row.name);
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
            $CL = $('#branch_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url: '/phjfht/api/v1/auth/queryBranchByPage',
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
                    ,{field:'seq_id', hidden:true}
                    ,{field:'parent_seq_id', hidden:true}
                    ,{field:'treepath', hidden:true}
                    ,{field:'parent_name', title:'上级名称',align:"center",width:40}
                    ,{field:'name', title:'名称',align:"center",width:40}
                    ,{field:'abbr_name', title:'简称',align:"center",width:40}
                    ,{field:'address', title:'地址',align:"center",width:40}
                ]],
                toolbar: _tools,
                onClickRow: function(rowIndex, rowData) {
                    curRow = rowData;
                    lastIndex = rowIndex;
                },
                rowStyler: function (index, row) {
                    console.log($('#userID').val());
                    if ($('#userID').val()!='1'){
                        if(row.seq_id == '1') {
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
                        treePath = treeNode.treepath;
                        pName = treeNode.name;
                    }
                }
            };
            var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
            var nodes = treeObj.getNodes();
            if(nodes.length > 0){
                treeObj.expandNode(nodes[0], true, true, true);
            }
            //父节点树的获取
            $('#fatherName').click(function (e) {
                $('#parentIdWin').window('open'); // open a window
            });

            //确定获取treepath
            $('#getTreepath_ok').click(function (e) {
                if(treePath) {
                    $('#fatherId').val(treePath);
                    $('#fatherName').val(pName);
                    var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
                    var nodes = treeObj.getNodes();
                    if(nodes.length > 0){
                        treeObj.expandNode(nodes[0], true, true, true);
                    }
                    $('#parentIdWin').window('close');
                }
            });
            //取消获取treepath
            $('#getTreepath_cancel').click(function (e) {
                treePath = '';
                pName = '';
                $('#fatherId').val(treePath);
                $('#fatherName').val(pName);
                var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
                var nodes = treeObj.getNodes();
                if(nodes.length > 0){
                    treeObj.expandNode(nodes[0], true, true, true);
                }
                $('#parentIdWin').window('close');
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
    $('#branch_grid').datagrid("clearChecked");
    $('#branch_grid').datagrid("clearSelections");
    $('#branch_grid').datagrid('reload');
}


function deleteAll(ids,names){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON('/phjfht/api/v1/auth/deleteBranch',{seq_uuid:ids},function(result){
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

