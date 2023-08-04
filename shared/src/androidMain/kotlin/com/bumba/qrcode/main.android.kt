package com.bumba.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.bumba.qrcode.qr_code.QRCodeHelperImpl
import com.bumba.qrcode.presentation.navigation.NavScreen

@Composable
fun MainView() = NavScreen(QRCodeHelperImpl(LocalContext.current))