
function provinceChange(){
	var cities = CommonPubAjax.queryCitiesSelectData($("#province").val());
	var cityOpt = $("#city option");
	if (cityOpt.length > 0){
		cityOpt.remove();
	}
	$("#city").append("<option value=''>---请选择---</option>");
	for(var i = 0; i < cities.length; i++){
		var city = cities[i];
		$("#city").append("<option value='"+city.id+"'>"+city.name+"</option>");
	}

	var areaOpt = $("#area option");
	if (areaOpt.length > 0){
		areaOpt.remove();
	}
	$("#area").append("<option value=''>---请选择---</option>");
}

function cityChange(){
	var areas = CommonPubAjax.queryAreasSelectData($("#city").val());
	var areaOpt = $("#area option");
	if (areaOpt.length > 0){
		areaOpt.remove();
	}
	$("#area").append("<option value=''>---请选择---</option>");
	for(var i = 0; i < areas.length; i++){
		var area = areas[i];
		$("#area").append("<option value='"+area.id+"'>"+area.name+"</option>");
	}

}