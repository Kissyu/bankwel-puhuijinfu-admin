var bk = bk || {};
/*======基于jQuery EasyUI开始======*/
function alert(msg,fn){
	jQuery.messager.alert("提示",msg,"info",fn);
}

function confirm(msg,callback,args,context){
	function _execFunc(func){
		if(jQuery.isArray(args)) {
			func.apply(context,args);
		}else if(args) {
			func.call(context,args);
		}else{
			func.call(context);
		}
	}
	context = context || callback;
	jQuery.messager.confirm("提示",msg,function(value){
			if(!callback)
				return false;
			if(!value && callback.onCancel){
				_execFunc(callback.onCancel);
				return false;
			}
			if(value){
				_execFunc(callback.onOK ? callback.onOK : callback);
			}
	  });
};

/**
 * 判断树节点是否闭合
 * return：true：闭合，false：展开
 */
function isCollapsed(treeNode){
	return jQuery(treeNode.target).find('span.tree-hit').hasClass('tree-collapsed');
}
/**
 * 打开排序窗口通用方法
 */
function sortHandler(data,url,target,cb,prefix){
	var result = bk.openModalDialog(url, 400, 400, data, _fn);
	!result && (result = window.returnValue);
	if (!result){
		return false;
	}
	_fn(result);
	function _fn(_result){
		if(target){
			var nodes = $.makeArray(target.children());
			$.each(_result, function(i, e) {
				$.each(nodes, function(index, el) {
					if ($(el).children(".tree-node").attr("node-id") == ((prefix?prefix:'')+e.id)) {
						$(target).append(el);
						return false;
					}
				});
			});
		}
		if(cb && $.isFunction(cb)){
			cb();
		}
	}
}
/**
 * 获取树节点通用方法
 */
var getTreeNode = function(treeID,nodeID){
	return $('#'+treeID).tree('find',nodeID);
};
/**
 * 添加树节点成功之后通用处理方法
 */
var addTreeNode = function(treeID,node,pNode){
	if (pNode) {
		$('#'+treeID).tree('append',{parent:pNode.target,data:[node]});
	}else{
		$('#'+treeID).tree('append',{data:[node]});
	}
	var newNode = getTreeNode(treeID,node.id);
	if (newNode) {
		$(newNode.target).trigger('click');
	}
};
/*======基于jQuery EasyUI结束======*/

/*======基于jQuery扩展开始======*/
(function($){
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push($.trim(this.value) || '');
			} else {
				o[this.name] = $.trim(this.value) || '';
			}
		});
		return o;
	};
})(jQuery);
/*======基于jQuery扩展结束======*/

/*======基于js原始扩展开始======*/
Array.prototype.indexOf = function(obj,key){
	var arr = this;
	for(var i = 0;i<arr.length;i++){
		if(!key){
			if(arr[i] == obj)
			   return i;
		}else{
		    if(arr[i][key] == obj[key])
			   return i;
		}
	}
	return -1;
};
Array.prototype.swap = function(src,dest){
	var arr = this;
	var tmp = arr[dest];
	arr[dest] = arr[src];
	arr[src] = tmp;
};

window.XMLHttpRequest.prototype._send = XMLHttpRequest.prototype.send;
window.XMLHttpRequest.prototype.send = function (body) {
    if(typeof(token) != 'undefined' && token != null && token != ""){
        this.setRequestHeader("DukeToken", token);
	}
    //duke_send(body);
    window.XMLHttpRequest.prototype._send.apply(this, arguments);
    //duke_send.apply(this, arguments);
}
/*======基于js原始扩展结束======*/


/*======系统工具类开始======*/
var SCREEN_W_FIX = 20,SCREEN_H_FIX = 40;

bk._dialogDimension = function(width,height){
	var left = (window.screen.availWidth-width)/2;
	var top = (window.screen.availHeight-height)/2;
	//screen.availWidth,screen.availHeight修正
	if(width >= screen.availWidth){
		width = screen.availWidth - (jQuery.browser.msie ? SCREEN_W_FIX : 0);
	}
	if(height >= screen.availHeight){
		height = screen.availHeight - (jQuery.browser.msie ? SCREEN_H_FIX : 0);
	}
	top = top <= SCREEN_H_FIX/2 ? 0 : top;
	left = left <= SCREEN_W_FIX/2 ? 0 : left;
	return {"left":left, "top":top, "width":width, "height":height};
};

bk.openWin = function(url,width,height,name,resizable){
	var dimension = bk._dialogDimension(width,height);
	var left = dimension.left, top = dimension.top;
	width = dimension.width;
	height = dimension.height;
	var options = "height="+height+",width="+width+",top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no, resizable="+(resizable === false?"no":"yes")+",location=no, status=no";
	try{
		window.open(url,(name|| new Date().getTime().toString()).replace(/\s+/gi,''),
					options).focus();
	}catch(e){
		alert("打开窗口错误:" + e.message + "<br/>"
			+ options);
	}
};

bk.openFullHieghtScreenWin = function(url,width,name){
	var dimension = bk._dialogDimension(width,height);
	var left = dimension.left, top = dimension.top;
	var height = screen.availHeight - (jQuery.browser.msie ? SCREEN_H_FIX : 0);
	var winName = name || new Date().getTime();
	var params = "height="+height+",width="+width+",top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no";
	var reParams = /\?.+/;
	var win = null;
	try{
		win = window.open("",winName,params);
		if(win.location.href.replace(reParams,"").indexOf(url.replace(reParams,"")) === -1){
			win = window.open(url,winName,params);
		}
	}catch(e){
		win = window.open(url,winName,params);
	}
	win.resizeTo(width,height);
	win.focus();
	return win;
};

bk.openFullScreenWin = function(url,name){
	var width = screen.availWidth - (jQuery.browser.msie ? SCREEN_W_FIX : 0);
	var height = screen.availHeight - (jQuery.browser.msie ? SCREEN_H_FIX : 0);
	var top = 0,left = 0;
	var winName = name || new Date().getTime();
	var params = "height="+height+",width="+width+",top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no";
	var reParams = /\?.+/;
	var win = null;
	try{
		win = window.open("",winName,params);
		if(win.location.href.replace(reParams,"").indexOf(url.replace(reParams,"")) === -1){
			win = window.open(url,winName,params);
		}
	}catch(e){
		win = window.open(url,winName,params);
	}
	win.resizeTo(width,height);
	win.focus();
	return win;
};

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉子
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    encheck:
    {
        validator:function (value)
        {
            var reg = /^[a-zA-Z]+$/;
            return reg.test(value);
        },
        message:'只能输入英文字母'
    },
    //移动手机号码验证
    mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确.'
    },
	//验证必须输入1或大于1的数
	isnum: {//value值为文本框中的值
	validator: function (value) {
		var reg = /^\+?[1-9][0-9]*$/;
		return reg.test(value);
	},
	message: '只能输入1或大于1的数.'
},
	//验证必须输入1或0
	isorno: {//value值为文本框中的值
		validator: function (value) {
			var reg = /^[01]$/;
			return reg.test(value);
		},
		message: '只能输入0或者1.'
	},
    //座机号码验证
    phone:{
    	validator:function(value){
    		var reg = /\(?0\d{2,3}[) -]?\d{8}$/;
    		return reg.test(value);
    	},
    	message:'请输入正确定座机号码'
    },
	remoteField: {
		validator: function(value, dataArray) {
			var data = $('#'+dataArray[1]).serializeObject();
			var result = $.ajax({
				url: dataArray[0],
				dataType: "json",
				data: data,
				async: false,
				cache: false,
				type: "post"
			}).responseText;
			return result == "false";
		},
		message: "输入的值已经存在."
	},
	validaVersion: {
		validator: function(value, dataArray) {
			var data = $('#'+dataArray[1]).serializeObject();
			var result = $.ajax({
				url: dataArray[0],
				dataType: "json",
				data: data,
				async: false,
				cache: false,
				type: "post"
			}).responseText;
			return result == "false";
		},
		message: "输入的版本号必须大于当前版本号."
	},
	selectBankValid: {
		validator: function(value, dataArray) {
			var data = $('#'+dataArray[1]).serializeObject();
			var result = $.ajax({
				url: dataArray[0],
				dataType: "json",
				data: data,
				async: false,
				cache: false,
				type: "post"
			}).responseText;
			return result == "false";
		},
		message: "同一类型银行只能选择一个."
	},
	remoteTwoField: {
		validator: function(value, dataArray) {
			var data = {};
			data.key1=dataArray[1];
			data.value1 = $.trim(value);
			data.id = $("#"+dataArray[2]+"").val();
			if(dataArray.length > 3){
				data.key2=dataArray[3];
				data.value2 = $("#"+dataArray[3]+"").val();
			}
			var result = $.ajax({
				url: dataArray[0],
				dataType: "json",
				data: data,
				async: false,
				cache: false,
				type: "post"
			}).responseText;
			return result == "false";
		},
		message: "输入的值已经存在."
	},
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    comparNumber:{
      validator:function(value,params){
          var number_lower=Number($("#"+params[0]+"").val());
          var number_upper=Number($("#"+params[1]+"").val());
          var message=params[2];
          //额度上限小于额度下线
          if(number_upper < number_lower){
              $.fn.validatebox.defaults.rules.comparNumber.message=message;
              return false;
          }else{
              return true;
          }
      },message:''
    },
    //用户账号验证(只能包括 _ 数字 字母) 
    account: {//param的值为[]中值
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    },
	isBlank: {
		validator: function (value) {
			return $.trim(value) != ''
		},
		message: '不能为空，全空格也不行'
	},
	hasNull:{
		validator: function (value) {
			return value.indexOf(" ") == -1;
		},
		message: '不能有空格'
	},
    //验证楼层信息
    louceng:{
    	  validator: function (value,params) {
    		 var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    		 if(!reg.test(value)){
    			 $('#'+params[0]).val(value+'层');
    		 }
             return true;
          }, message: ''
    },
    chuangkuan:{
  	  validator: function (value,params) {
  		 var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
  		 var temp_value = params[0];
  		 if(!reg.test(value)){
  			$('#'+params[0]).val(value+'米');
  		 }
           return true;
        }, message: ''
  },
  mianji:{
  	  validator: function (value,params) {
  		 var temp_value = params[0];
  		 if(value.indexOf("㎡") == -1){
  			 $('#'+temp_value).val(value+'㎡');
  		 }
         return true;
        }, message: ''
  },
	startEndTime:{
		validator: function(value,param){
			function getdate(str) {
				if(str=="") return;
				var datetimeArr = str.split(" ");
				var dateArr=datetimeArr[0].split("-");
				var timeArr=datetimeArr[1].split(":");
				return dateArr.concat(timeArr);
			}
			var frm = $("input[name='"+param+"']").val();
			var startDate=new Date();
			startDate.setFullYear(getdate(frm)[0],getdate(frm)[1]-1,getdate(frm)[2]);
			startDate.setHours(getdate(frm)[3],getdate(frm)[4],0);
			var endDte=new Date();
			endDte.setFullYear(getdate(value)[0],getdate(value)[1]-1,getdate(value)[2]);
			endDte.setHours(getdate(value)[3],getdate(value)[4],0);
			return endDte.getTime()-startDate.getTime()>0;
		},
		message: '结束时间应晚于开始时间！'
	}
});

bk.openModalDialog = function(url,width,height,args,_fn){
	var dimension = bk._dialogDimension(width,height);
	var left = dimension.left, top = dimension.top;
	width = dimension.width;
	height = dimension.height;
	if(window.showModalDialog){
		return window.showModalDialog(url,args,
				"dialogWidth:"+width+"px;dialogHeight:"+height+"px;dialogTop:"+top+";dialogLeft:"+left+";scroll:no;status:no");
	}
	window.dialogArguments = args;
	window._callback = _fn;
	bk.openWin(url, width, height, '',false);
};
/*======系统工具类结束======*/

function getShow(value) {
	return 1==value ? '显示':'不显示';
}

function getStatus(value) {
	return 1==value ? '有效':'无效';
}

function getYesNo (value){
	return 1 == value ? '是' : '否';
}

function getLanguage(value){
	return 'ZH_SIMP' == value ? '中文简体' : '维吾尔语';
}

function formateDateYYMMDD(value){
	if(value){
		return value.substring(0,10);
	}else{
		return "";
	}
}
function statusColor(value) {
	if(value==""||value==null){
		return;
	}
	var result={};
	switch (value){
		case 1:
			result={class:'dealing'};
			break;
		case 2:
			result={class:'completed'};
			break;
		case 3:
			result={class:'notpass'};
			break;
		default:
			break;
	}
	return result;
}
function getDealStatus(value) {
	switch(value){
		case 1:
			return '处理中';
			break;
		case 2:
			return '已完成';
			break;
		case 3:
			return '未通过';
			break;
	}
}
function dateformateYMDHM(value) {
	if(value){
		return value.substring(0,16);
	}else{
		return "";
	}
}

function getApprovalUser(value,row) {
	if(row.status!=1){
		return "已结束";
	}else if(value.length==0){
		return;
	}
	var name =[];
	for(var i = 0; i < value.length ;i++){
		name.push(value[i].human.name);
	}
	return name + "【审批】";
}

function getLogo(value){
	if(value){
		return '<img style="display: block;height: 100px;" src="'+value+'"/>';
	}
}

Date.prototype.Format = function (fmt) { //author: meizz
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

//计算天数差的函数，通用
function DateDiff(start,end){
	if(start==null||start.length==0||end==null||end.length==0){
		return 0;
	}

	var arr=start.split("-");
	var starttime=new Date(arr[0],arr[1],arr[2]);
	var starttimes=starttime.getTime();

	var arrs=end.split("-");
	var endtime=new Date(arrs[0],arrs[1],arrs[2]);
	var endtimes=endtime.getTime();

	var intervalTime = endtimes-starttimes;//两个日期相差的毫秒数 一天86400000毫秒
	var Inter_Days = ((intervalTime).toFixed(2)/86400000);//加1，是让同一天的两个日期返回一天

	return Inter_Days;
}
//Load
function Load() {
    $("<div class='datagrid-mask'></div>").css({ display: "block", width: "100%", height: $(window).height(),"position":"fixed","left":0,top:0,"z-index":99999 }).appendTo("body");
    $("<div class='datagrid-mask-msg'></div>").html("正在提交中，请稍候。。。").appendTo("body").css({ display: "block","position":"absolute", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2,"z-index":100000 });
}
//hidden Load
function removeLoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}
//转换大写中文
function changeMoneyToChinese(money){
	var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字
	var cnIntRadice = new Array("","拾","佰","仟"); //基本单位
	var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位
	var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位
	//var cnInteger = "整"; //整数金额时后面跟的字符
	var cnIntLast = "元"; //整型完以后的单位
	var maxNum = 999999999999999.9999; //最大处理的数字

	var IntegerNum; //金额整数部分
	var DecimalNum; //金额小数部分
	var ChineseStr=""; //输出的中文金额字符串
	var parts; //分离金额后用的数组，预定义
	if( money == "" ){
		return "";
	}
	money = parseFloat(money);
	if( money >= maxNum ){
		$.alert('超出最大处理数字');
		return "";
	}
	if( money == 0 ){
		//ChineseStr = cnNums[0]+cnIntLast+cnInteger;
		ChineseStr = cnNums[0]+cnIntLast
		//document.getElementById("show").value=ChineseStr;
		return ChineseStr;
	}
	money = money.toString(); //转换为字符串
	if( money.indexOf(".") == -1 ){
		IntegerNum = money;
		DecimalNum = '';
	}else{
		parts = money.split(".");
		IntegerNum = parts[0];
		DecimalNum = parts[1].substr(0,4);
	}
	if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换
		zeroCount = 0;
		IntLen = IntegerNum.length;
		for( i=0;i<IntLen;i++ ){
			n = IntegerNum.substr(i,1);
			p = IntLen - i - 1;
			q = p / 4;
			m = p % 4;
			if( n == "0" ){
				zeroCount++;
			}else{
				if( zeroCount > 0 ){
					ChineseStr += cnNums[0];
				}
				zeroCount = 0; //归零
				ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
			}
			if( m==0 && zeroCount<4 ){
				ChineseStr += cnIntUnits[q];
			}
		}
		ChineseStr += cnIntLast;
		//整型部分处理完毕
	}
	if( DecimalNum!= '' ){//小数部分
		decLen = DecimalNum.length;
		for( i=0; i<decLen; i++ ){
			n = DecimalNum.substr(i,1);
			if( n != '0' ){
				ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
			}
		}
	}
	if( ChineseStr == '' ){
		//ChineseStr += cnNums[0]+cnIntLast+cnInteger;
		ChineseStr += cnNums[0]+cnIntLast;
	}/* else if( DecimalNum == '' ){
	 ChineseStr += cnInteger;
	 ChineseStr += cnInteger;
	 } */
	return ChineseStr;
}