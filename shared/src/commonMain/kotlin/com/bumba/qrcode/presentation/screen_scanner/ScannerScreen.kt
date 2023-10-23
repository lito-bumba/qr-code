package com.bumba.qrcode.presentation.screen_scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.presentation.Screen
import com.bumba.qrcode.presentation.component.BackButton
import com.bumba.qrcode.presentation.component.CircularButton
import com.bumba.qrcode.presentation.util.ImagePicker
import com.bumba.qrcode.presentation.util.rememberBackHandler
import com.bumba.qrcode.presentation.util.toImageBitmap
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun ScannerScreen(
    screenNavState: MutableState<Screen>,
    imagePicker: ImagePicker,
    qrCodeHelper: QrCodeHelper
) {
    val viewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory { ScannerViewModel(qrCodeHelper) }
    )

    imagePicker.registerPicker { imageBytes ->
        viewModel.readQrCode(imageBytes.toImageBitmap())
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        QRCodeScanner(Modifier.fillMaxSize()) {
            viewModel.setText(it)
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton {
                screenNavState.value = Screen.MainScreen
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight(.3f).fillMaxWidth()
            ) {
                var isLightOn by rememberSaveable { mutableStateOf(false) }
                CircularButton(
                    backgroundColor = if (!isLightOn) Color.Black.copy(alpha = .3f) else
                        Color.White.copy(alpha = .5f),
                    imageVector = Icons.Filled.Lightbulb,
                    iconColor = Color.Black,
                    sizeIcon = 50.dp,
                    modifier = Modifier.size(80.dp)
                ) {
                    isLightOn = !isLightOn
                    viewModel.setText("QR Code Text result")
                }
                Spacer(Modifier.width(32.dp))
                CircularButton(
                    backgroundColor = Color.Black.copy(alpha = .2f),
                    imageVector = Icons.Filled.FileUpload,
                    iconColor = Color.Black,
                    sizeIcon = 50.dp,
                    modifier = Modifier.size(80.dp)
                ) {
                    imagePicker.pickImage()
                }
            }
        }
        QRResultScreen(
            info = viewModel.textFromQrCode,
            isVisible = viewModel.textFromQrCode.isNotBlank()
        ) {
            viewModel.setText()
        }
    }

    rememberBackHandler {
        if (viewModel.textFromQrCode.isNotBlank()) {
            viewModel.setText("")
            return@rememberBackHandler
        }

        screenNavState.value = Screen.MainScreen
    }
}