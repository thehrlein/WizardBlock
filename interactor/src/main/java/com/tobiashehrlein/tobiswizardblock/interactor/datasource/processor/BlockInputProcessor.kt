package com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor

import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult

interface BlockInputProcessor {

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

    suspend fun getBlockInputModels(game: Game): AppResult<InputData>
}
