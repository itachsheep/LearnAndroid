package com.skyworthauto.testjni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Act1";
	TestJni mJni;
	TextView txtTitle;
	
	static {
		System.loadLibrary("native-lib");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_act1);
		
		txtTitle = (TextView)findViewById(R.id.txtTitle);
		mJni = new TestJni();
		
		
	}
	
	
	public void onBtn1(View view){
		int result = mJni.fun(1, 2);
		txtTitle.setText(String.valueOf(result));
	}
	
	public void onBtn2(View view){
		int result = TestJni.funStatic(2, 2);
		txtTitle.setText(String.valueOf(result));
	}
	
	public void onBtn3(View view){
		int result = mJni.getField();
		txtTitle.setText(String.valueOf(result));
	}
	
	public void onBtn4(View view){
		int result = mJni.getStaticField();
		txtTitle.setText(String.valueOf(result));
	}
	
	public void onBtn5(View view){
		int result = mJni.invokeMethod();
		txtTitle.setText(String.valueOf(result));
	}
	
	public void onBtn6(View view){
		int result = mJni.invokeStaticMethod();
		txtTitle.setText(String.valueOf(result));
	}
	
	public void onBtn7(View view){
		JavaObject obj = mJni.createObject();
		txtTitle.setText(obj.toString());
	}
	
	public void onBtn8(View view){
		JavaObject obj = mJni.createObjectAndSet();
		txtTitle.setText(obj.toString());
	}
	
	public void onBtn9(View view){
//		TestJniOther obj = new TestJniOther();
//		int result = obj.fun_test_other(4,5);
//		txtTitle.setText(String.valueOf(result));
		
		float result = mJni.fun(1.1f, 2.2f);
		txtTitle.setText(String.valueOf(result));
	}
}
