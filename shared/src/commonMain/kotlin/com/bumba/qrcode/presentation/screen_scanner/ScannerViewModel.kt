package com.bumba.qrcode.presentation.screen_scanner

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class ScannerViewModel(
    val permissionsController: PermissionsController
) : ViewModel() {
    fun onButtonClick() {
        viewModelScope.launch {
            permissionsController.providePermission(Permission.CAMERA)
        }
    }
}