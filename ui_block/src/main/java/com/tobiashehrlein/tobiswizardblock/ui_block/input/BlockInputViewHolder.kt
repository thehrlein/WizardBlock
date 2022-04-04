package com.tobiashehrlein.tobiswizardblock.ui_block.input

import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.BlockInputInteractions
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.ItemBlockInputBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter

class BlockInputViewHolder(private val binding: ItemBlockInputBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        inputDataItem: InputDataItem,
        interactions: BlockInputInteractions
    ) {
        bindInputData(inputDataItem, interactions)
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                inputDataItem.userInput = progress
                bindInputData(inputDataItem, interactions)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // not needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // not needed
            }
        })

        binding.buttonDecrease.setOnClickListener {
            if (inputDataItem.userInput > 0) {
                inputDataItem.userInput = inputDataItem.userInput - 1
                bindInputData(inputDataItem, interactions)
            }
        }

        binding.buttonIncrease.setOnClickListener {
            if (inputDataItem.userInput < inputDataItem.cards) {
                inputDataItem.userInput = inputDataItem.userInput + 1
                bindInputData(inputDataItem, interactions)
            }
        }
    }

    fun bindInputData(inputDataItem: InputDataItem, interactions: BlockInputInteractions) {
        binding.executeAfter {
            this.item = inputDataItem
        }
        interactions.onInputChanged()
    }
}