package com.tao.okhttplearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity {

    private String TAG = "OkHttpClient";
//    OkHttpClient client;
//    Cache cache;
//    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*File cacheFile = new File(getCacheDir().toString(),"cache");
        LogUtil.i(TAG,"cacheFile "+cacheFile.toString());
        int cacheSize = 10 * 1024 * 1024;
        cache = new Cache(cacheFile,cacheSize);

        client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return null;
                    }
                })
                .build();
        request = new Request.Builder()
                .url("http://192.168.5.51:8080/123.txt").build();*/

    }

    public void useCacheControlTest(View view){
        CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(1000 * 5,TimeUnit.MILLISECONDS);//100ms
        CacheControl cacheContorl = builder.build();
        final Request request = new Request.Builder().cacheControl(cacheContorl).url("http://192.168.5.51:8080/123.txt")
                .build();
        File cacheFile = new File(getCacheDir().toString(),"cache");
        LogUtil.i(TAG,"cacheFile "+cacheFile.toString());
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile,cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,"useCacheControlTest onFailure "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Response netorkRes = response.networkResponse();
                Response cacheRes = response.cacheResponse();
                Log.i(TAG,"useCacheControlTest response = "+response.body().string());
                Log.i(TAG,"useCacheControlTest netorkRes = "+ netorkRes);
                Log.i(TAG,"useCacheControlTest cacheRes = "+ cacheRes);
            }
        });
    }

    /**
     * 强制缓存
     */
    public void forceCache(){

        CacheControl force_cache = new CacheControl.Builder().onlyIfCached().
                maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
//        request = new Request.Builder().url("").cacheControl(force_cache).build();
    }

    /**
     * 强制不使用缓存
     */
    public void forceNetwork(){
        CacheControl force_network = new CacheControl.Builder().noCache().build();
//        request = new Request.Builder().url("").cacheControl(force_network).build();
    }

    /**
     * 异步请求
     * @param view
     */
    public void asynRequest(View view){
          /*Response response1 = client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i(TAG,"asynRequest onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.i(TAG,"asynRequest onResponse");
            }
          });*/

    }

    /**
     * 同步请求
     * @param view
     */
    public void syncRequest(View view){
        /*new Thread(){
            @Override
            public void run() {
                try {

                    LogUtil.i(TAG,"send request for onclick!!!!");
                    Call call = client.newCall(request);
                    Response response = call.execute();
                    LogUtil.i(TAG,"call = "+call+", response ="+response);
                    //Log.i(TAG,"call = "+call+", response ="+response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

    }

}



