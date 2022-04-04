package com.tobiashehrlein.tobiswizardblock.presentation.block.trump

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.user.SetShowTrumpDialogEnabledUseCase
import kotlinx.coroutines.launch

class BlockTrumpViewModelImpl(
    private val isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase,
    private val setShowTrumpDialogEnabledUseCase: SetShowTrumpDialogEnabledUseCase
) : BlockTrumpViewModel() {

    override val showTrumpDialogEnabled = MutableLiveData<Boolean>()

    init {
        checkShowTrumpDialogEnabled()
    }

    private fun checkShowTrumpDialogEnabled() {
        viewModelScope.launch {
            showTrumpDialogEnabled.value =
                when (val result = isShowTrumpDialogEnabledUseCase.invoke()) {
                    is AppResult.Success -> result.value
                    is AppResult.Error -> false
                }
        }
    }

    override fun onAutoShowTrumpDialogChanged(checked: Boolean) {
        if (checked == showTrumpDialogEnabled.value) return
        viewModelScope.launch {
            when (val result = setShowTrumpDialogEnabledUseCase.invoke(checked)) {
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }
}
