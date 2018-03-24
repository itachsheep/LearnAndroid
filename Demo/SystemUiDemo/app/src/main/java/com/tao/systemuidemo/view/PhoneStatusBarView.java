/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tao.systemuidemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;


public class PhoneStatusBarView extends FrameLayout {
    private static final String TAG = "PhoneStatusBarView";
//    private static final boolean DEBUG = PhoneStatusBar.DEBUG;
    private static final boolean DEBUG_GESTURES = true;

//    PhoneStatusBar mBar;
    int mScrimColor;
    float mSettingsPanelDragzoneFrac;
    float mSettingsPanelDragzoneMin;

    boolean mFullWidthNotifications;
//    PanelView mFadingPanel = null;
//    PanelView mLastFullyOpenedPanel = null;
//    PanelView mNotificationPanel, mSettingsPanel;
//    private boolean mShouldFade;
//    private final PhoneStatusBarTransitions mBarTransitions;

    public PhoneStatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = getContext().getResources();
//        mScrimColor = res.getColor(R.color.notification_panel_scrim_color);
//        mSettingsPanelDragzoneMin = res.getDimension(R.dimen.settings_panel_dragzone_min);
//        try {
//            mSettingsPanelDragzoneFrac = res.getFraction(R.dimen.settings_panel_dragzone_fraction, 1, 1);
//        } catch (NotFoundException ex) {
//            mSettingsPanelDragzoneFrac = 0f;
//        }
//        mFullWidthNotifications = mSettingsPanelDragzoneFrac <= 0f;
//        mBarTransitions = new PhoneStatusBarTransitions(this);
    }







    @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        if (super.onRequestSendAccessibilityEvent(child, event)) {
            // The status bar is very small so augment the view that the user is touching
            // with the content of the status bar a whole. This way an accessibility service
            // may announce the current item as well as the entire content if appropriate.
            AccessibilityEvent record = AccessibilityEvent.obtain();
            onInitializeAccessibilityEvent(record);
            dispatchPopulateAccessibilityEvent(record);
            event.appendRecord(record);
            return true;
        }
        return false;
    }


}
