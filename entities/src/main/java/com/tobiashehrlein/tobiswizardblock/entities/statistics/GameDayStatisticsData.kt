package com.tobiashehrlein.tobiswizardblock.entities.statistics

import java.time.DayOfWeek

data class GameDayStatisticsData(
    val gameDays: Map<DayOfWeek, Int>
)
