package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerselection

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class PlayerSelectionViewModel : BaseToolbarViewModelImpl() {

    abstract val playerNameOptions: LiveData<Set<String>>
    abstract val playerCount: LiveData<Int>
    abstract val playerNames: LiveData<List<String>>

    abstract fun setPlayerCount(playerCount: Int)
    abstract fun setPlayerNames(playerNames: List<String>)
    abstract fun onProceedClicked(inputs: List<Pair<Int, String?>>?)
}