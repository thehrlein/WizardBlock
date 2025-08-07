package com.tobiashehrlein.tobiswizardblock.feature.common.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

interface ResourceHelper {

    fun getColor(@ColorRes colorRes: Int): Int
    fun getColorFromAttr(@AttrRes attrRes: Int): Int
    fun getDimension(@DimenRes dimenRes: Int): Float
    fun getString(@StringRes stringRes: Int): String
    fun getString(@StringRes stringRes: Int, param: Int): String
    fun getString(@StringRes stringRes: Int, param: String): String
    fun getString(@StringRes stringRes: Int, param1: String, param2: String): String
    fun getString(@StringRes stringRes: Int, vararg args: Any): String
    fun getPlural(@PluralsRes pluralRes: Int, quantity: Int): String
    fun getPlural(@PluralsRes pluralRes: Int, quantity: Int, param: Int): String
    fun getPlural(@PluralsRes pluralRes: Int, quantity: Int, param1: String, param2: Int): String
}

class ResourceHelperImpl(val context: Context) :
    ResourceHelper {

    override fun getColor(@ColorRes colorRes: Int) =
        ContextCompat.getColor(context, colorRes)

    override fun getColorFromAttr(@AttrRes attrRes: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrRes, typedValue, true)
        return typedValue.data
    }

    override fun getDimension(@DimenRes dimenRes: Int) =
        context.resources.getDimension(dimenRes)

    override fun getString(@StringRes stringRes: Int) =
        context.getString(stringRes)

    override fun getString(@StringRes stringRes: Int, param: Int) =
        context.getString(stringRes).format(param)

    override fun getString(@StringRes stringRes: Int, param: String) =
        context.getString(stringRes).format(param)

    override fun getString(@StringRes stringRes: Int, param1: String, param2: String) =
        context.getString(stringRes).format(param1, param2)

    @Suppress("SpreadOperator")
    override fun getString(stringRes: Int, vararg args: Any): String =
        context.getString(stringRes, *args)

    override fun getPlural(pluralRes: Int, quantity: Int): String =
        context.resources.getQuantityString(
            pluralRes,
            quantity
        )

    override fun getPlural(pluralRes: Int, quantity: Int, param: Int): String =
        context.resources.getQuantityString(
            pluralRes,
            quantity,
            param
        )

    override fun getPlural(pluralRes: Int, quantity: Int, param1: String, param2: Int): String =
        context.resources.getQuantityString(
            pluralRes,
            quantity,
            param1,
            param2
        )
}
