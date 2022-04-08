package com.tobiashehrlein.tobiswizardblock.ui_settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.settings.SettingsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarActivity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.dispatchOnDialogResult
import com.tobiashehrlein.tobiswizardblock.ui_settings.databinding.ActivitySettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : BaseToolbarActivity<SettingsViewModel, ActivitySettingsBinding>(), DialogInteractor {

    override val viewModel: SettingsViewModel by viewModel()
    override val navHostFragment: Int = R.id.activity_settings_nav_host_fragment
    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String
        get() = getString(R.string.settings_toolbar_title)
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_settings

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, SettingsActivity::class.java))
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
    }
}