package com.bumba.qrcode.di

import android.content.Context
import com.bumba.qrcode.data.SqlDelightRepositoryImpl
import com.bumba.qrcode.database.QrCodeDatabase
import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.domain.QrCodeHelperImpl
import com.bumba.qrcode.domain.QrCodeRepository

actual class AppModule(context: Context) {

    actual val qrCodeRepository: QrCodeRepository by lazy {
        SqlDelightRepositoryImpl(
            db = QrCodeDatabase(driver = DataDriverFactory(context).create())
        )
    }

    actual val qrCodeHelper: QrCodeHelper = QrCodeHelperImpl(context)
}