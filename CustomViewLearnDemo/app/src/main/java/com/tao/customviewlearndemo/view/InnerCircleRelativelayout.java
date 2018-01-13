package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/12.
 */

public class InnerCircleRelativelayout extends RelativeLayout {
    public InnerCircleRelativelayout(Context context) {
        this(context,null);
    }

    public InnerCircleRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_inner_circle_rl,this);
    }


}
