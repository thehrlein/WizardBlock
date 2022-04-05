package com.tobiashehrlein.tobiswizardblock.presentation.block.results

import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType

interface BlockResultsInteractions {

    fun onTrumpClicked(trumpType: TrumpType)
}
