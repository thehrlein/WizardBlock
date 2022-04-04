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
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockName
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockPlaceholder
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
            activityToolbarViewModel.setTitle(getString(R.string.game_block_toolbar_title, it))
        }
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.None)

        activityToolbarViewModel.gameId.observe(viewLifecycleOwner) {
            viewModel.setGameId(it)
        }

        viewModel.editInputEnabled.observe(viewLifecycleOwner, {
            requireActivity().invalidateOptionsMenu()
        })

        viewModel.showGameFinishedEvent.observe(viewLifecycleOwner, {
            binding.gameBlockKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12, 5f))
                .setPosition(-50f, binding.gameBlockKonfetti.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)

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

            activityToolbarViewModel.trackGameFinished()

            requireActivity().invalidateOptionsMenu()
        })

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
            viewModel.blockItems.observe(viewLifecycleOwner, { blockItems ->
                val columnCount = viewModel.columnCount.value ?: 0
                val headerItems = blockItems.filter { it is BlockPlaceholder || it is BlockName }
                val gameItems = blockItems.toMutableList().apply {
                    removeAll(headerItems)
                }

                binding.gamePlayerListLayout.apply {
                    removeAllViews()
                    headerItems.forEach {
                        if (it is BlockPlaceholder) {
                            addView(BlockPlaceHolderView(context).apply {
                                layoutParams = LinearLayout.LayoutParams(
                                    0,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    1f
                                )
                                setPlaceHolder(it, viewModel)
                            })
                        } else if (it is BlockName) {
                            addView(BlockNameView(context).apply {
                                layoutParams = LinearLayout.LayoutParams(
                                    0,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    2f
                                )
                                setName(it)
                            })
                        }
                    }
                }

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

                blockResultAdapter.submitList(gameItems)
                binding.gameList.scrollToPosition(blockItems.size - 1)
            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_block, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete_input).isEnabled = viewModel.editInputEnabled.value == true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_trophy -> {
                viewModel.onTrophyClicked()
                true
            }
            R.id.action_delete_input -> {
                viewModel.onDeleteInputClicked()
                true
            }
            R.id.action_info -> {
                viewModel.onInfoClicked()
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
                        (data?.getSerializableExtra(DialogEntity.KEY_DIALOG_ENTITY) as? DialogEntity.Custom.Trump)?.let {
                            viewModel.updateTrumpType(it.selectedTrumpType)
                        }
                    }
                }
            }
        }
    }
}