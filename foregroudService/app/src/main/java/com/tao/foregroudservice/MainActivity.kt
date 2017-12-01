package com.tao.foregroudservice

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_notification.setOnClickListener { view -> run{
            var intent = Intent("com.tao.my.service")
            startService(intent)
        } }
    }
}
