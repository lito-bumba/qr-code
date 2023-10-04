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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.presentation.Screen
import com.bumba.qrcode.presentation.component.BackButton
import com.bumba.qrcode.presentation.component.CircularButton
import com.bumba.qrcode.presentation.util.ImagePicker
import com.bumba.qrcode.presentation.util.rememberBitmapFromBytes

@Composable
fun QRCodeScannerScreen(
    screenNavState: MutableState<Screen>,
    imagePicker: ImagePicker
) {
    var textFromQrCode by rememberSaveable{ mutableStateOf("") }

    imagePicker.registerPicker { imageBytes ->

    }
    Box(modifier = Modifier.fillMaxWidth()) {
        QRCodeScanner(Modifier.fillMaxSize(), {})
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton {
                screenNavState.value = Screen.MainScreen
            }

            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red)
            ){
                Text(text = textFromQrCode, color = Color.Black)
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
    }
}