package com.tobiashehrlein.tobiswizardblock.ui.activity;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.ActivityMainBinding;
import com.tobiashehrlein.tobiswizardblock.ui.gameblock.TippResultFragment;
import com.tobiashehrlein.tobiswizardblock.ui.navigation.NavigationFragment;
import com.tobiashehrlein.tobiswizardblock.utils.dialog.DialogBuilderUtil;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import static com.tobiashehrlein.tobiswizardblock.utils.NullPointerUtils.*;

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

        backPressDialog = DialogBuilderUtil.createDialog(this, getString(R.string.back_press_dialog_title), getString(R.string.back_press_dialog_text), false, this);
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void setToolbarTitle(String title) {
        if (nullOrEmpty(title)) {
            bind.toolbarText.setText(getString(R.string.app_name));
        } else {
            bind.toolbarText.setText(getString(R.string.app_name_toolbar, title));
        }
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
        if (backPressDialog != null && !backPressDialog.isShowing()) {
            backPressDialog.show();
        }
    }

    @Override
    public void onConfirm() {
        dismissBackPressDialog();

        closeUntilNavigationFragment();
    }

    private void closeUntilNavigationFragment() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onCancel() {
        dismissBackPressDialog();
    }

    private void dismissBackPressDialog() {
        if (backPressDialog != null && backPressDialog.isShowing()) {
            backPressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }
}
