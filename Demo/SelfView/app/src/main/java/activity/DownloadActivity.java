package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import andorid.taow.selfview.R;
import service.DownloadService;

/**
 * Created by taow on 2017/6/5.
 */

public class DownloadActivity extends Activity {
    private Button btStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        btStart = (Button) findViewById(R.id.download_start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service = new Intent(DownloadActivity.this, DownloadService.class);
                startService(service);
            }
        });
    }
}
