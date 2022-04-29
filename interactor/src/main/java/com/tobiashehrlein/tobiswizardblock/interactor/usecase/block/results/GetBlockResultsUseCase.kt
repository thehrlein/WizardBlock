package com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItemData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetBlockResultsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, BlockItemData>() {

    override suspend fun execute(parameters: Game): AppResult<BlockItemData> {
        return gameRepository.getBlockResults(parameters)
    }
}
