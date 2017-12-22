package com.tao.mutithreadlearndemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by SDT14324 on 2017/12/22.
 */

public class FutureTestActivity extends Activity {
    Task callable;
    FutureTask<Integer> future;
    ExecutorService executor;
    String TAG = getClass().getSimpleName();
    class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int i = 0;
            for (; i < 20; i++) {
                try {
                   LogUtil.i(TAG,Thread.currentThread().getName() + "_" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_test);

    }

    public void startTask(View view){
        LogUtil.i(TAG,"start Task");
        callable = new Task();
        future = new FutureTask<Integer>(callable){
            @Override
            protected void done() {
                try {
                    Integer integer = get();//阻塞等待，获取异步任务返回值
                    LogUtil.i(TAG,"done : "+integer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //创建默认线程池
        executor = Executors.newCachedThreadPool();
        executor.execute(future);
    }

    public void cancelTask(View view){
        LogUtil.i(TAG,"cancel Task");
        future.cancel(true);
    }
}
