package com.tobiashehrlein.tobiswizardblock.core.entities.game.result

import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType

data class BlockItemData(
    val items: List<BlockItem>,
    val inputType: InputType,
    val columnCount: Int
)
