package com.tao.nu409_splitscreen.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tao.nu409_splitscreen.R;
import com.tao.nu409_splitscreen.adapter.LeftViewPagerAdapter;
import com.tao.nu409_splitscreen.fragment.BaseFragment;
import com.tao.nu409_splitscreen.fragment.LeftFragment;
import com.tao.nu409_splitscreen.fragment.MainFullFragment;
import com.tao.nu409_splitscreen.right.LeftBtFragment;
import com.tao.nu409_splitscreen.right.LeftDateFragment;
import com.tao.nu409_splitscreen.right.LeftRadioFragment;
import com.tao.nu409_splitscreen.right.LeftUsbMusicFragment;
import com.tao.nu409_splitscreen.util.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private BaseFragment mainFullFragment;
    private BaseFragment leftFragment;
    private BaseFragment rightFragment;
    private boolean iSsplitScreen = false;

    private FrameLayout fullLayout;
    private LinearLayout splitLayout;
    private FrameLayout leftLayout;
    private FrameLayout rightLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFrgList;

    private LeftUsbMusicFragment leftUsbMusicFragment0;
    private LeftBtFragment leftBtFragment;
    private LeftDateFragment leftDateFragment;
    private LeftRadioFragment leftRadioFragment;
    private LeftUsbMusicFragment leftUsbMusicFragment;
    private LeftBtFragment leftBtFragment5;
    private LeftViewPagerAdapter mLeftAdapter;

    private Button btMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initRightPart();

        initLeftPart();
    }

    private void initLeftPart() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_full,mainFullFragment);
        ft.replace(R.id.main_left,leftFragment);
//        ft.replace(R.id.main_right,rightFragment);
        ft.commit();
    }

    private void initRightPart() {
        mainFullFragment = new MainFullFragment();
        leftFragment = new LeftFragment();
        rightFragment = new LeftUsbMusicFragment();

        mFrgList = new ArrayList<>();
        leftUsbMusicFragment0 = new LeftUsbMusicFragment();
        leftBtFragment = new LeftBtFragment();
        leftDateFragment = new LeftDateFragment();
        leftRadioFragment = new LeftRadioFragment();
        leftUsbMusicFragment = new LeftUsbMusicFragment();
        leftBtFragment5 = new LeftBtFragment();

        mFrgList.add(leftUsbMusicFragment0);
        mFrgList.add(leftBtFragment);
        mFrgList.add(leftDateFragment);
        mFrgList.add(leftRadioFragment);
        mFrgList.add(leftUsbMusicFragment);
        mFrgList.add(leftBtFragment5);

        mLeftAdapter = new LeftViewPagerAdapter(getSupportFragmentManager(), mFrgList);
        mViewPager.setAdapter(mLeftAdapter);
        mViewPager.setCurrentItem(1, false);
    }

    private void initView() {
        fullLayout = (FrameLayout) findViewById(R.id.main_full);
        leftLayout = (FrameLayout) findViewById(R.id.main_left);
        rightLayout = (FrameLayout) findViewById(R.id.main_right);
        splitLayout = (LinearLayout) findViewById(R.id.main_split);
        btMain = (Button) findViewById(R.id.main_bt);
        mViewPager = (ViewPager) findViewById(R.id.main_vp);
    }

    public void split_screen(View view){
        if(iSsplitScreen){
            L.i(TAG,"split_screen true");
            //分屏 -> 全屏
            leftLayout.setVisibility(View.GONE);
            rightLayout.setVisibility(View.GONE);
            leftLayout.startAnimation(loadAnim(R.animator.push_left_out,null));
            rightLayout.startAnimation(loadAnim(R.animator.push_right_out,null));
            iSsplitScreen = false;
            btMain.setText(" << ");
        }else {
            //全屏 -> 分屏
            L.i(TAG,"split_screen false");
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.startAnimation(loadAnim(R.animator.push_left_in,null));
            rightLayout.startAnimation(loadAnim(R.animator.push_right_in,null));
            iSsplitScreen = true;
            btMain.setText(" >> ");
        }
    }

    private void startObjectorAnim(int id,View view){
        /*Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, id);
        animator.setTarget(view);
        animator.start();*/
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, id);
        animatorSet.setTarget(view);
        animatorSet.start();
    }

    private Animation loadAnim(int id, Animation.AnimationListener listener){
        ObjectAnimator animator = new ObjectAnimator();
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, id);
        if(listener != null){
            animation.setAnimationListener(listener);
        }
        return animation;
    }

    private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //L.i(TAG,"onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
           // L.i(TAG,"onAnimationEnd");
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            //L.i(TAG,"onAnimationRepeat");
        }
    };
}
