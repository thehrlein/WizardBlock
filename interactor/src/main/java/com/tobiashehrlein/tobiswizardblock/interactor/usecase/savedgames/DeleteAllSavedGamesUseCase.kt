package com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class DeleteAllSavedGamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit): AppResult<Unit> {
        return gameRepository.deleteAllGame()
    }
}
