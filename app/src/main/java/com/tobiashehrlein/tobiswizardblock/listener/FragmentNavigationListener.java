package com.tobiashehrlein.tobiswizardblock.listener;

import android.support.annotation.MenuRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;


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
