package com.bumba.qrcode.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bumba.qrcode.qr_code.QRCodeHelper
import com.bumba.qrcode.view.screen_qr_generator.QRGeneratorScreen
import com.bumba.qrcode.view.screen_qr_generator.QRGeneratorViewModel

@Composable
fun NavScreen(qrCode: QRCodeHelper) {
    val navState: MutableState<Screen> = remember { mutableStateOf(Screen.QRCodeGeneratorScreen) }
    val viewModel = QRGeneratorViewModel(qrCode)

    when (navState.value) {
        is Screen.QRCodeGeneratorScreen -> QRGeneratorScreen(viewModel)
    }
}
