package com.bumba.qrcode.presentation.screen_scanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumba.qrcode.presentation.Screen

@Composable
fun QRCodeScannerScreen(
    screenNavState: MutableState<Screen>
) {
    QRCodeScanner(Modifier.fillMaxSize()) {

    }
}