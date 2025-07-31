package com.tobiashehrlein.tobiswizardblock.old.utils.helper

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

object DateHelper {

    fun getGameStartDate(millis: Long): String {
        val date = Date(millis)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        val formattedTime = timeFormat.format(date)
        return "$formattedDate\n$formattedTime"
    }

    fun getGameDay(millis: Long): String {
        val date = Date(millis)
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getDayOfWeek(millis: Long): DayOfWeek {
        val instant = Instant.ofEpochMilli(millis)
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().dayOfWeek
    }
}
