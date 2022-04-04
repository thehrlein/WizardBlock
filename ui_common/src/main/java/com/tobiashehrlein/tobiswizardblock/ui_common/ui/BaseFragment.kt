package com.tobiashehrlein.tobiswizardblock.ui_common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tobiashehrlein.tobiswizardblock.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ResourceHelperImpl
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment<Model : BaseViewModel, Binding : ViewDataBinding> : Fragment() {

	protected abstract val viewModel: Model
	protected abstract val viewModelVariableId: Int
	@get:LayoutRes
	protected abstract val layoutId: Int
	protected lateinit var binding: Binding
	open val hasOptionsMenu: Boolean = false

	private val pageNavigator: PageNavigator by inject {
		parametersOf(requireActivity(), findNavController(), ResourceHelperImpl(requireContext()))
	}

	override fun onCreateView(
	    inflater: LayoutInflater,
	    container: ViewGroup?,
	    savedInstanceState: Bundle?
	): View? {
		setHasOptionsMenu(hasOptionsMenu)
		return DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false).also {
			binding = it
			binding.lifecycleOwner = this
			binding.setVariable(viewModelVariableId, viewModel)
			listenToNavigationEvents()
			onBindingCreated(savedInstanceState)
		}.root
	}

	@CallSuper
	open fun onBindingCreated(savedInstanceState: Bundle?) = Unit

	private fun listenToNavigationEvents() {
		viewModel.navigationEvent.observe(viewLifecycleOwner, {
			pageNavigator.navigateTo(it)
		})
	}
}
