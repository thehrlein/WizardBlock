package com.tobiashehrlein.tobiswizardblock.presentation.block.input

import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputDataItem

interface BlockInputInteractions {

    fun onInputChanged(inputDataItem: InputDataItem)
}
