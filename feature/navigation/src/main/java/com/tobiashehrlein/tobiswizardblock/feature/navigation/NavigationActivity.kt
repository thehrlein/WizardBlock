package com.tobiashehrlein.tobiswizardblock.feature.navigation

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.tobiashehrlein.tobiswizardblock.core.presentation.navigation.NavigationViewModel
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseActivity
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.isUsingDarkMode
import com.tobiashehrlein.tobiswizardblock.feature.navigation.databinding.ActivityNavigationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavigationActivity : BaseActivity<NavigationViewModel, ActivityNavigationBinding>() {

    override val viewModel: NavigationViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_navigation
    override val viewModelVariableId: Int = BR.viewModel
    override val navHostFragment: Int = R.id.navigation_nav_host_fragment

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, NavigationActivity::class.java)
            activity.startActivity(intent)
            activity.finishAffinity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetController.isAppearanceLightStatusBars = true
        }

        val isDarkMode = isUsingDarkMode()
        val systemAppearance = getString(
            if (isDarkMode) com.tobiashehrlein.tobiswizardblock.feature.common.R.string.tracking_user_property_system_appearance_dark else com.tobiashehrlein.tobiswizardblock.feature.common.R.string.tracking_user_property_system_appearance_light
        )
        viewModel.trackSystemAppearanceUserProperty(systemAppearance)
    }
}
