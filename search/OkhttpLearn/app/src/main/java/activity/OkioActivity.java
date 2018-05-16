package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.okhttplearn.LogUtil;
import com.tao.okhttplearn.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by SDT14324 on 2018/5/15.
 */

public class OkioActivity extends AppCompatActivity {
    private String TAG = OkioActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okio);


    }

    public void copyfile(View view) throws IOException {
        /*File from = new File(getFilesDir(),"test.txt");
        File to = new File(getFilesDir(),"dd.txt");

        try {
            BufferedSource bufferedSource = Okio.buffer(Okio.source(from));
            BufferedSink bufferedSink = Okio.buffer(Okio.sink(to));

            String copyContent = "";
            while (!bufferedSource.exhausted()){
                copyContent = bufferedSource.readString(Charset.defaultCharset());
                LogUtil.i(TAG,"copyContent: "+copyContent);
                bufferedSink.write(copyContent.getBytes());
            }
        }catch (IOException e){
            LogUtil.i(TAG,"copyfile exception !!");
            e.printStackTrace();
        }*/

        InputStream inputStream = getAssets().open("test.txt");
        FileOutputStream outputStream = new FileOutputStream(new File(getFilesDir(),"dd.txt"));
        BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(outputStream));
        while (!bufferedSource.exhausted()){
            String s = bufferedSource.readString(Charset.forName("UTF-8"));
            LogUtil.i(TAG,"s = "+s);
            bufferedSink.write(s.getBytes());
            bufferedSink.flush();
        }
        bufferedSource.close();
        bufferedSink.close();
    }
}
