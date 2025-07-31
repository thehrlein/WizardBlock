package com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerselection

import com.tobiashehrlein.tobiswizardblock.core.entities.settings.player.PlayerError

class PlayerSelectionHandler {

    private val inputViews = mutableListOf<PlayerNameInputView>()
    private val inputStates = mutableMapOf<Int, Pair<Boolean, String?>>()

    fun setInputViews(views: List<PlayerNameInputView>) {
        inputViews.clear()
        inputStates.clear()
        views.forEach {
            it.setPlayerError(null)
            inputViews.add(it)
        }

        views.forEachIndexed { index, view ->
            val position = index + 1
            inputStates[position] = Pair(false, null)
            setTextChangeListenerFor(view, position)
        }
    }

    private fun setTextChangeListenerFor(inputView: PlayerNameInputView, position: Int) {
        inputView.addTextChangeListener {
            inputStates[position] = Pair(true, it)
            if (it.isNotEmpty()) {
                inputView.setError(null)
            }
        }
    }

    fun setPlayerNames(playerNames: List<String>?) {
        if (playerNames == null) return
        playerNames.forEachIndexed { index, name ->
            inputViews[index].setPlayerName(name)
        }
    }

    fun setPlayerCount(count: Int) {
        inputViews.forEachIndexed { index, inputView ->
            val visible = index < count
            inputView.setVisible(visible)
            inputStates[index + 1] = Pair(visible, inputStates[index + 1]?.second)
        }
    }

    fun setPlayerNameOptions(playerNames: Set<String>?) {
        playerNames?.let { names ->
            inputViews.forEach {
                it.setAdapter(names)
            }
        }
    }

    fun getValues(): List<Pair<Int, String?>>? {
        val visibleInputs = inputStates.filter { it.value.first }
        var isError = false
        visibleInputs.forEach { input ->
            isError = checkIfValidOrError(input, visibleInputs, isError)
        }

        return if (isError) {
            null
        } else {
            inputStates.filter { it.value.first }.map {
                it.key to it.value.second
            }
        }
    }

    private fun checkIfValidOrError(
        input: Map.Entry<Int, Pair<Boolean, String?>>,
        visibleInputs: Map<Int, Pair<Boolean, String?>>,
        isError: Boolean
    ): Boolean {
        var isLocalError = isError
        val text = input.value.second
        val error = when {
            text.isNullOrEmpty() -> PlayerError.EMPTY
            visibleInputs.filter { it.key != input.key }.map { it.value.second }
                .contains(text) -> PlayerError.DUPLICATE
            else -> null
        }

        if (error != null) {
            isLocalError = true
        }
        inputViews[input.key - 1].setPlayerError(error)

        return isLocalError
    }

    fun onDestroy() {
        inputViews.clear()
        inputStates.clear()
    }
}
