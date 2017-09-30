package com.xutil.xutildemo.download;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xutil.xutildemo.LogUtil;
import com.xutil.xutildemo.R;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by SDT14324 on 2017/9/29.
 */

public class DownloadActivity extends Activity implements View.OnClickListener {
    private Button btStart;
    private Button btStop;
    private Button btContinue;
    private TextView tvState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_download);

        btStart = (Button) findViewById(R.id.ac_dl_start);
        btStop = (Button) findViewById(R.id.ac_dl_stop);
        btContinue = (Button) findViewById(R.id.ac_dl_continue);

        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ac_dl_continue:

                break;
            case R.id.ac_dl_start:
                downloadStart();
                break;
            case R.id.ac_dl_stop:

                break;
        }
    }

    private void downloadStart() {

    }

    class DownloadCallback implements Callback.CommonCallback<File> {

        @Override
        public void onSuccess(File file) {
            LogUtil.i("onSucess ");
        }

        @Override
        public void onError(Throwable throwable, boolean b) {
            LogUtil.i("onError ");
        }

        @Override
        public void onCancelled(CancelledException e) {
            LogUtil.i("onCancelled ");
        }

        @Override
        public void onFinished() {
            LogUtil.i("onFinished ");
        }
    }

}
