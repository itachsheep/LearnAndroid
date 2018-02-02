package com.tao.statushidedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FullScreenActivity extends Activity implements View.OnClickListener {
    private Button btShow;
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
    private String TAG = FullScreenActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btShow = findViewById(R.id.btShow);
        btShow.setOnClickListener(this);
        //mSystemUiHider = SystemUiHider.getInstance(this, btShow, HIDER_FLAGS);
       // mSystemUiHider.setup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btShow:
                if(isShow){
                    //btShow.setSystemUiVisibility(mHideFlags);
                    //状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
                    mHideFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
                    mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
                    mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    LogUtils.i(TAG,"onClick  mHideFlags = "+Integer.toHexString(mHideFlags));
                    //mSystemUiHider.hide();
                    btShow.setSystemUiVisibility(mHideFlags);
                    isShow = false;
                }else {

                    mShowFlags = View.SYSTEM_UI_FLAG_VISIBLE;
                    //让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar。
                    mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                    //让View全屏显示，Layout会被拉伸到StatusBar和NavigationBar下面。
                    mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                    LogUtils.i(TAG,"onClick  mShowFlags = "+Integer.toHexString(mShowFlags));
                    btShow.setSystemUiVisibility(mShowFlags);
                    //mSystemUiHider.show();

                    isShow = true;
                }

                break;
        }
    }
}
