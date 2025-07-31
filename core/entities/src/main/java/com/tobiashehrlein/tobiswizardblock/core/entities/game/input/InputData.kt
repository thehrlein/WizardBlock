package com.tobiashehrlein.tobiswizardblock.entities.game.input

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameRound

data class InputData(
    val inputModels: List<InputDataItem>,
    val inputType: InputType,
    val currentGameRound: GameRound
)
