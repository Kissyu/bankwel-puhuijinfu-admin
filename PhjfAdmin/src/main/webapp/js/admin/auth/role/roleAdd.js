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
            $('#submitAuthRole').click(function (e) {
                if (!isFormValid()) {
                    return;
                }
                if(!$(this).hasClass("submited")){
                    $(this).addClass("submited");
                    Load();
                    var data = $('#roleForm').serializeObject();
                    var url="";
                    if($("#seq_uuid").val()){
                        url = '/phjfht/api/v1/auth/modifyRole';
                    }else{
                        url = '/phjfht/api/v1/auth/addRole';
                    }
                    $.post(url, data, function (result) {
                        removeLoad();
                        if (result.code == 1) {
                            top.changeTab('添加角色',"角色管理","",'/phjfht/api/v1/auth/goRolePage',true);
                        } else {
                            alert(result.msg);
                        }
                        $('#submitAuthRole').removeClass("submited");
                    },"json");
                }
            });
        }

    };
}();

function isFormValid() {
    return $('#roleForm').form('validate');
}
