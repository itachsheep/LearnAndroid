package com.example.lenovo.systracedemo;

import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.awt.font.TextAttribute;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Trace.beginSection("test1111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 1; i < 1000; i++){
            test();
        }
    }
    public void test(){
        for (int i  = 0 ;i < 1000; i++){
            Log.i("test","Traceview is a tool that provides a graphical representations of trace logs. You can generate the logs by instrumenting your code with the Debug class." +
                    " This method of tracing is very precise because you can specify " +
                    "exactly where in the code you want to start and stop logging trace" +
                    " data. If you haven't yet generated these trace logs and saved them " +
                    "from your connected device to your local machine, go to Generate" +
                    " trace logs by instrumenting your app. Inspecting these logs using " +
                    "Traceview helps you debug your app and profile its performance.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Trace.endSection();
    }
}
