package com.bumba.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import com.bumba.qrcode.qr_code.QRCodeHelperImpl
import com.bumba.qrcode.qr_code.QRCodeHelperIos
import com.bumba.qrcode.presentation.navigation.NavScreen

fun MainViewController(qrCodeHelperIos: QRCodeHelperIos) =
    ComposeUIViewController {
        IosScreen(qrCodeHelperIos)
    }


@Composable
fun IosScreen(qrCodeHelperIos: QRCodeHelperIos) {

    NavScreen(QRCodeHelperImpl(qrCodeHelperIos))
}