package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.navigateSafe
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.GameSettingsActivity
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerselection.PlayerSelectionFragmentDirections
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesActivity
import com.tobiashehrlein.tobiswizardblock.feature.statistics.StatisticsActivity

class PageNavigatorImpl(
    private val activity: AppCompatActivity,
    private val navHostController: NavHostController,
    resourceHelper: ResourceHelper
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
            is BasePage.Block -> navigateTo(basePage)
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
}
