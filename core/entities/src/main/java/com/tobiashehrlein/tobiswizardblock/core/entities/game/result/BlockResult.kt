package com.tobiashehrlein.tobiswizardblock.core.entities.game.result

data class BlockResult(
    val player: String,
    val round: Int,
    val tip: Int?,
    val result: Int?,
    val difference: Int?,
    val total: Int?,
    val colorized: Boolean
) : BlockItem
