package com.java.mvpdemo.mvp.model;

import android.os.AsyncTask;
import android.view.View;

/**
 * Created by SDT14324 on 2017/9/20.
 */
//Params,Progress,Result
public class GreetingGeneratorTask extends AsyncTask<String,View,Integer> {
    public interface GreetingTaskListener{
        void onGreetingGenerated(String txt);
    }
    private String bTxt;
    private GreetingTaskListener listener;
    public GreetingGeneratorTask(String txt,GreetingTaskListener listener){
        bTxt = txt;
        this.listener = listener;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        try {
            Thread.sleep(2000); // Simulate computing
        } catch (InterruptedException e) { }

        return (int) (Math.random() * 100);
    }


    @Override
    protected void onPostExecute(Integer integer) {
        listener.onGreetingGenerated(bTxt+", "+integer);
    }

    /**************************************************************/

    @Override
    protected void onPreExecute() {
        // TODO: 2017/9/20
    }

    @Override
    protected void onProgressUpdate(View... values) {
        // TODO: 2017/9/20
    }
}
