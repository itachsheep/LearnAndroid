package com.tao.usecase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tao.usecase.fragment.AddFragment1;
import com.tao.usecase.fragment.AddFragment2;
import com.tao.usecase.fragment.BaseFragment;
import com.tao.usecase.fragment.TestButterKnifeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tao.usecase.R.id.fullscreen_content;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
//    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
           /* ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }*/
//            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    @BindView(fullscreen_content) View mContentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
//        mControlsView = findViewById(R.id.fullscreen_content_controls);
        ButterKnife.bind(this);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toggle();

               /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                TestButterKnifeFragment fragment = new TestButterKnifeFragment();
                ft.replace(R.id.container_fragment,fragment);
                ft.addToBackStack("tag");
                ft.commit();*/
                showFragment(new TestButterKnifeFragment());
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    private BaseFragment fragment1 = new AddFragment1();
    private BaseFragment fragment2 = new AddFragment2();

    @OnClick(R.id.main_pop)
    public void pop(){
        android.app.FragmentManager fm = getFragmentManager();
        /*final List<Fragment> list = fm.getFragments();
        for (Fragment frg : list) {
            Log.i("Test","Activity pop frg: "+frg);
        }*/
//        hideFragment();
        /*FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();*/
        showFragment(fragment1);
    }
    protected BaseFragment mCurrentFragment;
    private String FRAGMENT_TAG = "simple";
    public void showFragment(BaseFragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(!fragment.isAdded()){
            ft.add(R.id.container_fragment,fragment,FRAGMENT_TAG);
        }

        if(mCurrentFragment != null){
            ft.hide(mCurrentFragment);
        }

        ft.show(fragment);
        ft.commitAllowingStateLoss();
        Log.i("Test","showFragment mCurr: "+mCurrentFragment);
        mCurrentFragment = fragment;
    }

    public void hideFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Log.i("Test","hideFragment mCurr: "+mCurrentFragment);
        if(null != mCurrentFragment){
            ft.hide(mCurrentFragment);
        }
        ft.commitAllowingStateLoss();
        mCurrentFragment = null;

    }

    @OnClick(R.id.main_detail)
    public void detail(){
//        FragmentManager fm = getFragmentManager();
//        final List<Fragment> list = fm.getFragments();
//        for (Fragment frg : list) {
//            Log.i("Test","Activity detail frg: "+frg);
//        }
        showFragment(fragment2);
//        Log.i("Test","Activity detail set invisible 33");
//        FrameLayout layout = findViewById(R.id.container_fragment);
//        layout.setVisibility(View.INVISIBLE);



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
//        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
       /* ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }*/
//        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
