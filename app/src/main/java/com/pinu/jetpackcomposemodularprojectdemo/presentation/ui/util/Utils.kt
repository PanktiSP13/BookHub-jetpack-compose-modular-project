package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.jetpackcomposemodularprojectdemo.R
import java.io.ByteArrayOutputStream


object Permissions {

    val imagePickerPermission =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) arrayOf(Manifest.permission.CAMERA)
        else arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
}


fun showToast(context: Context, message: String) {
    if (message.isNotEmpty()) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

fun showCustomToast(context: Context, toastMessage: ToastMessage) {
    val toastType = when (toastMessage.type) {
        ToastMessageType.SUCCESS -> Type.SUCCESS
        ToastMessageType.ERROR -> Type.ERROR
        ToastMessageType.WARNING -> Type.WARNING
        ToastMessageType.INFORMATION -> Type.INFORMATION
        else -> Type.INFORMATION
    }
    if (toastMessage.message.isNotEmpty()) {
        SnToast.Builder().context(context).type(toastType).message(toastMessage.message).build()
    }
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


fun getImageUri(context: Context, inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        inImage,
        "${context.getString(R.string.app_name)}/${System.currentTimeMillis()}", null
    )
    return Uri.parse(path)
}