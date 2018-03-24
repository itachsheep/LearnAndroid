package com.tao.bitmaplearndemo;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by taowei on 2017/12/16.
 * 2017-12-16 12:00
 * BitmapLearnDemo
 * com.tao.bitmaplearndemo
 */

public class BitmapCompressActivity extends AppCompatActivity {

    private File sdFile;
    private String TAG = "BitmapCompressActivity";

    private File cacheFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress_bitmap);
        sdFile = Environment.getExternalStorageDirectory();
        cacheFile = getCacheDir();
        Log.i(TAG," cacheFile : "+cacheFile.getAbsolutePath());


        File externalFilesDir = getExternalFilesDir(null);
        Log.i(TAG,"externalFilesDir : "+externalFilesDir.getAbsolutePath());

        File testFile = new File(externalFilesDir,"test11.txt");
        try {
            boolean newFile = testFile.createNewFile();
            Log.i(TAG,"new File create : "+newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*File internal = getFilesDir();
        Log.i(TAG,"internal file: "+internal.getAbsolutePath());

        File obbDir = getObbDir();

        @SuppressLint("NewApi") File dataDir = getDataDir();

        @SuppressLint("NewApi") File codeCacheDir = getCodeCacheDir();

        @SuppressLint("NewApi") File[] externalMediaDirs = getExternalMediaDirs();

        Log.i(TAG,"obbDir : "+obbDir.getAbsolutePath()
                +", dataDir: "+dataDir.getAbsolutePath()+
                ", codeCacheDir:  "+codeCacheDir.getAbsolutePath()+
                ", externalMediaDirs : "+(externalFilesDir == null)
        );

        for (File file :
                externalMediaDirs) {
            Log.i(TAG,"externalMediaDirs file : "+file.getAbsolutePath());
        }
*/

    }
}
