package com.tao.ndksearch;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        TestJni testJni = new TestJni();
        int fun = testJni.fun(1, 2);
        int funStatic = testJni.funStatic(2, 3);

        LogUtils.i(TAG,"fun = "+fun+", funStatic = "+funStatic);

    }

    public void bt_funStatic(View view){
        int result = TestJni.funStatic(2, 2);
        showToast("2 + 2 = "+result);
    }



    public void showToast(String mes){
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();
    }


    public native String stringFromJNI();
}
