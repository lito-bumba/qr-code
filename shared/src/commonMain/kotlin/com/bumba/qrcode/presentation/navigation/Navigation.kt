package com.bumba.qrcode.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.presentation.screen_qr_generator.QRGeneratorScreen

@Composable
fun NavScreen(appModule: AppModule) {
    val navState: MutableState<Screen> = remember {
        mutableStateOf(Screen.QRCodeGeneratorScreen)
    }

    when (navState.value) {
        is Screen.QRCodeGeneratorScreen -> QRGeneratorScreen(appModule)
    }
}