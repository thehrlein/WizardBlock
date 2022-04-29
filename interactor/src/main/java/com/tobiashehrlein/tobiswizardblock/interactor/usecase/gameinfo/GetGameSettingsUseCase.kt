package com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetGameSettingsUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<Long, GameInfo>() {

    override suspend fun execute(parameters: Long): AppResult<GameInfo> {
        return gameSettingsRepository.getGameSettings(parameters)
    }
}
