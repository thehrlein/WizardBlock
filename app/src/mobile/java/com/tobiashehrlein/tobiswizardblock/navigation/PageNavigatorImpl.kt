package com.tobiashehrlein.tobiswizardblock.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.extension.checkAllMatched
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.feature.about.AboutActivity
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.GameSettingsActivity
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.SavedGamesActivity
import com.tobiashehrlein.tobiswizardblock.feature.settings.SettingsActivity
import com.tobiashehrlein.tobiswizardblock.feature.statistics.StatisticsActivity

class PageNavigatorImpl(
    private val activity: AppCompatActivity,
    navHostController: NavHostController,
    resourceHelper: ResourceHelper
) : BasePageNavigatorImpl(
    activity = activity,
    navHostController = navHostController,
    resourceHelper = resourceHelper
) {

   override fun navigateTo(page: BasePage.BaseNavigation) {
        when (page) {
            is BasePage.BaseNavigation.GameSettings -> GameSettingsActivity.start(activity)
            is BasePage.BaseNavigation.LastGames -> SavedGamesActivity.start(activity)
            is BasePage.BaseNavigation.Statistics -> StatisticsActivity.start(activity)
            is Page.Navigation.Info -> AboutActivity.start(activity)
            is Page.Navigation.Settings -> SettingsActivity.start(activity)
        }.checkAllMatched
    }
}
