package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pinu.domain.entities.BookItemDataModel

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


fun getBookList(context: Context): List<BookItemDataModel> {
    return readJsonFromAssets(context, "books.json")
}
