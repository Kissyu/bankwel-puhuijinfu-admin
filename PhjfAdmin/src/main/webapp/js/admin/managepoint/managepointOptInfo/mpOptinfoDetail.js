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
		    $('#btn_ok').click(function(e){
		    	window.close();
		    });
		   
		   
		    $('#btn_cancel').click(function(e){
		    	window.close();
		    });
		}
	};
}();

