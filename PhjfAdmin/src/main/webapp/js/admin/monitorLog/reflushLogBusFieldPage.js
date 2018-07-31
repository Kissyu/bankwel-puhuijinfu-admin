$.ajaxSetup({
	cache : false
});
$(function(){
	$PC.init();
});
var PageCtrl = $PC = function(){
	
	var me = null;	
	return{
		/**
		 * 页面初始化
		 */
		init:function(){
			this.initVars();
			this.bindUI();
		},
		/**
		 * 变量初始化
		 */
		initVars:function(){
			me = this;
            $.ajax({
                url:path + '/phjfht/api/v1/monitor/reflushLogBusField',
                type:'post',
                dataType:'json',
                data:'',
                beforeSend: function () {
                    load();
                },
                complete: function () {
                    disLoad();
                },
                success:function(result){
                    alert(result.msg);
                }
            });
		},	
		/**
		 * 绑定事件和数据
		 */
		bindUI:function(){
		}
	};
}();

//弹出加载层
function load() {
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("业务字段补充中，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });
}
//取消加载层
function disLoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}