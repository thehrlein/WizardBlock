package com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tobiashehrlein.tobiswizardblock.entities.navigation.PageNavigator
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ResourceHelperImpl
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseDialogFragment<Model : BaseViewModel, Binding : ViewDataBinding> : DialogInteractionFragment() {

    protected abstract val viewModel: Model
    protected abstract val layoutId: Int
    protected abstract val viewModelVariableId: Int
    protected lateinit var binding: Binding

    private val navigationHandler: PageNavigator by inject {
        parametersOf(requireActivity(), findNavController(), ResourceHelperImpl(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        DataBindingUtil.inflate<Binding>(
            LayoutInflater.from(context), layoutId, null, false
        ).also {
            binding = it
            binding.lifecycleOwner = this
            binding.setVariable(viewModelVariableId, viewModel)
            onBindingCreated()
        }

        return createDialog(savedInstanceState, binding.root)
    }

    abstract fun createDialog(savedInstanceState: Bundle?, view: View): AlertDialog

    @CallSuper
    open fun onBindingCreated() {
        viewModel.navigationEvent.observe(
            this,
            Observer {
                navigationHandler.navigateTo(it)
            }
        )
    }
}
