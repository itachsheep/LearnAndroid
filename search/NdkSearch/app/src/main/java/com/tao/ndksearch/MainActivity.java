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

    TestJni testJni;
    TextView textView;
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
        testJni = new TestJni();
        textView = findViewById(R.id.show_text);
    }

    public void bt_funStatic(View view){
        int result = TestJni.funStatic(2, 2);
        showToast("2 + 2 = "+result);
    }

    public void bt_fun__II(View view){
        showToast("2+3 = "+testJni.fun(2,3));
    }

    public void bt_fun__FF(View view){
        showToast("2.1+3.1 = "+testJni.fun(2.1f,3.1f));
    }

    public void bt_createObject(View view){
        textView.setText(testJni.createObject().toString());
    }

    public void bt_createObjectAndSet(View view){
        textView.setText(testJni.createObjectAndSet().toString());
    }






    public void showToast(String mes){
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();
    }


    public native String stringFromJNI();
}
