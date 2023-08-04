package com.bumba.qrcode.qr_code

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.bumba.qrcode.storage.getIntentToShare
import com.bumba.qrcode.storage.saveMediaToStorage
import com.bumba.qrcode.util.QRCodeSize
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.seiko.imageloader.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class QRCodeHelperImpl(private val context: Context) : QRCodeHelper {

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
            val intentShare = async { getIntentToShare(context, imageBitmap) }
            context.startActivity(Intent.createChooser(intentShare.await(), null))
        }
    }

    override suspend fun save(imageBitmap: ImageBitmap) {
        saveMediaToStorage(
            context = context,
            bitmap = imageBitmap.asAndroidBitmap()
        )
    }
}
