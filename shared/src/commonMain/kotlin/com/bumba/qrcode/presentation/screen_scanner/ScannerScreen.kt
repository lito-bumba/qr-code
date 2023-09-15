package com.bumba.qrcode.presentation.screen_scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.permissions.compose.BindEffect

@Composable
fun QRCodeScannerScreen(
    viewModel: ScannerViewModel
) {
    BindEffect(viewModel.permissionsController)

    LaunchedEffect(Unit) {
        viewModel.onButtonClick()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "QR Code Scanner", style = MaterialTheme.typography.titleLarge)
    }
}