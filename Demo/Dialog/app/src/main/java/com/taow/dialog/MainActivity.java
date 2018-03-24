package com.taow.dialog;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import view.SearchResult;

public class MainActivity extends Activity {
    FrameLayout layout;
    Button btShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (FrameLayout) findViewById(R.id.main_fl);
        btShow = (Button)findViewById(R.id.main_bt);

        setlistener();
    }

    private void setlistener() {
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchResult dialog = new SearchResult();
                dialog.show(getFragmentManager(),"ttt");
            }
        });

    }
}
