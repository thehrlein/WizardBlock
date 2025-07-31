package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetGameUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Long, Game>() {

    override suspend fun execute(parameters: Long): AppResult<Game> {
        return gameRepository.getGame(parameters)
    }
}
