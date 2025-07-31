package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetGameSettingsUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Long, GameInfo>() {

    override suspend fun execute(parameters: Long): AppResult<GameInfo> {
        return gameSettingsRepository.getGameSettings(parameters)
    }
}
