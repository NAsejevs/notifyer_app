package com.nasejevs.htmlfetcher

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

private const val TAG = "SERVICE"

class Fetcher : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "STARTING SERVICE")

        return super.onStartCommand(intent, flags, startId)
    }
}
