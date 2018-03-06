package com.tao.okhttplearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = OkhttpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_send_req).setOnClickListener(this);

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

    private void sendRequest() {
        final Request request = new Request.Builder()
                .url("http://www.baidu.com").build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,"onFailure e "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"onResponse = "+response.toString());
                Log.i(TAG,"onResponse = "+response.body().string());
//                Log.i(TAG,"onResponse = "+response);

            }
        });
    }
}



