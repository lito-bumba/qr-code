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
import com.bumba.qrcode.presentation.util.toImageBitmap

@Composable
fun QRCodeScannerScreen(
    screenNavState: MutableState<Screen>,
    imagePicker: ImagePicker,
    qrCodeHelper: QrCodeHelper
) {
    var textFromQrCode by rememberSaveable { mutableStateOf("") }
    imagePicker.registerPicker { imageBytes ->
        textFromQrCode = qrCodeHelper.read(imageBytes.toImageBitmap())
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        QRCodeScanner(Modifier.fillMaxSize()) {
            textFromQrCode = it
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton {
                screenNavState.value = Screen.MainScreen
            }

            /*if (textFromQrCode.isNotBlank()) {
                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f)
                    ),
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(.4f)
                ) {
                    Text(
                        text = textFromQrCode,
                        color = Color.Black,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }*/

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
                    textFromQrCode = "QR Code Text result"
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
            textFromQrCode,
            textFromQrCode.isNotBlank()
        ) {
            textFromQrCode = ""
        }
    }
}