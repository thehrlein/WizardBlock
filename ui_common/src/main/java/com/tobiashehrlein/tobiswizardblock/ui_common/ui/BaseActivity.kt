package com.tobiashehrlein.tobiswizardblock.ui_common.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.datastore.SettingsDataStore
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ResourceHelperImpl
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity<Model : BaseViewModel, Binding : ViewDataBinding> :
    AppCompatActivity() {

    abstract val viewModel: Model
    abstract val layoutRes: Int
    abstract val viewModelVariableId: Int
    abstract val navHostFragment: Int
    protected lateinit var binding: Binding
    private val settingsDataStore: SettingsDataStore by inject()

    private val navController: NavController by lazy {
        val hostFragment =
            supportFragmentManager.findFragmentById(navHostFragment) as NavHostFragment
        hostFragment.navController
    }

    private val pageNavigator: PageNavigator by inject {
        parametersOf(this, navController, ResourceHelperImpl(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.apply {
            setVariable(viewModelVariableId, viewModel)
            lifecycleOwner = this@BaseActivity
            observeNavigationEvents()
            setUpDisplayAlwaysOn()
            onBindingCreated()
        }
    }

    private fun observeNavigationEvents() {
        viewModel.navigationEvent.observe(this) {
            pageNavigator.navigateTo(it)
        }
    }


    private fun setUpDisplayAlwaysOn() {
        lifecycleScope.launchWhenStarted {
            settingsDataStore.getDisplayAlwaysOn().collectLatest { result ->
                setDisplayAlwaysOn(when (result) {
                    is AppResult.Success -> result.value
                    is AppResult.Error -> false
                })
            }
        }
    }

    private fun setDisplayAlwaysOn(alwaysOn: Boolean) {
        if (alwaysOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    open fun onBindingCreated() = Unit
}
