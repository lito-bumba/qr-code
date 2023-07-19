package com.bumba.qrcode

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.bumba.qrcode.qr_code.QRCodeHelper
import com.bumba.qrcode.util.QRCodeSize

@Composable
fun App(qrCodeHelper: QRCodeHelper) {
    MaterialTheme {
        val qrCode = qrCodeHelper.generate("QR Code App", QRCodeSize.WIDTH, QRCodeSize.HEIGHT)
        Image(
            bitmap = qrCode,
            contentDescription = null
        )
    }
}