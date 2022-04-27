package com.tobiashehrlein.tobiswizardblock.koin

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.fw_database_room.databaseModule
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.datastore.SettingsDataStore
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.firebase.AnalyticsDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor.BlockInputProcessor
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.processor.BlockResultsProcessor
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.sharedpref.UserSettingsPersistence
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameSettingsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.repository.UserRepository
import com.tobiashehrlein.tobiswizardblock.interactor.repository.WizardRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.GetGameUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.CalculateResultsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.GetBlockInputModelsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.InputsValidUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.StoreRoundUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.GetBlockResultsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.GetGameScoresUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.RemoveRoundUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.StoreGameFinishedUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.GetGameNameOptionsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.GetGameSettingsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.GetLastGameSettingsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.StoreGameInfoUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.general.TrackAnalyticsEventUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.player.GetPlayerNamesUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames.GetAllSavedGamesUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames.RemoveAllGamesFromSavedGamesUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames.RemoveGameFromSavedGameUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.settings.GetDisplayAlwaysOnUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.settings.SetDisplayAlwaysOnUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.ClearStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetGamesPlayedCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetMostWinsStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetPlayerCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetTopPointsStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.user.SetShowTrumpDialogEnabledUseCase
import com.tobiashehrlein.tobiswizardblock.navigation.PageNavigatorImpl
import com.tobiashehrlein.tobiswizardblock.presentation.about.AboutViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.about.AboutViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.block.GameBlockViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.GameBlockViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.BlockInputViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.BlockInputViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.correcttips.BlockInputCorrectTipsChoosePlayerViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.correcttips.BlockInputCorrectTipsChoosePlayerViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.block.results.BlockResultsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.results.BlockResultsViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.block.scores.BlockScoresViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.scores.BlockScoresViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.block.trump.BlockTrumpViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.trump.BlockTrumpViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.GameSettingsViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.gamerules.GameRulesViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.gamerules.GameRulesViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder.PlayerOrderViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder.PlayerOrderViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerselection.PlayerSelectionViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerselection.PlayerSelectionViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.navigation.NavigationViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.navigation.NavigationViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.SavedGamesViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.SavedGamesViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.info.SavedGamesInfoViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.info.SavedGamesInfoViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.settings.SettingsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.settings.SettingsViewModelImpl
import com.tobiashehrlein.tobiswizardblock.presentation.statistics.StatisticsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.statistics.StatisticsViewModelImpl
import com.tobiashehrlein.tobiswizardblock.repositories.datasource.datastore.WizardDataStoreImpl
import com.tobiashehrlein.tobiswizardblock.repositories.datasource.firebase.FirebaseDatasourceImpl
import com.tobiashehrlein.tobiswizardblock.repositories.datasource.processor.BlockInputProcessorImpl
import com.tobiashehrlein.tobiswizardblock.repositories.datasource.processor.BlockResultsProcessorImpl
import com.tobiashehrlein.tobiswizardblock.repositories.datasource.sharedpref.WizardBlockSharedPreferences
import com.tobiashehrlein.tobiswizardblock.repositories.repository.GameRepositoryImpl
import com.tobiashehrlein.tobiswizardblock.repositories.repository.GameSettingsRepositoryImpl
import com.tobiashehrlein.tobiswizardblock.repositories.repository.StatisticsRepositoryImpl
import com.tobiashehrlein.tobiswizardblock.repositories.repository.UserRepositoryImpl
import com.tobiashehrlein.tobiswizardblock.repositories.repository.WizardRepositoryImpl
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ResourceHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module

object Koin {

    private val single = module {
        // repositories
        single<GameSettingsRepository> {
            GameSettingsRepositoryImpl(
                playerCache = get(),
                gameCache = get()
            )
        }
        single<GameRepository> {
            GameRepositoryImpl(
                gameCache = get(),
                blockInputProcessor = get(),
                blockResultsProcessor = get()
            )
        }
        single<UserRepository> {
            UserRepositoryImpl(
                userSettingsPersistence = get(),
                settingsDataStore = get()
            )
        }
        single<WizardRepository> {
            WizardRepositoryImpl(
                analyticsDatasource = get()
            )
        }
        single<StatisticsRepository> {
            StatisticsRepositoryImpl(
                gameCache = get()
            )
        }

        // processor
        single<BlockInputProcessor> {
            BlockInputProcessorImpl()
        }
        single<BlockResultsProcessor> {
            BlockResultsProcessorImpl()
        }

        // datasource
        single {
            WizardBlockSharedPreferences(
                context = get()
            )
        } binds (arrayOf(UserSettingsPersistence::class))
        single {
            FirebaseDatasourceImpl(
                firebaseAnalytics = get()
            )
        } binds (arrayOf(AnalyticsDatasource::class))
        single {
            WizardDataStoreImpl(
                context = get()
            )
        } binds (arrayOf(SettingsDataStore::class))

        // other
        single { Firebase.analytics }
    }

    private val factory = module {
        // navigation handler
        factory<PageNavigator> { (activity: AppCompatActivity, navHostController: NavHostController, resourceHelper: ResourceHelper) ->
            PageNavigatorImpl(
                activity = activity,
                navHostController = navHostController,
                resourceHelper = resourceHelper
            )
        }

        // usecases
        factory {
            GetGameSettingsUseCase(
                gameSettingsRepository = get()
            )
        }
        factory {
            GetLastGameSettingsUseCase(
                gameSettingsRepository = get()
            )
        }
        factory {
            GetPlayerNamesUseCase(
                gameSettingsRepository = get()
            )
        }
        factory {
            StoreGameInfoUseCase(
                gameSettingsRepository = get()
            )
        }
        factory {
            GetGameNameOptionsUseCase(
                gameSettingsRepository = get()
            )
        }
        factory {
            GetGameUseCase(
                gameRepository = get()
            )
        }
        factory {
            GetBlockInputModelsUseCase(
                gameRepository = get()
            )
        }
        factory {
            InputsValidUseCase(
                gameRepository = get()
            )
        }
        factory {
            StoreRoundUseCase(
                gameRepository = get()
            )
        }
        factory {
            CalculateResultsUseCase(
                gameRepository = get()
            )
        }
        factory {
            GetBlockResultsUseCase(
                gameRepository = get()
            )
        }
        factory {
            GetGameScoresUseCase(
                gameRepository = get()
            )
        }
        factory {
            StoreGameFinishedUseCase(
                gameRepository = get()
            )
        }
        factory {
            RemoveRoundUseCase(
                gameRepository = get()
            )
        }
        factory {
            GetAllSavedGamesUseCase(
                gameRepository = get()
            )
        }
        factory {
            RemoveGameFromSavedGameUseCase(
                gameRepository = get()
            )
        }
        factory {
            RemoveAllGamesFromSavedGamesUseCase(
                gameRepository = get()
            )
        }
        factory {
            IsShowTrumpDialogEnabledUseCase(
                userRepository = get()
            )
        }
        factory {
            SetShowTrumpDialogEnabledUseCase(
                userRepository = get()
            )
        }
        factory {
            TrackAnalyticsEventUseCase(
                wizardRepository = get()
            )
        }
        factory {
            GetDisplayAlwaysOnUseCase(
                userRepository = get()
            )
        }
        factory {
            SetDisplayAlwaysOnUseCase(
                userRepository = get()
            )
        }
        factory {
            GetMostWinsStatisticsUseCase(
                statisticsRepository = get()
            )
        }
        factory {
            GetPlayerCountStatisticsUseCase(
                statisticsRepository = get()
            )
        }
        factory {
            GetTopPointsStatisticsUseCase(
                statisticsRepository = get()
            )
        }
        factory {
            GetGamesPlayedCountStatisticsUseCase(
                statisticsRepository = get()
            )
        }
        factory {
            ClearStatisticsUseCase(
                statisticsRepository = get()
            )
        }
    }

    private val viewModel = module {
        viewModel<NavigationViewModel> {
            NavigationViewModelImpl()
        }
        viewModel<GameSettingsViewModel> {
            GameSettingsViewModelImpl(
                getLastGameSettingsUseCase = get(),
            )
        }
        viewModel<PlayerSelectionViewModel> {
            PlayerSelectionViewModelImpl(
                getPlayerNamesUseCase = get()
            )
        }
        viewModel<PlayerOrderViewModel> {
            PlayerOrderViewModelImpl()
        }
        viewModel<GameRulesViewModel> {
            GameRulesViewModelImpl(
                storeGameInfoUseCase = get(),
                getGameNameOptionsUseCase = get()
            )
        }
        viewModel<GameBlockViewModel> { (gameId: Long) ->
            GameBlockViewModelImpl(
                gameId = gameId,
                trackAnalyticsEventUseCase = get()
            )
        }
        viewModel<BlockResultsViewModel> {
            BlockResultsViewModelImpl(
                getGameUseCase = get(),
                getBlockResultsUseCase = get(),
                getGameScoresUseCase = get(),
                storeGameFinishedUseCase = get(),
                storeRoundUseCase = get(),
                removeRoundUseCase = get(),
                isShowTrumpDialogEnabledUseCase = get()
            )
        }
        viewModel<BlockInputViewModel> { (gameId: Long) ->
            BlockInputViewModelImpl(
                gameId = gameId,
                getGameUseCase = get(),
                getBlockInputModelsUseCase = get(),
                inputsValidUseCase = get(),
                storeRoundUseCase = get(),
                calculateResultsUseCase = get()
            )
        }
        viewModel<BlockInputCorrectTipsChoosePlayerViewModel> {
            BlockInputCorrectTipsChoosePlayerViewModelImpl()
        }
        viewModel<BlockScoresViewModel> {
            BlockScoresViewModelImpl()
        }
        viewModel<AboutViewModel> {
            AboutViewModelImpl()
        }
        viewModel<BlockTrumpViewModel> { (selectedTrumpType: TrumpType) ->
            BlockTrumpViewModelImpl(
                selectedTrumpType = selectedTrumpType,
                isShowTrumpDialogEnabledUseCase = get(),
                setShowTrumpDialogEnabledUseCase = get()
            )
        }
        viewModel<SavedGamesViewModel> {
            SavedGamesViewModelImpl(
                getAllSavedGamesUseCase = get(),
                removeGameFromSavedGameUseCase = get(),
                removeAllGamesFromSavedGamesUseCase = get()
            )
        }
        viewModel<SavedGamesInfoViewModel> { (gameSettings: GameSettings) ->
            SavedGamesInfoViewModelImpl(
                gameSettings = gameSettings
            )
        }
        viewModel<SettingsViewModel> {
            SettingsViewModelImpl(
                getDisplayAlwaysOnUseCase = get(),
                setDisplayAlwaysOnUseCase = get()
            )
        }
        viewModel<StatisticsViewModel> {
            StatisticsViewModelImpl(
                getMostWinsStatisticsUseCase = get(),
                getPlayerCountStatisticsUseCase = get(),
                getTopPointsStatisticsUseCase = get(),
                getGamesPlayedCountStatisticsUseCase = get(),
                clearStatisticsUseCase = get()
            )
        }
    }

    val modules: List<Module>
        get() = listOf(
            single,
            factory,
            viewModel,
            databaseModule
        )
}
