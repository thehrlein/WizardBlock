package com.tobiashehrlein.tobiswizardblock.presentation.savedgames

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.savedgames.SavedGameEntity
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class SavedGamesViewModel : BaseToolbarViewModelImpl(), SavedGamesInteractions {

    abstract val savedGames: LiveData<List<SavedGameEntity>>
    abstract val noSavedGames: LiveData<Boolean>

    abstract fun onGameRemoved(item: SavedGameEntity?)
    abstract fun onDeleteActionClicked()
    abstract fun onDeleteGamesConfirmed()
}
