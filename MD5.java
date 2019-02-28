package com.encrypt;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static final String KEY_MD5 = "MD5";

    public static byte[] encryptMD5(byte[] data) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    public static void main(String[] args) throws Exception{
        String str0 = "123456";
        System.out.println("原文：" + str0);

        byte[] str1 = MD5.encryptMD5(str0.getBytes());
        BigInteger bigInteger = new BigInteger(str1);
        System.out.println("MD5加密后：" + bigInteger.toString(16));;
    }
}
