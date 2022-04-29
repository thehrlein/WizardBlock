package com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class RemoveGameFromSavedGameUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Long, Unit>() {

    override suspend fun execute(parameters: Long): AppResult<Unit> {
        return gameRepository.removeGameFromSavedGames(parameters)
    }
}
