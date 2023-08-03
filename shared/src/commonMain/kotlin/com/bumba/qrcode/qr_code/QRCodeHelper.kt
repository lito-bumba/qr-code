package com.bumba.qrcode.qr_code

import androidx.compose.ui.graphics.ImageBitmap

interface QRCodeHelper {

    fun generate(text: String): ImageBitmap

    suspend fun share(imageBitmap: ImageBitmap)

}
