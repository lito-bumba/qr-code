package com.bumba.qrcode.di

import com.squareup.sqldelight.db.SqlDriver

expect class DataDriverFactory {
    fun create(): SqlDriver
}