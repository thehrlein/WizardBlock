package com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions

import androidx.databinding.ViewDataBinding

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}
