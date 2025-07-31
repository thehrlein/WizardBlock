package com.tobiashehrlein.tobiswizardblock.core.entities.statistics

import java.time.DayOfWeek

data class GameDayStatisticsData(
    val gameDays: Map<DayOfWeek, Int>
)
