package com.tobiashehrlein.tobiswizardblock.old.utils.helper

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.tobiashehrlein.tobiswizardblock.core.entities.general.WBInsets

object WindowInsetsHelper {

    fun getWindowInsets(view: View, window: Window, onHeightDetermined: (WBInsets) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above, use WindowInsets to get the status bar height
            ViewCompat.setOnApplyWindowInsetsListener(view) { v: View?, insets: WindowInsetsCompat? ->
                // Get the system bars insets
                val topBar = insets?.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
                )
                val navigationBar = insets?.getInsets(
                    WindowInsetsCompat.Type.navigationBars()
                )
                val keyboard = insets?.getInsets(
                    WindowInsetsCompat.Type.ime()
                )
                val wBInsets = WBInsets(
                    statusBarHeight = topBar?.top ?: 0,
                    navigationBarHeight = navigationBar?.bottom ?: 0,
                    keyboardHeight = keyboard?.bottom ?: 0
                )

                onHeightDetermined(wBInsets)
                WindowInsetsCompat.CONSUMED
            }
        } else {
            val resources: Resources = view.context.resources

            val statusBarResourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = if (statusBarResourceId > 0) {
                resources.getDimensionPixelSize(statusBarResourceId)
            } else {
                0
            }
            // Use the resource identifier for status bar height
            val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            val navigationBarHeight = if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                0
            }

            val wBInsets = WBInsets(
                statusBarHeight = statusBarHeight,
                navigationBarHeight = navigationBarHeight,
                keyboardHeight = 0
            )
            onHeightDetermined(wBInsets)
        }
    }

    fun setLightStatusBar(lightStatusBar: Boolean, window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetController.isAppearanceLightStatusBars = lightStatusBar
        }
    }
}