package com.tobiashehrlein.tobiswizardblock.feature.settings

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.core.presentation.settings.SettingsViewModel
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.feature.settings.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {

    override val viewModel: SettingsViewModel by activityViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_settings

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        binding.settingsDisplayAlwaysOnSwitchView.onCheckedChange {
            viewModel.onDisplayAlwaysOnChecked(it)
        }
    }
}
