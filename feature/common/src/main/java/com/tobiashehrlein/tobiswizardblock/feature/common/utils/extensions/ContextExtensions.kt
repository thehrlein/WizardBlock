package com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions

import android.content.Context
import android.content.res.Configuration
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

fun Context.isLandscape(): Boolean {
    return resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE
}

fun Context.isUsingDarkMode(): Boolean {
    return when (resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }
}
