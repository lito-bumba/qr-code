package com.bumba.qrcode.qr_code

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.util.QRCodeSize
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.seiko.imageloader.asImageBitmap

class QRCodeHelperImpl : QRCodeHelper {

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
}