package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.feature.block.GameBlockActivity
import com.tobiashehrlein.tobiswizardblock.feature.block.input.BlockInputFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.block.input.correcttips.BlockInputCorrectTipsChoosePlayerDialog
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.FullscreenLoadingDialogFragment
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.SimpleAlertDialogFragment
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.navigateSafe
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesInfoDialog

abstract class BasePageNavigatorImpl(
    private val activity: AppCompatActivity,
    private val navHostController: NavHostController,
    private val resourceHelper: ResourceHelper
) : PageNavigator {

    fun navigateTo(page: BasePage.General) {
        when (page) {
            is BasePage.General.Loading.Show -> FullscreenLoadingDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Loading(page.dim)
            )
            is BasePage.General.Loading.Hide -> FullscreenLoadingDialogFragment.hide(activity.supportFragmentManager)
        }
    }

     fun navigateTo(page: BasePage.GameRules) {
        when (page) {
            is BasePage.GameRules.Block -> GameBlockActivity.start(activity, page.gameId)
            is BasePage.GameRules.Info -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfo()
            )
            is BasePage.GameRules.TipsEqualStitchesInfo -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoTipsEqualStitches()
            )
            is BasePage.GameRules.TipsEqualStitchesInfoFirstRound -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoTipsEqualStitchesFirstRound()
            )
            is BasePage.GameRules.AnniversaryVersion -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoAnniversaryMode()
            )
        }
    }

    fun navigateTo(page: BasePage.Input) {
        when (page) {
            is BasePage.Input.Block -> navHostController.navigateSafe(
                BlockInputFragmentDirections.actionBlockInputFragmentToGameBlockFragment()
            )
            is BasePage.Input.Info -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.InputInfo(
                    message = when (page.inputType) {
                        InputType.TIPP ->
                            when {
                                page.gameSettings.tipsEqualStitchesFirstRound && page.round == 1 ->
                                    resourceHelper.getString(
                                        R.string.block_input_info_bets_can_be_equal_stitches_message_first_round,
                                        page.gameSettings.tipsEqualStitches
                                    )
                                page.gameSettings.tipsEqualStitches -> resourceHelper.getString(
                                    R.string.block_input_info_bets_can_be_equal_stitches_message,
                                    page.round
                                )
                                else -> resourceHelper.getString(
                                    R.string.block_input_info_bets_bets_must_be_unequal_stitches_message,
                                    page.round
                                )
                            }
                        InputType.RESULT -> resourceHelper.getString(
                            R.string.block_input_info_result_message,
                            page.round - if (page.bombPlayed) 1 else 0
                        )
                    }
                )
            )
            is BasePage.Input.CorrectTipsBecauseOfCloudCard -> BlockInputCorrectTipsChoosePlayerDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.CorrectTipsChoosePlayer(
                    page.playerTipData,
                    page.round
                )
            )
            is BasePage.Input.BombPlayed -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.BlockInputBombPlayed()
            )
        }
    }

    fun navigateTo(page: BasePage.SavedGames) {
        when (page) {
            is BasePage.SavedGames.ContinueGame -> GameBlockActivity.start(
                activity,
                page.gameId
            )
            is BasePage.SavedGames.Info -> SavedGamesInfoDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.SavedGamesInfo(
                    page.gameSettings
                )
            )
            is BasePage.SavedGames.Delete -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.DeleteSavedGames()
            )
        }
    }

    fun navigateTo(page: BasePage.Settings) {
        when (page) {
            is BasePage.Settings.DialogDisplayAlwaysOn -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.SettingsDisplayAlwaysOn()
            )
        }
    }

    fun navigateTo(page: BasePage.Statistics) {
        when (page) {
            is BasePage.Statistics.Clear -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.ClearStatistics()
            )
        }
    }
}
