/**
 * Created by bankwel on 2018/1/4.
 */
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
        text:'添加简体中文',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            top.changeTab("友情链接管理", "友情链接管理","", path + '/phjfwebht/api/v1/links/goAddSimpLinksPage',false);
        }
    },'-',{
        id:'addRow',
        text:'维护其他语言信息',
        iconCls:'icon-add',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要维护的友情链接！');
                return;
            }
            if(row.language == 'ZH_UEY') {
                alert("请选中需要维护的中文数据！");
                return;
            }
            top.changeTab("友情链接管理", "友情链接管理","", path + '/phjfwebht/api/v1/links/goAddUeyLinksPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'updateRow',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要修改的数据！');
                return;
            }else if(row.status=="4") {
                alert('该数据已失效！');
                return;
            }
            top.changeTab("友情链接管理", "友情链接管理","", path + '/phjfwebht/api/v1/links/goModifyLinksPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'deleteRow',
        text:'失效',
        iconCls:'icon-remove',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选择要删除的数据！');
                return;
            }else if(row.status=="4") {
                alert('该数据已失效！');
                return;
            }
            deleteAll(row.seq_uuid,row.links_name);
        }
    }/*,'-',{
        id:'viewRow',
        text:'查看',
        iconCls:'icon-look',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要查看的数据！');
                return;
            }
            top.changeTab("合作伙伴管理", "合作伙伴管理","", path + '/phjfwebht/api/v1/partner/goViewPartnerPage?seq_uuid='+row.seq_uuid,false);
        }
    }*/];

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
            $CL = $('#webLinks_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfwebht/api/v1/links/queryLinksByPage',
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
                    {field:'links_id',hidden:true}
                    ,{field:'seq_uuid',hidden:true}
                    ,{field:'status',hidden:true}
                    ,{field:'links_code', title:'友情链接编号',width:40}
                    ,{field:'links_name', title:'友情链接名称',width:40}
                    ,{field:'language',hidden:true}
                    ,{field:'url', title:'链接地址',width:50}
                    ,{field:'orders',title:"序号",width:20}
                    ,{field:'is_show_name', title:'是否展示',width:20}
                    ,{field:'status_show', title:'状态',width:30}
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
    $('#webLinks_grid').datagrid("clearChecked");
    $('#webLinks_grid').datagrid("clearSelections");
    $('#webLinks_grid').datagrid('reload');
}


function deleteAll(ids,names){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON('/phjfwebht/api/v1/links/deleteLinks',{seq_uuid:ids},function(result){
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
