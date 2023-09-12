package com.bumba.qrcode.data

import com.bumba.qrcode.database.QrCodeDatabase
import com.bumba.qrcode.domain.QrCodeModel
import com.bumba.qrcode.domain.QrCodeRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightRepositoryImpl(
    db: QrCodeDatabase
) : QrCodeRepository {

    private val queries = db.qrcodeQueries
    override fun getQrCodes(): Flow<List<QrCodeModel>> {
        return queries
            .getQrCodes()
            .asFlow()
            .mapToList().map { qrCodesEntities ->
                qrCodesEntities.map { qrCodeEntity ->
                    qrCodeEntity.toQrCode()
                }
            }
    }

    override suspend fun insertQrCode(info: String) {
        queries.insertQrCode(
            id = null,
            info = info,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteQrCode(id: Long) {
        queries.deleteQrCode(id)
    }
}