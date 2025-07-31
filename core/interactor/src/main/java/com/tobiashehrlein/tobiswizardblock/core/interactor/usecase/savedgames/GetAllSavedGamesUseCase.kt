package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.savedgames

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetAllSavedGamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, List<Game>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<Game>> {
        return gameRepository.getAllSavedGames()
    }
}
