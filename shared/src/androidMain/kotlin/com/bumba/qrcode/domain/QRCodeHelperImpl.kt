package com.bumba.qrcode.domain

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import com.bumba.qrcode.presentation.util.QRCodeSize
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.seiko.imageloader.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class QrCodeHelperImpl(private val context: Context) : QrCodeHelper {

    override fun generate(text: String): ImageBitmap {
        val writer = QRCodeWriter()
        val bitMatrix =
            writer.encode(text, BarcodeFormat.QR_CODE, QRCodeSize.WIDTH, QRCodeSize.HEIGHT)
        val sizeX = bitMatrix.width
        val sizeY = bitMatrix.height
        val bitmap = Bitmap.createBitmap(sizeX, sizeY, Bitmap.Config.RGB_565)
        for (x in 0 until sizeX) {
            for (y in 0 until sizeY) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap.asImageBitmap()
    }

    override suspend fun share(imageBitmap: ImageBitmap) {
        withContext(Dispatchers.Main) {
            val intentShare = getIntentShare(imageBitmap)
            context.startActivity(Intent.createChooser(intentShare, null))
        }
    }

    private suspend fun getIntentShare(imageBitmap: ImageBitmap): Intent {
        val bitmap = imageBitmap.asAndroidBitmap()
        return withContext(Dispatchers.IO) {
            Intent().apply {
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
    }
}

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