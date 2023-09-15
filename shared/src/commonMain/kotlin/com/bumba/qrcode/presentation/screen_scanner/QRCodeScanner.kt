package com.bumba.qrcode.presentation.screen_scanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun QRCodeScanner(modifier: Modifier, onQrCodeScanned: (String) -> Unit)