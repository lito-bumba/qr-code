package com.bumba.qrcode.data

import com.bumba.qrcode.domain.QrCodeModel
import database.QrCodeEntity

fun QrCodeEntity.toQrCode(): QrCodeModel {
    return QrCodeModel(
        id = id,
        info = info,
        createdAt = createdAt
    )
}