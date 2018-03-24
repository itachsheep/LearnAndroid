package com.tao.statushidedemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * Created by SDT14324 on 2018/3/19.
 */

public class MyDialog extends Dialog {


    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_window);
        Window mWindow = getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        mParams.type |= LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.flags |= LayoutParams.FLAG_LAYOUT_NO_LIMITS ;
        mParams.gravity |= Gravity.TOP;
        mParams.gravity = Gravity.CENTER;
        mParams.height = 800;
        mParams.width = 800;
        mWindow.setAttributes(mParams);
    }
}
