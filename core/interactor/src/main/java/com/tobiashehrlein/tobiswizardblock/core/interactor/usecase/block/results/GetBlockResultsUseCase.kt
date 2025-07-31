package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.BlockItemData
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetBlockResultsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, BlockItemData>() {

    override suspend fun execute(parameters: Game): AppResult<BlockItemData> {
        return gameRepository.getBlockResults(parameters)
    }
}
