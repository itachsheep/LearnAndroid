package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;

import com.google.gson.Gson;
import com.tao.okhttplearn.CryptUtils;
import com.tao.okhttplearn.LogUtil;
import com.tao.okhttplearn.R;

import java.io.IOException;

import Download.CheckUpgradeResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SDT14324 on 2018/5/24.
 */

public class DownloadActivity extends AppCompatActivity {
    private String TAG = DownloadActivity.class.getSimpleName();
    private CheckUpgradeResult upgradeResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        okHttpClient = builder.cache(null)
                .build();
    }
    OkHttpClient okHttpClient;
    public void test_request(View view){


        String url = "http://192.168.0.76/otav2/checkupgrade?";
        String jsonToString = "{\"manual\":\"1\",\"isCheckDeviceInfo\":\"1\",\"customerid\":\"37026\",\"snno\":\"1030933702618259999999\",\"vendor\":\"10\",\"did\":\"A30933702618259999999\",\"devicetype\":\"QD408\",\"hardversion\":\"VT3_02\",\"version\":\"17\"}";

        String param = Base64.encodeToString(jsonToString.getBytes(), Base64.NO_WRAP);
        String key = CryptUtils.hmacSHA1Encrypt(param, CryptUtils.PRIVATE_KEY);

        RequestBody requestBody = new FormBody.Builder()
                .add("key",key)
                .add("param",param)
                .build();
        Request request = new Request.Builder()
                .url(url).post(requestBody).build();

        LogUtil.i(TAG,"url : " + url);
        LogUtil.i(TAG,"key : " + key);
        LogUtil.i(TAG,"param : " + param);
        LogUtil.i(TAG,"json : " + jsonToString);

       okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i(TAG,"onFailure ");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.i(TAG,"onResponse response : "+response.body().string());
                Gson gson = new Gson();
                String result = response.body().string();
                upgradeResult  = gson.fromJson(result,CheckUpgradeResult.class);
                LogUtil.i(TAG,"onResponse : " + upgradeResult);
            }
        });

    }

    public void test_download(View view){

    }
}
