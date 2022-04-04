package com.tobiashehrlein.tobiswizardblock.ui_saved_games

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.SavedGamesViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogRequestCode
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.SwipeToDeleteCallback
import com.tobiashehrlein.tobiswizardblock.ui_saved_games.databinding.FragmentSavedGamesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SavedGamesFragment : BaseFragment<SavedGamesViewModel, FragmentSavedGamesBinding>(), DialogInteractor {

    override val viewModel: SavedGamesViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_saved_games
    override val viewModelVariableId: Int = BR.viewModel
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        SavedGamesAdapter(viewModel).also { savedGamesAdapter ->
            binding.gameList.apply {
                adapter = savedGamesAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))

                val itemTouchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(context) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        if (viewHolder is SavedGameViewHolder) {
                            viewModel.onGameRemoved(viewHolder.binding.item)
                        }
                    }
                })

                itemTouchHelper.attachToRecyclerView(this)
            }

            viewModel.savedGames.observe(viewLifecycleOwner) {
                savedGamesAdapter.submitList(it)
            }
            viewModel.noSavedGames.observe(viewLifecycleOwner) {
                requireActivity().invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_saved_games, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = viewModel.noSavedGames.value == false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.onDeleteActionClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.SAVED_GAMES_DELETE -> when (resultCode) {
                DialogResultCode.POSITIVE -> viewModel.onDeleteGamesConfirmed()
            }
        }
    }
}
