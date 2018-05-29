package com.tao.rxjavalearn.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SDT14324 on 2018/5/23.
 */

public class L {
    private static String TAG = "Rxj";
    public static void i(String tag, String mes){
        Log.i(TAG,tag+"."+mes);
    }

    public static Bitmap getBitmap(String url){
        L.i(TAG,"getBitmap");
        byte[] data = getNetData(url);
        return binary2Bitmap(data);
    }

    public  static byte[] getNetData(String url){
        L.i(TAG,"getNetData");
        if (TextUtils.isEmpty(url)) {
            L.i(TAG,"getNetData return");
            return null;
        }
        ByteArrayOutputStream bos = null;
        InputStream in = null;

        try {
            bos = new ByteArrayOutputStream();
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setInstanceFollowRedirects(true);
            if (conn.getResponseCode() == 301){
                L.i(TAG, "http 301 重定向！！" + url);
                String location= conn.getURL().toString();
                return  getNetData(location);
            }else {
                in = conn.getInputStream();
                L.i(TAG, "getting image from url" + url);
                byte[] buf = new byte[4 * 1024]; // 4K buffer
                int bytesRead;
                while ((bytesRead = in.read(buf)) != -1) {
                    bos.write(buf, 0, bytesRead);
                    //L.i(TAG,"bos size = "+bos.size());
                }
                //L.i(TAG,"write finish, bos total size = "+bos.size());
                return bos.toByteArray();
            }

        } catch (Exception e) {
            L.i(TAG,"exception e ");
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                } finally {
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        }
    }
    public static Bitmap binary2Bitmap(byte[] data) {
        if (data != null) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        return null;
    }
}
