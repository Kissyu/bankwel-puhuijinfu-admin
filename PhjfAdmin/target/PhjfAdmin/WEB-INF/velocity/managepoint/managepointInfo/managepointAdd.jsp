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
    <script src="https://webapi.amap.com/maps?v=1.4.7&key=fa04b6a7e3aa1f386f39978cb8b888c4"></script>

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

            $("#mainForm").validate();

            var geocoder;
            AMap.plugin('AMap.Geocoder',function(){
                geocoder = new AMap.Geocoder({

                });
            });
            $("#address").bind('input propertychange', function() {
                var address = $("#address").val();
                geocoder.getLocation(address,function(status,result){

                    if(status=='complete'&&result.geocodes.length){
                        if(simpleMarker != null){
                            map.remove(simpleMarker);
                        }
                        simpleMarker = new AMap.Marker({
                            position : [result.geocodes[0].location.lng,result.geocodes[0].location.lat],
                            map : map
                        });
                        $("#lat").val(result.geocodes[0].location.lat);
                        $("#lng").val(result.geocodes[0].location.lng);

                        map.setCenter(simpleMarker.getPosition())
                    }
                })
            });

            $("#lat").bind('input propertychange', function() {
                setMarker($("#lng").val(), $("#lat").val());

            });

            $("#lng").bind('input propertychange', function() {
                setMarker($("#lng").val(), $("#lat").val());
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
<body >
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>办理点管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>助农办理点管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/managepoint/addManagepoint" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <webTag:HiddenInputTag id="mp_id" name="mp_id" value="${data.mp_id}"/>
            <webTag:HiddenInputTag id="mp_code" name="mp_code" value="${data.mp_code}"/>
            <webTag:HiddenInputTag id="language" name="language" value="${data.language}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言:" value='${data.language_show}' readonly="true" maxlength="32"/>
                    <webTag:Text id="name" name="name" displayLable="名称" value='${data.name}' isRequired="true" maxlength="100" />
                    <webTag:Text id="contact" name="contact" displayLable="联系人" value='${data.contact}' maxlength="32" />
                </div>
                <div class="row">
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="移动电话" value='${data.mobilephone}' isMobile="true" maxlength="11"/>
                    <webTag:Text id="telephone" name="telephone" displayLable="固定电话" value='${data.telephone}' isPhone="true" maxlength="32" />
                    <webTag:Text id="open_hours" name="open_hours" displayLable="营业时间" value='${data.open_hours}' maxlength="100" />
                </div>
                <div class="row">
                    <webTag:ProvincesSelectTag id="province" name="province" displayLable="省" value='${data.province}' onchange="provinceChange();" isRequired="true"/>
                    <webTag:CitiesSelectTag id="city" name="city" displayLable="市" value='${data.city}' provinceId='${data.province}' onchange="cityChange();" isRequired="true"/>
                    <webTag:AreasSelectTag id="area" name="area" displayLable="区" value='${data.area}' cityId="${data.city}"/>
                </div>
                <div class="row">
                    <webTag:Text id="lat" name="lat" displayLable="纬度" value='${data.lat}' maxlength="11" />
                    <webTag:Text id="lng" name="lng" displayLable="经度" value='${data.lng}' maxlength="11" />
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_withdraw" name="is_withdraw" displayLable="是否可预约取款" parentCode="sys_isWithdraw" value='${data.is_withdraw}'  />
                </div>
                <div class="row">
                    <label class='control-label'>地理位置:</label>
                    <div id="map_container" style="width: 85%;height: 400px;border: 1px solid #0075C2;margin-left: 160px;margin-bottom: 20px;">
                    </div>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="address" name="address" displayLable="地址:" rows="5" value='${data.address}' maxlength="255" isRequired="true"/>
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_confirm" name="is_confirm" displayLable="是否确认" parentCode="sys_isConfirm" value='${data.is_confirm}'  />
                </div>
            </fieldset>
            <div class="row" align="right">
                <button id="submitBtn" type="submit" class="btn btn-danger"><i class="icon-inbox icon-white"></i>保存并查看</button>
                <a class="btn btn-danger" href="/phjfht/api/v1/managepoint/goManagepointQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
