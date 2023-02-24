package com.nmwilkinson.fullscreengesturenav

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidLogcatLogger.installOnDebuggableApp(this, LogPriority.VERBOSE)
    }
}