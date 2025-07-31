package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.savedgames

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class RemoveAllGamesFromSavedGamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit): AppResult<Unit> {
        return gameRepository.removeAllGamesFromSavedGames()
    }
}
