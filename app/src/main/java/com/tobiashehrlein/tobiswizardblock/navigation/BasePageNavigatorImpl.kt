package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.extension.checkAllMatched
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.feature.about.AboutActivity
import com.tobiashehrlein.tobiswizardblock.feature.block.GameBlockActivity
import com.tobiashehrlein.tobiswizardblock.feature.block.input.BlockInputFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.block.input.correcttips.BlockInputCorrectTipsChoosePlayerDialog
import com.tobiashehrlein.tobiswizardblock.feature.block.results.BlockResultsFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.block.trump.BlockTrumpDialog
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.FullscreenLoadingDialogFragment
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.SimpleAlertDialogFragment
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.navigateSafe
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerorder.PlayerOrderFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerselection.PlayerSelectionFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.navigation.NavigationActivity
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesInfoDialog
import com.tobiashehrlein.tobiswizardblock.feature.settings.SettingsActivity

abstract class BasePageNavigatorImpl(
    private val activity: AppCompatActivity,
    private val navHostController: NavHostController,
    private val resourceHelper: ResourceHelper
) : PageNavigator {

    override fun navigateTo(basePage: BasePage) {
        when (basePage) {
            is BasePage.General -> navigateTo(basePage)
            is BasePage.BaseNavigation -> navigateTo(basePage)
            is BasePage.PlayerSelection -> navigateTo(basePage)
            is BasePage.PlayerOrder -> navigateTo(basePage)
            is BasePage.GameRules -> navigateTo(basePage)
            is BasePage.Block -> navigateTo(basePage)
            is BasePage.Input -> navigateTo(basePage)
            is BasePage.SavedGames -> navigateTo(basePage)
            is BasePage.Settings -> navigateTo(basePage)
            is BasePage.Statistics -> navigateTo(basePage)
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.General) {
        when (page) {
            is BasePage.General.Loading.Show -> FullscreenLoadingDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Loading(page.dim)
            )
            is BasePage.General.Loading.Hide -> FullscreenLoadingDialogFragment.hide(activity.supportFragmentManager)
        }
    }

    abstract fun navigateTo(page: BasePage.BaseNavigation)

    private fun navigateTo(page: BasePage.PlayerSelection) {
        when (page) {
            is BasePage.PlayerSelection.PlayerOrder -> navHostController.navigateSafe(
                PlayerSelectionFragmentDirections.actionPlayerSelectionFragmentToPlayerOrderFragment()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.PlayerOrder) {
        when (page) {
            is BasePage.PlayerOrder.GameRules -> navHostController.navigateSafe(
                PlayerOrderFragmentDirections.actionPlayerOrderFragmentToGameRulesFragment()
            )
            is BasePage.PlayerOrder.Info -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.PlayerOrderInfo()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.GameRules) {
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
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.Block) {
        when (page) {
            is BasePage.Block.Input -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockInputFragment(
                    page.gameId,
                    page.inputType
                )
            )
            is BasePage.Block.Exit -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.Exit
            )
            is BasePage.Block.FinishManually -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.FinishGameManually()
            )
            is BasePage.Block.Menu -> NavigationActivity.start(activity)
            is BasePage.Block.Scores -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockScoresFragment(page.gameScoreData)
            )
            is BasePage.Block.About -> AboutActivity.start(activity)
            is BasePage.Block.Settings -> SettingsActivity.start(activity)
            is BasePage.Block.Trump -> BlockTrumpDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.Trump(page.trumpType)
            )
            is BasePage.Block.GameFinished -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameFinished(
                    message = resourceHelper.getPlural(
                        R.plurals.game_winner_message,
                        page.winners.size,
                        page.winners.joinToString { it.player },
                        page.winners.first().points
                    )

                )
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.Input) {
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
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.SavedGames) {
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
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.Settings) {
        when (page) {
            is BasePage.Settings.DialogDisplayAlwaysOn -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.SettingsDisplayAlwaysOn()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: BasePage.Statistics) {
        when (page) {
            is BasePage.Statistics.Clear -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.ClearStatistics()
            )
        }.checkAllMatched
    }
}
