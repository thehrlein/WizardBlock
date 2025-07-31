package com.tobiashehrlein.tobiswizardblock.core.presentation.savedgames.info

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseViewModelImpl

abstract class SavedGamesInfoViewModel : BaseViewModelImpl() {

    abstract val gameSettings: LiveData<GameSettings>
}
