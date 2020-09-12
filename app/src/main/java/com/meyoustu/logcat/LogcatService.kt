package com.meyoustu.logcat

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger


class LogcatService : Service() {


    @Throws(IOException::class)
    fun readLog(): String {
        val logCatProc = Runtime.getRuntime().exec("logcat -d")
        val `is` = logCatProc.inputStream
        val sb = java.lang.StringBuilder()
        val buf = ByteArray(1024)
        var len: Int
        while (`is`.read(buf).also { len = it } != -1) {
            sb.append(String(buf, 0, len))
        }
        Log.e("======>", sb.toString())
        return sb.toString()
    }

    override fun onBind(p0: Intent?): IBinder? {
        if (null != mBinder) {
            return mBinder
        }
        return null
    }

    private val atomicInt = AtomicInteger(1)
    private var mBinder: MyBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.e("======>", "onCreate")
        mBinder = MyBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("======>", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyBinder : Binder() {
        fun doTask() {
            Log.e("======>", "doTask: ${atomicInt.getAndIncrement()}")
            readLog()
        }
    }
}
