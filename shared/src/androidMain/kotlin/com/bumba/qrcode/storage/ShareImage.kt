package com.bumba.qrcode.storage

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun getIntentToShare(context: Context, imageBitmap: ImageBitmap): Intent {
    val bitmap = imageBitmap.asAndroidBitmap()

    return Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_STREAM,
            bitmap.getUri(context)
        )
        putExtra(Intent.EXTRA_TEXT, "QR Code")
        type = "image/png"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
}

private fun Bitmap.getUri(context: Context): Uri {
    val sharedImagesDir = File(context.filesDir, "share_images")

    if (!sharedImagesDir.exists()) {
        sharedImagesDir.mkdirs()
    }

    val tempFileToShare: File = sharedImagesDir.resolve("share_picture.png")
    val outputStream = FileOutputStream(tempFileToShare)
    this@getUri.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.flush()
    return FileProvider.getUriForFile(context, AUTHORITY_FILE_PROVIDER, tempFileToShare)
}

private const val AUTHORITY_FILE_PROVIDER = "com.bumba.qrcode.fileProvider"
