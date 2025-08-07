package com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.processor

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputData
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult

interface BlockInputProcessor {

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

    suspend fun getBlockInputModels(game: Game): AppResult<InputData>
}
