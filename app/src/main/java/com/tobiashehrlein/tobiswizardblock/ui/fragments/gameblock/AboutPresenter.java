package com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock;

import android.content.Intent;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {


    private FragmentNavigationListener listener;

    public AboutPresenter() {

    }

    @Override
    public void init(FragmentNavigationListener listener) {
        this.listener = listener;

        if (isAttached()) {
            String title = getView().getTitle();
            listener.setToolbarTitle(title);
            listener.setToolbarBackButtonEnabled();
            listener.disableToolbarMenu();
            listener.setBackPressEnabled(true);
            getView().setListener();
        }
    }

    @Override
    public void fabButtonClicked() {
        if (isAttached()) {
            String text = ((AboutFragment)getView()).getString(R.string.about_email_text);
            String email = ((AboutFragment)getView()).getString(R.string.about_email);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_SUBJECT, ((AboutFragment)getView()).getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
            getView().sendEmail(intent);
        }
    }

    @Override
    public void openWizardBlockInPlayStore() {
        if (isAttached()) {
            getView().openWizardBlockInPlayStore();
        }
    }

    @Override
    public void openMovieBaseInPlayStore() {
        if (isAttached()) {
            String packageName = "tobiapplications.com.moviebase";
            getView().openMovieBaseInPlayStore(packageName);
        }
    }
}
