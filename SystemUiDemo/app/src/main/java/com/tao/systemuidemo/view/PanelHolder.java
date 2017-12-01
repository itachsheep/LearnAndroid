/*
 * Copyright (C) 2012 The Android Open Source Project
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
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class PanelHolder extends FrameLayout {
    public static final boolean DEBUG_GESTURES = true;

    private int mSelectedPanelIndex = -1;


    public PanelHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setChildrenDrawingOrderEnabled(true);
    }




    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mSelectedPanelIndex == -1) {
            return i;
        } else {
            if (i == childCount - 1) {
                return mSelectedPanelIndex;
            } else if (i >= mSelectedPanelIndex) {
                return i + 1;
            } else {
                return i;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }


}
