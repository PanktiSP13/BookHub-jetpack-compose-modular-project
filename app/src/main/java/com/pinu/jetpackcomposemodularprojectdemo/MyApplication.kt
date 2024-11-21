package com.pinu.jetpackcomposemodularprojectdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {

        // Singleton instance using lazy initialization
        val instance : MyApplication by lazy { MyApplication() }
    }

}