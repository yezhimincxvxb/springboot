package com.yzm.utils.password;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

/**
 * AES加密解密
 */
public class AESUtil {

    // “算法/模式/填充”
    private static final String cipherStr = "AES/CBC/NoPadding";
    private static final String ivs = "F0E1D2C3B4A5968778695A4B3C2D1E0F";

    // 加密
    public static byte[] encrypt(String strKey, String strIn) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherStr);
        SecretKeySpec keySpec = getKey(strKey);
        byte[] ivb = hexStr2Bytes(ivs);
        IvParameterSpec iv = new IvParameterSpec(ivb);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

        byte[] strInBytes = strIn.getBytes();
        int size = (strInBytes.length + 15) / 16 * 16;
        byte[] newIn = new byte[size];
        for (int i = 0; i < size; i++) {
            if (i < strInBytes.length) {
                newIn[i] = strInBytes[i];
            } else {
                newIn[i] = 0;
            }
        }

        return cipher.doFinal(newIn);
    }

    // 解码
    public static byte[] decrypt(String strKey, String strIn) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherStr);
        SecretKeySpec keySpec = getKey(strKey);
        byte[] ivb = hexStr2Bytes(ivs);
        IvParameterSpec iv = new IvParameterSpec(ivb);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

        byte[] strInBytes = parseStr(strIn);
        byte[] encrypted = cipher.doFinal(strInBytes);
        int alen = encrypted.length;
        System.out.println("encrypted len:" + alen);
        for (int i = 0; i < encrypted.length; i++) {
            if (encrypted[i] == 0) {
                alen = i;
                System.out.println("catch 0, stop copy:" + (encrypted.length - i));
                break;
            }
        }
        if (alen == encrypted.length) {
            return encrypted;
        }
        System.out.println("reqdata len:" + alen);
        return Arrays.copyOf(encrypted, alen);
    }

    // 密文
    public static String getStr(byte[] md) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int j = md.length;
        char[] str = new char[j * 2];
        int k = 0;
        for (byte byte0 : md) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    /**
     * 16进制转换为二进制
     */
    public static byte[] parseStr(String hexString) {
        if (StringUtils.isEmpty(hexString)) throw new IllegalArgumentException("Empty str");

        String hex = "0123456789ABCDEF";
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (hex.indexOf((hexChars[pos])) << 4 | hex.indexOf((hexChars[pos + 1])));
        }
        return d;
    }

    /**
     * 字符串转换成十六进制byte数组
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int len = src.length() / 2;
        byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0);
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1);
        return (byte) (b0 | b1);
    }

    /**
     * 生成秘钥
     */
    private static SecretKeySpec getKey(String strKey) {
        if (StringUtils.isEmpty(strKey)) throw new IllegalArgumentException("Empty key");

        byte[] arrBTmp;
        if (strKey.length() > 16) {
            arrBTmp = strKey.substring(0, 15).getBytes();
        } else {
            arrBTmp = strKey.getBytes();
        }
        return new SecretKeySpec(arrBTmp, "AES");
    }

    public static void main(String[] args) throws Exception {
        String code = "E0C20E9554DD16A8F6C13E5C";
        String key = "1f0e64a2ff34d599";
        byte[] codE;
        byte[] codD;

        // 加密
        codE = AESUtil.encrypt(key, code);
        // 密文
        String codS = getStr(codE);
        // 解密
        codD = AESUtil.decrypt(key, codS);

        System.out.println("原文：" + code);
        System.out.println("密钥：" + key);
        System.out.println("密文：" + codS);
        System.out.println("解密：" + new String(codD));
    }

}
