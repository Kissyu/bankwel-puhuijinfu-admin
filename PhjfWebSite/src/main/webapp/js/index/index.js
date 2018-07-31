$(function() {
    IndexCtrl.init();
});

var IndexCtrl = function() {
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
            // banner
            $("#js_banbox").bannerSlider();
            $('.ban_num').css("marginLeft", -$(".ban_num").width() / 2);
        }
    }
}();