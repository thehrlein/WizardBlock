package com.tobiashehrlein.tobiswizardblock.core.presentation.block.results

import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType

interface BlockResultsInteractions {

    fun onTrumpClicked(trumpType: TrumpType)
}
