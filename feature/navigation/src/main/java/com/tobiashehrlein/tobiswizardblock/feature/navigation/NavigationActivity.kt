package com.tobiashehrlein.tobiswizardblock.feature.navigation

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.tobiashehrlein.tobiswizardblock.core.presentation.navigation.BaseNavigationViewModel
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseActivity
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ScreenHelper
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.isUsingDarkMode
import com.tobiashehrlein.tobiswizardblock.feature.navigation.databinding.ActivityNavigationBinding
import com.tobiashehrlein.tobiswizardblock.old.utils.helper.WindowInsetsHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NavigationActivity : BaseActivity<BaseNavigationViewModel, ActivityNavigationBinding>() {

    override val viewModel: BaseNavigationViewModel by viewModel()
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
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val screenSize = ScreenHelper.getScreenSize(this)
        Timber.d("ScreenSize: $screenSize")

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

    override fun onBindingCreated() {
        adjustStatusBarHeight()
    }

    private fun adjustStatusBarHeight() {
        WindowInsetsHelper.getWindowInsets(binding.root, window) { vbInsets ->
            binding.statusBarBackground.layoutParams.height = vbInsets.statusBarHeight
            binding.root.setPadding(
                binding.root.paddingLeft,
                binding.root.paddingTop,
                binding.root.paddingRight,
                vbInsets.navigationBarHeight
            )
        }
    }
}
