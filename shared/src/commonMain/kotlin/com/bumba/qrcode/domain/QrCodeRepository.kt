package com.bumba.qrcode.domain

import kotlinx.coroutines.flow.Flow

interface QrCodeRepository {

    fun getQrCodes(): Flow<List<QrCode>>

    suspend fun insertQrCode(info: String)

    suspend fun deleteQrCode(id: Long)

}