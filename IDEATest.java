package com.encrypt;

import org.apache.commons.net.util.Base64;
import org.apache.hadoop.hdfs.protocol.ClientDatanodeProtocol;
import org.apache.spark.executor.Executor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IDEATest {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "IDEA";
    private static final String CIPHER_ALGORITHM = "IDEA/ECB/PKCS5Padding";

    /**
     * 产生密钥
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */

    public static byte[] getKey() throws IOException, NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    /**
     * 还原密钥： 二进制字节数组转为java 对象
     */
    public static Key toKey(byte[] keyByte){
        return new SecretKeySpec(keyByte, KEY_ALGORITHM);
    }

    /**
     * IDEA 加密
     * @param data
     * @param keyByte
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String data, byte[] keyByte)throws Exception {
        Key key = toKey(keyByte);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes(ENCODING));
    }

    public static String encryptIDEAHex(String data, byte[] keyByte) throws Exception{
        byte[] encodeByte = encrypt(data, keyByte);
        return Base64.encodeBase64String(encodeByte);
    }

    public static byte[] decrypt(byte[] data, byte[] keyByte) throws Exception{
        Key key = toKey(keyByte);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(String data, byte[]keyByte) throws Exception{
        Key key = toKey(keyByte);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(Base64.decodeBase64(data));
    }


    public static byte[] encrypt(byte[] data, byte[] keyByte) throws Exception{
        Key key = toKey(keyByte);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public void testIDEA(String file) throws Exception{


        RandomAccessFile inFile = new RandomAccessFile(file, "rw");
        int size = (int) inFile.length();
        byte[] data = new byte[size];
        inFile.read(data, 0, size);
        byte[] keyByte = getKey();
        long start = System.currentTimeMillis();
        decrypt(encrypt(data, keyByte), keyByte);
        long end = System.currentTimeMillis();
        System.out.println("data size: " + size / 1024 / 1024 + ", " + "时间：" + (end - start) * 1.0 / 1000);

    }
    public static void main(String[] args)  throws Exception{
        final int DATA_SIZE_MB = 1024 * 1024 * 100;
        String path = "E:\\data\\test\\part";
        for(int i = 0; i <= 10; i ++) {
            new IDEATest().testIDEA(path + "5.mkv");
        }

    }
}
