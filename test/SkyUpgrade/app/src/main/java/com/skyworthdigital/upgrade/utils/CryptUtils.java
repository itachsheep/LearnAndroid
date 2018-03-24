package com.skyworthdigital.upgrade.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class CryptUtils {

    private static final String HMAC_SHA1 = "HmacSHA1";
    // 私钥
    public static final String PRIVATE_KEY = "skyworthdigital_ota_467fg89er34";
    public static String hmacSHA1Encrypt(String data, String key) {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac;
        StringBuilder sb = new StringBuilder();
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            for (byte b : rawHmac) {
                sb.append(byteToHexString(b));
            }
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String byteToHexString(byte ib) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0f];
        ob[1] = digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
}
