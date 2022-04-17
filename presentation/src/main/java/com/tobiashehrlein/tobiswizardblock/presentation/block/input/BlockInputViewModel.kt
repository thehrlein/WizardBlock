package com.tobiashehrlein.tobiswizardblock.presentation.block.input

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModelImpl

abstract class BlockInputViewModel : BaseViewModelImpl(), BlockInputInteractions {

    abstract val inputType: LiveData<InputType>
    abstract val inputModels: LiveData<List<InputDataItem>>
    abstract val game: LiveData<Game>
    abstract val inputsValid: LiveData<Boolean>
    abstract val showAnniversaryOption: LiveData<Boolean>
    abstract val summedInputs: LiveData<Int>
    abstract val trumpType: LiveData<TrumpType>
    abstract val bombPlayed: LiveData<Boolean>
    abstract val cloudCardPlayed: LiveData<Boolean>
    abstract val playerTipDataCorrectedEvent: LiveData<PlayerTipData>

    abstract fun onInfoIconClicked()
    abstract fun onCorrectTipsClicked()
    abstract fun onBlockInputBombPlayedInfoClicked()
    abstract fun correctPlayerTips(correctedPlayerTipData: List<PlayerTipData>)
    abstract fun onSaveClicked()
    abstract fun onBlockPlayedSwitchChanged(bombPlayed: Boolean)
}
