package com.tao.kotlindemo

import android.content.Context
import android.widget.Toast

/**
 * Created by SDT14324 on 2017/11/15.
 */
class ToastUtils {
    companion object {
        fun showToast(context: Context, mes: String) {
            Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
        }
    }
}