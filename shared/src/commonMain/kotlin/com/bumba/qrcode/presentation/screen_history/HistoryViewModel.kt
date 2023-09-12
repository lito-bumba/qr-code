package com.bumba.qrcode.presentation.screen_history

import androidx.compose.runtime.mutableStateOf
import com.bumba.qrcode.domain.QrCodeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: QrCodeRepository
) : ViewModel() {
    var state = mutableStateOf(HistoryState())
        private set

    init {
        fetchAll()
    }

    fun fetchAll() {
        viewModelScope.launch {
            try {
                repository.getQrCodes().collect { qrCodeList ->
                    state.value = HistoryState(qrCodes = qrCodeList)
                }
            } catch (exception: Exception) {
                state.value = HistoryState(
                    isError = true,
                    errorMessage = "${exception.message}"
                )
            }
        }
    }

    fun deleteQrCode(id: Long) {
        viewModelScope.launch {
            repository.deleteQrCode(id)
        }
    }
}