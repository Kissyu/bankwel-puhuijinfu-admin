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
        },
        /**
         * 渲染页面控件
         */
        renderUI: function () {
        },
        /**
         * 绑定事件和数据
         */
        bindUI: function () {

            $('#submitBranch').click(function (e) {
                if (!isFormValid()) {
                    return;
                }
                if(!$(this).hasClass("submited")){
                    $(this).addClass("submited");
                    Load();
                    var data = $('#modifyBranchForm').serializeObject();
                    var url="/phjfht/api/v1/auth/modifyBranch";

                    $.post(url, data, function (result) {
                        removeLoad();
                        if (result.code == 1) {
                            top.changeTab('机构',"机构列表","icon-apply", "/phjfht/api/v1/auth/goBranchPage",true);
                        } else {
                            alert(result.message);
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