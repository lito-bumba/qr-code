package com.bumba.qrcode.presentation.screen_main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.presentation.Screen
import com.bumba.qrcode.presentation.component.CircularButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    screenNavState: MutableState<Screen>,
    qrCodeViewModel: QRGeneratorViewModel
) {
    val scope = rememberCoroutineScope()
    var inputText by rememberSaveable { mutableStateOf("") }
    var isInputTextError by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ScreenTopBar {
                screenNavState.value = Screen.HistoryScreen { info ->
                    val qrCodeImage = qrCodeViewModel.onGenerate(info)
                    screenNavState.value =
                        Screen.QrCodeViewerScreen(qrCodeImage) {
                            qrCodeViewModel.onShare(qrCodeImage)
                        }
                }
            }
        },
        containerColor = Color.White
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    isInputTextError = inputText.isBlank()
                },
                label = { Text("texto", style = MaterialTheme.typography.titleMedium) },
                textStyle = MaterialTheme.typography.titleMedium,
                singleLine = false,
                supportingText = {
                    if (isInputTextError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Texto não pode ser vázio",
                            textAlign = TextAlign.Start,
                            color = Color.Red
                        )
                    }
                },
                isError = isInputTextError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (inputText.isBlank()) {
                        isInputTextError = inputText.isBlank()
                        return@Button
                    }

                    scope.launch {
                        when (val qrCodeState =
                            qrCodeViewModel.onNewQRCode(inputText).await()) {
                            is QRGeneratorState.Error -> {}
                            is QRGeneratorState.Success -> {
                                screenNavState.value =
                                    Screen.QrCodeViewerScreen(qrCodeState.qrCode) {
                                        qrCodeViewModel.onShare(qrCodeState.qrCode)
                                    }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(25),
                modifier = Modifier.height(50.dp).width(150.dp)
            ) {
                Text(
                    text = "Generate",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenTopBar(onHistoryClick: () -> Unit) {
    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "QR Code",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it }
            ) {
                CircularButton(
                    imageVector = Icons.Default.History,
                    onClick = onHistoryClick
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.padding(16.dp)
    )
}