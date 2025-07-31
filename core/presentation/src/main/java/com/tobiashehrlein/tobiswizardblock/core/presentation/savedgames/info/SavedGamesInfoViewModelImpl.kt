package com.tobiashehrlein.tobiswizardblock.core.presentation.savedgames.info

import androidx.lifecycle.MutableLiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings

class SavedGamesInfoViewModelImpl(
    gameSettings: GameSettings
) : SavedGamesInfoViewModel() {

    override val gameSettings = MutableLiveData(gameSettings)
}
