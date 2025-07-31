package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.input

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class StoreRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<InsertRoundData, Unit>() {

    override suspend fun execute(parameters: InsertRoundData): AppResult<Unit> {
        return gameRepository.insertRound(parameters)
    }
}
