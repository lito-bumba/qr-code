package com.bumba.qrcode.domain

import androidx.compose.ui.graphics.ImageBitmap

interface QrCodeHelper {

    fun generate(text: String): ImageBitmap

    fun read(image: ImageBitmap): String

    suspend fun share(imageBitmap: ImageBitmap)

}