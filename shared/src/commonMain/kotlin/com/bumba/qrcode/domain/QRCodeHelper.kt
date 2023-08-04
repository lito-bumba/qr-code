package com.bumba.qrcode.domain

import androidx.compose.ui.graphics.ImageBitmap

interface QRCodeHelper {

    fun generate(text: String): ImageBitmap

    suspend fun share(imageBitmap: ImageBitmap)

    suspend fun save(imageBitmap: ImageBitmap)

}