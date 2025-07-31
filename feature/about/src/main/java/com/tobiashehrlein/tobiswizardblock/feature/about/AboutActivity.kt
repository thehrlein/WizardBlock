package com.tobiashehrlein.tobiswizardblock.feature.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.core.presentation.about.AboutViewModel
import com.tobiashehrlein.tobiswizardblock.feature.about.databinding.ActivityAboutBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseToolbarActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutActivity : BaseToolbarActivity<AboutViewModel, ActivityAboutBinding>() {

    override val viewModel: AboutViewModel by viewModel()
    override val navHostFragment: Int = R.id.activity_about_nav_host_fragment
    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String?
        get() = getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.about_toolbar_title)
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_about

    companion object {
        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
