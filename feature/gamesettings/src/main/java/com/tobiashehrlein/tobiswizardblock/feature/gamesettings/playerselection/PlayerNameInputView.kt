package com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerselection

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.core.entities.settings.player.PlayerError
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.R
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.databinding.WidgetPlayerNameInputBinding

class PlayerNameInputView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetPlayerNameInputBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_player_name_input,
        this,
        true
    )

    fun setVisible(visible: Boolean) {
        binding.rootLayout.isVisible = visible
    }

    fun setPlayerName(text: String?) {
        binding.autoCompleteText.setText(text)
    }

    fun getPlayerName(): String? {
        return binding.autoCompleteText.text.toString()
    }

    fun setHint(hint: String) {
        binding.textInputLayoutOutlined.hint = hint
    }

    fun addTextChangeListener(textChangeListener: (String) -> Unit) {
        binding.autoCompleteText.addTextChangedListener { editable ->
            editable?.let {
                textChangeListener.invoke(it.toString())
            }
        }
    }

    fun setAdapter(items: Set<String>) {
        binding.autoCompleteText.setAdapter(
            ArrayAdapter(
                context,
                com.tobiashehrlein.tobiswizardblock.feature.common.R.layout.widget_auto_complete_text_row,
                items.toList()
            )
        )
    }

    fun setError(errorText: String?) {
        binding.textInputLayoutOutlined.apply {
            if (errorText.isNullOrEmpty()) {
                error = null
                isErrorEnabled = false
            } else {
                error = errorText
                isErrorEnabled = true
            }
        }
    }

    fun setPlayerError(errorType: PlayerError?) {
        setError(
            when (errorType) {
                PlayerError.EMPTY -> context.getString(
                    com.tobiashehrlein.tobiswizardblock.feature.common.R.string.player_selection_player_name_empty
                )
                PlayerError.DUPLICATE -> context.getString(
                    com.tobiashehrlein.tobiswizardblock.feature.common.R.string.player_selection_player_name_duplicate
                )
                else -> null
            }
        )
    }
}
