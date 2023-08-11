package com.bumba.qrcode.di

import android.content.Context
import com.bumba.qrcode.database.QrCodeDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DataDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            QrCodeDatabase.Schema,
            context,
            "qrcode.db"
        )
    }

}