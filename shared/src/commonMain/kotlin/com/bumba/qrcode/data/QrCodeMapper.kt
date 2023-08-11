package com.bumba.qrcode.data

import com.bumba.qrcode.domain.QrCode
import database.QrCodeEntity

fun QrCodeEntity.toQrCode(): QrCode {
    return QrCode(
        id = id,
        info = info,
        createdAt = createdAt
    )
}