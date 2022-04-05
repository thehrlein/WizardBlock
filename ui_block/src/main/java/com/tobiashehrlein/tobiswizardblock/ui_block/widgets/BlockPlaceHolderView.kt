package com.tobiashehrlein.tobiswizardblock.ui_block.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockPlaceholder
import com.tobiashehrlein.tobiswizardblock.presentation.block.results.BlockResultsInteractions
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.ViewBlockPlaceholderBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater

class BlockPlaceHolderView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: ViewBlockPlaceholderBinding =
        DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.view_block_placeholder,
            this,
            true
        )

    fun setPlaceHolder(item: BlockPlaceholder, blockResultsInteractions: BlockResultsInteractions) {
        binding.executeAfter {
            this.item = item
        }

        binding.root.setOnClickListener {
            blockResultsInteractions.onTrumpClicked(item.trumpType)
        }
    }
}
