package com.tobiashehrlein.tobiswizardblock.entities.game.input

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game

data class CheckInputValidityData(
    val game: Game,
    val bombPlayed: Boolean,
    val inputDataItems: List<InputDataItem>
)
