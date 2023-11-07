package com.bumba.qrcode.presentation.screen

import androidx.compose.ui.graphics.ImageBitmap

sealed interface QRCodeEvent {
    object BackToMain : QRCodeEvent
    object OpenScanner : QRCodeEvent

    data class Generate(val info: String, val isNew: Boolean = true) : QRCodeEvent
    object GetHistory : QRCodeEvent
    data class DeleteHistory(val qrCodeId: Long) : QRCodeEvent

    data class Scan(val imageBitmap: ImageBitmap) : QRCodeEvent
    object ClearResult : QRCodeEvent
    data class SetText(val text: String) : QRCodeEvent
    class OnPhotoPicked(val bytes: ByteArray) : QRCodeEvent

    data class Share(val imageBitmap: ImageBitmap) : QRCodeEvent
}