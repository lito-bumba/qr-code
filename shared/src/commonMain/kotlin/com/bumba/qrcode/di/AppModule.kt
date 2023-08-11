package com.bumba.qrcode.di

import com.bumba.qrcode.domain.QrCodeHelper
import com.bumba.qrcode.domain.QrCodeRepository

expect class AppModule {
    val qrCodeHelper: QrCodeHelper
    val qrCodeRepository: QrCodeRepository
}