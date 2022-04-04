package com.tobiashehrlein.tobiswizardblock.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CalculateResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItemData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.GameCache
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor.BlockInputProcessor
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor.BlockResultsProcessor
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository

class GameRepositoryImpl(
    private val gameCache: GameCache,
    private val blockInputProcessor: BlockInputProcessor,
    private val blockResultsProcessor: BlockResultsProcessor
) : GameRepository {

    override suspend fun getGame(gameId: Long): AppResult<Game> {
        return gameCache.getGame(gameId)
    }

    override suspend fun getBlockInputData(game: Game): AppResult<InputData> {
        return blockInputProcessor.getBlockInputModels(game)
    }

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> {
        return blockInputProcessor.checkInputsValidity(inputValidityData)
    }

    override suspend fun insertRound(roundData: InsertRoundData): AppResult<Unit> {
        return gameCache.insertRound(roundData)
    }

    override suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>> {
        return blockResultsProcessor.calculateResults(calculateResultData)
    }

    override suspend fun getBlockResults(game: Game): AppResult<BlockItemData> {
        return when (val result = blockResultsProcessor.generateBlockResultModels(game)) {
            is AppResult.Success -> result
            is AppResult.Error -> result
        }
    }

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> {
        return blockResultsProcessor.getGameScores(game)
    }

    override suspend fun setGameFinished(gameId: Long): AppResult<Unit> {
        return when (val result = gameCache.getGameInfo(gameId)) {
            is AppResult.Success -> {
                gameCache.insertGameInfo(
                    result.value.copy(
                        gameFinished = true
                    )
                ).map { Unit }
            }
            is AppResult.Error -> result
        }
    }

    override suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit> {
        return gameCache.removeRound(deleteRoundData)
    }

    override suspend fun getAllSavedGames(): AppResult<List<Game>> {
        return gameCache.getAllSavedGames()
    }

    override suspend fun deleteGame(gameId: Long): AppResult<Unit> {
        return gameCache.deleteGame(gameId)
    }

    override suspend fun deleteAllGame(): AppResult<Unit> {
        return gameCache.deleteALlGames()
    }
}