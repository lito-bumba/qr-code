package com.bumba.qrcode.presentation.screen_scanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumba.qrcode.presentation.Screen
import dev.icerock.moko.permissions.compose.BindEffect

@Composable
fun QRCodeScannerScreen(
    screenNavState: MutableState<Screen>,
    viewModel: ScannerViewModel
) {
    BindEffect(viewModel.permissionsController)

    LaunchedEffect(Unit) {
        viewModel.onButtonClick()
    }

    QRCodeScanner(Modifier.fillMaxSize()){

    }
}