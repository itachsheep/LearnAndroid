package com.tao.lunbodemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import fragment.BaseFragment;
import fragment.DateFragment;
import fragment.LogoFragment;
import fragment.MusicFragment;
import fragment.RadioFragment;
import fragment.WheatherFragment;

public class LunboActivity extends AppCompatActivity {
    ViewPager viewPager;
    List<BaseFragment> list;
    MyAdapter adapter;

    WheatherFragment wheatherFragment0;
    LogoFragment logoFragment ;
    RadioFragment radioFragment ;
    MusicFragment musicFragment ;
    DateFragment dateFragment ;
    WheatherFragment wheatherFragment5 ;
    LogoFragment logoFragment6;

    private boolean isRadio = false;
    private boolean isMusic = false;
     //当前页面位置
    private int mCurrentPositon;
    private String TAG = LunboActivity.class.getSimpleName();
    private static final int WHAT_AUTO_PLAY = 1000;
    //自动播放时间
    private int mAutoPalyTime = 2000;
    //页面切换时间
    private static final int SCROLL_DURATION = 500;
    @SuppressLint("HandlerLeak")
    private Handler autoPlayHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mCurrentPositon++;
            viewPager.setCurrentItem(mCurrentPositon);
            autoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.main_vp);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LunboActivity.this,"11111",Toast.LENGTH_SHORT).show();
            }
        });

        list = new ArrayList<>();
        wheatherFragment0 = new WheatherFragment();
        logoFragment = new LogoFragment();
        radioFragment = new RadioFragment();
        musicFragment = new MusicFragment();
        dateFragment = new DateFragment();
        wheatherFragment5 = new WheatherFragment();
        logoFragment6 = new LogoFragment();

        list.add(wheatherFragment0);
        list.add(logoFragment);
        list.add(radioFragment);
        list.add(musicFragment);
        list.add(dateFragment);
        list.add(wheatherFragment5);
        list.add(logoFragment6);

        adapter = new MyAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1,false);
        MyPageListener pageListener = new MyPageListener();
        viewPager.addOnPageChangeListener(pageListener);
        //设置自动轮播切换时的时间
        setDefaultDuration();
        //设置切换时的动画效果
        //setDefaultAnmation();
    }

    private void setDefaultAnmation(){
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                    float MIN_SCALE = 0.85f;
                    float MIN_ALPHA = 0.5f;

                    @Override
                    public void transformPage(View view, float position) {
                        int pageWidth = view.getWidth();
                        int pageHeight = view.getHeight();

                        if (position < -1) { // [-Infinity,-1)
                            // This page is way off-screen to the left.
                            view.setAlpha(0);
                        } else if (position <= 1) { // [-1,1]
                            // Modify the default slide transition to
                            // shrink the page as well
                            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                            if (position < 0) {
                                view.setTranslationX(horzMargin - vertMargin / 2);
                            } else {
                                view.setTranslationX(-horzMargin + vertMargin / 2);
                            }
                            // Scale the page down (between MIN_SCALE and 1)
                            view.setScaleX(scaleFactor);
                            view.setScaleY(scaleFactor);
                            // Fade the page relative to its size.
                            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                        } else { // (1,+Infinity]
                            // This page is way off-screen to the right.
                            view.setAlpha(0);
                        }
                    }
                }
        );
    }

    private void setDefaultDuration() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            Scroller scroller = new Scroller(LunboActivity.this, new LinearInterpolator()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, SCROLL_DURATION);
                }
                @Override
                public void startScroll(int startX, int startY, int dx, int dy) {
                    super.startScroll(startX, startY, dx, dy, SCROLL_DURATION);
                }
            };
            field.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    public void disRadio(View view){
        isRadio = true;
        boolean isSucess = list.remove(radioFragment);
        adapter.notifyDataSetChanged();
        Log.i(TAG,"disRadio isSuceess = "+isSucess);
    }

    public void disMusic(View view){
        isMusic = true;
        list.remove(musicFragment);
        adapter.notifyDataSetChanged();
    }

    public void lunbo(View view ){

        autoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);

    }

    public void showall(View view){
        if(isRadio){
            isRadio = false;
            list.add(2,radioFragment);
        }else if(isMusic){
            isMusic = false;
            list.add(3,musicFragment);
        }
        adapter.notifyDataSetChanged();
    }


    class MyPageListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i(TAG,"onPageScrolled position = "+position
//                +", positionOffset = "+positionOffset
//                +", positionOffsetPixels = "+positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            Log.i(TAG,"onPageSelected position = "+position);
            mCurrentPositon = position % (list.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                int current = viewPager.getCurrentItem();
                int lastReal = viewPager.getAdapter().getCount()-2;
                Log.i(TAG,"onPageScrollStateChanged current = "+current
                        +", lastReal = "+lastReal);
                if (current == 0) {
                    viewPager.setCurrentItem(lastReal, false);
                } else if (current == lastReal+1) {
                    viewPager.setCurrentItem(1, false);
                }
            }
        }
    }
    class MyAdapter extends FragmentStatePagerAdapter {
//        private List<BaseFragment> list;
        public MyAdapter(FragmentManager fm,List<BaseFragment> list) {
            super(fm);
//            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public int getItemPosition(Object object) {
            Log.i(TAG,"getItemPosition object = "+object );
            return POSITION_NONE;
        }
    }
}
