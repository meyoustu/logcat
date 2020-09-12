package com.meyoustu.logcat

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mMyBinder = p1 as LogcatService.MyBinder
            mMyBinder.doTask()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(MainActivity::class.java.name, p0.toString())
        }
    }

    private lateinit var mMyBinder: LogcatService.MyBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        bindService(Intent(this, LogcatService::class.java), mServiceConnection, BIND_AUTO_CREATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}