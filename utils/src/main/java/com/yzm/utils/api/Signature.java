package com.yzm.utils.api;

import lombok.Data;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Random;

/**
 * 视频上传腾讯云，获取签名
 */
@Data
public class Signature {

    private String secretId;
    private String secretKey;
    private long currentTime;
    private int random;
    private int signValidDuration;
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";

    private static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

    public String getUploadSignature() throws Exception {
        String strSign = "";
        String contextStr = "";

        long endTime = currentTime + signValidDuration;
        contextStr = contextStr + "secretId=" + URLEncoder.encode(secretId, "utf8");
        contextStr = contextStr + "&currentTimeStamp=" + currentTime;
        contextStr = contextStr + "&expireTime=" + endTime;
        contextStr = contextStr + "&random=" + random;
        // 鉴黄
        contextStr = contextStr + "&procedure=QCVB_SimpleProcessFile({1,1,1,1})";

        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
            mac.init(secretKey);

            byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
            byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
            strSign = Base64.getEncoder().encodeToString(sigBuf);
            strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            throw e;
        }
        return strSign;
    }

    public static void main(String[] args) {
        //得到Sign
        Signature sign = new Signature();
        //个人API密钥中的Secret Id
        sign.setSecretId("AKIDZw4wZwpFBsNcb5rM8fxzlHdjZ9TZ25Wn");
        //个人API密钥中的Secret Key
        sign.setSecretKey("A4j8HAoCPN6brsc7MNDn8D2f3B8qJYXK");
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24);

        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
        }
    }

}
