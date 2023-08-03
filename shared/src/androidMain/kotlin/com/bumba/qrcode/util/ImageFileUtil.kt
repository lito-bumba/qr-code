package com.bumba.qrcode.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


suspend fun Bitmap.getUri(context: Context): Uri = withContext(Dispatchers.IO) {
    val sharedImagesDir = File(context.filesDir, "share_images")

    if (!sharedImagesDir.exists()) {
        sharedImagesDir.mkdirs()
    }

    val tempFileToShare: File = sharedImagesDir.resolve("share_picture.png")
    val outputStream = FileOutputStream(tempFileToShare)
    this@getUri.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.flush()

    FileProvider.getUriForFile(
        context,
        "com.bumba.qrcode.fileProvider",
        tempFileToShare
    )
}
