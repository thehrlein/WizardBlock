package com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class StoreGameInfoUseCase(
    private val gameSettingsRepository: GameSettingsRepository
) : BaseUseCase<GameInfo, Long>() {

    override suspend fun execute(parameters: GameInfo): AppResult<Long> {
        return gameSettingsRepository.storeGameInfo(parameters)
    }
}
