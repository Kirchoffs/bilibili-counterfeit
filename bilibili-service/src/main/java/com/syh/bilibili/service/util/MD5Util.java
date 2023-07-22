package com.syh.bilibili.service.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

public class MD5Util {
    public static String sign(String content, String salt, String charset) {
        content = content + salt;
        return DigestUtils.md5Hex(getContentBytes(content, charset));
    }

    public static boolean verify(String content, String sign, String salt, String charset) {
        content = content + salt;
        String mySign = DigestUtils.md5Hex(getContentBytes(content, charset));
        return mySign.equals(sign);
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (!"".equals(charset)) {
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException exp) {
                throw new RuntimeException("Unsupported encoding exception for MD5.");
            }
        } else {
            return content.getBytes();
        }
    }
}
