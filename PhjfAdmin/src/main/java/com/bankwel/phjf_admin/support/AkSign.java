package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.util.Sign;
import com.jfinal.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ldq on 2016/3/21.
 */
public class AkSign implements Sign {
    private static final Logger log = LoggerFactory.getLogger(AkSign.class);
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private String appId;
    private String key;

    public static final AkSign sign = new AkSign();

    private AkSign(){
        this.setAppId(PropKit.use("config.properties").get("phjfAdmin.AppId"));
        this.setKey(PropKit.use("config.properties").get("phjfAdmin.ak.Sign_AES_KEY"));
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 生成数据Sign签名
     * @param timestamp
     * @return
     */
    public String createSign(String timestamp){
        return md5(this.getAppId()+this.getKey()+timestamp);
    }

    /**
     * 生成数据Sign签名
     * @param timestamp
     * @return
     */
    public String createSign(String appId,String timestamp){
        return md5(appId+this.getKey()+timestamp);
    }

    /**
     * 校验数据Sign签名是否正确
     * @param timestamp
     * @param ak
     * @return
     */
    public boolean checkSign(String timestamp, String ak){
        if(F.validate.isEmpty(ak)
//                ||ValidateHelper.isNull(appId)
                ||F.validate.isEmpty(timestamp)){
            return false;
        }
        String tempSign = createSign(timestamp);
        if(!tempSign.trim().equals(ak.trim())){
            return false;
        }
        return true;
    }


    /**
     * 计算content的md5摘要.
     *
     * @param content
     * @return md5结果
     */
    public static String md5(String content) {
        byte[] data = getMD5Digest().digest(content.getBytes());
        char[] chars = encodeHex(data);
        return new String(chars);
    }

    private static MessageDigest getMD5Digest() {
        try {
            MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
            md5MessageDigest.reset();
            return md5MessageDigest;
        } catch (NoSuchAlgorithmException nsaex) {
            throw new RuntimeException("Could not access MD5 algorithm, fatal error");
        }
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexidecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data a byte[] to convert to Hex characters
     * @return A char[] containing hexidecimal characters
     */
    private static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
