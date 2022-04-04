package com.tobiashehrlein.tobiswizardblock.ui_navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tobiashehrlein.tobiswizardblock.presentation.navigation.NavigationViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseActivity
import com.tobiashehrlein.tobiswizardblock.ui_navigation.databinding.ActivityNavigationBinding
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
    }
}