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
        id:'viewRow',
        text:'查看',
        iconCls:'icon-look',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选中要查看的货基产品信息！');
                return;
            }
            top.changeTab("（二期）货基产品管理", "（二期）货基产品管理","", path + '/phjfht/api/v1/financial/goFinancialFundViewPage?seq_uuid='+row.seq_uuid,false);
        }
    },'-',{
        id:'deployRow',
        text:'下架',
        iconCls:'icon-down',
        handler:function(){
            var row = $CL.datagrid('getSelected');
            if(!row){
                alert('请选择要下架的货基产品！');
                return;
            }
            deployAll(row.seq_uuid,'确定要下架['+row.title+']','unPublishFinancialFund');
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
            $CL = $('#phjfFinancialFund_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/financial/queryFinancialFundByPage',
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
                    ,{field:'fp_code', title:'货基产品编号',width:50}
                    ,{field:'fp_type_show', title:'理财产品类型',width:40}
                    ,{field:'bt_name', title:'产品所属银行',width:40}
                    ,{field:'title', title:'货基产品名称',width:50}
                    ,{field:'fin_nav', title:'产品净值',width:30}
                    ,{field:'fin_nav_total', title:'产品累计净值',width:40}
                    ,{field:'fin_income', title:'昨日收益',width:30}
                    ,{field:'fin_income_unit', title:'万份产品单位收益',width:40}
                    ,{field:'fin_income_rate', title:'产品收益率',width:40}
                    ,{field:'amount_min', title:'起售金额',width:40}
                    // ,{field:'third_code', title:'第三方产品代码',width:50}
                    ,{field:'isRecom_show', title:'是否推荐',width:30}
                    ,{field:'is_show_name', title:'终端是否展现',width:30}
                    ,{field:'status_show', title:'状态',width:30,}
                    ,{field:'publish_date', title:'起售日期',width:40}
                    ,{field:'under_date', title:'下架日期',width:40}
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
    $('#phjfFinancialFund_grid').datagrid("clearChecked");
    $('#phjfFinancialFund_grid').datagrid("clearSelections");
    $('#phjfFinancialFund_grid').datagrid('reload');
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
