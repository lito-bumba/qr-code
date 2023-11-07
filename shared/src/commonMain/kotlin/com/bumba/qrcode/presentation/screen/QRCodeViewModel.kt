package com.bumba.qrcode.presentation.screen

import androidx.compose.runtime.mutableStateOf
import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.domain.QrCodeRepository
import com.bumba.qrcode.presentation.util.toImageBitmap
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QRCodeViewModel(
    private val helper: QrCodeHelper,
    private val repository: QrCodeRepository
) : ViewModel() {
    var state = mutableStateOf(QRCodeState())
        private set

    fun onEvent(event: QRCodeEvent) {
        when (event) {
            QRCodeEvent.BackToMain -> state.value = QRCodeState()

            is QRCodeEvent.SetText -> {
                state.value = state.value.copy(inputText = event.text)
            }

            is QRCodeEvent.Generate -> {
                state.value = QRCodeState(
                    isViewerVisible = true,
                    inputText = state.value.inputText
                )
                viewModelScope.launch {
                    if (event.isNew) repository.insertQrCode(event.info)
                    val imageBitmap = helper.generate(event.info)
                    delay(1000)
                    state.value = state.value.copy(
                        info = event.info,
                        imageBitmap = imageBitmap,
                        isLoading = false
                    )
                }
            }

            QRCodeEvent.OpenScanner -> state.value = QRCodeState(isScannerVisible = true)

            is QRCodeEvent.Scan -> {
                state.value = state.value.copy(textResult = helper.read(event.imageBitmap))
            }

            is QRCodeEvent.ClearResult -> {
                state.value = state.value.copy(textResult = "")
            }

            QRCodeEvent.GetHistory -> {
                state.value = QRCodeState(isHistoryVisible = true)
                viewModelScope.launch {
                    delay(1000)
                    repository.getQrCodes()
                        .collect { qrCodeList ->
                            state.value =
                                state.value.copy(
                                    qrCodes = qrCodeList.sortedByDescending { it.createdAt },
                                    isLoading = false
                                )
                        }
                }
            }

            is QRCodeEvent.DeleteHistory -> {
                viewModelScope.launch {
                    repository.deleteQrCode(event.qrCodeId)
                }
            }

            is QRCodeEvent.OnPhotoPicked -> {
                state.value = state.value.copy(imageBitmap = event.bytes.toImageBitmap())
                onEvent(QRCodeEvent.Scan(event.bytes.toImageBitmap()))
            }

            is QRCodeEvent.Share -> {
                viewModelScope.launch {
                    helper.share(event.imageBitmap)
                }
            }
        }
    }
}