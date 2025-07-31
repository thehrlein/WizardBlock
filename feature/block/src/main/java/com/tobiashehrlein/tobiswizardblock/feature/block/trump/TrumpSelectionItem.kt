package com.tobiashehrlein.tobiswizardblock.feature.block.trump

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.game.result.Trump
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.feature.block.R
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.WidgetTrumpSelectionItemBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.executeAfter
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater

class TrumpSelectionItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetTrumpSelectionItemBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_trump_selection_item,
        this,
        true
    )

    fun setItem(trump: Trump, onItemClickListener: (TrumpType) -> Unit) {
        binding.executeAfter {
            this.item = trump
        }

        binding.root.setOnClickListener {
            onItemClickListener.invoke(trump.type)
            setChecked(true)
        }

        binding.radioButton.setOnClickListener {
            onItemClickListener.invoke(trump.type)
        }
    }

    fun setChecked(checked: Boolean) {
        binding.radioButton.isChecked = checked
    }

    fun isChecked(): Boolean {
        return binding.radioButton.isChecked
    }
}
