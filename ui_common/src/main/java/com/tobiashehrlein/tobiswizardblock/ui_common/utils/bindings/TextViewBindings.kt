package com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings

import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.savedgames.SavedGameEntity
import com.tobiashehrlein.tobiswizardblock.old.utils.helper.DateHelper
import com.tobiashehrlein.tobiswizardblock.ui_common.R

@BindingAdapter("resultText")
fun TextView.setResultText(value: Int?) {
    text = value?.let {
        it.toString()
    }
}

@BindingAdapter("differenceText")
fun TextView.setDifferenceText(value: Int?) {
    text = value?.let {
        setTextColor(ContextCompat.getColor(context, if (it > 0) R.color.block_result_positive_difference else R.color.block_result_negative_difference))
        it.toString()
    }
}

@BindingAdapter("inputTitle")
fun TextView.setInputTitle(game: Game?) {
    if (game?.currentRoundNumber == null) return
    text = context.getString(R.string.block_input_general_title, game.currentRoundNumber)
}

@BindingAdapter("bold")
fun TextView.setBold(bold: Boolean?) {
    setTypeface(null, if (bold == true) Typeface.BOLD else Typeface.NORMAL)
}

@BindingAdapter("position")
fun TextView.setPosition(pos: Int?) {
    if (pos == null) return
    text = context.getString(R.string.block_scores_position, pos)
}

@BindingAdapter("playerNames")
fun TextView.setPlayerNames(playerNames: List<String>) {
    text = playerNames.joinToString { it }
}

@BindingAdapter("round")
fun TextView.setRound(item: SavedGameEntity) {
    text = if (item.gameFinished) context.getString(R.string.saved_game_finished) else context.getString(R.string.saved_game_current_max_round, item.currentRound, item.maxRound)
}

@BindingAdapter("gameStartDate")
fun TextView.setGameStartDate(gameStartDate: Long) {
    text = DateHelper.getGameStartDate(gameStartDate)
}