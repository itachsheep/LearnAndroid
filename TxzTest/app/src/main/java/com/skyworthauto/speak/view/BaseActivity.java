package com.skyworthauto.speak.view;

import android.app.Activity;
import android.os.Bundle;

import com.skyworthauto.speak.R;


public class BaseActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

}
