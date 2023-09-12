package com.bumba.qrcode.presentation.screen_qr_viewer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.presentation.Screen
import com.bumba.qrcode.presentation.component.BackButton
import com.bumba.qrcode.presentation.component.CircularButton
import com.bumba.qrcode.presentation.component.QRCodeShimmer
import com.bumba.qrcode.presentation.component.iconShare
import com.bumba.qrcode.presentation.util.Platform
import com.bumba.qrcode.presentation.util.getPlatform
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeViewer(screenNavState: MutableState<Screen>) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    val screenViewerState = screenNavState.value as Screen.QrCodeViewerScreen

    LaunchedEffect(Unit) {
        delay(500)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(),
                        content = {
                            BackButton { screenNavState.value = Screen.MainScreen }
                        }
                    )
                },
                actions = {
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInHorizontally { it },
                        exit = slideOutHorizontally { it },
                        content = {
                            CircularButton(
                                imageVector = iconShare,
                                onClick = screenViewerState.onShare
                            )
                        }
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.padding(16.dp)
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White)
                    .border(BorderStroke(3.dp, Color.Black))
                    .size(300.dp)
            ) {
                if (isLoading) {
                    QRCodeShimmer()
                    return@Column
                }

                Image(
                    bitmap = screenViewerState.qrCodeImage,
                    contentScale = ContentScale.Fit,
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .fillMaxSize(if (getPlatform() == Platform.IOS) .8f else 1f)
                )
            }
        }
    }
}