package com.tobiashehrlein.tobiswizardblock.core.entities.game.general

import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType

data class GameRound(
    val round: Int,
    val playerTipData: List<PlayerTipData>?,
    val playerResultData: List<PlayerResultData>?,
    val trumpType: TrumpType
) {

    val inputTypeForThisRound: InputType?
        get() = when {
            roundCompleted -> null
            playerTipData == null -> InputType.TIPP
            else -> InputType.RESULT
        }

    val roundCompleted: Boolean
        get() = !playerResultData.isNullOrEmpty()
}
