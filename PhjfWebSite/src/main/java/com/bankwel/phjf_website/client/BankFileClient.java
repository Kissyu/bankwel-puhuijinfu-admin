package com.bankwel.phjf_website.client;

import com.alibaba.fastjson.JSONObject;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.Base64ImgKit;
import com.jfinal.kit.JsonKit;

/**
 * Created by scottsln on 2016/10/17.
 */
public class BankFileClient {

    public static String uploadImg(String extension,String busPath,String base64) {
        String result = "";
        try{
            String requestJson = JsonKit.toJson(F.transKit.asMap("extension",extension,"busPath",busPath,"base64",base64));
            String json = HttpInterConstants.Img_Upload_INF.postJson(requestJson);
            if(F.validate.isNotEmpty(json)){
                JSONObject rMap = JSONObject.parseObject(json);
                if(F.validate.isNotEmpty(rMap)){
                    JSONObject data = rMap.getJSONObject("data");
                    if(F.validate.isNotEmpty(data) && F.validate.isNotEmpty(data.get("url"))){
                        result = data.getString("url");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        String extension = "jpeg";
        String patch = "lxent";
        String base64 = Base64ImgKit.getFromFile("D:\\mnt\\files\\upload\\lx\\apic22940.jpg");
//        uploadImg();
        System.out.println(base64);
        System.out.println(uploadImg(extension,patch,base64));
    }
}
