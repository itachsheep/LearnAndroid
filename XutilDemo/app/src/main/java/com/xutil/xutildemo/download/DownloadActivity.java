package com.xutil.xutildemo.download;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.xutil.xutildemo.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by SDT14324 on 2017/9/29.
 */

@ContentView(R.layout.ac_download)
public class DownloadActivity extends Activity  {

    @ViewInject(R.id.ac_dl_start)
    private Button btStart;

    @ViewInject(R.id.ac_dl_lv)
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    @Event(R.id.ac_dl_start)
    private void startDownload(View view){
        for (int i = 0; i < 5; i++) {
            String url = "http://192.168.0.76:9191/nginxSource/30001/7085/36ed7845-067f-4d23-9edc-d71abe7074f1.zip";
            String label = i + "download_" + System.nanoTime();
            DownloadManager.getInstance().startDownload(
                    url, label,
                    "/sdcard/download/" + label + ".zip", true, false, null);
        }
    }





}
