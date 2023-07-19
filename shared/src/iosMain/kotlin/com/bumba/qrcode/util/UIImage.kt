package com.bumba.qrcode.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.get
import org.jetbrains.skia.*
import platform.CoreFoundation.CFDataGetBytePtr
import platform.CoreFoundation.CFDataGetLength
import platform.CoreFoundation.CFRelease
import platform.CoreGraphics.*
import platform.UIKit.UIImage

private fun UIImage.toSkiaImage(): Image? {
    val imageRef = CGImageCreateCopyWithColorSpace(
        image = this.CGImage,
        space = CGColorSpaceCreateDeviceRGB()
    ) ?: return null

    val width = CGImageGetWidth(imageRef).toInt()
    val height = CGImageGetHeight(imageRef).toInt()

    val bytesPerRow = CGImageGetBytesPerRow(imageRef)
    val data = CGDataProviderCopyData(CGImageGetDataProvider(imageRef))
    val bytePointer = CFDataGetBytePtr(data)
    val length = CFDataGetLength(data)
    val alphaInfo = CGImageGetAlphaInfo(imageRef)

    val alphaType = when (alphaInfo) {
        CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst, CGImageAlphaInfo.kCGImageAlphaPremultipliedLast -> ColorAlphaType.PREMUL
        CGImageAlphaInfo.kCGImageAlphaFirst, CGImageAlphaInfo.kCGImageAlphaLast -> ColorAlphaType.UNPREMUL
        CGImageAlphaInfo.kCGImageAlphaNone, CGImageAlphaInfo.kCGImageAlphaNoneSkipFirst, CGImageAlphaInfo.kCGImageAlphaNoneSkipLast -> ColorAlphaType.OPAQUE
        else -> ColorAlphaType.UNKNOWN
    }

    val byteArray = ByteArray(length.toInt()) { index ->
        bytePointer!![index].toByte()
    }
    CFRelease(data)
    CGImageRelease(imageRef)

    return Image.makeRaster(
        imageInfo = ImageInfo(
            width = width,
            height = height,
            colorType = ColorType.RGBA_8888,
            alphaType = alphaType,
            colorSpace = ColorSpace.sRGB
        ),
        bytes = byteArray,
        rowBytes = bytesPerRow.toInt(),
    )
}

fun UIImage.toImageBitmap(): ImageBitmap {
    val skiaImage = this.toSkiaImage() ?: return ImageBitmap(1, 1)
    return skiaImage.toComposeImageBitmap()
}