package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.savedgames

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class RemoveGameFromSavedGameUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Long, Unit>() {

    override suspend fun execute(parameters: Long): AppResult<Unit> {
        return gameRepository.removeGameFromSavedGames(parameters)
    }
}
