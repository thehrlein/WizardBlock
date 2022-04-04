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
        get() = gameRounds
            .lastOrNull { it.playerResultData != null }
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
     * Last round which was played, can be the current if not completed
     * Can be null if no round played yet
     */
    val lastPlayedGameRound: GameRound?
        get() = gameRounds.lastOrNull()

    val inputType: InputType
        get() = lastPlayedGameRound?.let {
            if (it.roundCompleted) {
                InputType.TIPP
            } else {
                it.inputTypeForThisRound
            }
        } ?: InputType.TIPP

    val currentRoundNumber: Int
        get() = currentGameRound?.round ?: FIRST_ROUND

    /**
     * Returns current round or null if game is finished
     * Creates the next round if needed
     */
    val currentGameRound: GameRound?
        get() = when {
            lastRoundNumber == maxRound && lastPlayedGameRound?.roundCompleted == true -> null
            lastPlayedGameRound?.roundCompleted?.not() == true -> lastPlayedGameRound
            else -> GameRound(gameRounds.size + 1, null, null, TrumpType.Unselected)
        }

    val gameFinished: Boolean
        get() = lastRoundNumber == maxRound && lastPlayedGameRound?.roundCompleted == true //currentGameRound == null

    private val lastRoundNumber: Int?
        get() = lastPlayedGameRound?.round
}

