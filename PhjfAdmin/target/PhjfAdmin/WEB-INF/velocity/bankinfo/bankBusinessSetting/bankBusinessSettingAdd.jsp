<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/compent/jstl/webTag-Bootstrap.tld" prefix="webTag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>系统</title>
    <jsp:include page="/WEB-INF/jsp/pub/basePhjfInclude.jsp" flush="true"/>
    <script src="http://webapi.amap.com/maps?v=1.3&key=fa04b6a7e3aa1f386f39978cb8b888c4"></script>

    <script>
        $(function(){
            var temp_lat = $("#lat").val();
            var temp_lng = $("#lng").val();
            var simpleMarker;
            var simpleMarkerPoint;
            var centerMarker = [87.629922,43.806247];
            if(temp_lat && temp_lng){
                simpleMarkerPoint = centerMarker = [temp_lng,temp_lat];
            }
            var map = new AMap.Map('map_container', {
                resizeEnable: true,
                zoom:11,
                center: centerMarker
            });

            AMap.plugin(['AMap.ToolBar','AMap.Scale','AMap.OverView'],
                    function(){
                        map.addControl(new AMap.ToolBar());
                        map.addControl(new AMap.Scale());
                        map.addControl(new AMap.OverView({isOpen:true}));
                    });

            if(simpleMarkerPoint){
                simpleMarker = new AMap.Marker({
                    position : simpleMarkerPoint,
                    map : map
                });
            }

            var _onClick = function(e){
                if(simpleMarker != null){
                    map.remove(simpleMarker);
                }
                simpleMarker = new AMap.Marker({
                    position : e.lnglat,
                    map : map
                });
                $("#lat").val(e.lnglat.lat);
                $("#lng").val(e.lnglat.lng);
            }

            var clickListener = AMap.event.addListener(map, "click", _onClick); //绑定事件，返回监听对象
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };


            $("#mainForm").validate({
                rules: {
                    bt_code: {
                        getBankType: []
                    }
                },
                onkeyup: false
            });

            //银行信息自动带出
            jQuery.validator.addMethod("getBankType",function(value,element) {
                var flag = true;
                if (bt_code == value) {
                    return flag;
                }
                var bankType = CommonPubAjax.getBankType(value);
                $("#bt_name").val(CommonPubAjax.isEmpty(bankType) ? "" : bankType.name);
                bt_code = value;
                flag = CommonPubAjax.isEmpty(bankType) ? false : true;
                return flag;
            },"银行类型代码不存在，请重新录入。");

            var geocoder;
            AMap.plugin('AMap.Geocoder',function(){
                geocoder = new AMap.Geocoder({

                });
            });
            function setMarker(lng, lat){
                if(simpleMarker != null){
                    map.remove(simpleMarker);
                }
                simpleMarkerPoint = [lng,lat];
                simpleMarker = new AMap.Marker({
                    position : simpleMarkerPoint,
                    zoom:11,
                    map : map
                });

                map.setCenter(simpleMarker.getPosition())
            }
        })
    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>银行业务管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行业务管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/bankinfo/addBankBusinessSetting" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="bt_code" name="bt_code" displayLable="银行类型代码" value='${data.bt_code}' isRequired="true" maxlength="32"/>
                    <webTag:Text id="bt_name" name="bt_name" displayLable="银行类型名称" value='${data.bt_name}' readonly="true" maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="business_code" name="business_code" displayLable="银行业务" parentCode="bank_function" value='${data.business_code}'  isRequired="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_show" name="is_show" displayLable="显示状态" parentCode="sys_isShow" value='${data.is_show}'  isRequired="true" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="status" name="status" displayLable="状态" parentCode="sys_status" value='${data.status}'  isRequired="true" />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goBankBusinessSettingQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
            </div>
        </form>
    </div>
    <!-- 数据区 end -->
</div>
<!-- 		底部高度填充块 -->
<div class="zeoBottom"></div>
<!-- 		底部高度填充块 结束-->
</body>
</html>
