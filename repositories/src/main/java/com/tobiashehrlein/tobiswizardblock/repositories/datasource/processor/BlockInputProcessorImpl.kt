package com.tobiashehrlein.tobiswizardblock.repositories.datasource.processor

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameRound
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor.BlockInputProcessor
import com.tobiashehrlein.tobiswizardblock.old.utils.helper.BlockHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val FIRST_ROUND = 1
private const val DEFAULT_PLAYER_INPUT = 0

class BlockInputProcessorImpl : BaseDatasource, BlockInputProcessor {

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> =
        withContext(Dispatchers.Default) {
            safeCall {
                val game = inputValidityData.game
                when (game.inputType) {
                    InputType.TIPP -> {
                        when {
                            game.gameInfo.gameSettings.tipsEqualStitches -> true
                            game.currentRoundNumber == FIRST_ROUND &&
                                game.gameInfo.gameSettings.tipsEqualStitchesFirstRound -> true
                            else -> inputValidityData.inputSum != game.currentRoundNumber
                        }
                    }
                    InputType.RESULT -> when {
                        game.gameInfo.gameSettings.anniversaryVersion ->
                            inputValidityData.inputSum <= game.currentRoundNumber
                        else -> inputValidityData.inputSum == game.currentRoundNumber
                    }
                }
            }
        }

    override suspend fun getBlockInputModels(game: Game): AppResult<InputData> =
        withContext(Dispatchers.IO) {
            safeCall {
                val round = game.currentGameRound ?: error("current round could not be determined")
                val inputType = round.inputTypeForThisRound ?: InputType.TIPP
                InputData(
                    inputModels = getCorrectOrderInputList(game, inputType, round),
                    inputType = inputType,
                    currentGameRound = round
                )
            }
        }

    private fun getCorrectOrderInputList(
        game: Game,
        inputType: InputType,
        gameRound: GameRound
    ): List<InputDataItem> {
        val inputModels = game.gameInfo.players.mapIndexed { index: Int, name: String ->
            InputDataItem(
                type = inputType,
                player = name,
                isDealer = BlockHelper.isDealer(
                    gameRound.round,
                    game.maxRound,
                    game.gameInfo.players.size,
                    index + 1
                ),
                currentRound = game.currentRoundNumber,
                cards = game.currentRoundNumber,
                userInput = gameRound.playerTipData?.firstOrNull { it.playerName == name }?.tip
                    ?: DEFAULT_PLAYER_INPUT
            )
        }

        val newList = ArrayList(inputModels)
        while (newList.last().isDealer.not()) {
            val lastOne = newList.removeLast()
            newList.add(0, lastOne)
        }

        return newList
    }
}
