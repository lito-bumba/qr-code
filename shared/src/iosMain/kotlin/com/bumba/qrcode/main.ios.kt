package com.bumba.qrcode

import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.domain.QRCodeHelperIos
import com.bumba.qrcode.presentation.NavScreen
import com.bumba.qrcode.presentation.util.ImagePickerFactory

fun MainViewController(qrCodeHelperIos: QRCodeHelperIos) =
    ComposeUIViewController {
        NavScreen(
            AppModule(qrCodeHelperIos),
            ImagePickerFactory(LocalUIViewController.current).createPicker()
        )
    }