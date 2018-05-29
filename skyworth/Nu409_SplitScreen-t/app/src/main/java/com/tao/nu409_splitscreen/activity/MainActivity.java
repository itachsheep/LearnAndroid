package com.tao.nu409_splitscreen.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tao.nu409_splitscreen.R;
import com.tao.nu409_splitscreen.fragment.BaseFragment;
import com.tao.nu409_splitscreen.fragment.LeftFragment;
import com.tao.nu409_splitscreen.fragment.MainFullFragment;
import com.tao.nu409_splitscreen.fragment.RightFragment;
import com.tao.nu409_splitscreen.util.L;

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

    private Button btMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullLayout = (FrameLayout) findViewById(R.id.main_full);
        leftLayout = (FrameLayout) findViewById(R.id.main_left);
        rightLayout = (FrameLayout) findViewById(R.id.main_right);
        splitLayout = (LinearLayout) findViewById(R.id.main_split);
        btMain = (Button) findViewById(R.id.main_bt);

        mainFullFragment = new MainFullFragment();
        leftFragment = new LeftFragment();
        rightFragment = new RightFragment();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_full,mainFullFragment);
        ft.replace(R.id.main_left,leftFragment);
        ft.replace(R.id.main_right,rightFragment);
        ft.commit();
    }

    public void split_screen(View view){
        if(iSsplitScreen){
            L.i(TAG,"split_screen true");
            //分屏 -> 全屏
            leftLayout.setVisibility(View.GONE);
            rightLayout.setVisibility(View.GONE);

            leftLayout.startAnimation(loadAnim(R.anim.push_left_out,null));
            rightLayout.startAnimation(loadAnim(R.anim.push_right_out,null));
            iSsplitScreen = false;
            btMain.setText(" << ");
        }else {
            //全屏 -> 分屏
            L.i(TAG,"split_screen false");
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.startAnimation(loadAnim(R.anim.push_left_in,null));
            rightLayout.startAnimation(loadAnim(R.anim.push_right_in,null));

            iSsplitScreen = true;
            btMain.setText(" >> ");
        }
    }

    private Animation loadAnim(){
        ObjectAnimator animator = new ObjectAnimator();
        animator.setPropertyName("translationX");
        animator.setFloatValues();
        return null;
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
