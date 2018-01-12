package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/12.
 */

public class CircleRelativelayout extends RelativeLayout {
    public CircleRelativelayout(Context context) {
        this(context,null);
    }

    public CircleRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_circle_rl,this);
    }


}
