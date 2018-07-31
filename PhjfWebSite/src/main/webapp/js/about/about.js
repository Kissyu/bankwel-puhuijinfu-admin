/**
 * Created by Administrator on 2016/11/24.
 */
$(function() {
    About.init();
});

var About = function() {
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
            var localHref = window.parent.location.pathname;
            for(var i = 0; i < $(".leftNav li").length; i++){
                if($(".leftNav li").eq(i).children("a").attr("href") == localHref){
                    $(".leftNav li").eq(i).addClass("curAboutPage");
                }else {
                    $(".leftNav li").eq(i).removeClass("curAboutPage")
                }
            }
        }
    }
}();