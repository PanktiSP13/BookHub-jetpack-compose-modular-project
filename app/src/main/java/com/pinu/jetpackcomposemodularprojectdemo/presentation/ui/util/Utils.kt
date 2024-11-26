package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Permissions {

    val imagePickerPermission =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) arrayOf(android.Manifest.permission.CAMERA)
        else arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
}


fun showToast(context: Context, message: String) {
    if (message.isNotEmpty()) Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun checkIfAllPermissionGranted(context: Context,permissions: Array<String>): Boolean {
    return permissions.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

fun <T> readJsonFromAssets(context: Context,fileName: String): T {
    val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}

