package com.czh.adapter

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ctx = this
    }

    companion object {
        lateinit var ctx: Application
    }
}