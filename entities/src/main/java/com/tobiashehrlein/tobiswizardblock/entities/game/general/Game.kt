package com.tobiashehrlein.tobiswizardblock.entities.game.general

import com.tobiashehrlein.tobiswizardblock.entities.game.input.Input
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import java.io.Serializable

private const val FIRST_ROUND = 1
private const val CARD_COUNT = 60

data class Game(
    val gameInfo: GameInfo,
    val gameRounds: List<GameRound>
) : Serializable {

    val maxRound: Int
        get() = CARD_COUNT / gameInfo.players.size

    val previousTotals: List<Input>
        get() = lastCompletedGameRound
            ?.playerResultData?.map {
                Input(
                    playerName = it.playerName,
                    input = it.total
                )
            }
            ?: gameInfo.players.map {
                Input(
                    playerName = it,
                    input = 0
                )
            }

    /**
     * Last completed game round
     */
    val lastCompletedGameRound: GameRound?
        get() = gameRounds.lastOrNull { it.roundCompleted }

    /**
     * Last non-completed game round
     */
    val lastNonCompletedGameRound: GameRound?
        get() = gameRounds.lastOrNull { !it.roundCompleted }

    val inputType: InputType
        get() = lastNonCompletedGameRound
            ?.inputTypeForThisRound
            ?: InputType.TIPP

    val currentRoundNumber: Int
        get() = currentGameRound?.round ?: FIRST_ROUND

    /**
     * Returns current round or null if game is finished
     * Creates the next round if needed
     */
    val currentGameRound: GameRound?
        get() = when {
            gameFinished -> null
            lastNonCompletedGameRound != null -> lastNonCompletedGameRound
            else -> GameRound(gameRounds.size + 1, null, null, TrumpType.Unselected)
        }

    val gameFinished: Boolean
        get() = gameInfo.gameFinished || lastCompletedGameRound?.round == maxRound

}
