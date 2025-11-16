package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.feature.about.AboutActivity
import com.tobiashehrlein.tobiswizardblock.feature.block.results.BlockResultsFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.block.trump.BlockTrumpDialog
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.SimpleAlertDialogFragment
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.navigateSafe
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.GameSettingsActivity
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerselection.PlayerSelectionFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.navigation.NavigationActivity
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesActivity
import com.tobiashehrlein.tobiswizardblock.feature.statistics.StatisticsActivity

class PageNavigatorImpl(
    private val activity: AppCompatActivity,
    private val navHostController: NavHostController,
    private val resourceHelper: ResourceHelper
) : BasePageNavigatorImpl(
    activity = activity,
    navHostController = navHostController,
    resourceHelper = resourceHelper
) {

    override fun navigateTo(basePage: BasePage) {
        when (basePage) {
            is BasePage.General -> navigateTo(basePage)
            is BasePage.BaseNavigation -> navigateTo(basePage)
            is BasePage.GameRules -> navigateTo(basePage)
            is BasePage.BaseBlock -> navigateTo(basePage)
            is BasePage.Input -> navigateTo(basePage)
            is BasePage.SavedGames -> navigateTo(basePage)
            is BasePage.Settings -> navigateTo(basePage)
            is BasePage.Statistics -> navigateTo(basePage)
            is Page.PlayerSelection -> navigateTo(basePage)
        }
    }

    private fun navigateTo(page: BasePage.BaseNavigation) {
        when (page) {
            is BasePage.BaseNavigation.GameSettings -> GameSettingsActivity.start(activity)
            is BasePage.BaseNavigation.LastGames -> SavedGamesActivity.start(activity)
            is BasePage.BaseNavigation.Statistics -> StatisticsActivity.start(activity)
        }
    }

    private fun navigateTo(page: Page.PlayerSelection) {
        when (page) {
            is Page.PlayerSelection.GameRules -> navHostController.navigateSafe(
                PlayerSelectionFragmentDirections.actionPlayerSelectionFragmentToGameRulesFragment()
            )
        }
    }

    private fun navigateTo(page: BasePage.BaseBlock) {
        when (page) {
            is BasePage.BaseBlock.Input -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockInputFragment(
                    page.gameId,
                    page.inputType
                )
            )
            is BasePage.BaseBlock.Exit -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.Exit
            )
            is BasePage.BaseBlock.FinishManually -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.FinishGameManually()
            )
            is BasePage.BaseBlock.Menu -> NavigationActivity.start(activity)
            is BasePage.BaseBlock.Scores -> navHostController.navigateSafe(
                BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockScoresFragment(page.gameScoreData)
            )
            is BasePage.BaseBlock.About -> AboutActivity.start(activity)
            is BasePage.BaseBlock.Trump -> BlockTrumpDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.Trump(page.trumpType)
            )
            is BasePage.BaseBlock.GameFinished -> SimpleAlertDialogFragment.show(
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
        }
    }
}
