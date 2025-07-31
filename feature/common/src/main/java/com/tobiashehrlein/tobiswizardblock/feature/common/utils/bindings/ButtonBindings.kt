package com.tobiashehrlein.tobiswizardblock.feature.common.utils.bindings

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.feature.common.R

@BindingAdapter("inputType", "summedInputs", "cardCount")
fun Button.setButtonText(inputType: InputType?, summedInputs: Int?, cardCount: Int?) {
    val suffix = if (summedInputs != null && cardCount != null) {
        context.getString(
            com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_input_summed_inputs,
            summedInputs,
            cardCount
        )
    } else {
        context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.general_empty_string)
    }

    text = context.getString(
        if (inputType == InputType.TIPP) {
            R.string.block_input_enter_bets
        } else {
            R.string.block_input_enter_results
        },
        suffix
    )
}
