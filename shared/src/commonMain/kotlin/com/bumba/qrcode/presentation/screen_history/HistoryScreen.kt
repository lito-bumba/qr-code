package com.bumba.qrcode.presentation.screen_history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.qrcode.presentation.Screen
import com.bumba.qrcode.presentation.component.BackButton
import com.bumba.qrcode.presentation.component.BottomSheetFromWish
import com.bumba.qrcode.presentation.util.toDateFormatted

@Composable
fun HistoryScreen(
    screenNavState: MutableState<Screen>,
    viewModel: HistoryViewModel
) {
    val state = viewModel.state.value
    BottomSheetFromWish(
        visible = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (state.isError) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        modifier = Modifier.size(120.dp),
                        tint = Color.Red
                    )
                    Text(
                        text = state.errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(48.dp))
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        modifier = Modifier.size(60.dp)
                            .clickable { viewModel.fetchAll() }
                    )
                    return@Box
                }

                Spacer(Modifier.height(64.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.qrCodes) { qrCode ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp)
                                .clickable {
                                    (screenNavState.value as Screen.HistoryScreen)
                                        .onGenerate(qrCode.info)
                                }
                        ) {
                            Text(
                                text = qrCode.createdAt.toDateFormatted(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    qrCode.id?.let { qrCodeId ->
                                        viewModel.deleteQrCode(qrCodeId)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "delete"
                                )
                            }
                        }
                        Divider()
                    }
                }
            }
            BackButton {
                screenNavState.value = Screen.MainScreen
            }
        }
    }
}