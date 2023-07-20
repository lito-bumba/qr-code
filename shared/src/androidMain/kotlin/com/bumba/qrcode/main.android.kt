package com.bumba.qrcode

import androidx.compose.runtime.Composable
import com.bumba.qrcode.qr_code.QRCodeHelperImpl
import com.bumba.qrcode.view.navigation.NavScreen

@Composable fun MainView() = NavScreen(QRCodeHelperImpl())
