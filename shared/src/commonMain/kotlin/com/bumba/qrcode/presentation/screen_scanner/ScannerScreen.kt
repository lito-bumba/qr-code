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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.presentation.component.BackButton
import com.bumba.qrcode.presentation.component.BottomSheetFromWish
import com.bumba.qrcode.presentation.component.CircularButton
import com.bumba.qrcode.presentation.screen.QRCodeEvent
import com.bumba.qrcode.presentation.screen.QRCodeState
import com.bumba.qrcode.presentation.util.ImagePicker
import com.bumba.qrcode.presentation.util.rememberBackHandler

@Composable
fun ScannerScreen(
    imagePicker: ImagePicker,
    state: QRCodeState,
    onEvent: (QRCodeEvent) -> Unit
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(QRCodeEvent.OnPhotoPicked(imageBytes))
    }

    BottomSheetFromWish(
        visible = state.isScannerVisible,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            QRCodeScanner(Modifier.fillMaxSize()) {
                onEvent(QRCodeEvent.SetText(it))
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(
                    background = MaterialTheme.colorScheme.primary.copy(alpha = .8f),
                    iconColor = Color.White
                ) { onEvent(QRCodeEvent.BackToMain) }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight(.3f).fillMaxWidth()
                ) {
                    var isLightOn by rememberSaveable { mutableStateOf(false) }
                    CircularButton(
                        backgroundColor = if (!isLightOn)
                            MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                        else Color.White.copy(alpha = .5f),
                        imageVector = Icons.Filled.Lightbulb,
                        iconColor = Color.Black,
                        sizeIcon = 50.dp,
                        modifier = Modifier.size(80.dp)
                    ) {
                        isLightOn = !isLightOn
                        //onEvent(QRCodeEvent.SetText("QR Code Text result"))
                    }
                    Spacer(Modifier.width(32.dp))
                    CircularButton(
                        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = .3f),
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
                info = state.textResult
            ) {
                onEvent(QRCodeEvent.ClearResult)
            }
        }
    }

    rememberBackHandler {
        if (state.textResult.isNotBlank()) {
            onEvent(QRCodeEvent.SetText(""))
            return@rememberBackHandler
        }

        onEvent(QRCodeEvent.BackToMain)
    }
}