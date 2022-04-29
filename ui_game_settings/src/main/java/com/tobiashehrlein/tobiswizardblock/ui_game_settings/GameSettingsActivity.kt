package com.tobiashehrlein.tobiswizardblock.ui_game_settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarActivity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.dispatchOnDialogResult
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.databinding.ActivityGameSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameSettingsActivity : BaseToolbarActivity<GameSettingsViewModel, ActivityGameSettingsBinding>(), DialogInteractor {

    override val viewModel: GameSettingsViewModel by viewModel()
    override val navHostFragment: Int = R.id.game_settings_nav_host_fragment
    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.None
    override val toolbarTitle: String? = null
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_game_settings

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, GameSettingsActivity::class.java))
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
    }
}
