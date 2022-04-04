package com.tobiashehrlein.tobiswizardblock.ui_navigation

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.presentation.navigation.NavigationViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.ui_navigation.databinding.FragmentNavigationBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NavigationFragment : BaseFragment<NavigationViewModel, FragmentNavigationBinding>() {

    override val viewModel: NavigationViewModel by sharedViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_navigation

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
    }
}