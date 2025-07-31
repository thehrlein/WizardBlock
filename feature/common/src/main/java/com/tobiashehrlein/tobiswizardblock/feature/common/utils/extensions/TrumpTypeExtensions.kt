package com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.feature.common.R

@ColorRes
fun TrumpType.getColorRes(): Int? {
    return when (this) {
        is TrumpType.Selected.Blue -> R.color.trump_color_blue
        is TrumpType.Selected.Red -> R.color.trump_color_red
        is TrumpType.Selected.Green -> R.color.trump_color_green
        is TrumpType.Selected.Yellow -> R.color.trump_color_yellow
        else -> null
    }
}

@StringRes
fun TrumpType.getNameRes(): Int? {
    return when (this) {
        is TrumpType.Selected.Blue -> R.string.block_trump_type_blue
        is TrumpType.Selected.Red -> R.string.block_trump_type_red
        is TrumpType.Selected.Green -> R.string.block_trump_type_green
        is TrumpType.Selected.Yellow -> R.string.block_trump_type_yellow
        else -> null
    }
}
