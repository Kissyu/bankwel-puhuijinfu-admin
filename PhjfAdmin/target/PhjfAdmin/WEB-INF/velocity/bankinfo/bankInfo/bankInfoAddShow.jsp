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
    <script src="https://webapi.amap.com/maps?v=1.4.7&key=c3ea708c2d00e1a63283b111bf120b8d"></script>
    <script src="//webapi.amap.com/ui/1.0/main.js"></script>
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
        })
    </script>
</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>银行信息管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>银行机构管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新增</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12" action="/phjfht/api/v1/bankinfo/addBankInfo" method="POST">
            <!-- 提示信息标签 -->
            <webTag:ReturnMsgTag id="msg" name="msg" successflag='${code}' msg='${msg}' />
            <webTag:HiddenInputTag id="seq_uuid" name="seq_uuid" value="${data.seq_uuid}"/>
            <fieldset>
                <div class="row">
                    <webTag:Text id="language_show" name="language_show" displayLable="语言:" value='${data.language_show}' readonly="true" maxlength="32"/>
                    <webTag:Text id="bt_code" name="bt_code" displayLable="银行类型编号" value='${data.bt_code}' isRequired="true" maxlength="32" readonly="true"/>
                    <webTag:Text id="bt_name" name="bt_name" displayLable="银行类型名称" value='${data.bt_name}' readonly="true" maxlength="32"/>
                </div>
                <div class="row">
                    <webTag:Text id="name" name="name" displayLable="银行机构名称" value='${data.name}' isRequired="true" readonly="true"/>
                    <webTag:Text id="parent_bank_name" name="parent_bank_name" displayLable="上级银行机构" value='${data.parent_bank_name}' readonly="true"/>
                    <webTag:Text id="contact" name="contact" displayLable="联系人" value='${data.contact}' isRequired="true" readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="mobilephone" name="mobilephone" displayLable="移动电话" value='${data.mobilephone}' isMobile="true" maxlength="11" readonly="true"/>
                    <webTag:Text id="telephone" name="telephone" displayLable="固定电话" value='${data.telephone}' isPhone="true" readonly="true" />
                    <webTag:Text id="email" name="email" displayLable="邮箱" value='${data.email}' isEmail="true" readonly="true" />
                </div>
                <div class="row">
                    <webTag:Text id="third_bank_code" name="third_bank_code" displayLable="银行网点第三方编码" value='${data.third_bank_code}' readonly="true" />
                    <webTag:Text id="lat" name="lat" displayLable="纬度" value='${data.lat}' readonly="true" />
                    <webTag:Text id="lng" name="lng" displayLable="经度" value='${data.lng}' readonly="true" />
                </div>
                <div class="row">
                    <label class='control-label'>地图:</label>
                    <div id="map_container" style="width: 85%;height: 400px;border: 1px solid #0075C2;margin-left: 160px;margin-bottom: 20px;">
                    </div>
                </div>
                <div class="row">
                    <webTag:TextareaTag id="address" name="address" displayLable="地址" rows="5" value='${data.address}' maxlength="255" isRequired="true" readonly="true"/>
                    <webTag:TextareaTag id="remark" name="remark" displayLable="备注" rows="5" value='${data.remark}' maxlength="255" readonly="true"/>
                    <webTag:DataDictionarySelectTag sys_id="Phjf" id="is_confirm" name="is_confirm" displayLable="是否确认" parentCode="sys_isConfirm" value='${data.is_confirm}'  />
                </div>
            </fieldset>
            <div class="row" align="right">
                <c:if test="${data.language == 'ZH_SIMP'}">
                    <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goAddSimpBankInfoPage"><i class="icon-inbox icon-white"></i>录入下一个</a>
                </c:if>
                <a class="btn btn-danger" href="/phjfht/api/v1/bankinfo/goBankInfoPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
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
