package com.bumba.qrcode.qr_code

import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.util.toImageBitmap
import platform.UIKit.UIImage

interface QRCodeHelperIos {
    fun generate(text: String): UIImage
}

class QRCodeHelperImpl(private val qrCode: QRCodeHelperIos): QRCodeHelper {

    override fun generate(text: String): ImageBitmap {
        return qrCode.generate(text).toImageBitmap()
    }
}