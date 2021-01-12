package com.example.test.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * base64
 * @author wangxiaolei
 * @date 2020/4/13 20:15
 */
public class Base64Utils {

    /**
     * base64加密
     * @param str
     * @author wangxiaolei
     * @date 2020/4/13 20:15
     * @return java.lang.String
     */
    public static String encodeToString(String str) throws Exception {
        return encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeToString(byte[] bytes) throws Exception {
        byte[] encode = Base64.getEncoder().encode(bytes);
        return new String(encode, StandardCharsets.UTF_8);
    }

    /**
     * 解密
     * @param str
     * @author wangxiaolei
     * @date 2020/4/13 20:15
     * @return java.lang.String
     */
    public static String decodeToString(String str) throws Exception {
        return decodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
    public static String decodeToString(byte[] bytes) throws Exception {
        byte[] encode = Base64.getDecoder().decode(bytes);
        return new String(encode, StandardCharsets.UTF_8);
    }
}
