package com.tobiashehrlein.tobiswizardblock.listener;

import androidx.annotation.MenuRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;


/**
 * Created by Tobias Hehrlein on 28.11.2017.
 */

public interface FragmentNavigationListener {

    void replaceFragment(Fragment fragment, boolean addToStack);
    void showDialog(DialogFragment dialogFragment);
    void setToolbarTitle(String title);
    void setToolbarBackButtonEnabled();
    void setToolbarBackButtonDisabled();
    void setBackPressEnabled(boolean enabled);
    void inflateToolbarMenu(@MenuRes int menu_game_block);
    void disableToolbarMenu();
    void setToolbarMenuItemListener(Toolbar.OnMenuItemClickListener listener);
    void startNewGame();
    void disableModifyLastInputAction();
    void enableModifyLastInputAction();
    void setLastInputActionTipps();
    void setLastInputActionResults();
}
