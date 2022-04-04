package com.tobiashehrlein.tobiswizardblock.ui_block.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockName
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.ViewBlockNameBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater

class BlockNameView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding : ViewBlockNameBinding =
        DataBindingUtil.inflate(context.layoutInflater,
        R.layout.view_block_name,
        this,
        true)


    fun setName(item: BlockName) {
        binding.executeAfter {
            this.item = item
        }
    }
}