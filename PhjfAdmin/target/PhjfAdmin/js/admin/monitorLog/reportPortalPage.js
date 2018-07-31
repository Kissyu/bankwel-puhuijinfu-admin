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
            $CL = $('#reportPortal_grid');
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
            $CL.datagrid({
                url:path + '/phjfht/api/v1/monitor/reportQuery',
                queryParams: {
                    "reportWay": $('#reportWay').val(),
                    "timePeriod": $('#timePeriod').val()
                },
                fit: true,
                fitColumns: false,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: false,
                idField: '',
                pagination: true,
                pageSize: 25,
                pageList: [25, 30, 50],
                columns:[[
                    {field:'reportWay', hidden:true}
                    ,{field:'startTime', hidden:true}
                    ,{field:'reportSubject', title:'统计主体',width:200}
                    ,{field:'reportNum', title:'统计数量(次)',width:200}
                    ,{field: 'action', title: '查看记录', width: 220,align : 'center',
                        formatter: function (value, row, index) {
                            return '<a href="javascript:;" onclick="look(\'' + row.reportWay + '\',\'' + row.reportSubject + '\',\'' + row.startTime + '\')">查看</a>';
                        }
                    }
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
            //查询
            $('#searchBtn').click(function(e){
                var data = $('#searchForm').serializeObject();
                $CL.datagrid('load',data);
            });
        }
    };
}();

function look(reportWay,reportSubject,startTime){
    var param = "reportWay=" + reportWay + "&reportSubject=" + reportSubject+ "&startTime=" + startTime;
    top.changeTab("统计日志明细查询", "统计日志明细查询","", path + '/phjfht/api/v1/monitor/goQueryLogListPage?' + param,false);
}