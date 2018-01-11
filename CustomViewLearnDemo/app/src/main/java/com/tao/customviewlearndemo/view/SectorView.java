package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;


/**
 * make a circle view
 * @author Young
 * 2015/5/7
 */

public class SectorView extends View {

	
	private static final String TAG = "SectorView";

	private Context mContext;
    private Paint paint;
    private RectF mOval;
       
/*    private int roundColor;
      
    private int roundProgressColor;  
       
    private int textColor;  
      
    private float textSize;  
      
    private float roundWidth;  */
       
    private int max;  
      
    private int progress;  
  
/*    private boolean textIsDisplayable;
    
    private int style;  */
      
    public static final int STROKE = 0;  
    public static final int FILL = 1;  
    
    
    private static final int POSISION_COOL = 0;
    private static final int POSISION_WARM = 1;
    private static final int POSISION_NATURAL = 2;
    
    private int mCurrentPosition = 0;
/*    private float mDownX = 0;
    private float mDownY = 0;
    private float mRadius = 0;*/
    
    private int mStartAngle = 0;
    private boolean mIsMoving = false;
    
    int mLocation[] = new int[2];
    
	private Drawable mColdDrawble;
	private Drawable mWarmDrawble;
	private Drawable mNatureDrawble;
    
    private CircleViewChangeListen mViewChangeListenner;
    
    private void initDrawble(){
    	mColdDrawble = getResources().getDrawable(R.drawable.ac_knob_indicator_freeze);
    	mWarmDrawble = getResources().getDrawable(R.drawable.ac_knob_indicator_warm);
    	mNatureDrawble = getResources().getDrawable(R.drawable.ac_knob_indicator_natural);
    }
    
    private void drawAngle(Canvas c){
    	if(mIsMoving){
    		c.save();
        	paint.setARGB(150, 185, 164, 130);
            c.drawArc(mOval, mStartAngle, 360 * progress / max, true, paint);
            c.restore();
    	}
    }
    
    private void drawCoolPosition(Canvas c){

        if(!mIsMoving){
            c.save();
	        paint.setARGB(255, 185, 164, 130);
	        c.drawArc(mOval, -90, 360 * progress / max, true, paint);  
	        c.restore();
	        mColdDrawble.setBounds(50, 45, 50 + mColdDrawble.getIntrinsicWidth(), 45 +
                    mColdDrawble.getIntrinsicHeight());
	        mColdDrawble.draw(c);
	        c.restore();
        }

    }
    
    
    
    private void drawHotPosition(Canvas c){
    	
        if(!mIsMoving){
            c.save();
	       	paint.setARGB(255, 185, 164, 130);
	        c.drawArc(mOval, -30, 360 * progress / max, true, paint);
	        c.restore();
	        mWarmDrawble.setBounds(120, 170, 120 + mWarmDrawble.getIntrinsicWidth(), 170 +
                    mWarmDrawble.getIntrinsicHeight());
	        mWarmDrawble.draw(c);
	        c.restore();
        }
 
    }
    
    
    private void drawNaturalPosition(Canvas c){
    	
    	if(!mIsMoving){
    		c.save();
    		paint.setARGB(255, 185, 164, 130);
            c.drawArc(mOval, 30, 360 * progress / max, true, paint);
            c.restore();
            mNatureDrawble.setBounds(40, 300, 40 + mNatureDrawble.getIntrinsicWidth(), 300 +
                    mNatureDrawble.getIntrinsicHeight());
            mNatureDrawble.draw(c);
            c.restore();
    	}
        
    }
    
    
    /** 
     * 利用反三角函数，求转过的角度
	 *  Math.asin(1)    
	 *  Math.acos(1)
	 *  Math.atan(1)
     */ 
    private boolean mTop = false;
    private int getStartAngle(int w, int h){
        
        float centerX = 0, centerY = getHeight()/2;
        float width = w - centerX;
        float height = h - centerY;
        int ang = Math.round((float)(Math.atan(width/height) * 180/ Math.PI));
        if(ang < 0){
            ang = -ang -90;
        }else if(ang > 0){
            ang = -ang +90;
        }


        //------------------------------
        /*
    	int angle = 0;
    	h = (int) (h - mRadius - 30);
        int x1 = 0, x2 = w; //点1坐标
        int y1 = (int) mRadius - 30, y2 = h; //点2坐标
        int x = Math.abs(x1-x2);
        int y = Math.abs(y1-y2);
        double z = Math.sqrt(x*x + y*y);
        angle = Math.round((float)(Math.asin(y/z)/Math.PI*180));//最终角度
    	
    	if(h < y1){ 		
    		if(angle > 90){
    			angle = 90;
    		}
    		angle = -angle;
    		mTop = true;
    	}else{
    		if(angle > 30){
    			angle = 30;
    		}
    		mTop = false;
    	}*/
    	
//    	Log.d(TAG, "mLocation == " + y1 + " x2 == " + w + " y2 == " + h + "  getStartAngle == " + angle);
//    	return angle;
        return ang;
    }


      
    public SectorView(Context context) {
        this(context, null);  
        mContext = context;
        initDrawble();
//        LauncherApplication application = LauncherApplication.getInstance();
//		mCurrentPosition = application.getCurrACMode();
    }  
  
    public SectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
        initDrawble();
//        LauncherApplication application = LauncherApplication.getInstance();
//		mCurrentPosition = application.getCurrACMode();
    }  
      
    public SectorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);  
        mContext = context;
        initDrawble();
        paint = new Paint();
  
          
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SectorView);  
          

/*        roundColor = mTypedArray.getColor(R.styleable.SectorView_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.SectorView_roundProgressColor, Color.GRAY);  
        textColor = mTypedArray.getColor(R.styleable.SectorView_textColor, Color.GREEN);  
        textSize = mTypedArray.getDimension(R.styleable.SectorView_textSize, 15);  
        roundWidth = mTypedArray.getDimension(R.styleable.SectorView_roundWidth, 90);  */
        max = mTypedArray.getInteger(R.styleable.SectorView_max, 100);  
/*        textIsDisplayable = mTypedArray.getBoolean(R.styleable.SectorView_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.SectorView_style, 0);  */
          
        mTypedArray.recycle();  
        //setBackground(getResources().getDrawable(R.drawable.ac_knob_bg));

//        LauncherApplication application = LauncherApplication.getInstance();
//		mCurrentPosition = application.getCurrACMode();

    }  
      
  
    @Override
    protected void onDraw(Canvas canvas) {
    	
    	drawAngle(canvas);

        switch (mCurrentPosition) {
        
		case POSISION_COOL:
			drawCoolPosition(canvas);
			break;
			
		case POSISION_WARM:
			drawHotPosition(canvas);
	        break;
			
		case POSISION_NATURAL:
			drawNaturalPosition(canvas);
			break;
		}


    } 

    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMax(6);
        setProgress(1);

        int radius = (int) (getMeasuredWidth()); 
//        mRadius = radius;
          
        paint.setARGB(255, 185, 164, 130);//参数1为透明度
        paint.setAntiAlias(true); 
        
//        radius = radius - 30;
   
        if(null == mOval){
            mOval = new RectF(-radius, 0, radius, 2*radius);
        }    
        
        paint.setStrokeWidth(2); //-90 -30 30
        paint.setStyle(Paint.Style.FILL);
        
        int width = MeasureSpec.getSize(widthMeasureSpec) / 2;
        int height = MeasureSpec.getSize(heightMeasureSpec) / 2;
//        Log.d("CircleView", "onMeasure, height=" + height + ", width=" + width);
        //updateCenter(width, height);
        getLocationOnScreen(mLocation); 
    }
    
    
    long time = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event){

//        Log.d(TAG, "onTouchEvent() >>> [" + event.getRawX()+ " , " + event.getRawY() + "]");
    	switch (event.getAction()) {
    	
		case MotionEvent.ACTION_DOWN:
//			Log.d(TAG, "onTouchEvent() >>> ACTION_DOWN");
/*			mDownX = event.getRawX();
			mDownY = event.getRawY();*/
			mIsMoving = false;
			
			break;
			
		case MotionEvent.ACTION_UP:
//            Log.d(TAG, "onTouchEvent() >>> ACTION_DOWN");
			mIsMoving = false;		
	    	if(isEnabled() && !mIsMoving){
/*	    		float x = event.getRawX();
	    		float y = event.getRawY();*/
	    		
	    		if(mStartAngle >= 0){
	    			mCurrentPosition = POSISION_NATURAL;
	    		}
	    		
	    		else if(mStartAngle < 0 && mStartAngle >= -60){
	    			mCurrentPosition = POSISION_WARM;
	    		}
	    		
	    		else if(mStartAngle < -60){
	    			mCurrentPosition = POSISION_COOL;
	    		}

/*                LauncherApplication airApplication = LauncherApplication.getInstance();
				airApplication.setCurrACMode(mCurrentPosition);*/


	    		invalidate();

                if(null != mViewChangeListenner){
                    mViewChangeListenner.viewChange(mCurrentPosition, false);
                }
	    	}

			break;
			
		case MotionEvent.ACTION_MOVE:
//            Log.d(TAG, "onTouchEvent() >>> ACTION_DOWN");
/*			int x = (int) event.getRawX();
			int y = (int) event.getRawY();*/
            int x = (int) event.getX();
            int y = (int) event.getY();
			mIsMoving = true;
			mStartAngle = getStartAngle(x, y) - 30;//扇形共60度，起始角度减去30度，保证手指在扇形中间角度
			invalidate();  
			break;

		default:
			break;
		}

    	
    	return super.onTouchEvent(event);
    }

    /**
     * 根据AC模式更新UI
     * @param mode
     */
    public void setACMode(int mode){
    	
    	mCurrentPosition = mode;
/*		SharedPreferences mShare = mContext.getSharedPreferences("skyworthauto_air_info", 0);
		SharedPreferences.Editor editor = mShare.edit(); 
		editor.putInt("ac_mode", mCurrentPosition);
		editor.commit();*/

		invalidate();  
		Log.d(TAG, "set windstyle == " + mCurrentPosition);
    }
      
      
    public synchronized int getMax() {  
        return max;  
    }  
  

    public synchronized void setMax(int max) {  
        if(max < 0){  
            throw new IllegalArgumentException("max not less than 0");
        }  
        this.max = max;  
    }  
  
 
    public synchronized int getProgress() {  
        return progress;  
    }  
  
 
    public synchronized void setProgress(int progress) {  
        if(progress < 0){  
            throw new IllegalArgumentException("progress not less than 0");
        }  
        if(progress > max){  
            progress = max;  
        }  
        if(progress <= max){  
            this.progress = progress;
            postInvalidate();
        }

    }  
      
      
/*    public int getCricleColor() {
        return roundColor;  
    }  
  
    public void setCricleColor(int cricleColor) {  
        this.roundColor = cricleColor;  
    }  
  
    public int getCricleProgressColor() {  
        return roundProgressColor;  
    }  
  
    public void setCricleProgressColor(int cricleProgressColor) {  
        this.roundProgressColor = cricleProgressColor;  
    }  
  
    public int getTextColor() {  
        return textColor;  
    }  
  
    public void setTextColor(int textColor) {  
        this.textColor = textColor;  
    }  
  
    public float getTextSize() {

        return textSize;  
    }  
  
    public void setTextSize(float textSize) {  
        this.textSize = textSize;  
    }  
  
    public float getRoundWidth() {  
        return roundWidth;  
    }  
  
    public void setRoundWidth(float roundWidth) {  
        this.roundWidth = roundWidth;  
    }  */
  
    
    
    public void setViewChangeListenner(CircleViewChangeListen l){
    	mViewChangeListenner = l;
    }
    
  
	public interface CircleViewChangeListen{
		public void viewChange(int position, boolean onresume);
	}

}
