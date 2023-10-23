package com.bumba.qrcode.presentation.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberBackHandler(onBack: () -> Unit)