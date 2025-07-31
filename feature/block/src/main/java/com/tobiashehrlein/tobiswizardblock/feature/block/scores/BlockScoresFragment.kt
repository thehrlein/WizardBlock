package com.tobiashehrlein.tobiswizardblock.feature.block.scores

import android.os.Bundle
import android.widget.LinearLayout
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.core.presentation.block.GameBlockViewModel
import com.tobiashehrlein.tobiswizardblock.core.presentation.block.scores.BlockScoresViewModel
import com.tobiashehrlein.tobiswizardblock.feature.block.BR
import com.tobiashehrlein.tobiswizardblock.feature.block.R
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.FragmentBlockScoresBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseToolbarFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockScoresFragment :
    BaseToolbarFragment<BlockScoresViewModel, GameBlockViewModel, FragmentBlockScoresBinding>() {

    override val viewModel: BlockScoresViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_block_scores
    override val activityToolbarViewModel: GameBlockViewModel by activityViewModel()
    private val navArgs: BlockScoresFragmentArgs by navArgs()

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(
            getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_scores_toolbar_title)
        )
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)

        initAdapter()
    }

    private fun initAdapter() {
        BlockScoresAdapter().also { scoresAdapter ->
            binding.blockScoreList.apply {
                adapter = scoresAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            }

            scoresAdapter.submitList(navArgs.gameScores.results)
        }
    }
}
