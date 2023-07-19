package com.bumba.qrcode.view.screen_qr_generator

import androidx.compose.runtime.mutableStateOf
import com.bumba.qrcode.qr_code.QRCodeHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QRGeneratorViewModel(private val qrCodeHelper: QRCodeHelper) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    var state = mutableStateOf<QRGeneratorState>(QRGeneratorState.Loading)
        private set

    fun onGenerateQRCode(text: String) {
        state.value = QRGeneratorState.Loading

        viewModelScope.launch {
            delay(2000)
            try {
                val imageBitmap = qrCodeHelper.generate(text)
                state.value = QRGeneratorState.Success(text, imageBitmap)
            }catch (e: Exception) {
                state.value = QRGeneratorState.Error("${e.message}")
            }
        }
    }
}
