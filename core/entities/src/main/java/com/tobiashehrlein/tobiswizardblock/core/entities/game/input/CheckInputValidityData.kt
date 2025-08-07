package com.tobiashehrlein.tobiswizardblock.core.entities.game.input

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.Game

data class CheckInputValidityData(
    val game: Game,
    val bombPlayed: Boolean,
    val inputDataItems: List<InputDataItem>
)
