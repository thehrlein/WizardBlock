package com.tobiashehrlein.tobiswizardblock.entities.game.input

data class InputDataItem(
    val type: InputType,
    val player: String,
    val isDealer: Boolean,
    val currentRound: Int,
    val cards: Int,
    var userInput: Int = 0
)
