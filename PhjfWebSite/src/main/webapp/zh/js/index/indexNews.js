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
            // 公司新闻
            $(".companyNews li").on("mouseover",function () {
                for(var i=0;i<$(".companyNews li").length;i++){
                    $(".companyNews li").eq(i).removeClass("mouseOverNews");
                }
                $(this).addClass("mouseOverNews");
                for(var i=0;i<$(".newsImgList li").length;i++){
                    $(".newsImgList li").eq(i).removeClass("newsImgShow");
                }
                $(".newsImgList li").eq($(this).index()).addClass("newsImgShow");
            });
        }
    }
}();