package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class StoreGameFinishedUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Long, Unit>() {

    override suspend fun execute(parameters: Long): AppResult<Unit> {
        return gameRepository.setGameFinished(parameters)
    }
}
