package com.skyworthauto.aircondition.views;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.LogUtil;
import com.tao.customviewlearndemo.R;


/**
 * Vertical seekbar 
 * @author Young
 * 2015/5/7
 */


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ExSeekBar extends View {
	private String TAG = "ExSeekBar";

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	private Drawable mBackground;
	private Drawable mProgressDrawable;
	private Drawable mClipedDrawable;
	private Drawable mThumb;
	private int mThumbOffset;

	private boolean mBackgroundIsDirty = true;
	private boolean mProgressDrawableIsDirty = true;

	private int mMax = 100;
	private int mProgress;

	private int mOrientation = HORIZONTAL;
	private int mMinWidth = 100;
	private int mMinHeight;
	private int mThickness;

	private OnExSeekBarChangeListener mOnExSeekBarChangeListener;

	private PointF mTempPoint = new PointF();

	public ExSeekBar(Context context) {
		super(context);
		LogUtil.i(TAG,"ExSeekBar 1 ");
		initWithDefaultStyle();
	}

	public ExSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		LogUtil.i(TAG,"ExSeekBar 2");
	}

	public ExSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LogUtil.i(TAG,"ExSeekBar 3 ");
//		initWithDefaultStyle();

		// don't use style get the attrs again
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ExSeekBar);
		int val = 0;
		Drawable drawable = null;
		for (int i = 0; i < a.getIndexCount(); i++) {
			int index = a.getIndex(i);
			
			if(index == R.styleable.ExSeekBar_android_thumb){
				drawable = a.getDrawable(index);
				setThumb(drawable);
			}else if(index == R.styleable.ExSeekBar_android_thumbOffset){
				val = a.getDimensionPixelSize(index, 0);
				setThumbOffset(val);
			}else if(index == R.styleable.ExSeekBar_android_orientation){
				val = a.getInteger(index, HORIZONTAL);
				setOrientation(val);
			}			
			else if(index == R.styleable.ExSeekBar_android_minWidth){
				val = a.getDimensionPixelSize(index, 0);
				setMinWidth(val);
			}

			else if(index == R.styleable.ExSeekBar_android_minHeight){
				val = a.getDimensionPixelSize(index, 0);
				setMinHeight(val);
			}else if(index == R.styleable.ExSeekBar_android_progressDrawable){
				drawable = a.getDrawable(index);
				setProgressDrawable(drawable);
			}

			else if(index == R.styleable.ExSeekBar_android_background){
				drawable = a.getDrawable(index);
				setBackground(drawable);
			}


			else if(index == R.styleable.ExSeekBar_android_max){
				val = a.getInteger(index, 100);
				setMax(val);
			}


			else if(index == R.styleable.ExSeekBar_android_progress){
				val = a.getInteger(index, 0);
				setProgress(val);
			}

			else if(index == R.styleable.ExSeekBar_android_thickness){
				val = a.getDimensionPixelSize(index, 0);
				setThickness(val);
			}

		}
		a.recycle();
	}

	private void initWithDefaultStyle() {
		TypedArray a = getContext().obtainStyledAttributes(null,
				R.styleable.ExSeekBar, android.R.attr.seekBarStyle, 0);

		Drawable drawable = a
				.getDrawable(R.styleable.ExSeekBar_android_progressDrawable);
		setProgressDrawable(drawable);
		drawable = a.getDrawable(R.styleable.ExSeekBar_android_thumb);
		setThumb(drawable);
		a.recycle();
	}

	public void setMinWidth(int w) {
		mMinWidth = w;

		invalidate();
	}

	public void setMinHeight(int h) {
		mMinHeight = h;

		invalidate();
	}

	public void setMax(int max) {
		if (max < 0) {
			max = 0;
		} else if (max > 10000) {
			max = 10000;
		}
		mMax = max;
	}

	/**
	 * 根据progress 更新UI
	 * @param progress
	 */
	public void setProgress(int progress) {
		if (progress < 0) {
			progress = 0;
		} else if (progress > mMax) {
			progress = mMax;
		}
		setProgress(progress, false);
	}

	private void setProgress(int progress, boolean fromUser) {
		boolean changed = mProgress != progress;
		mProgress = progress;

		invalidate();
		if (mOnExSeekBarChangeListener != null && changed) {
			mOnExSeekBarChangeListener.onProgressChanged(this, progress,
					fromUser);
		}
	}

	public void setProgressDrawable(Drawable drawable) {
		if (mProgressDrawable != drawable) {
			mProgressDrawable = drawable;
			mProgressDrawableIsDirty = true;

			if (mProgressDrawable instanceof LayerDrawable) {
				LayerDrawable d = (LayerDrawable) mProgressDrawable;
				Drawable bd = d.findDrawableByLayerId(android.R.id.background);
				if (bd != null) {
					setBackground(bd);
				}
			}

			invalidate();
		}
	}

	public void setThumb(Drawable drawable) {
		mThumb = drawable;
		resolveDrawable(mThumb, false);

		invalidate();
	}

	public void setThumbOffset(int offset) {
		mThumbOffset = offset;

		invalidate();
	}

	public void setThickness(int thickness) {
		mThickness = thickness;

		invalidate();
	}

	public void setOrientation(int orientation) {
		mOrientation = orientation;

		invalidate();
	}

	public void setOnExSeekBarChangeListener(OnExSeekBarChangeListener listener) {
		mOnExSeekBarChangeListener = listener;
	}

	public OnExSeekBarChangeListener getOnExSeekBarChangeListener() {
		return mOnExSeekBarChangeListener;
	}

	public int getProgress() {
		return mProgress;
	}

	public int getMax() {
		return mMax;
	}

	public int getMinWidth() {
		return mMinWidth;
	}

	public int getMinHeight() {
		return mMinHeight;
	}

	public int getThumbOffset() {
		return mThumbOffset;
	}

	public Drawable getThumb() {
		return mThumb;
	}

	public Drawable getProgressDrawable() {
		return mProgressDrawable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(!isEnabled())
			return true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mOnExSeekBarChangeListener != null) {
				mOnExSeekBarChangeListener.onStartTrackingTouch(this);
			}
		case MotionEvent.ACTION_MOVE:
			setPressed(true);
			updateProgress(event);
			break;
		case MotionEvent.ACTION_UP:
			setPressed(false);
			updateProgress(event);
			if (mOnExSeekBarChangeListener != null) {
				mOnExSeekBarChangeListener.onStopTrackingTouch(this);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			setPressed(false);
			if (mOnExSeekBarChangeListener != null) {
				mOnExSeekBarChangeListener.onStopTrackingTouch(this);
			}
			break;
		}
		postInvalidate();
		return true;
	}

	private void updateProgress(MotionEvent event) {
		final int pl = getPaddingLeft();
		final int pr = getPaddingRight();
		final int pt = getPaddingTop();
		final int pb = getPaddingBottom();
		final int thumbWidth = mThumb == null ? 0 : mThumb.getIntrinsicWidth();
		int progress = 0;
		int[] location = new int[2];
		getLocationOnScreen(location);
		if (mOrientation == HORIZONTAL) {
			progress = (int) ((event.getRawX() - location[0] - pl - thumbWidth / 2)
					/ (getWidth() - pl - pr - thumbWidth) * mMax);
		} else {
			int tmp = getHeight() - pt - pb - thumbWidth;
			progress = (int) ((tmp - (event.getRawY() - location[1]) + pt + thumbWidth / 2)
					/ tmp * mMax);
		}

		if (progress < 0) {
			progress = 0;
		} else if (progress > mMax) {
			progress = mMax;
		}

		if (progress != mProgress) {
			setProgress(progress, true);
		}
	}

	@Override
	public void setBackground(Drawable background) {
		if (mBackground != background) {
			mBackground = background;
			mBackgroundIsDirty = true;

			invalidate();
		}
	}

	@Override
	public Drawable getBackground() {
		return mBackground;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int w = getPaddingLeft() + getPaddingRight();
		int h = getPaddingTop() + getPaddingBottom();

		final Drawable b = getBackground();
		int bHeight = b == null ? 0 : b.getIntrinsicHeight();
		int bWidth = b == null ? 0 : b.getIntrinsicWidth();
		int pHeight = mProgressDrawable == null ? 0 : mProgressDrawable
				.getIntrinsicHeight();
		int tHeight = mThumb == null ? 0 : mThumb.getIntrinsicHeight();
		h += Math
				.max(Math.max(bHeight, pHeight), Math.max(tHeight, mMinHeight));
		w += Math.max(bWidth, mMinWidth);

		if (mOrientation == VERTICAL) {
			// exchange the width and height
			w += h;
			h = w - h;
			w = w - h;
		}

		setMeasuredDimension(resolveSizeAndState(w, widthMeasureSpec, 0),
				resolveSizeAndState(h, heightMeasureSpec, 0));
	}

	public static int resolveSizeAndState(int size, int measureSpec,
			int childMeasuredState) {
		int result = size;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (specMode) {
		case MeasureSpec.UNSPECIFIED:
			result = size;
			break;
		case MeasureSpec.AT_MOST:
			if (specSize < size) {
				result = specSize | MEASURED_STATE_TOO_SMALL;
			} else {
				result = size;
			}
			break;
		case MeasureSpec.EXACTLY:
			result = specSize;
			break;
		}
		return result | (childMeasuredState & MEASURED_STATE_MASK);
	}
	private int tempDrawPb;
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();

		final int pl = getPaddingLeft();
		final int pr = getPaddingRight();
		final int pt = getPaddingTop();
		final int pb = getPaddingBottom();
		final int width = getWidth();
		final int height = getHeight();

		if (mOrientation == VERTICAL) {
			canvas.rotate(-90);
			canvas.translate(-height, 0);
		}
		canvas.translate(pl, pt);

		// step 1. draw background
		LogUtil.i(TAG,"mBackgroundIsDirty: "+mBackgroundIsDirty);
		if (mBackground != null) {
			canvas.save();

			if (mBackgroundIsDirty) {
				resolveDrawable(mBackground, true);
				mBackgroundIsDirty = false;
			}
			getCenterOffset(mBackground, mTempPoint);
			canvas.translate(mTempPoint.x, mTempPoint.y);
			mBackground.draw(canvas);

			canvas.restore();
		}
		tempDrawPb = mProgress;
		if(tempDrawPb > 90 ) {
			tempDrawPb = 90;
		}else if(tempDrawPb < 5){
			tempDrawPb = 5;
		}

		// step 2. draw progress
		if (mProgressDrawable != null) {
			canvas.save();

			if (mProgressDrawableIsDirty) {
				resolveProgressDrawable();
				mProgressDrawableIsDirty = false;
			}
			mClipedDrawable.setLevel(10000 * tempDrawPb / mMax);
			getCenterOffset(mClipedDrawable, mTempPoint);
			canvas.translate(mTempPoint.x, mTempPoint.y);
			mClipedDrawable.draw(canvas);

			canvas.restore();
		}

		// step 3. draw thumb
		if (mThumb != null) {
			canvas.save();

			getCenterOffset(mThumb, mTempPoint);
			mTempPoint.x = -mThumb.getIntrinsicWidth();
			if (mOrientation == HORIZONTAL) {
				mTempPoint.x += width - pl - pr;
			} else {
				mTempPoint.x += height - pt - pb;
			}
			mTempPoint.x = mTempPoint.x * tempDrawPb / mMax;
			LogUtil.i(TAG,"onDraw tempDrawPb = "+tempDrawPb+",mTempPoint.x = "+mTempPoint.x+", mTempPoint.y = "+mTempPoint.y);
			canvas.translate(mTempPoint.x, mTempPoint.y);
			mThumb.draw(canvas);

			canvas.restore();
		}

		canvas.restore();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		Drawable d = mThumb;
		if (d != null && d.isStateful()) {
			d.setState(getDrawableState());
		}
	}

	private void resolveProgressDrawable() {
		Drawable d = mProgressDrawable;

		if (d instanceof LayerDrawable) {
			d = ((LayerDrawable) d)
					.findDrawableByLayerId(android.R.id.progress);
		}

		if (d instanceof ScaleDrawable) {
			mClipedDrawable = d;
		} else {
			// GradientDrawable
			if (d.getIntrinsicWidth() < 0 || d.getIntrinsicHeight() < 0) {
				resolveDrawable(d, true);
			} else {
				resolveDrawable(d, false);
				d = getFillDrawable(d);
			}
			mClipedDrawable = new ClipDrawable(d, Gravity.LEFT,
					ClipDrawable.HORIZONTAL);
		}
		resolveDrawable(mClipedDrawable, true);
	}

	private Drawable getFillDrawable(Drawable drawable) {
		int w = mThumb == null ? 0 : -mThumb.getIntrinsicWidth();
		if (mOrientation == HORIZONTAL) {
			w += getWidth() - getPaddingLeft() - getPaddingRight();
		} else {
			w += getHeight() - getPaddingTop() - getPaddingBottom();
		}

		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, drawable.getIntrinsicHeight(),
				config);
		Canvas c = new Canvas(bitmap);
		if (drawable instanceof NinePatchDrawable) {
			drawable.draw(c);
		} else {
			int count = 0;
			if (mOrientation == HORIZONTAL) {
				count = w / drawable.getIntrinsicWidth() + 1;
			} else {
				count = w / drawable.getIntrinsicHeight() + 1;
			}
			for (int i = 0; i < count; i++) {
				drawable.draw(c);
				c.translate(drawable.getIntrinsicWidth(), 0);
			}
		}
		BitmapDrawable ret = new BitmapDrawable(getResources(), bitmap);
		resolveDrawable(ret, false);
		return ret;
	}

	private void resolveDrawable(Drawable drawable, boolean fill) {
		if (drawable != null) {
			if (fill || drawable instanceof NinePatchDrawable) {
				int w = getWidth() - getPaddingLeft() - getPaddingRight();
				int h = getHeight() - getPaddingTop() - getPaddingBottom();

				if (mOrientation == VERTICAL) {
					w += h;
					h = w - h;
					w = w - h;
				}

				if (drawable instanceof NinePatchDrawable) {
					h = drawable.getIntrinsicHeight();
				}

				if (mThumb != null) {
					w -= mThumb.getIntrinsicWidth() - mThumbOffset;
				}

				drawable.setBounds(0, 0, w, mThickness == 0 ? h : mThickness);
			} else {
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
			}
		}
	}

	private void getCenterOffset(Drawable drawable, PointF out) {
		int dw = drawable.getBounds().width();
		int dh = drawable.getBounds().height();
		int width = getWidth() - getPaddingLeft() - getPaddingRight();
		int height = getHeight() - getPaddingTop() - getPaddingBottom();

		if (mOrientation == VERTICAL) {
			width += height;
			height = width - height;
			width = width - height;
		}

		out.x = (width - dw) / 2.0f;
		out.y = (height - dh) / 2.0f;
	}

	public interface OnExSeekBarChangeListener {

		void onProgressChanged(ExSeekBar seekBar, int progress, boolean fromUser);

		void onStartTrackingTouch(ExSeekBar seekBar);

		void onStopTrackingTouch(ExSeekBar seekBar);
	}

}
