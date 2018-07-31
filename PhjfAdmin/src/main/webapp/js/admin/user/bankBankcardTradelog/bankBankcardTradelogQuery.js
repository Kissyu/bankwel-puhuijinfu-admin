/**
 * Created by Administrator on 2017/12/25.
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

    var _tools = [];

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
            $CL = $('#phjfBankBankcardTradelog_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:'/phjfht/api/v1/user/queryBankcardTradelogByPage',
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
                    {field:'seq_id', hidden:true}
                    ,{field:'user_name', title:'会员账户',width:40}
                    ,{field:'bt_name', title:'开户行',width:40}
                    ,{field:'bind_card_no', title:'一类户卡号',width:60}
                    ,{field:'bank_card_no', title:'二类户卡号',width:60}
                    ,{field:'tradeType_show', title:'交易类型',width:25}
                    ,{field:'amount', title:'交易金额',width:40}
                    ,{field:'currency_show', title:'币种',width:20}
                    ,{field:'trade_seq', title:'交易流水号',width:40}
                    ,{field:'apply_time', title:'交易申请时间',width:40}
                    ,{field:'finish_time', title:'交易完成时间',width:40}
                    ,{field:'invalid_time', title:'交易失效时间',width:40}
                    ,{field:'status_show', title:'状态',width:25}
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


        }
    };
}();

function reloadGrid(){
    $('#phjfBankBankcardTradelog_grid').datagrid("clearChecked");
    $('#phjfBankBankcardTradelog_grid').datagrid("clearSelections");
    $('#phjfBankBankcardTradelog_grid').datagrid('reload');
}




