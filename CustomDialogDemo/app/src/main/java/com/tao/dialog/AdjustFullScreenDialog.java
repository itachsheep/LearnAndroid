package com.android.systemui.statusbar.c30;

import com.android.systemui.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;



/**
 * Created by taow on 2017/6/19.
 */

public class AdjustFullScreenDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int layoutResID;
    /**
     * 要监听的控件id
     */
    private int[] listenedItems;
    private OnDialogItemClickListener mListener;

    public AdjustDialog(Context context, int layoutResID, int[] listenedItems) {
//        super(context, R.style.dialog_custom);
        super(context, com.android.internal.R.style.Theme_Panel_Volume);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(layoutResID);
        Window window = getWindow();
        window.setGravity(Gravity.TOP);
        WindowManager windowManager = window.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.type = WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL;
        layoutParams.y = 0;
        layoutParams.width = defaultDisplay.getWidth() / 2;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        window.addFlags(LayoutParams.FLAG_FULLSCREEN);
       
        
        //
        for(int id: listenedItems){
            findViewById(id).setOnClickListener(this);
        }
    }

    public void setTextView(int resId,String text){
        ((TextView)findViewById(resId)).setText(text);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if(null != mListener){
            mListener.OnDialogItemClick(this,v);
        }
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener listener){
        mListener = listener;
    }

    public interface OnDialogItemClickListener {
        void OnDialogItemClick(AdjustDialog dialog, View itemView);
    }
}
