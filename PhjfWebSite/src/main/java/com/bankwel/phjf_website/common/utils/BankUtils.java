package com.bankwel.phjf_website.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bankwel.framework.core.F;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by ldq on 2016/6/24.
 */
public class BankUtils {

    public static String apiBaiduUrl = "http://apis.baidu.com/datatiny/cardinfo/cardinfo";
    public static String apiKey = "3a2babadf99baf884869f13514dbf412";
    /**
     * 按银行卡号查询银行名称
     * @param cardnum
     * @return
     */
    public static Map queryBankName(String cardnum) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = apiBaiduUrl + "?cardnum=" + cardnum;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  apiKey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return result;
        JSONObject json = JSON.parseObject(result);
        if ("1".equals(json.getString("status"))){
            JSONObject data = JSON.parseObject(json.get("data")+"");
            return F.transKit.asMap("bank_name", data.getString("bankname"), "card_type", data.getString("cardtype"));
        } else {
            return null;
        }
    }

    public static void main(String[] args){
        //
        System.out.println(BankUtils.queryBankName("6246880152"));
    }
}
