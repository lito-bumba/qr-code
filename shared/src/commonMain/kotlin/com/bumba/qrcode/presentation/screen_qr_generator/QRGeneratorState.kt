package com.bumba.qrcode.presentation.screen_qr_generator

import androidx.compose.ui.graphics.ImageBitmap

sealed interface QRGeneratorState {
    object Loading: QRGeneratorState
    data class Error(val message: String): QRGeneratorState
    data class Success(val text: String, val qrCode: ImageBitmap) : QRGeneratorState
}