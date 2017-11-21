package com.skyworthauto.speak.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.util.DebugUtil;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZConfigManager;
import com.txznet.sdk.TXZConfigManager.ActiveListener;
import com.txznet.sdk.TXZConfigManager.AsrEngineType;
import com.txznet.sdk.TXZConfigManager.InitListener;
import com.txznet.sdk.TXZConfigManager.InitParam;
import com.txznet.sdk.TXZConfigManager.TtsEngineType;
import com.txznet.sdk.TXZTtsManager;

public class MainActivity extends AppCompatActivity implements ActiveListener, InitListener{
	InitParam mInitParam;
	private String tag = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.finish();
			}
		});


		findViewById(R.id.init).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.i(tag,"init..");
				init();
			}
		});

		findViewById(R.id.bt_music).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.i(tag,"start musciactivity..");
				Intent intent = new Intent(MainActivity.this,MusicActivity.class);
				startActivity(intent);

			}
		});

		findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.i("this is test!!");

			}
		});
	}

	private void init(){
		{
			// TODO 获取接入分配的appId和appToken
			String appId = "09c12ae0cbd11cc844074b5cc844e8c4";
			String appToken = "d6a5e4758dc60a679566dd69c6d5dbe6aa143cff";
			// TODO 设置初始化参数
			mInitParam = new InitParam(appId, appToken);
			// TODO 可以设置自己的客户ID，同行者后台协助计数/鉴权
			// mInitParam.setAppCustomId("ABCDEFG");
			// TODO 可以设置自己的硬件唯一标识码
			// mInitParam.setUUID("0123456789");
		}

		{
			// TODO 设置识别和tts引擎类型
			mInitParam.setAsrType(AsrEngineType.ASR_YUNZHISHENG).setTtsType(
					TtsEngineType.TTS_YUNZHISHENG);
		}

		{
			// TODO 设置唤醒词
			String[] wakeupKeywords = this.getResources().getStringArray(R.array.txz_sdk_init_wakeup_keywords);
			mInitParam.setWakeupKeywordsNew(wakeupKeywords);
		}

		{
			// TODO 可以按需要设置自己的对话模式
			// mInitParam.setAsrMode(AsrMode.ASR_MODE_CHAT);
			// TODO 设置识别模式，默认自动模式即可
			// mInitParam.setAsrServiceMode(AsrServiceMode.ASR_SVR_MODE_AUTO);
			// TODO 设置是否允许启用服务号
			// mInitParam.setEnableServiceContact(true);
			// TODO 设置开启回音消除模式
			// mInitParam.setFilterNoiseType(1);
			// TODO 其他设置
		}

		mInitParam.setWakeupThreshhold(-3.2f);

		// TODO 初始化在这里
		TXZConfigManager.getInstance().initialize(this, mInitParam, this, this);
		//TXZConfigManager.getInstance().initialize(this, this);
	}

	@Override
	public void onFirstActived() {
		Log.i("TestDemo", "onFirstActived..");
	}

	@Override
	public void onSuccess() {
		TXZTtsManager.getInstance().speakText("同行者引擎初始化成功");
		Toast.makeText(this, "初始化同行者引擎初始化成功!!" , Toast.LENGTH_LONG).show();
		DebugUtil.showTips("同行者引擎初始化成功");
		Log.i("TestDemo", "onSucess..");
	}

	@Override
	public void onError(int i, String errDesc) {
		Log.i("TestDemo", "onError.. "+errDesc);
	}

}
