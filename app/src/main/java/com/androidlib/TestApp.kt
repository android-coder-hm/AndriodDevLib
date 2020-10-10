package com.androidlib

import android.app.Application
import com.developlib.ConfigSingleton

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ConfigSingleton.init(this, true)
    }
}