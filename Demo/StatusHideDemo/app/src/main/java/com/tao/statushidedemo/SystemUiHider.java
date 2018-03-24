package com.tao.statushidedemo;

import android.app.Activity;
import android.view.View;

/**
 * Created by SDT14324 on 2018/2/2.
 */

public abstract class SystemUiHider {
    /**
     * The activity associated with this UI hider object.
     */
    protected Activity mActivity;
    /**
     * The view on which {@link View#setSystemUiVisibility(int)} will be called.
     */
    protected View mAnchorView;
    /**
     * The current UI hider flags.
     *
     * @see #FLAG_FULLSCREEN
     * @see #FLAG_HIDE_NAVIGATION
     * @see #//FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES
     */

    protected int mFlags;

    public static final int FLAG_FULLSCREEN = 0x2;//WindowManager.LayoutParams.FLAG_FULLSCREEN =  0x00000400;
    public static final int FLAG_HIDE_NAVIGATION = FLAG_FULLSCREEN | 0x4;
    /**
     * When this flag is set, the
     * {@link android.view.WindowManager.LayoutParams#FLAG_LAYOUT_IN_SCREEN}
     * flag will be set on older devices, making the status bar "float" on top
     * of the activity layout. This is most useful when there are no controls at
     * the top of the activity layout.
     * <p>
     * This flag isn't used on newer devices because the
     * <a href="http://developer.android.com/design/patterns/actionbar.html">
     * action bar</a>, the most important structural element of an Android app,
     * should be visible and not obscured by the system UI.
     */
    public static final int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 0x1;
    protected SystemUiHider(Activity activity, View anchorView, int flags) {
        mActivity = activity;
        mAnchorView = anchorView;
        mFlags = flags;
    }


    /**
     * Creates and returns an instance of {@link SystemUiHider} that is
     * appropriate for this device. The object will be either a
     * {@link SystemUiHiderBase} or {@link SystemUiHiderHoneycomb} depending on
     * the device.
     *
     * @param activity
     *            The activity whose window's system UI should be controlled by
     *            this class.
     * @param anchorView
     *            The view on which {@link View#setSystemUiVisibility(int)} will
     *            be called.
     * @param flags
     *            Either 0 or any combination of {@link #FLAG_FULLSCREEN},
     *            {@link #FLAG_HIDE_NAVIGATION}, and
     *            {@link #//FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES}.
     */
    public static SystemUiHider getInstance(Activity activity, View anchorView, int flags) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//3.1
//            return new SystemUiHiderHoneycomb(activity, anchorView, flags);
//        }
//        else {
//            return new SystemUiHiderBase(activity, anchorView, flags);
//        }
        return new SystemUiHiderHoneycomb(activity, anchorView, flags);
    }

    /**
     * Sets up the system UI hider. Should be called from
     * {@link Activity#onCreate}.
     */
    public abstract void setup();

    /**
     * Registers a callback, to be triggered when the system UI visibility
     * changes.
     */
    public void setOnVisibilityChangeListener(OnVisibilityChangeListener listener) {
        if (listener == null) {
            listener = sDummyListener;
        }

        mOnVisibilityChangeListener = listener;
    }

    /**
     * The current visibility callback.
     */
    protected OnVisibilityChangeListener mOnVisibilityChangeListener = sDummyListener;

    /**
     * A dummy no-op callback for use when there is no other listener set.
     */
    private static OnVisibilityChangeListener sDummyListener = new OnVisibilityChangeListener() {
        @Override
        public void onVisibilityChange(boolean visible) {
        }
    };

    /**
     * A callback interface used to listen for system UI visibility changes.
     */
    public interface OnVisibilityChangeListener {
        /**
         * Called when the system UI visibility has changed.
         *
         * @param visible
         *            True if the system UI is visible.
         */
        public void onVisibilityChange(boolean visible);
    }

    /**
     * Toggle the visibility of the system UI.
     */
    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Returns whether or not the system UI is visible.
     */
    public abstract boolean isVisible();

    /**
     * Hide the system UI.
     */
    public abstract void hide();

    /**
     * Show the system UI.
     */
    public abstract void show();
}
