package com.tao.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    var intents = Intent("com.tao.foregroud.service")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_onclick01.requestFocus()

//        btn_onclick01.setOnClickListener {
//            view -> ToastUtils.showToast(view.context,"Hello Kotlin !!")
//        }

        btn_onclick01.setOnClickListener {
            view -> run{ ToastUtils.showToast(view.context,"hello Kotlin")} }


        btn_start_service.setOnClickListener { view -> run {

            startService(intents)
            ToastUtils.showToast(view.context,"启动service服务！！")
        } }

        btn_stop_service.setOnClickListener { view -> run{
            stopService(intents)
        } }
    }

    private fun startMyService(){

    }

    override fun onStop() {
        super.onStop()
    }
}
