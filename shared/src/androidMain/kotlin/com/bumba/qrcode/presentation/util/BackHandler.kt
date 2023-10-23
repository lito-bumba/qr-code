package com.bumba.qrcode.presentation.util

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun rememberBackHandler(onBack: () -> Unit) {
    BackHandler(
        enabled = true,
        onBack = onBack
    )
}