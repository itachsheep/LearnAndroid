package com.tao.customviewlearndemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.ClipDrawView;
import com.tao.customviewlearndemo.view.InnerCircleRelativelayout;
import com.tao.customviewlearndemo.view.LearnPPCircleView;
import com.tao.customviewlearndemo.view.OuterCircleRelativelayout;
import com.tao.customviewlearndemo.view.PPCircleProgressView;

import java.util.Random;

/**
 * Created by SDT14324 on 2018/1/11.
 */

public class ViewTestActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "ViewTestActivity";
//    private CircleRangeView circleRangeView;
//    private AirView mAirView;
    private String [] valueArray;
    private Random random;
    private OuterCircleRelativelayout outerCircle;
    private InnerCircleRelativelayout innerCircle;
    private RelativeLayout rlParent;
    private ImageView ivArrow;

    private PPCircleProgressView ppCircleView;
    private Button btPPcircle;

    private LearnPPCircleView learnPPCircleView;
    private Button btLearnPPcircle;
    private ClipDrawView clipDrawView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicle_range);
        initView();
        initListener();
    }

    private int i = 0;
    public void setPPCircleProgress(View view){
        ppCircleView.setProgress("哈哈",100,true);
    }

    public void learnPPCircleProgress(View view){
        learnPPCircleView.setProgress("哈哈",50,true);
    }

    private void initListener() {
        initCircleRangeView();
        initAirViewSer();
    }

    private void initAirViewSer() {
        innerCircle.setOnClickListener(this);
//        mAirView.setAirModeChangeListener(new AirView.OnAirModeChangeListener() {
//            @Override
//            public void onAirModeChange(int mode, float angle) {
//                //float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,int pivotYType, float pivotYValue
//               /* RotateAnimation animation = new RotateAnimation(
//                        0,360,
//                        RotateAnimation.RELATIVE_TO_PARENT, 0.5f,
//                        RotateAnimation.RELATIVE_TO_PARENT ,0.5f);*/
//                /*RotateAnimation animation = new RotateAnimation(
//                        0,360,
//                        RotateAnimation.ABSOLUTE,300 ,
//                        RotateAnimation.ABSOLUTE ,640);*/
//
//                int[] location = new  int[2] ;
//                innerCircle.getLocationOnScreen(location);
//                RotateAnimation animation = new RotateAnimation(
//                        0,360,
//                        RotateAnimation.ABSOLUTE,location[0] + innerCircle.getWidth() / 2 ,
//                        RotateAnimation.ABSOLUTE ,location[1] + innerCircle.getHeight());
//                animation.setDuration(5000);
//                animation.setFillAfter(true);
//                ivArrow.startAnimation(animation);
//
//                Log.i(TAG,"x = "+location[0]+", y = "+location[1]
//                +", width/2 = "+innerCircle.getWidth() / 2
//                +", height = "+innerCircle.getHeight());
//
//
//                /*Log.i(TAG,"pivotx = "+rlParent.getPivotX()+", x = "+rlParent.getX()
//                +",width =  "+rlParent.getWidth()
//                +", pivoty = "+rlParent.getPivotY()
//                +", y = "+rlParent.getY());
//                DisplayMetrics dm  = getResources().getDisplayMetrics();
//                float density = dm.density;
//                int densityDPI = dm.densityDpi;
//                Log.i(TAG,"widthPixels = "+dm.widthPixels+",heightPixels = "+dm.heightPixels);*/
//            }
//        });
    }

    private void initCircleRangeView() {
//        circleRangeView.setOnClickListener(this);
        valueArray=getResources().getStringArray(R.array.circlerangeview_values);
        random=new Random();
    }

    private void initView() {
//        circleRangeView=  findViewById(R.id.circleRangeView);
        outerCircle = findViewById(R.id.out_circle);
        innerCircle = findViewById(R.id.inner_circle);
//        mAirView = findViewById(R.id.airview);
        ivArrow = findViewById(R.id.iv_arrow);
        rlParent = findViewById(R.id.rl_parent);

//        ppCircleView = findViewById(R.id.pp_circle);
//        btPPcircle = findViewById(R.id.bt_ppcircle);
        learnPPCircleView = findViewById(R.id.pp_learn_circle);
        clipDrawView = findViewById(R.id.cdv);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.inner_circle:
                if(outerCircle.getVisibility() == View.VISIBLE){
                    outerCircle.setVisibility(View.INVISIBLE);
                    //mAirView.setVisibility(View.INVISIBLE);
                }else if(outerCircle.getVisibility() != View.VISIBLE){
                    outerCircle.setVisibility(View.VISIBLE);
                   // mAirView.setVisibility(View.VISIBLE);
                }

                break;

           /* case R.id.circleRangeView:
                {
                    List<String> extras =new ArrayList<>();
                    extras.add("收缩压：116");
                    extras.add("舒张压：85");
                    int i=random.nextInt(valueArray.length);
                    circleRangeView.setValueWithAnim(valueArray[i],extras);
                    break;
                }*/

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mAirView.onTouchEvent(event);
        learnPPCircleView.onTouchEvent(event);
        Log.i("LearnPPCircleView","Activity onTouchEvent");
        return super.onTouchEvent(event);
    }
}
