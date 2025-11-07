package com.tobiashehrlein.tobiswizardblock.core.entities.navigation

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType

sealed class Page : BasePage() {

    sealed class PlayerSelection : BasePage() {
        object GameRules : PlayerSelection()
    }

}