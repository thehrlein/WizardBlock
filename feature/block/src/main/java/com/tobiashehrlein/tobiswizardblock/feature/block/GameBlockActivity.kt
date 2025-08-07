package com.tobiashehrlein.tobiswizardblock.feature.block

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.core.presentation.block.GameBlockViewModel
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.ActivityGameBlockBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseToolbarActivity
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.utils.DialogRequestCode
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.dispatchOnDialogResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GameBlockActivity : BaseToolbarActivity<GameBlockViewModel, ActivityGameBlockBinding>(), DialogInteractor {

    override val viewModel: GameBlockViewModel by viewModel {
        parametersOf(gameId)
    }
    override val navHostFragment: Int = R.id.game_block_nav_host_fragment
    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.None
    override val toolbarTitle: String? = null
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_game_block
    private val gameId: Long
        get() = intent.getLongExtra(EXTRA_GAME_ID, DEFAULT_GAME_ID).takeIf { it != DEFAULT_GAME_ID }
            ?: error("gameId can not be null or $DEFAULT_GAME_ID")

    companion object {
        private const val EXTRA_GAME_ID = "extra.game_id"
        private const val DEFAULT_GAME_ID: Long = -1
        fun start(activity: AppCompatActivity, gameId: Long) {
            activity.startActivity(
                Intent(activity, GameBlockActivity::class.java).apply {
                    putExtra(EXTRA_GAME_ID, gameId)
                }
            )
            activity.finishAffinity()
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.BLOCK_EXIT -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> viewModel.openMenu()
                    DialogResultCode.NEGATIVE -> finish()
                    DialogResultCode.NEUTRAL -> Unit
                }
            }
            else -> supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
        }
    }
}
