package com.bumba.qrcode

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bumba.qrcode.qr_code.QRCodeHelper

@Composable
fun App(qrCodeHelper: QRCodeHelper) {
    MaterialTheme {
        val qrCode = qrCodeHelper.generate("QR Code App")
        Image(
            bitmap = qrCode,
            contentDescription = null
        )
    }
}