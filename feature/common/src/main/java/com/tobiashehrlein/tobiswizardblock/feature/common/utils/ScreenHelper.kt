package com.tobiashehrlein.tobiswizardblock.feature.common.utils

import android.content.Context
import android.os.Build
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager

object ScreenHelper {

    fun getScreenSize(context: Context): Size {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30+ (Android 11 and above)
            val windowMetrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics

            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())

            val width = windowMetrics.bounds.width() - insets.left - insets.right
            val height = windowMetrics.bounds.height() - insets.top - insets.bottom
            Size(width, height)
        } else {
            // Pre Android 11
            val displayMetrics = context.resources.displayMetrics
            Size(displayMetrics.widthPixels, displayMetrics.heightPixels)
        }
    }
}