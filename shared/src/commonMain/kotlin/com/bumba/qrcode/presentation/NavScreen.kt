package com.bumba.qrcode.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.presentation.screen_history.HistoryScreen
import com.bumba.qrcode.presentation.screen_history.HistoryViewModel
import com.bumba.qrcode.presentation.screen_qr_viewer.QRCodeViewer
import com.bumba.qrcode.presentation.screen_main.MainScreen
import com.bumba.qrcode.presentation.screen_main.QRGeneratorViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

sealed interface Screen {
    object MainScreen : Screen
    data class HistoryScreen(
        val onGenerate: (String) -> Unit
    ) : Screen
    data class QrCodeViewerScreen(
        val qrCodeImage: ImageBitmap,
        val onShare: () -> Unit
    ) : Screen
}

@Composable
fun NavScreen(appModule: AppModule) {
    val screenNavState: MutableState<Screen> =
        remember { mutableStateOf(Screen.MainScreen) }

    val qrCodeViewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory {
            QRGeneratorViewModel(
                qrCodeHelper = appModule.qrCodeHelper,
                qrCodeRepository = appModule.qrCodeRepository
            )
        }
    )

    val historyViewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory { HistoryViewModel(appModule.qrCodeRepository) }
    )

    when (screenNavState.value) {
        is Screen.QrCodeViewerScreen -> QRCodeViewer(screenNavState)
        is Screen.HistoryScreen -> HistoryScreen(screenNavState, historyViewModel)
        else -> MainScreen(screenNavState, qrCodeViewModel)
    }
}