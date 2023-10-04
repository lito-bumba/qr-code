package com.bumba.qrcode.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toDateFormatted(): String {
    val date = Instant.fromEpochMilliseconds(this)
    val dateTime = date.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.dayOfMonth.toTwoNumbers()}/${dateTime.monthNumber.toTwoNumbers()}/" +
            dateTime.year + " ${dateTime.hour.toTwoNumbers()}:${dateTime.minute.toTwoNumbers()}" +
            dateTime.second.toTwoNumbers()
}

private fun Int.toTwoNumbers(): String {
    if (this > 9)
        return this.toString()

    return "0$this"
}