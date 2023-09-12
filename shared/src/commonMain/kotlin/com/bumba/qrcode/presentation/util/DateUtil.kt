package com.bumba.qrcode.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toDateFormatted(): String {
    val date = Instant.fromEpochMilliseconds(this)
    val dateTime = date.toLocalDateTime(TimeZone.currentSystemDefault())
    return dateTime.toString()
}