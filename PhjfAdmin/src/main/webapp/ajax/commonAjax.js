var CommonPubAjax = new Object();

CommonPubAjax.queryCitiesSelectData = function (province_id){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/queryCitiesSelectData",
        type:"post",
        async: false,
        dataType: "json",
        data:{"province_id":province_id},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};

CommonPubAjax.queryAreasSelectData = function (city_id){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/queryAreasSelectData",
        type:"post",
        async: false,
        dataType: "json",
        data:{"city_id":city_id},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};

CommonPubAjax.getManagepoint = function (mp_code){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/getManagepoint",
        type:"post",
        async: false,
        dataType: "json",
        data:{"mp_code":mp_code},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};

CommonPubAjax.getBank = function (bank_code){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/getBank",
        type:"post",
        async: false,
        dataType: "json",
        data:{"bank_code":bank_code},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};


CommonPubAjax.getBankType = function (bt_code){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/getBankType",
        type:"post",
        async: false,
        dataType: "json",
        data:{"bt_code":bt_code},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};

CommonPubAjax.getNewsPlate = function (plate_code){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/getNewsPlate",
        type:"post",
        async: false,
        dataType: "json",
        data:{"plate_code":plate_code},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};
CommonPubAjax.getApp = function (app_id){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/getApp",
        type:"post",
        async: false,
        dataType: "json",
        data:{"app_id":app_id},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
                result = ajaxResult.data;
            }
        }
    });
    return result;
};
CommonPubAjax.getAboutUsNav = function (nav_code){
    var result;
    $.ajax({
        url:includeContextPath+"/phjfht/common/ajax/getAboutUsNav",
        type:"post",
        async: false,
        dataType: "json",
        data:{"nav_code":nav_code},
        success:function(ajaxResult){
            if("4" == ajaxResult.code){
                var redirect = includeContextPath+ajaxResult.redirect;
                parent.window.location.href = redirect;
            } else if("1" == ajaxResult.code){
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