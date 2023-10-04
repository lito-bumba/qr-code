package com.bumba.qrcode.presentation.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toImageBitmap(): ImageBitmap {
    return Bitmap.makeFromImage(
        Image.makeFromEncoded(this)
    ).asComposeImageBitmap()
}