package com.gcc.util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5加密工具类
 *
 * @author 小李探花
 * @since 2022-01-18 12:28:28
 */
public final class Md5PasswordUtils {

    /**
     * 散列次数
     */
    private static final int TOTAL = 48;
    /**
     * 步长
     */
    private static final int STEP_SIZE = 3;

    private static final int DIGITS_LENGTH = 16;

    /**
     * 获得摘要
     *
     * @param text 需要加密的字符串
     * @return 字符串
     */
    public static String encryption(String text) {
        String digits = "abc123def456ghi0";
        char[] hexDigits = digits.toCharArray();
        try {
            byte[] btInput = text.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "123456";
        }
    }


    /**
     * 生成密文, 用于加密字符串
     *
     * @param password 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String dynamicEncryption(String password) {
        String salt = genSalt();
        password = encryption(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < cs.length; i += STEP_SIZE) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }


    /**
     * 生成固定盐值
     *
     * @return String
     */
    private static String genSalt() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < DIGITS_LENGTH) {
            for (int i = 0; i < DIGITS_LENGTH - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }


    /**
     * 解密, 用于校验使用encryption加密的字符串
     *
     * @param password             需要校验的字符串
     * @param dynamicEncryptionPwd 已加密的字符串
     * @return true | false
     */
    public static boolean decrypt(String password, String dynamicEncryptionPwd) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < TOTAL; i += STEP_SIZE) {
            cs1[i / 3 * 2] = dynamicEncryptionPwd.charAt(i);
            cs1[i / 3 * 2 + 1] = dynamicEncryptionPwd.charAt(i + 2);
            cs2[i / 3] = dynamicEncryptionPwd.charAt(i + 1);
        }
        String salt = new String(cs2);
        String key = encryption(password + salt);
        return key.equals(new String(cs1));
    }
}
