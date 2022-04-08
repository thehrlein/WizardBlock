package com.tobiashehrlein.tobiswizardblock.ui_settings

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.presentation.settings.SettingsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.ui_settings.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {

    override val viewModel: SettingsViewModel by sharedViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_settings

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        binding.settingsDisplayAlwaysOnSwitchView.onCheckedChange {
            viewModel.onDisplayAlwaysOnChecked(it)
        }
    }
}