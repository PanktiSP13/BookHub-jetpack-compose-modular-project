package com.pinu.jetpackcomposemodularprojectdemo.ui.util

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

fun readJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun getBookList(context: Context): List<BookItemDataModel> {
    val json = readJsonFromAssets(context, "books.json")
    val type = object : TypeToken<List<BookItemDataModel>>() {}.type
    return Gson().fromJson(json, type)
}

fun checkIfAllPermissionGranted(context: Context,permissions: Array<String>): Boolean {
    return permissions.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}