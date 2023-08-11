package com.bumba.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.domain.QrCodeHelperImpl
import com.bumba.qrcode.domain.QRCodeHelperIos
import com.bumba.qrcode.presentation.navigation.NavScreen

fun MainViewController(qrCodeHelperIos: QRCodeHelperIos) =
    ComposeUIViewController {
        NavScreen(
            AppModule(qrCodeHelperIos)
        )
    }