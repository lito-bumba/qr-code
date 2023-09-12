package com.bumba.qrcode.presentation.screen_main

import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.domain.QrCodeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class QRGeneratorViewModel(
    private val qrCodeHelper: QrCodeHelper,
    private val qrCodeRepository: QrCodeRepository
) : ViewModel() {
    fun onNewQRCode(info: String): Deferred<QRGeneratorState> {
        return viewModelScope.async {
            try {
                qrCodeRepository.insertQrCode(info)
                val imageBitmap = onGenerate(info)
                QRGeneratorState.Success(info, imageBitmap)
            } catch (e: Exception) {
                QRGeneratorState.Error("${e.message}")
            }
        }
    }

    fun onGenerate(info: String): ImageBitmap {
        return qrCodeHelper.generate(info)
    }

    fun onShare(picture: ImageBitmap) {
        viewModelScope.launch {
            qrCodeHelper.share(picture)
        }
    }
}