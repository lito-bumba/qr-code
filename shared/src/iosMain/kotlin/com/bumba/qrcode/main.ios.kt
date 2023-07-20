package com.bumba.qrcode

import androidx.compose.ui.window.ComposeUIViewController
import com.bumba.qrcode.qr_code.QRCodeHelperImpl
import com.bumba.qrcode.qr_code.QRCodeHelperIos
import com.bumba.qrcode.view.navigation.NavScreen

fun MainViewController(qrCodeHelperIos: QRCodeHelperIos) =
    ComposeUIViewController {
        NavScreen(QRCodeHelperImpl(qrCodeHelperIos))
    }