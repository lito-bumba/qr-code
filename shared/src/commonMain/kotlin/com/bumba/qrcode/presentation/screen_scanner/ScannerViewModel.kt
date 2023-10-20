package com.bumba.qrcode.presentation.screen_scanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.domain.QrCodeHelper
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val qrCodeHelper: QrCodeHelper
) : ViewModel() {
    var textFromQrCode by mutableStateOf("")
        private set

    fun readQrCode(imageBitmap: ImageBitmap) {
        viewModelScope.launch {
            textFromQrCode = qrCodeHelper.read(imageBitmap)
        }
    }

    fun clearText() {
        textFromQrCode = ""
    }
}