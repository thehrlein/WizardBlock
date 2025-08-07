package com.tobiashehrlein.tobiswizardblock.feature.common.utils.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.feature.common.R

@BindingAdapter("inputType", "gameFinished", requireAll = true)
fun ExtendedFloatingActionButton.setFabIconAndText(inputType: InputType?, gameFinished: Boolean?) {
    if (inputType == null || gameFinished == null) return

    text = when {
        gameFinished -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.general_exit)
        inputType == InputType.TIPP -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.game_block_results_fab_add_prediction)
        inputType == InputType.RESULT -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.game_block_results_fab_add_results)
        else -> null
    }

    setIconResource(
        when {
            gameFinished -> R.drawable.wb_ic_exit
            else -> R.drawable.wb_ic_add
        }
    )

    extend()
}
