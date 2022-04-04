package com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.DimenRes
import kotlin.math.roundToInt

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.getDimen(@DimenRes dimenRes: Int): Int {
    return resources.getDimension(dimenRes).roundToInt()
}

fun Context.getExactDimen(@DimenRes dimenRes: Int): Int {
    return (resources.getDimension(dimenRes) / resources.displayMetrics.density).roundToInt()
}

fun Context.getColorReference(resId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}
