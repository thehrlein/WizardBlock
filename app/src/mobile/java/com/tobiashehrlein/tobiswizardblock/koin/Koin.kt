package com.tobiashehrlein.tobiswizardblock.koin

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.core.presentation.navigation.BaseNavigationViewModel
import com.tobiashehrlein.tobiswizardblock.core.presentation.navigation.NavigationViewModelImpl
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.ResourceHelper
import com.tobiashehrlein.tobiswizardblock.navigation.PageNavigatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object Koin {

    private val single = module {

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

    }

    private val viewModel = module {
        viewModel<BaseNavigationViewModel> {
            NavigationViewModelImpl(
                trackAnalyticsUserPropertyUseCase = get()
            )
        }
    }

    val modules: List<Module>
        get() = listOf(
            single,
            factory,
            viewModel,
        )
}
