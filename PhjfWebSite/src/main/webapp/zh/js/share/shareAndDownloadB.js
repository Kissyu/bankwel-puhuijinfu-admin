/**
 * Created by bankwel on 2018/3/21.
 */
$(function() {
    var mySwiper = new Swiper ('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        slidesPerView: 2,
        spaceBetween: 0,
    });
    $.ajax({
        url: '/phjf/api/v1/page/share',
        type:'post',
        async: false,
        dataType:'json',
        data:{
            app_code:"phjf_merchclient",
            language:"ZH_SIMP"
        },
        success:function(result){
            if(result.code == 1) {
            }else {
            }
        }
    });
    $('.downAndroid').on('click',function() {
        if(isAndroid_ios()) {
            var androidUrl = "http://file.bankwel.com/BankwelImg/fileLoader/test/phjf/app/merchclient/com.puhuijinfu.merchclient.test.apk";
            downLoadCountAjax(androidUrl);
        } else {
            return false;
        }
    });
    $('.downIos').on('click',function() {
        if(!isAndroid_ios()) {
            var iosUrl = "https://itunes.apple.com/cn/app/id1098082021?mt=8";
            downLoadCountAjax(iosUrl);
        }else {
            return false;
        }
    });

    //判断是否是安卓还是ios
    function isAndroid_ios(){
        var u = navigator.userAgent, app = navigator.appVersion;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        return isAndroid==true?true:false;
    }
    function downLoadCountAjax(jumpUrl) {
        $.ajax({
         url: '/phjf/api/v1/page/setDownloadAppCount',
         type:'post',
         async: false,
         dataType:'json',
         data:{
             app_code:"phjf_merchclient",
             language:"ZH_SIMP"
         },
         success:function(result){
         if(result.code == 1) {
             window.location.href=jumpUrl;
         }else {
         }
         }
         });
    }
});