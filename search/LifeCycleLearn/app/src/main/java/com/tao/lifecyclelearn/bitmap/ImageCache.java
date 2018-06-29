package com.tao.lifecyclelearn.bitmap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.Set;

public class ImageCache {
    private static ImageCache instance;
    private Context mContext;
    private MemoryCache<String,Bitmap> memoryCache;



    public static ImageCache getInstance(){
        if(instance == null){
            synchronized (ImageCache.class){
                if(instance == null){
                    instance = new ImageCache();
                }
            }
        }
        return instance;
    }

    public void init(Context context){
        mContext = context;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        memoryCache = new MemoryCache<>(activityManager.getMemoryClass() / 8 * 1024 * 1024);
    }

    public void clearMemory(){
        memoryCache.evictAll();
    }
}
