$.ajaxSetup({
	cache : false
});
$(function() {
	$PC.init();
	$('.sky').hide();
});
var PageCtrl = $PC = function() {

	var me = null, initBannerSize = {}, selectedTabTitle = null,backdata=null;
	var needScrollTabs = [];
	var branch_seq_id = null;
	var apply_seq_id = null;

	return {
		/**
		 * 页面初始化
		 */
		init : function() {
			this.initVars();
			this.renderUI();
			this.bindUI();
		},
		/**
		 * 变量初始化
		 */
		initVars : function() {
			me = this;
		},
		/**
		 * 渲染页面控件
		 */
		renderUI : function() {
			$('#mainTabs').tabs({
				onSelect : function(title) {
					selectedTabTitle = title;
					//是否是需要刷新的标签
					if($RT.isRefreshTab(title)){
						$RT.doRefreshTab(title);
						$RT.removeRefreshTab(title);
					}
				},
				onContextMenu : function(e,title) {
					e.preventDefault();
					$('#tab-menu').menu('show', {
						left : e.pageX,
						top : e.pageY
					});
				}
			});
			apply_seq_id = $('#applySele').children('option:selected').val();  //机构选中值
			branch_seq_id = $('#branchSele').children('option:selected').val();
			localStorage.setItem("branchId",branch_seq_id);
		},
		/**
		 * 绑定事件和数据
		 */
		bindUI : function() {
			//修改密码窗口
			me.setModifyPwdWin();
			//tab右键
			me.initTabMenu();
			//快捷按钮
			$('a', 'div.deail_info').click(function(e) {
				me[$(this).attr('id')]();
			});
			//左侧导航
			//树形控件展现设置
			var zTreeMainSetting = {
				data: {
					simpleData: {enable:true, idKey: "seq_id", pIdKey: "parent_seq_id", rootPId: "" , nameKey:"name"}
				},async : {
					enable : true,//设置 zTree 是否开启异步加载模式
					url : "/queryResourceList",//Ajax 获取数据的 URL 地址
					autoParam : [ "seq_id" ], //异步加载时需要自动提交父节点属性的参数
					otherParam: ["apply_center_seq_id",apply_seq_id, "branch_seq_id", branch_seq_id]
				},
				callback: {
					beforeClick: function(treeId, treeNode) {
						var title = treeNode.name;
						var url = treeNode.aurl;

						if ($('#mainTabs').tabs('exists', title)) {
							$('#mainTabs').tabs('select', title);
						}else{
							if(url){
								var url = treeNode.open_type=="outweb"?url:url;
								addPanel(title,url);
							}
						}
					}
				}
			};
			var index = 0;
			function addPanel(title,url){
				index++;
				var content;
				//var needScroll = $.inArray(title,needScrollTabs) == -1?'no':'yes';
				if (url) {
					content = '<iframe scrolling="no" id="'
						+ title
						+ '" frameborder="0"  src="'
						+ url
						+ '" style="width:100%;height:100%;"></iframe>';
				}
				$('#mainTabs').tabs('add', {
					title : title,
					closable : true,
					//iconCls : icon,
					content : content
				});
			}
			var treeObj = $.fn.zTree.init($('#mainMenuTree'), zTreeMainSetting);
			var nodes = treeObj.getNodes();
			if(nodes.length > 0){
				treeObj.expandNode(nodes[0], true, false, true);
			}
			//切换应用中心
			$('#applySele').change(function(){
				apply_seq_id = $(this).children('option:selected').val();
				branch_seq_id = $('#branchSele').children('option:selected').val();  //机构选中值
				var settingApply = {
					data: {
						simpleData: {enable:true, idKey: "seq_id", pIdKey: "parent_seq_id", rootPId: "" , nameKey:"name"}
					},async : {
						enable : true,//设置 zTree 是否开启异步加载模式
						url : "/queryResourceList",//Ajax 获取数据的 URL 地址
						autoParam : [ "seq_id" ], //异步加载时需要自动提交父节点属性的参数
						otherParam: ["apply_center_seq_id",apply_seq_id, "branch_seq_id", branch_seq_id]
					},
					callback: {
						beforeClick: function(treeId, treeNode) {
							var title = treeNode.name;
							var url = treeNode.aurl;

							if ($('#mainTabs').tabs('exists', title)) {
								$('#mainTabs').tabs('select', title);
							}else{
								if(url){
									var url = treeNode.open_type=="outweb"?url:url;
									addPanel(title,url);
								}
							}
						}
					}
				};
				var treeObj1 = $.fn.zTree.init($('#mainMenuTree'), settingApply);
				var nodes1 = treeObj1.getNodes();
				if(nodes1.length > 0){
					treeObj1.expandNode(nodes1[0], true, false, true);
				}
				closeAllTabs();
				//$('#bodyCenter').panel("reload");
			});
			//切换组织机构
			$('#branchSele').change(function(){
				branch_seq_id =$(this).children('option:selected').val();//这就是selected的值
				apply_seq_id = $('#applySele').children('option:selected').val();  //机构选中值
				localStorage.setItem("branchId",branch_seq_id);
				var settingBranch = {
					data: {
						simpleData: {enable:true, idKey: "seq_id", pIdKey: "parent_seq_id", rootPId: "" , nameKey:"name"}
					},async : {
						enable : true,//设置 zTree 是否开启异步加载模式
						url : "/queryResourceList",//Ajax 获取数据的 URL 地址
						autoParam : [ "seq_id" ], //异步加载时需要自动提交父节点属性的参数
						otherParam: ["apply_center_seq_id",apply_seq_id, "branch_seq_id", branch_seq_id]
					},
					callback: {
						beforeClick: function(treeId, treeNode) {
							var title = treeNode.name;
							var url = treeNode.aurl;

							if ($('#mainTabs').tabs('exists', title)) {
								$('#mainTabs').tabs('select', title);
							}else{
								if(url){
									var url = treeNode.open_type=="outweb"?url:url;
									addPanel(title,url);
								}
							}
						}
					}
				};
				var treeObj2 = $.fn.zTree.init($('#mainMenuTree'), settingBranch);
				var nodes2 = treeObj2.getNodes();
				if(nodes2.length > 0){
					treeObj2.expandNode(nodes2[0], true, false, true);
				}
				closeAllTabs();
			});
			//设置顶部右侧按钮
			me.setBannerRightButton();
			//获取待处理订单数量
			//window.setInterval(getInfoPolling, 5000);
            //默认加载首页
			//var content = '<iframe scrolling="no" id="首页" frameborder="0"  src="'
			//	+ ('/test/testAdd')
			//	+ '" style="width:100%;height:100%;"></iframe>';
			//$('#mainTabs').tabs('add', {
			//	title : "首页",
			//	closable : false,
			//	iconCls : "",
			//	content : content
			//});
		},
		doLogout : function() {
			location.replace('/logout?DukeToken=' + token);
		},
		doModifyPwd : function() {
			$('#modifyPwdWin').window('open');
		},
		setModifyPwdWin:function(){
			$('#modifyPwd_cancel').click(function(e){
				$('#modifyPwdWin').window('close');
				$('#modifyPwdForm').form('clear');
				$('#errorMsg2').hide();
				$('#errorMsg1').hide();
				$('#errorMsg').hide();
				$('font.error','#errorMsg').text("");
				$('font.error','#errorMsg1').text("");
				$('font.error','#errorMsg2').text("");
			});
			$('#oldPwd').keypress(function(e){
				if(e.keyCode == 13){
					if($.trim($('#newPwd').val()).length > 0){
						if($.trim($('#confirmPwd').val()).length > 0){
							$('#modifyPwd_ok').trigger('click');
						}else{
							$('#confirmPwd').focus();
						}
					}else{
						$('#newPwd').focus();
					}
				}
			});
			$('#confirmPwd').keypress(function(e){
				if(e.keyCode == 13){
					if($.trim($('#oldPwd').val()).length > 0){
						$('#modifyPwd_ok').trigger('click');
					}else{
						$('#oldPwd').focus();
					}
				}
			});
			$('#newPwd').keypress(function(e){
				if(e.keyCode == 13){
					if($.trim($('#confirmPwd').val()).length > 0){
						$('#modifyPwd_ok').trigger('click');
					}else{
						$('#confirmPwd').focus();
					}
				}
			});
			$('#oldPwd').blur(function(e){
				var oldpsw = $.trim($("#oldPwd").val());
				if(oldpsw.length > 0){
					$.ajax({
						url:'/admin/getUserName.htm',
						type:'post',
						dataType:'json',
						data:{
							oldpsw:$.md5(oldpsw)
							},
						success:function(result){
							backdata = result.code;
							var msg = result.message;
							if(backdata == -1){
								$('font.error','#errorMsg').text(msg);
								$('#errorMsg').show();
								$("#oldPwd").select();
								$('font.error','#errorMsg1').text("");
								$('font.error','#errorMsg2').text("");
								$('#errorMsg1').hide();
								$('#errorMsg2').hide();
							}else{
								$('font.error','#errorMsg').text("");
								$('#errorMsg').hide();							
							}
						}
					});
				}
				
			});
			$('#confirmPwd').blur(function(e){
				var psw = $.trim($("#newPwd").val());
				var repsw=$.trim($("#confirmPwd").val());
				if(backdata==-1){
					$('font.error','#errorMsg').text(msg);
					$('#errorMsg').show();
					$("#oldPwd").select();
					$('font.error','#errorMsg1').text("");
					$('font.error','#errorMsg2').text("");
					$('#errorMsg1').hide();
					$('#errorMsg2').hide();
					return;
				}
				if(psw===''){		
					$("#newPwd").focus();
					$('font.error','#errorMsg1').text('请输入新密码');
					$('#errorMsg1').show();
					$("#newPwd").select();
					$('font.error','#errorMsg').text("");
					$('font.error','#errorMsg2').text("");
					$('#errorMsg').hide();
					$('#errorMsg2').hide();
				}else{
				if(repsw!=psw){
					$('font.error','#errorMsg2').text('两次输入不一致');
					$('#errorMsg2').show();
					$("#newPwd").select();	
					$('font.error','#errorMsg').text("");
					$('font.error','#errorMsg1').text("");
					$('#errorMsg').hide();
					$('#errorMsg1').hide();
				}else{
					$('font.error','#errorMsg').text("");
					$('font.error','#errorMsg1').text("");
					$('font.error','#errorMsg2').text("");
					$('#errorMsg').hide();
					$('#errorMsg1').hide();
					$('#errorMsg2').hide();
				}
				}
				
			});
			$('#modifyPwd_ok').click(function(e){
				var oldpsw = $.trim($("#oldPwd").val());
				var psw = $.trim($("#newPwd").val());
				var repsw=$.trim($("#confirmPwd").val());
				if(backdata==-1){
					$('font.error','#errorMsg').text(msg);
					$('#errorMsg').show();
					$("#oldPwd").select();
					$('font.error','#errorMsg1').text("");
					$('font.error','#errorMsg2').text("");
					$('#errorMsg1').hide();
					$('#errorMsg2').hide();
					return;
				}
				if(oldpsw===''){
					$('font.error','#errorMsg').text('输入旧密码');
					$('#errorMsg').show();
					$("#oldPwd").select();
					$("#oldPwd").focus();
					$('font.error','#errorMsg1').text("");
					$('font.error','#errorMsg2').text("");
					$('#errorMsg1').hide();
					$('#errorMsg2').hide();
				}else if(psw===''){		
					$("#newPwd").focus();
					$('font.error','#errorMsg1').text('请输入新密码');
					$('#errorMsg1').show();
					$("#newPwd").select();
					$('font.error','#errorMsg').text("");
					$('font.error','#errorMsg2').text("");
					$('#errorMsg').hide();
					$('#errorMsg2').hide();
				}else if(repsw===psw){
					$('font.error','#errorMsg').text("");
					$('font.error','#errorMsg1').text("");
					$('#errorMsg').hide();
					$('#errorMsg1').hide();
					$.ajax({
						url:'/admin/modUserPasswordByID.htm',
						type:'post',
						dataType:'json',
						data:{
							psw:$.md5(psw)
						},
						success:function(result){
							var data = result.code;
							var msg = result.message;
							if(data == -1){
								$('font.error','#errorMsg').text(msg || '密码修改出错，请联系管理员！');
								$('#errorMsg').show();
								$("#oldPwd").select();
							}else{
								$('#modifyPwd_cancel').click();
								alert("修改成功");
							}
						}
					});
					
				}else{
					$('font.error','#errorMsg2').text('两次输入不一致');
					$('#errorMsg2').show();
					$("#newPwd").select();
					$('font.error','#errorMsg').text("");
					$('font.error','#errorMsg1').text("");
					$('#errorMsg').hide();
					$('#errorMsg1').hide();
				}		
			});
		},
		initTabMenu : function() {
			$("#menu-closeOthers").click(function() {
				confirm('您确定要关闭其他所有标签吗?', function() {
					var tabs = $('#mainTabs').tabs('tabs');
					for ( var i = tabs.length - 1; i >= 0; i--) {
						var title = tabs[i].panel('options').title;
						var closeable = tabs[i].panel('options').closable;
						if (title != selectedTabTitle && closeable) {
							$('#mainTabs').tabs('close', title);
						}
					}
				});
			});
			$("#menu-refresh").click(
					function() {
						$RT.doRefreshTab(selectedTabTitle);
					});
			$("#menu-close").click(function() {
				$("#mainTabs").tabs("close", selectedTabTitle);
			});
			$("#menu-closeAll").click(function() {
				$("#closeAllBtn").click();
			});
		},
		setBannerRightButton:function(){
			$("#closeAllBtn").click(
					function() {
						confirm('您确定要关闭所有标签吗?', function() {
							closeAllTabs();
						});
					});
			$('#ceBanner').click(function() {
				var cur_icon = $('#ceBanner').linkbutton('options').iconCls;
				if (cur_icon == 'icon-expand-up') {
					initBannerSize.nh = $('#bannerBg').innerHeight();
					$('#bannerBg').hide();
					$('body').layout('panel', 'north').panel('resize', {
						height : 1
					});
					$('body').layout('resize');
					$('#ceBanner').linkbutton({
						iconCls : 'icon-expand-down'
					});
					$('#ceBanner').attr('title', '展开');
				} else if (cur_icon == 'icon-expand-down') {
					$('#bannerBg').show();
					$('body').layout('panel', 'north').panel('resize', {
						height : initBannerSize.nh
					});
					$('body').layout('resize');
					$('#ceBanner').linkbutton({
						iconCls : 'icon-expand-up'
					});
					$('#ceBanner').attr('title', '收起');
				}
			});		
		}
	};
}();

//刷新标签页统一处理
var RefreshTabs = $RT = function(){
	var refreshTabs = {};
	return{
		setRefreshTab:function(titles){
			var arr = titles.split(',');
			for(x in arr){
				refreshTabs[arr[x]] = true;
			}
		},
		removeRefreshTab:function(titles){
			var arr = titles.split(',');
			for(x in arr){
				if(refreshTabs[arr[x]]){
					refreshTabs[arr[x]] = false;
				}
			}
		},
		isRefreshTab:function(title){
			return !!refreshTabs[title];
		},
		doRefreshTab:function(title){
			var contentFrame = $("#mainTabs").tabs("getTab",
					title).children("iframe").get(0);
			contentFrame.src = contentFrame.src;
		}
	};
}();

function addTab(title, iconCls, url, forceRefresh) {
    if(typeof(url) != 'undefined'  && url != null && url != ""){
        var midKey =  url.indexOf("?") > -1 ? "&DukeToken=" : "?DukeToken=";
        url = url + midKey + token;
    }

	if ($('#mainTabs').tabs('exists', title)) {
		if(forceRefresh){
			$('#mainTabs').tabs('select', title);
			$('#mainTabs').tabs('getSelected').children("iframe").attr("src",url);
		} else {
			$('#mainTabs').tabs('select', title);
		}
	} else {
		var content = '<iframe scrolling="no"  frameborder="0" id="' + title + '" src="' + url
				+ '" style="width:100%;height:100%;overflow:hidden;"></iframe>';
		$('#mainTabs').tabs('add', {
			title : title,
			closable : true,
			iconCls : iconCls,
			content : content
		});
	}
}

function refreshTab(title, iconCls, url, forceRefresh){
    if(typeof(url) != 'undefined'  && url != null && url != ""){
        var midKey =  url.indexOf("?") > -1 ? "&DukeToken=" : "?DukeToken=";
        url = url + midKey + token;
    }

	if ($('#mainTabs').tabs('exists', title)) {
		if(forceRefresh){
			$('#mainTabs').tabs('select', title);
			$('#mainTabs').tabs('getSelected').children("iframe").attr("src",url);
		} else {
			$('#mainTabs').tabs('select', title);
		}
	}
}

function changeTab(oldtitle,newtitle,icon,url,flag){
	$("#mainTabs").tabs("close", oldtitle);
	$("#mainTabs").tabs("close", newtitle);
	top.refreshTab("首页", "", '/oaUserProcess/detail.htm', true);
	top.addTab(newtitle,icon, url,flag);
}
function openTab(title){
	$('#navPanel .easyui-linkbutton').each(function(i,e){
		var text = $(e).text();
		if(text == title){
			$(e).click();
		}
	});
}
function closeAllTabs() {
	var tabs = $('#mainTabs').tabs('tabs');
	for ( var i = tabs.length - 1; i >= 0; i--) {
		if ("首页" == tabs[i].panel('options').title){
			continue;
		}
		$('#mainTabs').tabs('close',
			tabs[i].panel('options').title);
	}
}
/*
 * 顶部tab点击
 * */
(function(){
    $("#topTabs a").on("click",function(e) {
        e.preventDefault();
        if ($(this).attr('id') == 'current'){
        	return;
        }else{
        	$("#topTabs a").removeAttr('id');
        	$(this).attr('id','current');
        	var url = $(this).attr('url');
        	if(!url){
        		$('#bodyWest').empty();
        		$('#bodyWest').html('未实现');
        		return;
        	}
        	$.get('/'+url,null,function(html){
        		$('#bodyWest').empty();
        		$('#bodyWest').html(html);
        	});
        }
    });
    $("#topTabs li:first a").trigger('click');
    $("#topTabs li:first a").attr('id','current');


})();




function getInfoPolling() {
    $.ajax({
        url: "/occupancyInfo/getOccupancyNum.htm",
        data: { "timed": new Date().getTime() },
        dataType: "json",
        timeout: 5000,
        error: function(XMLHttpRequest, textStatus, errorThrown) {
           
        },
        success: function(data, textStatus) {
        	if(data.data>0){
        		var tempAudio = document.getElementById("mp3");
        		tempAudio.play();
        		$.messager.show({
        			title:'温馨提示:', 
            		msg:data.message, 
            		timeout:4000, 
            		showType:'slide'
        		}).click(function(){
        			$(".top_menu_manage_member").trigger('click');
                    setTimeout(function(){
                        $('#right_menu_manage_member_title').panel('expand');
                    },500); 
                    setTimeout(function(){
                        $("#right_menu_manage_member").trigger('click');
                    },500);
               });
        	}

        }
    });
}