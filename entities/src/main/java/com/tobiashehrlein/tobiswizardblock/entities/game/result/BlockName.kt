package com.tobiashehrlein.tobiswizardblock.entities.game.result

data class BlockName(
    val name: String,
    val isDealer: Boolean,
    val isCurrentLeader: Boolean
) : BlockItem
