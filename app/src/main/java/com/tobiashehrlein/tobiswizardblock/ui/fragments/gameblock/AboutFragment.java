package com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentAboutBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias on 10.09.2017.
 */

public class AboutFragment extends Fragment implements AboutContract.View {

    private FragmentAboutBinding bind;
    private AboutContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;


    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentNavigationListener) {
            listener = (FragmentNavigationListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentAboutBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = bind.getRoot().getContext();

        presenter = new AboutPresenter(this);
        presenter.attach(this);
        presenter.init(listener);
    }

    @Override
    public void setListener() {
        bind.fab.setOnClickListener(v -> letVoid(presenter, AboutContract.Presenter::fabButtonClicked));
        bind.layoutMoviebase.setOnClickListener(v -> letVoid(presenter, AboutContract.Presenter::openMovieBase));
    }

    @Override
    public void sendEmail(Intent email) {
        if (isNull(context)) {
            return;
        }

        Intent mailer  = Intent.createChooser(email, context.getString(R.string.about_email_chooser_title));
        try {
            startActivity(mailer);
            Toast.makeText(getContext(), context.getString(R.string.about_send_email_loading), Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), context.getString(R.string.about_no_email_clients), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.action_about);
    }

    @Override
    public void openMovieBase(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        letVoid(presenter, BaseMvpPresenter::detach);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
