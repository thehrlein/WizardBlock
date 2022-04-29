package com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class RemoveRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<DeleteRoundData, Unit>() {

    override suspend fun execute(parameters: DeleteRoundData): AppResult<Unit> {
        return gameRepository.removeRound(parameters)
    }
}
