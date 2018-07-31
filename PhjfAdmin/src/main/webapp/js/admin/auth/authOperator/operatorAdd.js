/**
 * Created by bankwel on 2017/9/25.
 */

$.ajaxSetup({
    cache: false,
    traditional: true
});
$(function () {
    $PC.init();
    $('.sky').hide();
});
var PageCtrl = $PC = function () {
    var branchId = null;
    var branchName = null;
    var me = null;
    return {
        /**
         * 页面初始化
         */
        init: function () {
            this.initVars();
            this.renderUI();
            this.bindUI();
        },
        /**
         * 变量初始化
         */
        initVars: function () {
            me = this;
            $SL = $('#mainBranchTree');
        },
        /**
         * 渲染页面控件
         */
        renderUI: function () {
            $('#branchTreeWin').window('move',{left:300,top:50});
            //树形控件展现设置
            var zTreeMainSetting = {
                data: {
                    simpleData: {enable:true, idKey: "seq_id", pIdKey: "parent_seq_id", nameKey:"name"}
                },
                check: {
                    enable: true,
                    chkStyle: "radio",
                    radioType: "all"
                }
                ,async : {
                    enable : true,//设置 zTree 是否开启异步加载模式
                    url : "/phjfht/api/v1/auth/queryOptBranchList",//Ajax 获取数据的 URL 地址
                    otherParam: ["branch_seq_id", localStorage.getItem("branchId")]
                },
                callback: {
                    onCheck: function (event,treeId,treeNode) {
                        branchId = treeNode.seq_id;
                        branchName = treeNode.name;
                        
                    }
                }
            };
            var treeObj = $.fn.zTree.init($('#mainBranchTree'), zTreeMainSetting);
            var nodes = treeObj.getNodes();
            if(nodes.length > 0){
                treeObj.expandNode(nodes[0], true, true, true);
            }
        },
        /**
         * 绑定事件和数据
         */
        bindUI: function () {
            $('#branch_name').click(function () {
                $('#branchTreeWin').window('open');
            });
            $('#btn_ok').click(function(e){
                if(!branchId){
                    alert("请选择机构！");
                    return;
                }
                $('#branch_name').val(branchName);
                $('#branch_seq_id').val(branchId);
                $('#branchTreeWin').window('close');
            });
            $('#btn_cancel').click(function(e){
                $('#branchTreeWin').window('close');
            });

            $('#submitBranch').click(function (e) {
                if (!isFormValid()) {
                    return;
                }
                var reg = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,20}/;
                if(!$("#password").val().match(reg)){
                    alert("密码必须包含数字、字母、特殊字符，并且长度在8-20位之间");
                    return ;
                }

                if(!$(this).hasClass("submited")){
                    $(this).addClass("submited");
                    Load();
                    var data = $('#addBranchForm').serializeObject();
                    data.password = $.md5($("#password").val());
                    var url="/phjfht/api/v1/auth/addOperator";

                    $.post(url, data, function (result) {
                        removeLoad();
                        if (result.code == 1) {
                            top.changeTab('机构',"机构列表","icon-apply", "/phjfht/api/v1/auth/goOperatorPage",true);
                        } else {
                            alert(result.msg);
                        }
                        $('#submitBranch').removeClass("submited");
                    },"json");
                }
            });
        }
    };
}();

function isFormValid() {
    return $('#addBranchForm').form('validate');
}



