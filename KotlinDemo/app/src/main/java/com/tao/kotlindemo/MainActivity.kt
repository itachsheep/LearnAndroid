package com.tao.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_onclick01.requestFocus()
//        btn_onclick01.setOnClickListener { view -> btn_onclick01.text = "androidstarjack欢迎你"}
        btn_onclick01.setOnClickListener {
            view -> ToastUtils.showToast(view.context,"Hello Kotlin !!")
        }
    }

    override fun onStop() {
        super.onStop()
    }
}
