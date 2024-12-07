package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    fun getRealPathFromURI(context: Context,uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }

    fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
        // Create a temporary file in the cache directory
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        file.createNewFile()

        // Write the bitmap to the file
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return file
    }

}