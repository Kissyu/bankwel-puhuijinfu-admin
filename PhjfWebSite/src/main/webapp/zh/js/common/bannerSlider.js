/**defaultLi---包含图片的项目
 * defautlNum--- 表示图片的小圆点
 * showLi ---显示图片项目的类名
 * showSpan---标记圆点显示的类名
 * duration : 自动轮播间隔时间
 * @authors :liuezhong
 * @date    2016-05-13 13:48
 * @version $banner-slider$
 */
(function ($) { 
	"use strict";
	$.fn.bannerSlider = function ( ) {
	  var $defaultParent = $(this),
		  $defaultLi     = $(this).find("li"),
		  $defaultNum    = $(this).parent().find(".sub_num");
		  $defaultParent.defaults = {
		  // animate values
		    length  : $defaultLi.length,			    
		    showLi  : "ban_show",
		    showSpan : "sub_num_show",			
		    bantimer   : null,		   
		    duration : 4000,		
		 };
	  var showCurrent  = 0;
		function next(){
			showCurrent++;
			actualAct();
		}
		function actualAct(){
			$defaultNum.removeClass($defaultParent.defaults.showSpan);
			$defaultLi.stop().animate({opacity:0},"slow").removeClass($defaultParent.defaults.showLi);
			$defaultNum.eq(showCurrent % $defaultParent.defaults.length).addClass($defaultParent.defaults.showSpan);
			$defaultLi.eq(showCurrent % $defaultParent.defaults.length).stop().animate({opacity:1},"slow").addClass($defaultParent.defaults.showLi);
		}
		function interval(){
			clearInterval($defaultParent.defaults.bantimer);
			$defaultParent.defaults.bantimer=setInterval(function(){
				next();
			}, $defaultParent.defaults.duration);
		}
		$defaultNum.on('mouseenter',function(){
			clearInterval($defaultParent.defaults.bantimer);
			showCurrent=$(this).index();
			actualAct();
		});
		$(this).on('mouseleave',function(){
			interval()
		});
		$(this).on("mouseenter",function(){
			clearInterval($defaultParent.defaults.bantimer);
		});
		actualAct();
		interval()
	};
})(jQuery);