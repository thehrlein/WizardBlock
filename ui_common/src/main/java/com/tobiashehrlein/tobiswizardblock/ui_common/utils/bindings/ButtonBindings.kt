package com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.ui_common.R

@BindingAdapter("inputType", "summedInputs", "cardCount")
fun Button.setButtonText(inputType: InputType?, summedInputs: Int?, cardCount: Int?) {
    val suffix = if (summedInputs != null && cardCount != null) {
        context.getString(R.string.block_input_summed_inputs, summedInputs, cardCount)
    } else {
        context.getString(R.string.general_empty_string)
    }

    text = context.getString(
        if (inputType == InputType.TIPP) {
            R.string.block_input_enter_tips
        } else {
            R.string.block_input_enter_results
        },
        suffix
    )
}
