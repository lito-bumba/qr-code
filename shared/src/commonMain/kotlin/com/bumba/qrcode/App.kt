package com.bumba.qrcode

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.bumba.qrcode.qr_code.QRCodeHelper
import com.bumba.qrcode.view.screen_qr_generator.QRGeneratorScreen
import com.bumba.qrcode.view.screen_qr_generator.QRGeneratorViewModel

@Composable
fun App(qrCodeHelper: QRCodeHelper) {
    MaterialTheme {
        val viewModel = QRGeneratorViewModel(qrCodeHelper)
        QRGeneratorScreen(viewModel)
    }
}