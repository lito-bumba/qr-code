package com.bumba.qrcode.presentation.screen_qr_generator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.domain.QrCodeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QRGeneratorViewModel(
    private val qrCodeHelper: QrCodeHelper,
    private val qrCodeRepository: QrCodeRepository
): ViewModel() {
    private var _state = mutableStateOf<QRGeneratorState>(QRGeneratorState.Loading)
    val state: State<QRGeneratorState> = _state

    fun onGenerateQRCode(info: String) {
        _state.value = QRGeneratorState.Loading

        viewModelScope.launch {
            delay(2000)
            try {
                val imageBitmap = qrCodeHelper.generate(info)
                qrCodeRepository.insertQrCode(info)
                _state.value = QRGeneratorState.Success(info, imageBitmap)
            } catch (e: Exception) {
                _state.value = QRGeneratorState.Error("${e.message}")
            }
        }
    }

    fun onShare(picture: ImageBitmap) {
        viewModelScope.launch {
            qrCodeHelper.share(picture)
        }
    }
}