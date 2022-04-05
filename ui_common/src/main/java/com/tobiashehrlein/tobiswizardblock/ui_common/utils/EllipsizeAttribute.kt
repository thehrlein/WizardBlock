package com.tobiashehrlein.tobiswizardblock.ui_common.utils

import android.text.TextUtils

enum class EllipsizeAttribute(val value: Int, val truncateAt: TextUtils.TruncateAt? = null) {
    NOT_SET(-1),
    ELLIPSIZE_NONE(0),
    START(1, TextUtils.TruncateAt.START),
    MIDDLE(2, TextUtils.TruncateAt.MIDDLE),
    END(3, TextUtils.TruncateAt.END),
    MARQUEE(4, TextUtils.TruncateAt.MARQUEE);

    companion object {
        fun getEllipsize(value: Int): EllipsizeAttribute {
            return values().firstOrNull { it.value == value } ?: NOT_SET
        }
    }
}
