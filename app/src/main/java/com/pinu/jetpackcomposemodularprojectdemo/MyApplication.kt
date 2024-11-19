package com.pinu.jetpackcomposemodularprojectdemo

import android.app.Application


class MyApplication : Application() {

    companion object {

        // Singleton instance using lazy initialization
        val instance : MyApplication by lazy { MyApplication() }
    }

}