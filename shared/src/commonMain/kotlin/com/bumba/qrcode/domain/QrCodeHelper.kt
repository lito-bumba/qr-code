package com.bumba.qrcode.domain

import androidx.compose.ui.graphics.ImageBitmap

interface QrCodeHelper {

    fun generate(text: String): ImageBitmap

    suspend fun share(imageBitmap: ImageBitmap)

}