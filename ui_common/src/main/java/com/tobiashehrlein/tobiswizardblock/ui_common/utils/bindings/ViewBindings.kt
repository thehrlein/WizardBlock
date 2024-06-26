package com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun View.setVisibility(boolean: Boolean) {
    isVisible = boolean
}
