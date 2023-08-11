package com.bumba.qrcode.di

import com.bumba.qrcode.data.SqlDelightRepositoryImpl
import com.bumba.qrcode.database.QrCodeDatabase
import com.bumba.qrcode.domain.QRCodeHelperIos
import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.domain.QrCodeHelperImpl
import com.bumba.qrcode.domain.QrCodeRepository

actual class AppModule(
    private val qrCodeHelperIos: QRCodeHelperIos
) {
    actual val qrCodeRepository: QrCodeRepository by lazy {
        SqlDelightRepositoryImpl(
            db = QrCodeDatabase(
                driver = DataDriverFactory().create()
            )
        )
    }
    actual val qrCodeHelper: QrCodeHelper by lazy {
        QrCodeHelperImpl(qrCodeHelperIos)
    }
}