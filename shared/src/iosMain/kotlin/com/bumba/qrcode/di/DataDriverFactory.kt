package com.bumba.qrcode.di

import com.bumba.qrcode.database.QrCodeDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DataDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            QrCodeDatabase.Schema,
            "qrcode_db"
        )
    }
}