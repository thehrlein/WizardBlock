package com.tobiashehrlein.tobiswizardblock.feature.block.trump

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.Trump
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.feature.block.R
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.WidgetTrumpSelectionGroupBinding

class TrumpSelectionGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetTrumpSelectionGroupBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.widget_trump_selection_group,
        this,
        true
    )
    private val items = mutableMapOf<TrumpType.Selected, TrumpSelectionItem>()
    private var onCheckedChange: ((TrumpType) -> Unit)? = null

    init {
        setItems()
    }

    private fun setItems() {
        binding.groupOne.removeAllViews()
        binding.groupTwo.removeAllViews()

        binding.groupOne.addView(
            TrumpSelectionItem(context).apply {
                setItem(
                    Trump(
                        context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_trump_type_blue),
                        TrumpType.Selected.Blue
                    )
                ) {
                    setTrumpSelected(it)
                }
            }.also {
                items[TrumpType.Selected.Blue] = it
            }
        )
        binding.groupOne.addView(
            TrumpSelectionItem(context).apply {
                setItem(
                    Trump(
                        context.getString(
                            com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_trump_type_green
                        ),
                        TrumpType.Selected.Green
                    )
                ) {
                    setTrumpSelected(it)
                }
            }.also {
                items[TrumpType.Selected.Green] = it
            }
        )
        binding.groupTwo.addView(
            TrumpSelectionItem(context).apply {
                setItem(
                    Trump(
                        context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_trump_type_red),
                        TrumpType.Selected.Red
                    )
                ) {
                    setTrumpSelected(it)
                }
            }.also {
                items[TrumpType.Selected.Red] = it
            }
        )
        binding.groupTwo.addView(
            TrumpSelectionItem(context).apply {
                setItem(
                    Trump(
                        context.getString(
                            com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_trump_type_yellow
                        ),
                        TrumpType.Selected.Yellow
                    )
                ) {
                    setTrumpSelected(it)
                }
            }.also {
                items[TrumpType.Selected.Yellow] = it
            }
        )
    }

    private fun setTrumpSelected(trumpType: TrumpType) {
        for ((type, item) in items) {
            if (type != trumpType) {
                item.setChecked(false)
            }
        }
        onCheckedChange?.invoke(trumpType)
    }

    fun setSelectedItem(selectedTrumpType: TrumpType?) {
        items.forEach {
            it.value.setChecked(it.key == selectedTrumpType)
        }
    }

    fun setOnCheckedChangeListener(onCheckedChange: (TrumpType) -> Unit) {
        this.onCheckedChange = onCheckedChange
    }

    fun reset() {
        items.forEach {
            it.value.setChecked(false)
        }
    }
}
