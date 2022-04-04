package com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.ui_common.R

@BindingAdapter("inputType", "gameFinished", requireAll = true)
fun ExtendedFloatingActionButton.setFabIconAndText(inputType: InputType?, gameFinished: Boolean?) {
    if (inputType == null || gameFinished == null) return

    text = when {
        gameFinished -> context.getString(R.string.game_block_results_fab_exit)
        inputType == InputType.TIPP -> context.getString(R.string.game_block_results_fab_add_prediction)
        inputType == InputType.RESULT -> context.getString(R.string.game_block_results_fab_add_results)
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
