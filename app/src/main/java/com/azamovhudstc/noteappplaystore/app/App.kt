package com.azamovhudstc.noteappplaystore.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){

    companion object {
        lateinit var instance: App
    }
    override fun onCreate() {
        super.onCreate()
    }
}