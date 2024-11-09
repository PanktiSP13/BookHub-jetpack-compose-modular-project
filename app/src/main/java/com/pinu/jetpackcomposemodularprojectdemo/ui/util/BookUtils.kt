package com.pinu.jetpackcomposemodularprojectdemo.ui.util

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, message: String) {
    if (message.isNotEmpty()) Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

