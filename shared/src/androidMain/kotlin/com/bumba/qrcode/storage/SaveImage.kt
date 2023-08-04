package com.bumba.qrcode.storage

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumba.qrcode.util.FileLocation
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun saveMediaToStorage(context: Context, bitmap: Bitmap) {
    val filename = "${System.currentTimeMillis()}.png"
    var fos: OutputStream? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, FileLocation.FOLDER_NAME.createFolder())
            }
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(FileLocation.FOLDER_NAME.createFolder())
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }
    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
}

private fun String.createFolder(): String {
    if (isBlank())
        return ""

    val folder = File(Environment.DIRECTORY_PICTURES, this)

    if (!folder.exists())
        folder.mkdirs()

    return folder.path
}
