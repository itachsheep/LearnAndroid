package com.tao.jnidemo;

import com.example.ndkdemo.JniClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	private String TAG = MainActivity.class.getSimpleName();
	static {
		System.loadLibrary("hellow");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void jniCall(View view){
		Log.i(TAG, "jnicall click");
		JniClient.printStr();
	}

}
