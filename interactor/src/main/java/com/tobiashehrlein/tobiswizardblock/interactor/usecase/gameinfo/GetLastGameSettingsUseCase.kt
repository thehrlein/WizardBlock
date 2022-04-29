package com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetLastGameSettingsUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Unit, GameInfo?>() {

    override suspend fun execute(parameters: Unit): AppResult<GameInfo?> {
        return gameSettingsRepository.getLastGameSettings()
    }
}
