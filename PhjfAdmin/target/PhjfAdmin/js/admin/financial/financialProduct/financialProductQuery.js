/**
 * Created by Administrator on 2017/11/17.
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
    //     id:'addRow',
    //     text:'添加简体中文',
    //     iconCls:'icon-add',
    //     handler:function(){
    //         bk.openWin('/phjfFinancialProduct/detail.htm?id=', 800,550,'添加理财产品信息',true);
    //     }
    // },'-',{
    //     id:'addRow',
    //     text:'维护其他语言',
    //     iconCls:'icon-add',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选中要修改的理财产品信息！');
    //             return;
    //         }
    //         bk.openWin('/phjfFinancialProduct/addOtherLanguage.htm?id='+row.fpId,800,550,'修改理财产品信息',true);
    //     }
    // },'-',{
    //     id:'updateRow',
    //     text:'修改',
    //     iconCls:'icon-edit',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选中要修改的理财产品信息！');
    //             return;
    //         }
    //         if(row.status == 2){
    //             alert("已发布的理财产品，不能进行修改！")
    //             return ;
    //         }
    //         bk.openWin('/phjfFinancialProduct/detail.htm?id='+row.fpId,800,550,'修改理财产品信息',true);
    //     }
    // },'-',{
    //     id:'deleteRow',
    //     text:'删除',
    //     iconCls:'icon-remove',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选择要删除的理财产品信息！');
    //             return;
    //         }
    //         if(row.status == 2){
    //             alert("已发布的理财产品，不能进行删除！")
    //             return ;
    //         }
    //         deleteAll(row.fpId,row.title);
    //
    //     }
    // },'-',{
        id:'viewRow',
        text:'查看',
        iconCls:'icon-look',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要查看的理财产品信息！');
                return;
            }
            top.changeTab("理财产品信息", "理财产品信息","", path + '/phjfht/api/v1/financial/goFpViewPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'modifyRow',
        text:'修改',
        iconCls:'icon-edit',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要修改的理财产品信息！');
                return;
            }
            top.changeTab("理财产品信息", "理财产品信息","", path + '/phjfht/api/v1/financial/goFpViewPage?seq_uuid='+row.seq_uuid + '&isModify=true',false);
        }
    // },'-',{
    //     id:'deployRow',
    //     text:'发布',
    //     iconCls:'icon-upload',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选择要发布的产品！');
    //             return;
    //         }
    //         if(row.status == 2){
    //             alert("已发布的理财产品，不能重复发布！")
    //             return ;
    //         }
    //         deployAll(row.fpId,'确定要发布','deploy');
    //     }
    },'-',{
        id:'deployRow',
        text:'下架',
        iconCls:'icon-down',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选择要下架的理财产品！');
                return;
            }
            deployAll(row.seq_uuid,'确定要下架['+row.name+']','unPublishFp');
        }
    // },'-',{
    //     id:'viewRow',
    //     text:'理财产品原始信息',
    //     iconCls:'icon-look',
    //     handler:function(){
    //         var row = $CL.datagrid('getSelected');
    //         if(!row){
    //             alert('请选中要查看的理财产品信息！');
    //             return;
    //         }
    //         top.addTab(row.title+"的原始理财信息","icon-content", '/phjfFinancialOriginalInfo/index.htm?fpCode='+row.fpCode+'&status='+row.status+'&isShow='+row.isShow,false);
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
            $CL = $('#phjfFinancialProduct_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/financial/queryFpByPage',
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
                    {field:'fp_id', hidden:true}
                    ,{field:'fp_code', title:'理财产品编号',width:50}
                    ,{field:'fp_type_show', title:'理财产品类型',width:50}
                    ,{field:'bt_name', title:'产品所属银行',width:40}
                    ,{field:'name', title:'理财产品名称',width:50}
                    ,{field:'openType_show', title:'申购开放类型',width:40}
                    ,{field:'risk_level_show', title:'风险评价',width:40}
                    // ,{field:'inverate_expect_max', title:'预期化收益上限',width:40}
                    // ,{field:'inverate_expect_min', title:'预期化收益下限',width:40}
                    ,{field:'inverate_actual', title:'实际收益率',width:40}
                    // ,{field:'amount_acc_min', title:'单笔最低投资金额',width:40}
                    // ,{field:'amount_acc_max', title:'单笔最高投资金额',width:40}
                    // ,{field:'amount_redeem_min', title:'单笔最低赎回金额',width:40}
                    // ,{field:'amount_redeem_max', title:'单笔最高赎回金额',width:40}
                    // ,{field:'amount_total_min', title:'最低投资金额',width:40}
                    // ,{field:'amount_total_max', title:'最高投资金额',width:40}
                    ,{field:'raise_start_date', title:'募集开始日期',width:40}
                    ,{field:'raise_end_date', title:'募集结束日期',width:40}
                    ,{field:'start_date', title:'本期开始日期',width:40}
                    ,{field:'end_date', title:'本期结束日期',width:40}
                    ,{field:'expire_end_date', title:'到期日期',width:40}
                    ,{field:'amount_total_max', title:'最高投资金额',width:40}
                    // ,{field:'third_code', title:'第三方产品代码',width:50}
                    ,{field:'tStatus_show', title:'第三方产品状态',width:40}
                    ,{field:'is_show_name', title:'终端是否展现',width:30}
                    ,{field:'status_show', title:'状态',width:30,}
                    ,{field:'publish_date', title:'起售日期',width:40}
                    // ,{field:'under_date', title:'下架日期',width:40}
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
    $('#phjfFinancialProduct_grid').datagrid("clearChecked");
    $('#phjfFinancialProduct_grid').datagrid("clearSelections");
    $('#phjfFinancialProduct_grid').datagrid('reload');
}


function deleteAll(ids,names){
    confirm('确定删除['+names+']吗？',{
        onOK:function(){
            $.getJSON('/phjfFinancialProduct/delete.htm',{ids:ids},function(result){
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
function deployAll(seq_uuid,title,url){
    confirm(title,{
        onOK:function(){
            $.getJSON("/phjfht/api/v1/financial/"+url,{seq_uuid:seq_uuid},function(result){
                if(result.code != 1){
                    alert(result.msg);
                    return;
                }
                reloadGrid();
            });
        }
    });
}
