package com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CalculateResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItemData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult

interface BlockResultsProcessor {

    suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>>

    suspend fun generateBlockResultModels(game: Game): AppResult<BlockItemData>

    suspend fun getGameScores(game: Game): AppResult<GameScoreData>
}
