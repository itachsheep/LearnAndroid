package com.tao.mutithreadlearndemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by SDT14324 on 2017/12/26.
 */

public class AsynTaskBugActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyntask_bug);

    }

    public void start(View view){
        for(int i = 0; i < 500; i++){
            new MyTask().execute();
        }
    }
    class MyTask extends AsyncTask<Void,Integer,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                LogUtil.i(TAG,Thread.currentThread().getName() +" -> ");
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
