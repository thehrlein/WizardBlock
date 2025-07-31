package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.extension.checkAllMatched
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
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
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.GameSettingsActivity
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerorder.PlayerOrderFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerselection.PlayerSelectionFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.navigation.NavigationActivity
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesActivity
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesInfoDialog
import com.tobiashehrlein.tobiswizardblock.feature.settings.SettingsActivity
import com.tobiashehrlein.tobiswizardblock.feature.statistics.StatisticsActivity

class PageNavigatorImpl(
    private val activity: AppCompatActivity,
    private val navHostController: NavHostController,
    private val resourceHelper: ResourceHelper
) : PageNavigator {

    override fun navigateTo(page: Page) {
        when (page) {
            is Page.General -> navigateTo(page)
            is Page.Navigation -> navigateTo(page)
            is Page.PlayerSelection -> navigateTo(page)
            is Page.PlayerOrder -> navigateTo(page)
            is Page.GameRules -> navigateTo(page)
            is Page.Block -> navigateTo(page)
            is Page.Input -> navigateTo(page)
            is Page.SavedGames -> navigateTo(page)
            is Page.Settings -> navigateTo(page)
            is Page.Statistics -> navigateTo(page)
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.General) {
        when (page) {
            is Page.General.Loading.Show -> FullscreenLoadingDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Loading(page.dim)
            )
            is Page.General.Loading.Hide -> FullscreenLoadingDialogFragment.hide(activity.supportFragmentManager)
        }
    }

    private fun navigateTo(page: Page.Navigation) {
        when (page) {
            is Page.Navigation.GameSettings -> GameSettingsActivity.start(activity)
            is Page.Navigation.LastGames -> SavedGamesActivity.start(activity)
            is Page.Navigation.Info -> AboutActivity.start(activity)
            is Page.Navigation.Settings -> SettingsActivity.start(activity)
            is Page.Navigation.Statistics -> StatisticsActivity.start(activity)
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.PlayerSelection) {
        when (page) {
            is Page.PlayerSelection.PlayerOrder -> navHostController.navigateSafe(
                PlayerSelectionFragmentDirections.actionPlayerSelectionFragmentToPlayerOrderFragment()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.PlayerOrder) {
        when (page) {
            is Page.PlayerOrder.GameRules -> navHostController.navigateSafe(
                PlayerOrderFragmentDirections.actionPlayerOrderFragmentToGameRulesFragment()
            )
            is Page.PlayerOrder.Info -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.PlayerOrderInfo()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.GameRules) {
        when (page) {
            is Page.GameRules.Block -> GameBlockActivity.start(activity, page.gameId)
            is Page.GameRules.Info -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfo()
            )
            is Page.GameRules.TipsEqualStitchesInfo -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoTipsEqualStitches()
            )
            is Page.GameRules.TipsEqualStitchesInfoFirstRound -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoTipsEqualStitchesFirstRound()
            )
            is Page.GameRules.AnniversaryVersion -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoAnniversaryMode()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.Block) {
        when (page) {
            is Page.Block.Input -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockInputFragment(
                    page.gameId,
                    page.inputType
                )
            )
            is Page.Block.Exit -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.Exit
            )
            is Page.Block.FinishManually -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.FinishGameManually()
            )
            is Page.Block.Menu -> NavigationActivity.start(activity)
            is Page.Block.Scores -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockScoresFragment(page.gameScoreData)
            )
            is Page.Block.About -> AboutActivity.start(activity)
            is Page.Block.Settings -> SettingsActivity.start(activity)
            is Page.Block.Trump -> BlockTrumpDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.Trump(page.trumpType)
            )
            is Page.Block.GameFinished -> SimpleAlertDialogFragment.show(
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

    private fun navigateTo(page: Page.Input) {
        when (page) {
            is Page.Input.Block -> navHostController.navigateSafe(
                BlockInputFragmentDirections.actionBlockInputFragmentToGameBlockFragment()
            )
            is Page.Input.Info -> SimpleAlertDialogFragment.show(
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
            is Page.Input.CorrectTipsBecauseOfCloudCard -> BlockInputCorrectTipsChoosePlayerDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.CorrectTipsChoosePlayer(
                    page.playerTipData,
                    page.round
                )
            )
            is Page.Input.BombPlayed -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.BlockInputBombPlayed()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.SavedGames) {
        when (page) {
            is Page.SavedGames.ContinueGame -> GameBlockActivity.start(
                activity,
                page.gameId
            )
            is Page.SavedGames.Info -> SavedGamesInfoDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.SavedGamesInfo(
                    page.gameSettings
                )
            )
            is Page.SavedGames.Delete -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.DeleteSavedGames()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.Settings) {
        when (page) {
            is Page.Settings.DialogDisplayAlwaysOn -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.SettingsDisplayAlwaysOn()
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.Statistics) {
        when (page) {
            is Page.Statistics.Clear -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.ClearStatistics()
            )
        }.checkAllMatched
    }
}
