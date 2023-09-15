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
import com.bumba.qrcode.presentation.screen_scanner.QRCodeScannerScreen
import com.bumba.qrcode.presentation.screen_scanner.ScannerViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

sealed interface Screen {
    object MainScreen : Screen
    data class HistoryScreen(
        val onGenerate: (String) -> Unit
    ) : Screen

    data class QrCodeViewerScreen(
        val qrCodeImage: ImageBitmap,
        val onShare: () -> Unit
    ) : Screen

    object QRCodeScanner : Screen
}

@Composable
fun NavScreen(appModule: AppModule) {
    val screenNavState: MutableState<Screen> = remember { mutableStateOf(Screen.MainScreen) }
    val generator = getViewModel(
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
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val scannerViewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory { ScannerViewModel(factory.createPermissionsController()) }
    )

    when (screenNavState.value) {
        is Screen.QrCodeViewerScreen -> QRCodeViewer(screenNavState)
        is Screen.HistoryScreen -> HistoryScreen(screenNavState, historyViewModel)
        is Screen.QRCodeScanner -> QRCodeScannerScreen(scannerViewModel)
        else -> MainScreen(screenNavState, generator)
    }
}