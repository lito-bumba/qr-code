package com.bumba.qrcode.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.presentation.component.CircularButton
import com.bumba.qrcode.presentation.screen.HistoryScreen
import com.bumba.qrcode.presentation.screen.QRCodeEvent
import com.bumba.qrcode.presentation.screen.QRCodeViewModel
import com.bumba.qrcode.presentation.screen.QRCodeViewer
import com.bumba.qrcode.presentation.screen_scanner.ScannerScreen
import com.bumba.qrcode.presentation.util.ImagePicker
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(appModule: AppModule, imagePicker: ImagePicker) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val permissionsController = factory.createPermissionsController()
    BindEffect(permissionsController)
    val scope = rememberCoroutineScope()
    val viewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory {
            QRCodeViewModel(
                helper = appModule.qrCodeHelper,
                repository = appModule.qrCodeRepository
            )
        }
    )

    if (
        !viewModel.state.value.isHistoryVisible &&
        !viewModel.state.value.isScannerVisible
    ) {
        Scaffold(
            topBar = {
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
                                onClick = { viewModel.onEvent(QRCodeEvent.GetHistory) },
                                backgroundColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            },
            containerColor = Color.White.copy(alpha = .1f, red = .1f)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(all = 16.dp),
                ) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val state = viewModel.state.value
                    OutlinedTextField(
                        value = state.inputText,
                        onValueChange = { viewModel.onEvent(QRCodeEvent.SetText(it)) },
                        label = { Text("texto", style = MaterialTheme.typography.titleLarge) },
                        textStyle = MaterialTheme.typography.titleLarge,
                        singleLine = false,
                        supportingText = {
                            if (state.isInputTextError) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Texto não pode ser vázio",
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Red
                                )
                            }
                        },
                        isError = state.isInputTextError,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = MaterialTheme.colorScheme.primary,
                            textColor = Color.Black,
                            containerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    val focusManager = LocalFocusManager.current
                    Button(
                        onClick = {
                            if (state.isInputTextError) return@Button
                            keyboardController?.hide()
                            focusManager.clearFocus(true)
                            viewModel.onEvent(QRCodeEvent.Generate(state.inputText))
                        },
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.height(60.dp).fillMaxWidth()
                    ) {
                        Text(
                            text = "Generate",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxHeight(.3f),
                    verticalArrangement = Arrangement.Center
                ) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                try {
                                    permissionsController.providePermission(Permission.CAMERA)
                                    viewModel.onEvent(QRCodeEvent.OpenScanner)
                                } catch (deniedAlways: DeniedAlwaysException) {

                                } catch (denied: DeniedException) {

                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(90.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    QRCodeViewer(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
    HistoryScreen(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
    ScannerScreen(
        imagePicker = imagePicker,
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
}