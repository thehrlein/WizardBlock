package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetGameNameOptionsUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Unit, Set<String>>() {

    override suspend fun execute(parameters: Unit): AppResult<Set<String>> {
        return gameSettingsRepository.getGameNameOptions()
    }
}
