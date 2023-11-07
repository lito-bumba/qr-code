package com.bumba.qrcode.presentation.screen

import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.domain.QrCodeModel

data class QRCodeState(
    val qrCodes: List<QrCodeModel> = emptyList(),
    val imageBitmap: ImageBitmap? = null,
    val info: String = "",
    val isLoading: Boolean = true,
    val error: String = "",
    val inputText: String = "",
    val isInputTextError: Boolean = false,
    val isViewerVisible: Boolean = false,
    val textResult: String = "",
    val isHistoryVisible: Boolean = false,
    val isScannerVisible: Boolean = false
)