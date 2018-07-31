/**
 * Created by Administrator on 2016/11/24.
 */
$(function() {
    header.init();
});

var header = function() {
    var me = null;

    return {
        init: function() {
            me = this;
            this.renderUI();
            this.bindUI();
        },
        renderUI: function() {
            var localHref = window.parent.location.pathname;
            $('.nav li').each(function () {
                $('.navTopLi').removeClass("curPage");
                $('.navSubLi').removeClass("currentPage");
                console.log($(this).children().eq(0));
                if($(this).children().eq(0).attr("href")==localHref &&$(this).hasClass("navTopLi")) {
                    $(this).addClass("curPage");
                    return false;
                }else if($(this).children().eq(0).attr("href")==localHref &&$(this).hasClass("navSubLi")) {
                    $(this).addClass("currentPage");
                    return false;
                }else if(localHref=='/') {
                    $('.nav li').eq(0).addClass("curPage");
                    return false;
                }else {
                    return true;
                }
            });
        },
        bindUI: function() {
            $('.usAb').on('mouseenter',function () {
                $('.subli').show();
                $('.headerWrap').hide();
            });
            $('.subli').on('mouseleave',function () {
                $('.subli').hide();
                $('.headerWrap').hide();
            });
            $('.otherUs').click(function () {
                $('.subli').hide();
            });
            $('.otherUs').on('mouseenter',function () {
                $('.subli').hide();
                $('.headerWrap').hide();
            });
            $('.cDownload').on('mouseenter',function () {
                $('.headerWrap').show();
                $('.subli').hide();
            });
            $('.selectLan').click(function () {
                $('.subli').hide();
                $('.headerWrap').hide();
            });
            $('.headerWrap').on('mouseleave',function () {
                $('.headerWrap').hide();
                $('.subli').hide();
            });
        }
    }
}();