package com.tobiashehrlein.tobiswizardblock.entities.game.result

import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType

data class BlockItemData(
    val items: List<BlockItem>,
    val inputType: InputType,
    val columnCount: Int
)
