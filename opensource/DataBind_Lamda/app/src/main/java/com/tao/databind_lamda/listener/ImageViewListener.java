package com.tao.databind_lamda.listener;

import android.view.View;
import android.widget.Toast;

/**
 * Created by SDT14324 on 2017/10/20.
 */

public class ImageViewListener {

    public ImageViewListener imageViewListener = new ImageViewListener();
    public void testImageViewLisetener(View view){

        Toast.makeText(view.getContext(),"testImageViewListener!!",Toast.LENGTH_SHORT).show();
    }

    public void newImageViewLisetner(View view){

    }
}
