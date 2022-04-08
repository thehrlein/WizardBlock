package com.tobiashehrlein.tobiswizardblock.repositories.datasource.processor

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CalculateResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItem
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItemData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockName
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockPlaceholder
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockResult
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockRound
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor.BlockResultsProcessor
import com.tobiashehrlein.tobiswizardblock.old.utils.extensions.isEven
import com.tobiashehrlein.tobiswizardblock.old.utils.helper.BlockHelper
import kotlin.math.abs

private const val POINTS_IF_CORRECT = 20
private const val POINTS_EACH_STITCH = 10

class BlockResultsProcessorImpl : BaseDatasource, BlockResultsProcessor {

    override suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>> =
        safeCall {
            val playerResultData = mutableListOf<PlayerResultData>()
            calculateResultData.resultData.forEach { resultData ->
                var points = 0
                val result = resultData.result

                points += if (resultData.tip == result) {
                    result * POINTS_EACH_STITCH
                } else {
                    -abs(resultData.tip - result) * POINTS_EACH_STITCH
                }
                if (resultData.tip == result) {
                    points += POINTS_IF_CORRECT
                }

                playerResultData.add(
                    PlayerResultData(
                        playerName = resultData.playerName,
                        result = result,
                        difference = points,
                        total = resultData.previousTotal + points
                    )
                )
            }
            playerResultData
        }

    override suspend fun generateBlockResultModels(game: Game): AppResult<BlockItemData> =
        safeCall {
            val blockItems = mutableListOf<BlockItem>()
            blockItems.add(
                BlockPlaceholder(
                    game.currentGameRound?.trumpType ?: TrumpType.Unselected
                )
            )
            val players = game.gameInfo.players
            val currentRoundNumber = game.currentRoundNumber
            val lastRoundWithTotal = game.lastPlayedGameRound?.let {
                when {
                    it.playerResultData.isNullOrEmpty().not() -> it
                    it.round >= 2 -> game.gameRounds[game.gameRounds.size - 2]
                    else -> null
                }
            }
            val currentTotals =
                lastRoundWithTotal?.playerResultData?.sortedByDescending { it.total }

            blockItems.addAll(
                players.mapIndexed { index: Int, name: String ->
                    BlockName(
                        name = name,
                        isDealer = BlockHelper.isDealer(
                            currentRoundNumber,
                            game.maxRound,
                            players.size,
                            index + 1
                        ),
                        isCurrentLeader = currentTotals?.firstOrNull()?.total != null &&
                            currentTotals.firstOrNull { it.playerName == name }?.total == currentTotals.firstOrNull()?.total
                    )
                }
            )
            game.gameRounds.forEach { round ->
                val roundPlayerTipData = round.playerTipData ?: return@forEach
                // do not show round number after trump selected and without any tips made
                blockItems.add(
                    BlockRound(
                        round = round.round,
                        colorized = round.round.isEven()
                    )
                )
                blockItems.addAll(
                    players.map { player ->
                        val resultData = round.playerResultData?.firstOrNull { it.playerName == player }
                        BlockResult(
                            player = player,
                            round = round.round,
                            tip = roundPlayerTipData.first { it.playerName == player }.tip,
                            result = resultData?.result,
                            difference = resultData?.difference,
                            total = resultData?.total,
                            colorized = round.round.isEven()
                        )
                    }
                )
            }

            val columnCount = game.gameInfo.players.size * 2 + 1
            BlockItemData(
                items = blockItems,
                inputType = game.inputType,
                columnCount = columnCount
            )
        }

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> =
        safeCall {
            val gameFinished = game.gameFinished
            val players = game.gameInfo.players

            val lastRound = game.gameRounds.lastOrNull { it.playerResultData != null }
            val scores = mutableListOf<GameScore>()
            players.mapIndexed { index, name ->
                Pair(
                    name,
                    lastRound?.playerResultData?.firstOrNull { it.playerName == name }?.total ?: 0
                )
            }.groupBy { it.second }
                .entries
                .sortedByDescending { it.key }
                .mapIndexed { index: Int, entry: Map.Entry<Int, List<Pair<String, Int>>> ->
                    scores.addAll(
                        entry.value.map {
                            GameScore(index + 1, it.first, it.second)
                        }
                    )
                }

            GameScoreData(
                gameFinished = gameFinished,
                winnerAlreadyShown = game.gameInfo.gameFinished,
                results = scores
            )
        }
}
