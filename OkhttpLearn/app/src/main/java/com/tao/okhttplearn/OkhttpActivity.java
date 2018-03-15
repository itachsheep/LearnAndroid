package com.tao.okhttplearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = OkhttpActivity.class.getSimpleName();
    OkHttpClient client;
    Cache cache;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_send_req).setOnClickListener(this);

        File cacheFile = new File(getCacheDir().toString(),"cache");
        Log.i(TAG,"cacheFile "+cacheFile.toString());
        int cacheSize = 10 * 1024 * 1024;
        cache = new Cache(cacheFile,cacheSize);

        client = new OkHttpClient.Builder()
                //.cache(cache)
                .build();
        request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt").build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_send_req:
            {
                sendRequest();
            }
                break;
            default:
                break;
        }
    }

    private void sendRequest()  {


        new Thread(){
            @Override
            public void run() {
                try {

                    Response response1 = client.newCall(request).execute();
                    Log.i(TAG," response1 = "+response1);
                    Log.i(TAG," cache response1 = "+response1.cacheResponse());
                    Log.i(TAG," network response1 = "+response1.networkResponse());
                    response1.body().close();

                    Response response2 = client.newCall(request).execute();
                    Log.i(TAG," response2 = "+response2);
                    Log.i(TAG," cache response2 = "+response2.cacheResponse());
                    Log.i(TAG," network response2 = "+response2.networkResponse());
                    response2.body().close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}



