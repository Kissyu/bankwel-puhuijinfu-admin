/**
 * Created by Administrator on 2017/12/13.
 */
$(function() {
    PUCtrl.init();
});

var PUCtrl = function() {
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
            $(".plicyPlateList li").on("click",function () {
                for(var i=0;i<$(".plicyPlateList li").length;i++){
                    $(".plicyPlateList li").eq(i).removeClass("curPlate");
                }
                $(this).addClass("curPlate");
                for(var i=0;i<$(".plicyOutlineBox").length;i++) {
                    $(".plicyOutlineBox").eq(i).removeClass("plicyOutlineShow");
                }
                $(".plicyOutlineBox").eq($(this).index()).addClass("plicyOutlineShow");
            });
        }
    }
}();