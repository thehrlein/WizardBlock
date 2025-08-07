package com.tobiashehrlein.tobiswizardblock.core.entities.game.result

data class BlockName(
    val name: String,
    val isDealer: Boolean,
    val isCurrentLeader: Boolean
) : BlockItem
