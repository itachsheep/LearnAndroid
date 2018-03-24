package com.skyworthdigital.upgrade.util;

/*
 * 文 件 名 : CryptUtils.java 版 权 : Ltd. Copyright (c) 2015 深圳创维数字技术有限公司,All rights
 * reserved 描 述 : &lt;描述&gt; 创建人 : 韩红强 创建时间: 2015-12-8 上午8:10:15
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import android.text.TextUtils;

/**
 * 加密解密工具类
 * 
 */
public class CryptUtils {
    private static final String HMAC_SHA1 = "HmacSHA1";
    // 私钥
    public static final String PRIVATE_KEY = "skyworthdigital_ota_467fg89er34";

    /**
     * 加密字符串
     * 
     * @param str
     * @param privatekey
     * @return [参数说明]
     * @return String
     * @exception throws [违例类型] [违例说明]
     */
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

    /**
     * 将字节转换成16进制
     * 
     * @param ib
     * @return [参数说明]
     * @return String
     * @exception throws [违例类型] [违例说明]
     */
    private static String byteToHexString(byte ib) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0f];
        ob[1] = digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    /**
     * 判断请求是否合法 true:合法 false:不合法
     * @param key
     * @param param2
     * @return [参数说明]
     * @return boolean
     * @exception throws [违例类型] [违例说明]
     */
    public static boolean requestIsLegal(String key, String param2) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(param2)) {
            // 服务端生成的key
            String serverKey = CryptUtils.hmacSHA1Encrypt(param2, PRIVATE_KEY);
            if (serverKey.equals(key)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            // 参数为空直接返回false
            return false;
        }
    }

}
