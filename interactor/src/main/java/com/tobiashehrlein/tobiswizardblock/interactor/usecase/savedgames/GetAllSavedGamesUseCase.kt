package com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetAllSavedGamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, List<Game>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<Game>> {
        return gameRepository.getAllSavedGames()
    }
}
