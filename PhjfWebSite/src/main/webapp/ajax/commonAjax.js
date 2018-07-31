var CommonPubAjax = new Object();

CommonPubAjax.getBank = function (bt_code, lat, lng){
    var result;
    $.ajax({
        url:"/phjf/common/ajax/getBank",
        type:"post",
        async: false,
        dataType: "json",
        data:{"bt_code":bt_code, lat:lat, lng:lng},
        success:function(ajaxResult){
            if("1" == ajaxResult.code){
                result = ajaxResult.data.bankList;
            }
        }
    });
    return result;
};

CommonPubAjax.queryBank = function (bt_code,type, lat, lng){
    var result;
    $.ajax({
        url:"/phjf/bank/ajax/queryBank",
        type:"post",
        async: false,
        dataType: "json",
        data:{"bt_code":bt_code,"type":type, lat:lat, lng:lng},
        success:function(ajaxResult){
            if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};

CommonPubAjax.getGeoHash = function (lat, lng,accuracy){
    var result;
    $.ajax({
        url:"/phjf/common/ajax/getGeoHash",
        type:"post",
        async: false,
        dataType: "json",
        data:{ lat:lat, lng:lng, accuracy:accuracy},
        success:function(ajaxResult){
            if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};

CommonPubAjax.isEmpty = function (model){
    return isUndefined(model) || isUndefined(model.seq_uuid);
};


function isUndefined(paraValue){
    if(paraValue==null||paraValue==undefined||paraValue=="") return true;
    return false;
}