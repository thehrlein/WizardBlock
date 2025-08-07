package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.player

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetPlayerNamesUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Unit, Set<String>>() {

    override suspend fun execute(parameters: Unit): AppResult<Set<String>> {
        return gameSettingsRepository.getAllPlayerNamesFromCache()
    }
}
