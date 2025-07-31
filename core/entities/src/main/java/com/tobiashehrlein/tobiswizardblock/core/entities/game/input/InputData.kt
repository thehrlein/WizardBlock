package com.tobiashehrlein.tobiswizardblock.core.entities.game.input

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameRound

data class InputData(
    val inputModels: List<InputDataItem>,
    val inputType: InputType,
    val currentGameRound: GameRound
)
