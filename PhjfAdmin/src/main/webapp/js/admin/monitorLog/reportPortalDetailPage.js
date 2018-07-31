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
            $CL = $('#reportPortalDetail_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            var data = $('#searchForm').serializeObject();
            $CL.datagrid({
                url:path + '/phjfht/api/v1/monitor/queryMonitorBuslogListByPage',
                queryParams: {
                    "reportWay": $('#reportWay').val(),
                    "startTime": $('#startTime').val(),
                    "reportSubject": $('#reportSubject').val()
                },
                fit: true,
                fitColumns: false,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: false,
                idField: 'seq_id',
                pagination: true,
                pageSize: 25,
                pageList: [25, 30, 50],
                columns:[[
                    {field:'seq_id', hidden:true}
                    ,{field:'sys_code', hidden:true}
                    ,{field:'sys_code_show', title:'平台系统',width:100}
                    ,{field:'sub_sys_code', title:'子系统编号',width:60}
                    ,{field:'user_id', title:'用户编号',width:80}
                    ,{field:'clientip', title:'请求IP',width:100}
                    ,{field:'client_type', title:'终端类型',width:100}
                    ,{field:'url', title:'请求链接',width:400}
                    ,{field:'protocol', title:'请求协议',width:80}
                    ,{field:'bus_path', title:'操作路径',width:200}
                    ,{field:'bus_method', title:'操作方法',width:100}
                    ,{field:'bus_target', title:'操作目标',width:100}
                    ,{field:'bus_content', title:'操作内容',width:100}
                    ,{field:'useragent', title:'客户端代理',width:400}
                    ,{field:'querystring', title:'请求查询参数',width:140}
                    ,{field:'starttime', title:'请求时间',width:100}
                    ,{field:'endtime', title:'写入日志时间',width:100}
                ]],
                toolbar: _tools,
                onClickRow: function(rowIndex, rowData) {
                    curRow = rowData;
                    lastIndex = rowIndex;
                },
            });
        },
        /**
         * 绑定事件和数据
         */
        bindUI:function(){
        }
    };
}();