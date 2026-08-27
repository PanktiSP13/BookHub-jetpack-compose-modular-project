package com.pinu.jetpackcomposemodularprojectdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {

        // Singleton instance using lazy initialization
        private var instance : MyApplication?= null

        fun getInstance() = instance ?: synchronized(this){ MyApplication() }
    }

    override fun onCreate() {
        super.onCreate()
        if(instance == null){
            instance = this
        }
    }

}