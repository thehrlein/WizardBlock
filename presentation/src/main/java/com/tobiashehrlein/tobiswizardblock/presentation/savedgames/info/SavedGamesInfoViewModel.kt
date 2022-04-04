package com.tobiashehrlein.tobiswizardblock.presentation.savedgames.info

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModelImpl

abstract class SavedGamesInfoViewModel : BaseViewModelImpl() {

    abstract val gameSettings: LiveData<GameSettings>
}