package com.yzm.utils.password;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public enum EncryptUtil {
    INSTANCE;

    // 加密算法名称
    public static final String MD5 = "MD5";
    public static final String HmacMD5 = "HmacMD5";
    public static final String SHA1 = "SHA1";
    public static final String HmacSHA1 = "HmacSHA1";

    // 编码格式；默认使用uft-8
    public String charset = "utf-8";

    /**
     * 使用MessageDigest进行单向加密（无秘钥）
     *
     * @param res       被加密的文本
     * @param algorithm 加密算法名称
     * @param charset   编码格式
     */
    private String messageDigest(String res, String algorithm, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return Base64Encode(md.digest(res.getBytes(charset)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设秘钥）
     *
     * @param res       被加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密使用的秘钥
     * @param charset   编码格式
     */
    private String keyGeneratorMac(String res, String algorithm, String key, String charset) {
        try {
            SecretKey sk;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            return Base64Encode(mac.doFinal(res.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换成二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (StringUtils.isEmpty(hexStr)) throw new IllegalArgumentException("Empty str");

        String hex = "0123456789ABCDEF";
        int len = (hexStr.length() / 2);
        byte[] result = new byte[len];
        char[] cs = hexStr.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (hex.indexOf(cs[pos]) << 4 | hex.indexOf(cs[pos + 1]));
        }
        return result;
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     */
    public String MD5(String res, String charset) {
        if (StringUtils.isEmpty(charset)) charset = this.charset;
        return messageDigest(res, MD5, charset);
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     */
    public String MD5(String res, String key, String charset) {
        if (StringUtils.isEmpty(charset)) charset = this.charset;
        return keyGeneratorMac(res, HmacMD5, key, charset);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     */
    public String SHA1(String res, String charset) {
        if (StringUtils.isEmpty(charset)) charset = this.charset;
        return messageDigest(res, SHA1, charset);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     */
    public String SHA1(String res, String key, String charset) {
        if (StringUtils.isEmpty(charset)) charset = this.charset;
        return keyGeneratorMac(res, HmacSHA1, key, charset);
    }

    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     */
    public String XOREncode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     */
    public String XORDecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * 直接使用异或（第一调用加密，第二次调用解密）
     *
     * @param res 密文
     * @param key 秘钥
     */
    public int XOR(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     */
    public String Base64Encode(byte[] res) {
        return new String(Base64.encode(res));
    }

    /**
     * 使用Base64进行解密
     *
     * @param res 密文
     */
    public String Base64Decode(byte[] res) throws Base64DecodingException {
        return new String(Base64.decode(res));
    }

    public static void main(String[] args) throws Base64DecodingException {
        String cod = "E0C20E9554DD16A8F6C13E5C";
        String md5 = EncryptUtil.INSTANCE.MD5(cod, "");
        System.out.println("md5 = " + md5);
        String key = "1f0e64a2ff34d599";
        String md5K = EncryptUtil.INSTANCE.MD5(cod, key, "");
        System.out.println("md5K = " + md5K);
        System.out.println("===========================================");

        String sha1 = EncryptUtil.INSTANCE.SHA1(cod, "");
        System.out.println("sha1 = " + sha1);
        String sha1K = EncryptUtil.INSTANCE.SHA1(cod, key, "");
        System.out.println("sha1K = " + sha1K);
        System.out.println("===========================================");

        String xOREncode = EncryptUtil.INSTANCE.XOREncode(cod, key);
        System.out.println("xOREncode = " + xOREncode);
        String xorDecode = EncryptUtil.INSTANCE.XORDecode(xOREncode, key);
        System.out.println("xorDecode = " + xorDecode);
        System.out.println("===========================================");

        int xor = EncryptUtil.INSTANCE.XOR(1323250266, key);
        System.out.println("xor = " + xor);
        int xor2 = EncryptUtil.INSTANCE.XOR(xor, key);
        System.out.println("xor2 = " + xor2);

        System.out.println("===========================================");

        String encode = EncryptUtil.INSTANCE.Base64Encode(cod.getBytes());
        System.out.println("encode = " + encode);
        String decode = EncryptUtil.INSTANCE.Base64Decode(encode.getBytes());
        System.out.println("decode = " + decode);
    }

}
