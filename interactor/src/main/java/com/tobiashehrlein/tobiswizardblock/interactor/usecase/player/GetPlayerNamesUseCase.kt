package com.tobiashehrlein.tobiswizardblock.interactor.usecase.player

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetPlayerNamesUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Unit, Set<String>>() {

    override suspend fun execute(parameters: Unit): AppResult<Set<String>> {
        return gameSettingsRepository.getAllPlayerNamesFromCache()
    }
}
