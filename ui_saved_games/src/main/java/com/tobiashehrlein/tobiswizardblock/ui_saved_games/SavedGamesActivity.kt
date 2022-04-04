package com.tobiashehrlein.tobiswizardblock.ui_saved_games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.SavedGamesViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarActivity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.dispatchOnDialogResult
import com.tobiashehrlein.tobiswizardblock.ui_saved_games.databinding.ActivitySavedGamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedGamesActivity : BaseToolbarActivity<SavedGamesViewModel, ActivitySavedGamesBinding>(), DialogInteractor {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String
        get() = getString(R.string.saved_game_toolbar_title)
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_saved_games
    override val viewModel: SavedGamesViewModel by viewModel()
    override val navHostFragment: Int = R.id.activity_saved_games_nav_host_fragment

    companion object {
        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, SavedGamesActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
    }
}