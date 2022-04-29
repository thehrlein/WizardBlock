package com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions

import androidx.databinding.ViewDataBinding

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}
