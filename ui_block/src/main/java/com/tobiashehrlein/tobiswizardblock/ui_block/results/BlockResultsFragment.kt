package com.tobiashehrlein.tobiswizardblock.ui_block.results

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItem
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockName
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockPlaceholder
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockResult
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockRound
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.block.GameBlockViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.results.BlockResultsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_block.BR
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.FragmentBlockResultsBinding
import com.tobiashehrlein.tobiswizardblock.ui_block.widgets.BlockNameView
import com.tobiashehrlein.tobiswizardblock.ui_block.widgets.BlockPlaceHolderView
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogRequestCode
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ItemDecoration
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


private const val KONFETTI_MIN_DEGREES = 0.0
private const val KONFETTI_MAX_DEGREES = 360.0
private const val KONFETTI_MIN_SPEED = 1f
private const val KONFETTI_MAX_SPEED = 5f
private const val KONFETTI_TIME_TO_LIVE = 2000L
private const val KONFETTI_SIZE_IN_DP = 12
private const val KONFETTI_SIZE_MASS = 5f
private const val KONFETTI_POSITION_OFFSET = 50f
private const val KONFETTI_PARTICLES_PER_SECOND = 300
private const val KONFETTI_EMITTING_TIME = 5000L

class BlockResultsFragment :
    BaseToolbarFragment<BlockResultsViewModel, GameBlockViewModel, FragmentBlockResultsBinding>(),
    DialogInteractor {

    override val viewModel: BlockResultsViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_block_results
    override val activityToolbarViewModel: GameBlockViewModel by sharedViewModel()
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        viewModel.gameName.observe(viewLifecycleOwner) {
            activityToolbarViewModel.setTitle(it)
        }
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.None)
        activityToolbarViewModel.gameId.observe(viewLifecycleOwner) {
            viewModel.setGameId(it)
        }
        viewModel.editInputEnabled.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
        viewModel.finishManuallyEnabled.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
        viewModel.showGameFinishedEvent.observe(viewLifecycleOwner) { points ->
            binding.gameBlockKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(KONFETTI_MIN_DEGREES, KONFETTI_MAX_DEGREES)
                .setSpeed(KONFETTI_MIN_SPEED, KONFETTI_MAX_SPEED)
                .setFadeOutEnabled(true)
                .setTimeToLive(KONFETTI_TIME_TO_LIVE)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(KONFETTI_SIZE_IN_DP, KONFETTI_SIZE_MASS))
                .setPosition(
                    -KONFETTI_POSITION_OFFSET,
                    binding.gameBlockKonfetti.width + KONFETTI_POSITION_OFFSET,
                    -KONFETTI_POSITION_OFFSET,
                    -KONFETTI_POSITION_OFFSET
                )
                .streamFor(KONFETTI_PARTICLES_PER_SECOND, KONFETTI_EMITTING_TIME)

            ReviewManagerFactory.create(requireActivity()).run {
                requestReviewFlow()
                    .addOnCompleteListener { reviewInfo ->
                        if (reviewInfo.isSuccessful) {
                            Timber.d("Request Review Successful")
                            launchReviewFlow(requireActivity(), reviewInfo.result)
                                .addOnFailureListener {
                                    Timber.d("Request launch failed : $it")
                                }.addOnSuccessListener {
                                    Timber.d("Finished with rating")
                                }
                        }
                    }
            }

            activityToolbarViewModel.trackGameFinished(points)

            requireActivity().invalidateOptionsMenu()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.showExitDialog()
        }
        initAdapter()
    }

    private fun initAdapter() {
        BlockResultsAdapter().also { blockResultAdapter ->
            binding.gameList.apply {
                adapter = blockResultAdapter
                addItemDecoration(
                    ItemDecoration(
                        LinearLayout.VERTICAL,
                        AppCompatResources.getDrawable(context, R.drawable.wb_shape_divider)
                    )
                )
                addItemDecoration(
                    ItemDecoration(
                        LinearLayout.HORIZONTAL,
                        AppCompatResources.getDrawable(context, R.drawable.wb_shape_divider)
                    )
                )
            }
            viewModel.blockItems.observe(viewLifecycleOwner) { blockItems ->
                val columnCount = viewModel.columnCount.value ?: 0
                val headerItems = blockItems.filter { it is BlockPlaceholder || it is BlockName }
                val gameItems = blockItems.toMutableList().apply {
                    removeAll(headerItems)
                }

                addHeaderItems(headerItems)
                setGridSize(columnCount, blockResultAdapter)
                blockResultAdapter.submitList(gameItems)
                binding.gameList.scrollToPosition(blockItems.count { it is BlockRound || it is BlockResult } - 1)
            }
        }
    }

    private fun setGridSize(
        columnCount: Int,
        blockResultAdapter: BlockResultsAdapter
    ) {
        binding.gameList.layoutManager =
            GridLayoutManager(requireContext(), columnCount).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (blockResultAdapter.getItemViewType(position)) {
                            R.layout.item_block_round -> 1
                            R.layout.item_block_result -> 2
                            else -> 0
                        }
                    }
                }
            }
    }

    private fun addHeaderItems(headerItems: List<BlockItem>) {
        binding.gamePlayerListLayout.apply {
            removeAllViews()
            headerItems.forEach {
                if (it is BlockPlaceholder) {
                    addView(
                        BlockPlaceHolderView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                            )
                            setPlaceHolder(it, viewModel)
                        }
                    )
                } else if (it is BlockName) {
                    addView(
                        BlockNameView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                2f
                            )
                            setName(it)
                        }
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_block, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete_input).apply {
            isEnabled = viewModel.editInputEnabled.value == true
            title = getString(
                if (viewModel.inputType.value == InputType.RESULT) {
                    R.string.game_block_results_menu_delete_last_tip_input
                } else {
                    R.string.game_block_results_menu_delete_last_result_input
                }
            )
        }

        menu.findItem(R.id.action_finish_game).isEnabled = viewModel.finishManuallyEnabled.value == true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_trophy -> {
                viewModel.onTrophyClicked()
                true
            }
            R.id.action_delete_input -> {
                viewModel.onMenuDeleteInputClicked()
                true
            }
            R.id.action_finish_game -> {
                viewModel.finishGameManuallyClicked()
                true
            }
            R.id.action_info -> {
                viewModel.onMenuInfoClicked()
                true
            }
            R.id.action_settings -> {
                viewModel.onMenuSettingsClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.CHOOSE_TRUMP -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> {
                        (
                                data?.getSerializableExtra(DialogEntity.KEY_DIALOG_ENTITY) as?
                                        DialogEntity.Custom.Trump
                                )?.let {
                                viewModel.updateTrumpType(it.selectedTrumpType)
                            }
                    }
                }
            }
            DialogRequestCode.GAME_BLOCK_FINISH_MANUALLY -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> viewModel.onFinishGameManuallyConfirmed()
                }
            }
        }
    }
}
