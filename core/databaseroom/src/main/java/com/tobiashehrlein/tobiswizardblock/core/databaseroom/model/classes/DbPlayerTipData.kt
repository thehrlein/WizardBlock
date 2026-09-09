package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes

data class DbPlayerTipData(
    val playerName: String,
    val tip: Int,
    val correctedCauseOfCloudCard: Boolean,
    val cloudCardCorrectionCount: Int = 0,
    val cloudCardCorrectionSteps: List<Int>? = null
)
