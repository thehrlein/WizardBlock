package com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetBlockInputModelsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, InputData>() {

    override suspend fun execute(parameters: Game): AppResult<InputData> {
        return gameRepository.getBlockInputData(parameters)
    }
}
