package com.tobiashehrlein.tobiswizardblock.entities.game.input

data class InputDataItem(
    val type: InputType,
    val player: String,
    val isDealer: Boolean,
    val currentRound: Int,
    val cards: Int,
    val cloudCardPlayed: Boolean = false,
    val minInput : Int = 0,
    var userInput: Int = 0
)
