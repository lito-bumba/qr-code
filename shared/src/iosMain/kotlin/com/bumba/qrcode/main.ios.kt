package com.bumba.qrcode

import androidx.compose.ui.window.ComposeUIViewController
import com.bumba.qrcode.qr_code.QRCodeHelperImpl
import com.bumba.qrcode.qr_code.QRCodeHelperIos

fun MainViewController(qrCodeHelperIos: QRCodeHelperIos) =
    ComposeUIViewController { App(QRCodeHelperImpl(qrCodeHelperIos)) }