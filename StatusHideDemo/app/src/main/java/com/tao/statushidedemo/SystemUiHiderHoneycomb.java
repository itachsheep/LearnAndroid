package com.tao.statushidedemo;

import android.app.Activity;
import android.view.View;

/**
 * Created by SDT14324 on 2018/2/2.
 */

public class SystemUiHiderHoneycomb extends SystemUiHiderBase {
    private String TAG = SystemUiHiderHoneycomb.class.getSimpleName();
    /**
     * Flags for {@link View#setSystemUiVisibility(int)} to use when showing the
     * system UI.
     */
    private int mShowFlags;

    /**
     * Flags for {@link View#setSystemUiVisibility(int)} to use when hiding the
     * system UI.
     */
    private int mHideFlags;
    /**
     * Whether or not the system UI is currently visible. This is cached from
     * {@link android.view.View.OnSystemUiVisibilityChangeListener}.
     */
    private boolean mVisible = true;

    /**
     * Flags to test against the first parameter in
     * {@link android.view.View.OnSystemUiVisibilityChangeListener#onSystemUiVisibilityChange(int)}
     * to determine the system UI visibility state.
     */
    private int mTestFlags;


    protected SystemUiHiderHoneycomb(Activity activity, View anchorView, int flags) {
        super(activity, anchorView, flags);

        mShowFlags = View.SYSTEM_UI_FLAG_VISIBLE;
        mHideFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        mTestFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        LogUtils.i(TAG,"SystemUiHiderHoneycomb mFlags = "+Integer.toHexString(mFlags));
        if ((mFlags & FLAG_FULLSCREEN) != 0) {
            LogUtils.i(TAG,"SystemUiHiderHoneycomb 1 ... "+Integer.toHexString((mFlags & FLAG_FULLSCREEN)));
            // If the client requested fullscreen, add flags relevant to hiding
            // the status bar. Note that some of these constants are new as of
            // API 16 (Jelly Bean). It is safe to use them, as they are inlined
            // at compile-time and do nothing on pre-Jelly Bean devices.
            mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if ((mFlags & FLAG_HIDE_NAVIGATION) != 0) {
            LogUtils.i(TAG,"SystemUiHiderHoneycomb 2 ... "+Integer.toHexString((mFlags & FLAG_HIDE_NAVIGATION)));
            // If the client requested hiding navigation, add relevant flags.
            mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            mTestFlags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        LogUtils.i(TAG,"SystemUiHiderHoneycomb mShowFlags = "+Integer.toHexString(mShowFlags)
        +", mHideFlags = "+Integer.toHexString(mHideFlags)
        +", mTestFlags = "+Integer.toHexString(mTestFlags));
    }

    /** {@inheritDoc} */
    @Override
    public void setup() {
       // mAnchorView.setOnSystemUiVisibilityChangeListener(mSystemUiVisibilityChangeListener);
    }

    private View.OnSystemUiVisibilityChangeListener mSystemUiVisibilityChangeListener = new View.OnSystemUiVisibilityChangeListener() {
        @Override
        public void onSystemUiVisibilityChange(int vis) {
            // View.VISIBLE = 0;
            //View.INVISIBLE = 4;
            //View.GONE = 8;
            LogUtils.i(TAG,"onSystemUiVisibilityChange vis = "+Integer.toHexString(vis));
            // Test against mTestFlags to see if the system UI is visible.
            if ((vis & mTestFlags) != 0) {

                mVisible = false;

            } else {
                mAnchorView.setSystemUiVisibility(mShowFlags);


                // Trigger the registered listener and cache the visibility
                // state.
                //mOnVisibilityChangeListener.onVisibilityChange(true);
                mVisible = true;
            }
        }
    };

    /** {@inheritDoc} */
    @Override
    public boolean isVisible() {
        return mVisible;
    }

    /** {@inheritDoc} */
    @Override
    public void hide() {
        LogUtils.i(TAG,"hide mHideFlags = "+Integer.toHexString(mHideFlags));
        mAnchorView.setSystemUiVisibility(mHideFlags);
    }
    /** {@inheritDoc} */
    @Override
    public void show() {
        LogUtils.i(TAG,"show mShowFlags = "+Integer.toHexString(mShowFlags));
        mAnchorView.setSystemUiVisibility(mShowFlags);
    }

}
