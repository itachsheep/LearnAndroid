package com.tao.mutithreadlearndemo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncTaskActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private ProgressBar pbWait;
    private ProgressBar pbShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbWait = findViewById(R.id.pb_wait);
        pbShow = findViewById(R.id.pb_show);
//        asyncTask.execute()

//        PhantomReference<String> phantomReference = new PhantomReference<>()
    }

    @SuppressLint("StaticFieldLeak")
    AsyncTask<String,Integer,User> asyncTask = new AsyncTask<String, Integer, User>() {

        @Override
        protected void onPreExecute() {
            //在doInBackground前执行，ui线程
            LogUtil.i(TAG,"onPreExecute  ");
            pbWait.setVisibility(View.VISIBLE);
            pbShow.setProgress(0);
            pbShow.setMax(10);
        }

        @Override
        protected User doInBackground(String... strings) {
            //在后台线程中执行，参数是execute()方法传递过来的
            LogUtil.i(TAG,"doInBackground  strings: "+strings[0]);
            for(int progress = 0; progress < 10 ; progress++){

                try {
                    Thread.sleep(1000);
                    publishProgress(progress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            User user = new User("taowei",25);
            return user;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //在ui线程中，更新进度。
            LogUtil.i(TAG,"onProgressUpdate values : "+values[0]);
            pbWait.setVisibility(View.INVISIBLE);
            pbShow.setVisibility(View.VISIBLE);
            pbShow.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(User result) {
            //在doInBackground后执行，ui线程，result是 doInBackground返回的结果
            LogUtil.i(TAG,"onPostExecute  ");
            Toast.makeText(AsyncTaskActivity.this,result.getName()+","+result.getAge(),Toast.LENGTH_SHORT).show();
        }
    };

    public void start(View view){
        asyncTask.execute("##hahah##");
    }

    class User {
        String name;
        int age;
        public User(String name,int age){
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


}
