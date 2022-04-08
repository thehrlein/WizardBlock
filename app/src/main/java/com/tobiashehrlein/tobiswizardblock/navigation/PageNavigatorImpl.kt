package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.entities.extension.checkAllMatched
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.ui_about.AboutActivity
import com.tobiashehrlein.tobiswizardblock.ui_block.GameBlockActivity
import com.tobiashehrlein.tobiswizardblock.ui_block.input.BlockInputFragmentDirections
import com.tobiashehrlein.tobiswizardblock.ui_block.input.correcttips.BlockInputCorrectTipsChoosePlayerDialog
import com.tobiashehrlein.tobiswizardblock.ui_block.results.BlockResultsFragmentDirections
import com.tobiashehrlein.tobiswizardblock.ui_block.trump.BlockTrumpDialog
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.FullscreenLoadingDialogFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.SimpleAlertDialogFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.navigateSafe
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.GameSettingsActivity
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.playerorder.PlayerOrderFragmentDirections
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.playerselection.PlayerSelectionFragmentDirections
import com.tobiashehrlein.tobiswizardblock.ui_navigation.NavigationActivity
import com.tobiashehrlein.tobiswizardblock.ui_saved_games.SavedGamesActivity
import com.tobiashehrlein.tobiswizardblock.ui_saved_games.SavedGamesInfoDialog
import com.tobiashehrlein.tobiswizardblock.ui_settings.SettingsActivity

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
                DialogEntity.Text.PlayerOrderInfo(resourceHelper)
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.GameRules) {
        when (page) {
            is Page.GameRules.Block -> GameBlockActivity.start(activity, page.gameId)
            is Page.GameRules.Info -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfo(resourceHelper)
            )
            is Page.GameRules.TipsEqualStitchesInfo -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoTipsEqualStitches(resourceHelper)
            )
            is Page.GameRules.TipsEqualStitchesInfoFirstRound -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoTipsEqualStitchesFirstRound(resourceHelper)
            )
            is Page.GameRules.AnniversaryVersion -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameRulesInfoAnniversaryMode(resourceHelper)
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
                DialogEntity.Text.Exit(resourceHelper)
            )
            is Page.Block.Menu -> NavigationActivity.start(activity)
            is Page.Block.Scores -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockScoresFragment(page.gameScoreData)
            )
            is Page.Block.About -> AboutActivity.start(activity)
            is Page.Block.Settings -> SettingsActivity.start(activity)
            is Page.Block.Trump -> BlockTrumpDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.Trump(page.trumpType, resourceHelper)
            )
            is Page.Block.GameFinished -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameFinished(page.winners, resourceHelper)
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
                    page.inputType,
                    page.round,
                    page.gameSettings,
                    resourceHelper
                )
            )
            is Page.Input.CorrectTipsBecauseOfCloudCard -> BlockInputCorrectTipsChoosePlayerDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.CorrectTipsChoosePlayer(
                    page.playerTipData,
                    page.round,
                    resourceHelper
                )
            )
            is Page.Input.BombPlayed -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.BlockInputBombPlayed(
                    resourceHelper
                )
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.SavedGames) {
        when (page) {
            is Page.SavedGames.ContinueGame -> GameBlockActivity.start(
                activity, page.gameId
            )
            is Page.SavedGames.Info -> SavedGamesInfoDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.SavedGamesInfo(
                    page.gameSettings,
                    resourceHelper
                )
            )
            is Page.SavedGames.Delete -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.DeleteSavedGames(resourceHelper)
            )
        }.checkAllMatched
    }

    private fun navigateTo(page: Page.Settings) {
        when (page) {
            is Page.Settings.DialogDisplayAlwaysOn -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.SettingsDisplayAlwaysOn(resourceHelper)
            )
        }.checkAllMatched
    }
}
