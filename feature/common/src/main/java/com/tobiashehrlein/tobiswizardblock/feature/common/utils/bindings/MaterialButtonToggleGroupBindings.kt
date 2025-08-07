package com.tobiashehrlein.tobiswizardblock.feature.common.utils.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

@BindingAdapter("playerCount")
fun MaterialButtonToggleGroup.setPlayerCount(count: Int?) {
    if (count == null) return
    (getChildAt(count - 2) as MaterialButton).isChecked = true
}
