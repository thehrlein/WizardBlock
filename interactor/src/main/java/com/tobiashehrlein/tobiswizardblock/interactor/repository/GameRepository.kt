package com.tobiashehrlein.tobiswizardblock.interactor.repository

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

interface GameRepository {

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun getBlockInputData(game: Game): AppResult<InputData>

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

    suspend fun insertRound(roundData: InsertRoundData): AppResult<Unit>

    suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>>

    suspend fun getBlockResults(game: Game): AppResult<BlockItemData>

    suspend fun getGameScores(game: Game): AppResult<GameScoreData>

    suspend fun setGameFinished(gameId: Long): AppResult<Unit>

    suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit>

    suspend fun getAllSavedGames(): AppResult<List<Game>>

    suspend fun removeGameFromSavedGames(gameId: Long): AppResult<Unit>

    suspend fun removeAllGamesFromSavedGames(): AppResult<Unit>

    suspend fun deleteAllGame(): AppResult<Unit>
}
