package com.tao.rxjavalearn.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.tao.rxjavalearn.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SDT14324 on 2018/5/25.
 */

public class SchedulerActivity extends AppCompatActivity {
    private String TAG = SchedulerActivity.class.getSimpleName();
    private String imageurl = "https://mobile.umeng.com/images/pic/home/social/img-1.png";
    private ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        iv = (ImageView) findViewById(R.id.sche_iv);
    }

    public void test_scheduer(View view){
        Observer<Bitmap> observer = new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {
                L.i(TAG,"test_scheduer onSubscribe");
            }

            @Override
            public void onNext(Bitmap bitmap) {
                L.i(TAG,"test_scheduer onNext bitmap == null ? "+(bitmap == null));

                if(bitmap == null )return ;
                iv.setImageBitmap(bitmap);
            }

            @Override
            public void onError(Throwable e) {
                L.i(TAG,"test_scheduer onError");
            }

            @Override
            public void onComplete() {
                L.i(TAG,"test_scheduer onComplete");
            }
        };
        Observable<Bitmap> observable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                Bitmap bitmap = getBitmap(imageurl);
                emitter.onNext(bitmap);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())//subscribe()执行的线程
                .observeOn(AndroidSchedulers.mainThread());//指定 observer 的回调发生在主线程

        observable.subscribe(observer);
    }

    private Bitmap getBitmap(String url){
        L.i(TAG,"getBitmap");
        byte[] data = getNetData(url);
        return binary2Bitmap(data);
    }

    private byte[] getNetData(String url){
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
                    L.i(TAG,"bos size = "+bos.size());
                }
                L.i(TAG,"write finish, bos total size = "+bos.size());
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
    public Bitmap binary2Bitmap(byte[] data) {
        if (data != null) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        return null;
    }
}
