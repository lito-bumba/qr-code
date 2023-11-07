package com.bumba.qrcode.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumba.qrcode.presentation.component.BackButton
import com.bumba.qrcode.presentation.component.BottomSheetFromWish
import com.bumba.qrcode.presentation.component.CircularButton
import com.bumba.qrcode.presentation.component.QRCodeShimmer
import com.bumba.qrcode.presentation.component.iconShare
import com.bumba.qrcode.presentation.util.Platform
import com.bumba.qrcode.presentation.util.getPlatform
import com.bumba.qrcode.presentation.util.rememberBackHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeViewer(
    state: QRCodeState,
    onEvent: (QRCodeEvent) -> Unit
) {
    BottomSheetFromWish(
        visible = state.isViewerVisible,
        modifier = Modifier.fillMaxSize()
    ) {
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
                                BackButton { onEvent(QRCodeEvent.BackToMain) }
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
                                    onClick = {
                                        state.imageBitmap?.let { bitmap ->
                                            onEvent(QRCodeEvent.Share(bitmap))
                                        }
                                    }
                                )
                            }
                        )
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent),
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
                    if (state.isLoading) {
                        QRCodeShimmer()
                        return@Column
                    }

                    state.imageBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentScale = ContentScale.Fit,
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .fillMaxSize(if (getPlatform() == Platform.IOS) .8f else 1f)
                        )
                    }
                }
            }
        }
    }

    rememberBackHandler { onEvent(QRCodeEvent.BackToMain) }
}