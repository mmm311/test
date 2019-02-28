package com.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class BlowFish {
    public static Key keyGenerator() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(Key key, String text) throws Exception{
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text.getBytes());
    }

    public static byte[] decrypt(Key key, byte[] bt) throws Exception{
        Cipher cipher =Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(bt);
    }


}
