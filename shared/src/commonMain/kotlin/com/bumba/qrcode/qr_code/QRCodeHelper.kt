package com.bumba.qrcode.qr_code

import androidx.compose.ui.graphics.ImageBitmap

interface QRCodeHelper {

    fun generate(text: String, width: Int, height: Int): ImageBitmap
}