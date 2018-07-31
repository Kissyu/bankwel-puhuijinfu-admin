$.ajaxSetup({
	cache : false
});
$(function(){
	$PC.init();
});
var countdown=60;
function settime() {
    if (countdown == 0) {
        $(".getCode").show();
        $(".reGetCode").hide();
        countdown = 60;
        return;
    } else {
        $("#timeSend").html(countdown+'');
        countdown--;
    }
    setTimeout(function() {
            settime() }
        ,1000)
}
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
			if(localStorage.getItem("username")){
				$("#userName").val(localStorage.getItem("username"));
			}
			if(localStorage.getItem("password")){
				$("#password").val(localStorage.getItem("password"));
			}
			$(".maincontent").height(($(window).height()-$(".headerBox").outerHeight())+'px');
		},
		/**
		 * 绑定事件和数据
		 */
		bindUI:function(){
			$(".inpBox input").on("keydown",function () {
				$(".noneTip").hide();
			});
			$(document).on("keydown",function (e) {
				if(e.keyCode == 13){
					$('#loginBtn').trigger('click');
				}
			});
			$("body").on("click",'.combo-text',function(){
				$(this).siblings("span").find("span").click();
			});
			$(".combo-arrow").on("click",function (e) {
				$(".noneTip").hide();
			});

            //发送手机验证码
            $(".getCode").click (function () {
                $(".getCode").hide();
                var userName = $.trim($("#userName").val());
                var password = $.trim($("#password").val());
                $.ajax({
                    url: '/phjfht/api/v1/system/sendSmsCode',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        userName: userName,
                        type: 'ipLoginCheck',
                        password: $.md5(password)
                    },
                    success: function (result) {
                        var code = result.code;
                        if(code==1){
                            $(".getCode").hide();
                            $(".reGetCode").show();
                            settime();
                        }
                    }
                })
            });

		    $('#loginBtn').click(function(){
				Load();
				var userName = $.trim($("#userName").val());
				var password = $.trim($("#password").val());
				var applyCenter = $.trim($('#apply_center_seq_id').combobox('getValue'));
				var verifyCode = $.trim($("#verifyCode").val());
				if(userName==""){
					$('#userName').focus();
					$(".noneTip").html("账户名不能为空！");
					$(".noneTip").show();
					removeLoad();
				}else if(password == "") {
					$('#password').focus();
					$(".noneTip").html("密码不能为空！");
					$(".noneTip").show();
					removeLoad();
				}else if(verifyCode == "") {
                    $('#verifyCode').focus();
                    $(".noneTip").html("验证码不能为空！");
                    $(".noneTip").show();
                    removeLoad();
                }else {
					$.ajax({
						url: '/checkLogin',
						type: 'post',
						dataType: 'json',
						data: {
							userName: userName,
							password: $.md5(password),
							apply_center_seq_id:applyCenter,
                            verifyCode:verifyCode
						},
						success: function (result) {
							removeLoad();
							var code = result.code;
							var msg = result.message;
							var applyList = [{apply_seq_id:"",apply_name:"---选择应用中心---"}];

							applyList = applyList.concat(result.applyList);
							if (code == -1) {
								$(".noneTip").html(msg);
								$(".noneTip").show();
							}else if(code==1){
								$(".noneTip").html(msg);
								$(".noneTip").show();
								$("#apply_center_seq_id").combobox("loadData",applyList);
								$(".applyCentre").show();
								$(".loginBox").height("450px");

							}else {
								location.href = '/login?apply_center_seq_id='+applyCenter + "&DukeToken=" + result.DukeToken;
							}
						}
					});
				}
			});

		}
	};
}();