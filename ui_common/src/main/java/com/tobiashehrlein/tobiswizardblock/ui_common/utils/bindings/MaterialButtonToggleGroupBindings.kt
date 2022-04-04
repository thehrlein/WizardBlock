package com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.tobiashehrlein.tobiswizardblock.ui_common.R

@BindingAdapter("playerCount")
fun MaterialButtonToggleGroup.setPlayerCount(count: Int?) {
    if (count == null) return
    (getChildAt(count - 3) as MaterialButton).isChecked = true
}
