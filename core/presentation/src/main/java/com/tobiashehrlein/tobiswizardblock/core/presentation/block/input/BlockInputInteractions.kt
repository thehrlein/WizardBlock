package com.tobiashehrlein.tobiswizardblock.core.presentation.block.input

import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputDataItem

interface BlockInputInteractions {

    fun onInputChanged(inputDataItem: InputDataItem)
}
