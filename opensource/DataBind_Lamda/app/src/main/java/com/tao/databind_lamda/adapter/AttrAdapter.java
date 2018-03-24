package com.tao.databind_lamda.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tao.databind_lamda.LogUtils;
import com.tao.databind_lamda.listener.ImageViewListener;

/**
 * Created by SDT14324 on 2017/10/20.
 */

public class AttrAdapter {

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
        if (oldPadding != newPadding) {
            view.setPadding(10,
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }

    @BindingAdapter({"imageUrl","error"})
    public static void loadImage(ImageView imageView, String url,Drawable errorDrawable){
        LogUtils.i("loadImage url: "+url+", error drawable: "+errorDrawable);
        Picasso.with(imageView.getContext()).load(url).error(errorDrawable).into(imageView);
    }

    @BindingAdapter({"items","itemView"})
    public static void changeText(View view,String mes,String mes2){
        ((Button)view).setText(mes+mes2);
    }

    @BindingAdapter({"onLoadMoreCommand"})
    public static void setImageViewListener(View view, final ImageViewListener listener){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.i("setImageViewListener onClick ...");
                if(listener != null){
                    LogUtils.i("setImageViewListener onClick ...111");
                    listener.testImageViewLisetener(view);
                }
            }
        });
    }
}
