package com.tao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tao.customdialogdemo.R;


/**
 * Created by taow on 2017/6/19.
 */

public class AdjustDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int layoutResID;
    /**
     * 要监听的控件id
     */
    private int[] listenedItems;
    private OnDialogItemClickListener mListener;

    public AdjustDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_custom);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        setContentView(layoutResID);
        WindowManager windowManager = window.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.width = defaultDisplay.getWidth() / 2;
        layoutParams.height = defaultDisplay.getHeight();
        window.setAttributes(layoutParams);

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
