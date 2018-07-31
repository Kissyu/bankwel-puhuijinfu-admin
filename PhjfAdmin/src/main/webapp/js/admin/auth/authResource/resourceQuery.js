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
        id:'addMenu',
        text:'添加菜单',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            var url = "";
            if(!row){
                url = '/phjfht/api/v1/auth/goAddResourcePage?type=menu';
            }else{
                url = '/phjfht/api/v1/auth/goAddResourcePage?type=menu&parent_seq_id='+row.seq_id
            }
            top.addTab("添加菜单","",url,true);
        }
    },'-',{
        id:'addButton',
        text:'添加按钮',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row||row.type!='menu'){
                alert('请先选择类型为菜单的资源！');
                return;
            }
            top.addTab("添加按钮","", '/phjfht/api/v1/auth/goAddResourcePage?type=button&parent_seq_id='+row.seq_id,true);
        }
    },'-',{
        id:'addService',
        text:'添加服务',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row||row.type!='menu'){
                alert('请先选择类型为服务的资源！');
                return;
            }
            top.addTab("添加服务","", '/phjfht/api/v1/auth/goAddServicePage?resourceId='+row.seq_id,true);
        }
    },'-',{
        id:'updateRow',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要修改的资源！');
                return;
            }
            if(row.type=="service"){
                top.addTab("修改服务","", '/phjfht/api/v1/auth/goModifyServicePage?seq_uuid='+row.seq_uuid,true);
            }else {
                top.addTab("修改资源","", '/phjfht/api/v1/auth/goModifyResourcePage?type='+row.type+'&seq_uuid='+row.seq_uuid,true);
            }
        }
    },'-',{
        id:'deleteRow',
        text:'删除',
        iconCls:'icon-remove',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要删除的资源！');
                return;
            }
            var url ="";
            if(row.type=='service'){
                url = '/phjfht/api/v1/auth/deleteServiceById';
            }
            else{
                url = '/phjfht/api/v1/auth/deleteResourceById';
            }
            deleteResource(row.seq_uuid,row.name,url);
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
            $CL = $('#resourceManage_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/auth/queryResourceByPage',
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
                    {field:'seq_uuid', checkbox:true,hidden:true}
                    ,{field:'center_name', title:'应用中心',width:80}
                    ,{field:'parent_name', title:'上级名称',width:80}
                    ,{field:'name', title:'资源名称',width:80}
                    ,{field:'type_show', title:'类型',width:40}
                    ,{field:'url', title:'链接地址',width:80}
                    ,{field:'opt_target', title:'操作目标',width:80}
                    ,{field:'opt_content', title:'操作内容',width:120}
                    ,{field:'remark', title:'备注',width:60}
                    ,{field:'status_show', title:'状态',width:40}
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
        updateItem:function(){
            $('#updateRow').trigger('click');
        },
        viewItem:function(){
            $('#viewRow').trigger('click');
        },
        progressItem:function () {
            $('#viewProgress').trigger('click');
        }

    };
}();

function reloadGrid(){
    $('#resourceManage_grid').datagrid("clearChecked");
    $('#resourceManage_grid').datagrid("clearSelections");
    $('#resourceManage_grid').datagrid('reload');
}

function deleteResource(ids,names,url){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON(url, {seq_uuid:ids}, function (result) {
                if(result.code != 1){
                    alert(result.msg || '您没有此操作的权限，请联系管理员！');
                    return;
                }
                reloadGrid();
            });
        }
    });
}
