$.ajaxSetup({
    cache : false,
    traditional : true
});
$(function(){
    $PC.init();
    $('.sky').hide();
});
var PageCtrl = $PC = function(){

    var me = null;

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
        },
        /**
         * 渲染页面控件
         */
        renderUI:function(){
        },
        /**
         * 绑定事件和数据
         */
        bindUI:function(){
            $('#submitAuthResource').click(function (e) {
                if (!isFormValid()) {
                    return;
                }
                if(!$(this).hasClass("submited")){
                    $(this).addClass("submited");
                    Load();
                    var data = $('#resourceForm').serializeObject();
                    var url="";
                    var TabName = "";
                    if($("#seq_uuid").val()){
                        url = '/phjfht/api/v1/auth/modifyResource';
                        TabName = "修改资源";
                    }else{
                        url = '/phjfht/api/v1/auth/addResource';
                        if($("#type").val()=="menu"){
                            TabName = "添加菜单";
                        }else{
                            TabName = "添加按钮";
                        }
                    }
                    $.post(url, data, function (result) {
                        removeLoad();
                        if (result.code == 1) {
                            top.changeTab(TabName,"资源管理","",'/phjfht/api/v1/auth/goResourcePage',true);
                        } else {
                            alert(result.msg);
                        }
                        $('#submitAuthResource').removeClass("submited");
                    },"json");
                }
            });
        }

    };
}();

function isFormValid() {
    return $('#resourceForm').form('validate');
}
