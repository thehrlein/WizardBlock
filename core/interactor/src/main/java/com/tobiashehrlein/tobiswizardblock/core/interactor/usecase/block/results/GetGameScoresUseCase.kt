package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetGameScoresUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, GameScoreData>() {

    override suspend fun execute(parameters: Game): AppResult<GameScoreData> {
        return gameRepository.getGameScores(parameters)
    }
}
