/**
 * Created by Administrator on 2016/11/24.
 */
$(function() {
    Com.init();
});

var Com = function() {
    var me = null;

    return {
        init: function() {
            me = this;
            this.renderUI();
            this.bindUI();
        },
        renderUI: function() {
        },
        bindUI: function() {
            // 头部
            // $(".nav li").on("click",function () {
            //     $(".nav li").removeClass("curPage");
            //     $(this).addClass("curPage");
            // });
            // 选择语言
            // $(".selectLan").on("click",function (e) {
            //     if($(".curLan").hasClass("showselect")){
            //         $(".curLan").removeClass("showselect");
            //         $(".showLan").hide();
            //     }else{
            //         $(".curLan").addClass("showselect");
            //         $(".showLan").show();
            //     }
            //     e.stopPropagation();
            // });
            $(document).on("click",function () {
                $(".showLan").hide();
                $(".curLan").removeClass("showselect");
            });
            $(".lanList").on("click",function () {
                var lan = $(this).html();
                $(".curLan").html(lan+"<span></span>");
                $(".lanList").removeClass("forChoLan");
                $(this).siblings(".lanList").addClass("forChoLan");
            });
            // window.onresize=function(){
            //     changeFrameHeight();
            // }
            $('#qrCodeFrame').on('mouseenter',function () {
                $("#qrCodeFrame").addClass("hoverQrCodeFrame");
            });
            $('#qrCodeFrame').on('mouseleave',function () {
                $("#qrCodeFrame").removeClass("hoverQrCodeFrame");
            });
            $('#navFrame').on('mouseenter',function () {
                $('#navFrame').css("height","200");
            });
            $('#navFrame').on('mouseleave',function () {
                $('#navFrame').css("height","80");
            });
        }
    }
}();
// function changeFrameHeight(){
//     var ifm= document.getElementById("navFrame");
//     ifm.height=document.documentElement.clientHeight;
// }