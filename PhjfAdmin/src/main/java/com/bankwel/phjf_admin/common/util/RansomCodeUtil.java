package com.bankwel.phjf_admin.common.util;

import java.util.Random;

/**
 * Created by dingbs on 2016/11/7.
 */
public class RansomCodeUtil {

    private static char[] charSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char[] numSequence = "0123456789".toCharArray();

    private static Random random = new Random();


    public static  String generatorRansomCode(PrefixCodeType codeType){
        // 该变量用来保存系统生成的随机字符串
        StringBuilder sRand = new StringBuilder(codeType.value);

        for (int i = 0; i < 10; i++) {
            sRand.append(getRandomChar());
        }

        for (int i = 0; i < 5; i++) {
            sRand.append(getRandomNumChar());
        }

        return sRand.toString();
    }



    public enum PrefixCodeType{
        NEWS("新闻相关", "NEWS_"), BANK("银行相关", "BANK"),PROMOTE("推广码","PROMOTE_"),MANAGEPOINT("办理点相关","POINT_"),FINANCIAL("理财产品相关","FIN_"),INSURANCE("保险相关","INSURANCE_")
        ,CMS_BANNER("官网banner","CMSBANNER"),CMS_NAV("导航","CMSNAV"),CMS_CONTENT("内容","CMSCONTENT");
        // 成员变量
        private String remark;
        private String value;
        // 构造方法
         PrefixCodeType(String remark, String value) {
            this.remark = remark;
            this.value = value;
        }

    }

    // 随机生成一个字符
    private static String getRandomChar() {
        int index = random.nextInt(charSequence.length);
        return String.valueOf(charSequence[index]);
    }


    // 随机生成一个字符
    private static String getRandomNumChar() {
        int index = random.nextInt(numSequence.length);
        return String.valueOf(numSequence[index]);
    }


    public static  void main(String[] args){
        for(int i=0;i<100;i++){
            System.out.println(generatorRansomCode(PrefixCodeType.NEWS));
        }
    }

}
