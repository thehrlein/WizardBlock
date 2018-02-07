package com.tobiashehrlein.tobiswizardblock.ui.activity;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.support.annotation.MenuRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tobiapplications.thutils.dialog.DialogBuilderUtil;
import com.tobiapplications.thutils.dialog.DialogTwoButtonListener;
import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.ActivityMainBinding;
import com.tobiashehrlein.tobiswizardblock.ui.fragments.gamesettings.GameSettingsFragment;

import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
import static com.tobiapplications.thutils.dialog.DialogUtils.isDialogNotShowing;
import static com.tobiapplications.thutils.dialog.DialogUtils.isDialogShowing;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private ActivityMainBinding bind;
    private MainActivityContract.Presenter presenter;
    private boolean enableBackPress;
    private Dialog backPressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init() {
        presenter = new MainActivityPresenter();
        presenter.attach(this);
        presenter.init();

        backPressDialog = DialogBuilderUtil.createDialog(this, getString(R.string.back_press_dialog_title), getString(R.string.back_press_dialog_text), false, new DialogTwoButtonListener() {
            @Override
            public void onCancel() {
                dismissBackPressDialog();
            }

            @Override
            public void onConfirm() {
                dismissBackPressDialog();
                closeUntilNavigationFragment();
            }
        });
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void showDialog(DialogFragment dialogFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        dialogFragment.show(transaction, "");
    }

    @Override
    public void setToolbarTitle(String title) {
        if (isNullOrEmpty(title)) {
            bind.toolbarText.setText(getString(R.string.app_name));
        } else if (title.length() > 8){
            bind.toolbarText.setText(title);
        } else {
            bind.toolbarText.setText(getString(R.string.app_name_toolbar, title));
        }
    }

    @Override
    public void inflateToolbarMenu(@MenuRes int menuGameBlock) {
        bind.toolbar.inflateMenu(menuGameBlock);
    }

    @Override
    public void setToolbarMenuItemListener(Toolbar.OnMenuItemClickListener listener) {
        bind.toolbar.setOnMenuItemClickListener(listener);
    }

    @Override
    public void disableToolbarMenu() {
        bind.toolbar.getMenu().clear();
    }

    @Override
    public void hideToolbar() {
        bind.toolbar.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar() {
        bind.toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setToolbarBackButtonEnabled() {
        bind.toolbar.setNavigationIcon(R.drawable.arrowwhiteback);
        bind.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void setToolbarBackButtonDisabled() {
        bind.toolbar.setNavigationIcon(null);
    }

    @Override
    public void onWaitCompleted() {
        presenter.onCoverPageWaitCompleted();
    }

    @Override
    public void setBackPressEnabled(boolean enabled) {
        this.enableBackPress = enabled;
    }

    @Override
    public void onBackPressed() {
        if (enableBackPress) {
            super.onBackPressed();
        } else {
            showBackPressDialog();
        }
    }

    private void showBackPressDialog() {
        if (isDialogNotShowing(backPressDialog)) {
            backPressDialog.show();
        }
    }

    private void closeUntilNavigationFragment() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }

    private void dismissBackPressDialog() {
        if (isDialogShowing(backPressDialog)) {
            backPressDialog.dismiss();
        }
    }

    @Override
    public void startNewGame() {
        closeUntilNavigationFragment();
        replaceFragment(GameSettingsFragment.newInstance(), true);
    }

    @Override
    public void disableModifyLastInputAction() {
        bind.toolbar.getMenu().findItem(R.id.action_change_last_tipps_or_results).setEnabled(false);
    }

    @Override
    public void enableModifyLastInputAction() {
        bind.toolbar.getMenu().findItem(R.id.action_change_last_tipps_or_results).setEnabled(true);
    }

    @Override
    public void setLastInputActionTipps() {
        String tippTitle = getString(R.string.action_change_input_title_tipps);
        bind.toolbar.getMenu().findItem(R.id.action_change_last_tipps_or_results).setTitle(tippTitle);
    }

    @Override
    public void setLastInputActionResults() {
        String resultTitle = getString(R.string.action_change_input_title_results);
        bind.toolbar.getMenu().findItem(R.id.action_change_last_tipps_or_results).setTitle(resultTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
