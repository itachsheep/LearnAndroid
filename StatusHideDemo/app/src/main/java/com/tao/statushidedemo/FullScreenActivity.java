package com.tao.statushidedemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FullScreenActivity extends Activity implements View.OnClickListener {
    private Button btFlagShow;
    private Button btFlagHide;
    private Button btSendShow;
    private Button btSendHide;
    private Button btFrameworkHide;
    private Button btFrameworkShow;

    private final String DATE_CHANGE_ACTION = "com.skyworthauto.baic.settings.dateformat";
    private final String KEY_DATE_FORMAT = "dateformat";
//    private SystemUiHider mSystemUiHider;
    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #//AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    private boolean isShow = true;
    private int mHideFlags;
    private int mShowFlags;
    int i;
    private String TAG = FullScreenActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);*/

        btFlagShow = findViewById(R.id.bt_flag_show);
        btFlagHide = findViewById(R.id.bt_flag_hide);
        btSendShow = findViewById(R.id.bt_send_show);
        btSendHide = findViewById(R.id.bt_send_hide);
        btFrameworkHide = findViewById(R.id.bt_framework_hide);
        btFrameworkShow = findViewById(R.id.bt_framework_show);

        findViewById(R.id.bt_date_0).setOnClickListener(this);
        findViewById(R.id.bt_date_1).setOnClickListener(this);
        findViewById(R.id.bt_date_2).setOnClickListener(this);
        findViewById(R.id.bt_air_broadcasst).setOnClickListener(this);

        btFlagShow.setOnClickListener(this);
        btFlagHide.setOnClickListener(this);
        btSendShow.setOnClickListener(this);
        btSendHide.setOnClickListener(this);
        btFrameworkHide.setOnClickListener(this);
        btFrameworkShow.setOnClickListener(this);
        //mSystemUiHider = SystemUiHider.getInstance(this, btShow, HIDER_FLAGS);
       // mSystemUiHider.setup();
       /* btFlagShow.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.i(TAG,"onSystemUiVisibilityChange visibility = "+Integer.toHexString(visibility));
            }
        });*/


       registerReceiver(new BroadcastReceiver() {
           @Override
           public void onReceive(Context context, Intent intent) {
               while (true){
                    i++;
                   Log.i(TAG,"i = "+i);
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
       },new IntentFilter(DATE_CHANGE_ACTION));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_flag_show:
              {
                  mShowFlags = View.SYSTEM_UI_FLAG_VISIBLE;
                  //让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar。
                 // mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                  //让View全屏显示，Layout会被拉伸到StatusBar和NavigationBar下面。
                  //mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                  LogUtils.i(TAG,"onClick  mShowFlags = "+Integer.toHexString(mShowFlags));
                  btFlagShow.setSystemUiVisibility(mShowFlags);
                }
                break;
            case R.id.bt_flag_hide:
            {

                //btShow.setSystemUiVisibility(mHideFlags);
                //状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
                //mHideFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
                mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
                mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                LogUtils.i(TAG,"onClick  mHideFlags = "+Integer.toHexString(mHideFlags));
                //mSystemUiHider.hide();
                btFlagShow.setSystemUiVisibility(mHideFlags);

            }
                break;
            case R.id.bt_send_show:
            {
                Intent intent = new Intent("com.systemui.air.changed");
                intent.putExtra("visible",1);
                intent.getIntExtra("visible",1);
                sendBroadcast(intent);
            }
                break;
            case R.id.bt_send_hide:
            {
                Intent intent = new Intent("com.systemui.air.changed");
                intent.putExtra("visible",0);
                sendBroadcast(intent);
            }
                break;

            case R.id.bt_framework_hide:
            {
                Intent intent = new Intent("com.skyworth.navi.control");
                intent.putExtra("visible",0);
                sendBroadcast(intent);

            }
                break;

            case R.id.bt_framework_show:
            {
                mHandler.sendEmptyMessage(1);
//                Intent intent = new Intent("com.skyworth.navi.control");
//                intent.putExtra("visible",1);
//                sendBroadcast(intent);
            }
                break;

            case R.id.bt_date_0:
            {
                Intent intent = new Intent(DATE_CHANGE_ACTION);
                intent.putExtra(KEY_DATE_FORMAT,0);
                sendBroadcast(intent);
            }
                break;

            case R.id.bt_date_1:
            {
                Intent intent = new Intent(DATE_CHANGE_ACTION);
                intent.putExtra(KEY_DATE_FORMAT,1);
                sendBroadcast(intent);
            }
            break;


            case R.id.bt_date_2:
            {
//                Intent intent = new Intent(DATE_CHANGE_ACTION);
                Intent intent = new Intent("com.skyworthauto.baic.settings.timeformat");
                intent.putExtra(KEY_DATE_FORMAT,2);
                sendBroadcast(intent);
//                sendOrderedBroadcast(intent,null);
            }
            break;

            case R.id.bt_air_broadcasst:
            {
                Intent intent = new Intent(AIR_ACTION);
                sendBroadcast(intent);
            }
                break;
        }
    }
    private final String AIR_ACTION = "com.systemui.air.action";
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                {
                    LogUtils.i(TAG,"sendEmptyMessageDelayed  ");
                    Intent intent = new Intent("com.skyworth.navi.control");
                    intent.putExtra("visible",1);
                    sendBroadcast(intent);
                    mHandler.sendEmptyMessageDelayed(1,5000);
                }

                    break;
            }
        }
    };
    class Test {
        int a;
    }
}
