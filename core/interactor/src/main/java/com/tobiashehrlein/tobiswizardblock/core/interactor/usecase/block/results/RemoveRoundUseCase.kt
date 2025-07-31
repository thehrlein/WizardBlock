package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class RemoveRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<DeleteRoundData, Unit>() {

    override suspend fun execute(parameters: DeleteRoundData): AppResult<Unit> {
        return gameRepository.removeRound(parameters)
    }
}
