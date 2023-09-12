package com.bumba.qrcode.presentation.screen_history

import com.bumba.qrcode.domain.QrCodeModel

data class HistoryState(
    val isError: Boolean = false,
    val errorMessage: String = "",
    val qrCodes: List<QrCodeModel> = emptyList()
)