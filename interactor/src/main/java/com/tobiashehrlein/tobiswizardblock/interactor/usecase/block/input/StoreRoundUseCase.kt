package com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input

import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class StoreRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<InsertRoundData, Unit>() {

    override suspend fun execute(parameters: InsertRoundData): AppResult<Unit> {
        return gameRepository.insertRound(parameters)
    }
}
