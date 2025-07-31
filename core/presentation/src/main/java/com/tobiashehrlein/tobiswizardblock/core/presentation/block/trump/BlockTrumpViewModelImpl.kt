package com.tobiashehrlein.tobiswizardblock.core.presentation.block.trump

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.user.SetShowTrumpDialogEnabledUseCase
import kotlinx.coroutines.launch

class BlockTrumpViewModelImpl(
    selectedTrumpType: TrumpType,
    private val isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase,
    private val setShowTrumpDialogEnabledUseCase: SetShowTrumpDialogEnabledUseCase
) : BlockTrumpViewModel() {

    override val showTrumpDialogEnabled = MutableLiveData<Boolean>()
    override val selectedTrump = MutableLiveData(selectedTrumpType)

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

    override fun setSelectedTrump(trumpType: TrumpType) {
        this.selectedTrump.value = trumpType
    }

    override fun onAutoShowTrumpDialogChanged(checked: Boolean) {
        if (checked == showTrumpDialogEnabled.value) return
        viewModelScope.launch {
            when (val result = setShowTrumpDialogEnabledUseCase.invoke(checked)) {
                is AppResult.Success -> showTrumpDialogEnabled.value = checked
                is AppResult.Error -> Unit
            }
        }
    }
}
