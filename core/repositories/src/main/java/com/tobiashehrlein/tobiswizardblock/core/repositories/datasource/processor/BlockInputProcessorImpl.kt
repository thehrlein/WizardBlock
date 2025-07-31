package com.tobiashehrlein.tobiswizardblock.core.repositories.datasource.processor

import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.processor.BlockInputProcessor
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameRound
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.old.utils.helper.BlockHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val FIRST_ROUND = 1
private const val DEFAULT_PLAYER_TIP = 0
private const val DEFAULT_MIN_INPUT = 0
private const val MIN_INPUT_IF_CLOUD_PLAYED = 1
private const val DEFAULT_PLAYER_INPUT_IF_CLOUD_PLAYED = 1

class BlockInputProcessorImpl : BaseDatasource, BlockInputProcessor {

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> =
        withContext(Dispatchers.Default) {
            safeCall {
                val game = inputValidityData.game
                val anniversaryVersion = game.gameInfo.gameSettings.anniversaryVersion
                val inputSum = inputValidityData.inputDataItems.sumOf { it.userInput }
                when (game.inputType) {
                    InputType.TIPP -> {
                        when {
                            game.gameInfo.gameSettings.tipsEqualStitches -> true
                            game.currentRoundNumber == FIRST_ROUND &&
                                game.gameInfo.gameSettings.tipsEqualStitchesFirstRound -> true
                            else -> inputSum != game.currentRoundNumber
                        }
                    }
                    InputType.RESULT -> when {
                        anniversaryVersion -> {
                            val allMatchingMinInput = inputValidityData.inputDataItems.all {
                                it.userInput >= it.minInput
                            }
                            when {
                                !allMatchingMinInput -> false
                                inputValidityData.bombPlayed ->
                                    inputSum == game.currentRoundNumber - 1
                                else -> inputSum == game.currentRoundNumber
                            }
                        }
                        else -> inputSum == game.currentRoundNumber
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
            val playerTipData = gameRound.playerTipData?.firstOrNull { it.playerName == name }
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
                minInput = if (playerTipData?.correctedCauseOfCloudCard == true) {
                    MIN_INPUT_IF_CLOUD_PLAYED
                } else {
                    DEFAULT_MIN_INPUT
                },
                cloudCardPlayed = playerTipData?.correctedCauseOfCloudCard == true,
                userInput = getUserInput(
                    playerTipData,
                    game.gameInfo.gameSettings.anniversaryVersion
                )
            )
        }

        val newList = ArrayList(inputModels)
        while (newList.last().isDealer.not()) {
            val lastOne = newList.removeAt(newList.lastIndex)
            newList.add(0, lastOne)
        }

        return newList
    }

    private fun getUserInput(playerTipData: PlayerTipData?, anniversaryVersion: Boolean): Int {
        return when {
            anniversaryVersion &&
                playerTipData?.correctedCauseOfCloudCard == true &&
                playerTipData.tip == DEFAULT_PLAYER_TIP -> DEFAULT_PLAYER_INPUT_IF_CLOUD_PLAYED
            else -> playerTipData?.tip ?: DEFAULT_PLAYER_TIP
        }
    }
}
