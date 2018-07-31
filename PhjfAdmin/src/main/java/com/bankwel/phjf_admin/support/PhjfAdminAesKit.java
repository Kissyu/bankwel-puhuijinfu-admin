package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.jfinal.kit.PropKit;

/**
 * Created by admin on 2017/10/17.
 */
public class PhjfAdminAesKit {

    private String key;
    private String vector;

    public static final PhjfAdminAesKit aes = new PhjfAdminAesKit();

    private PhjfAdminAesKit(){
        this.setKey(PropKit.use("config.properties").get("phjf.aes.key"));
        this.setVector(PropKit.use("config.properties").get("phjf.aes.vector"));
    }

    public String encode(String str){
        return F.encryptionKit.aesEncode(this.key, this.vector, str);
    }

    public String decode(String str){
        return F.encryptionKit.aesDecode(this.key, this.vector, str);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    public static void main(String[] args) throws Exception{
        String str1 = PhjfAdminAesKit.aes.encode("123");
        String str2 = PhjfAdminAesKit.aes.encode("123");
        System.out.println(str1);//VXYrTTYvbEk1cDIwMmN1aGh1cXVhZz09
        System.out.println(str2);//VXYrTTYvbEk1cDIwMmN1aGh1cXVhZz09

        System.out.println(PhjfAdminAesKit.aes.decode(str1));//b01QMWYzUFpkYWpEY3hmUkxsbTBGQT09
        System.out.println(PhjfAdminAesKit.aes.decode(str2));//b01QMWYzUFpkYWpEY3hmUkxsbTBGQT09
    }
}
