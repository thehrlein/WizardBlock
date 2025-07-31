package com.tobiashehrlein.tobiswizardblock.core.presentation.block.results

import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType

interface BlockResultsInteractions {

    fun onTrumpClicked(trumpType: TrumpType)
}
