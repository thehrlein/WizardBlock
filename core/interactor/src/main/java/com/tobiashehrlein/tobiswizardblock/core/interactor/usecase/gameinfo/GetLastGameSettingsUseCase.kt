package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetLastGameSettingsUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Unit, GameInfo?>() {

    override suspend fun execute(parameters: Unit): AppResult<GameInfo?> {
        return gameSettingsRepository.getLastGameSettings()
    }
}
