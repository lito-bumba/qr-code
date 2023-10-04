package com.bumba.qrcode.presentation.util

import androidx.compose.runtime.Composable

expect class ImagePickerFactory {

    @Composable
    actual fun createPicker(): ImagePicker
}