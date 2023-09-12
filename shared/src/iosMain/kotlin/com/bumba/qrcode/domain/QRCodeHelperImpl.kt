package com.bumba.qrcode.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.presentation.util.QRCodeSize
import com.bumba.qrcode.presentation.util.toImageBitmap
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIWindow

interface QRCodeHelperIos {
    fun generate(text: String, width: Int, height: Int): UIImage
}

class QrCodeHelperImpl(
    private val qrCode: QRCodeHelperIos
) : QrCodeHelper {

    private var uiImage: UIImage? = null

    override fun generate(text: String): ImageBitmap {
        uiImage = qrCode.generate(text, QRCodeSize.WIDTH, QRCodeSize.HEIGHT)
        return uiImage?.toImageBitmap() ?: ImageBitmap(0, 0)
    }

    override suspend fun share(imageBitmap: ImageBitmap) {
        val window = UIApplication.sharedApplication.windows.last() as? UIWindow
        val currentViewController = window?.rootViewController
        val activityViewController = UIActivityViewController(
            activityItems = listOf(uiImage, "Qr Code"),
            applicationActivities = null
        )
        currentViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null,
        )
    }
}