package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.content.Context;
import android.content.Intent;
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
    private AboutPresenter presenter;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        bind.fab.setOnClickListener(v -> presenter.fabButtonClicked());
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
    public void onDestroyView() {
        super.onDestroyView();
        letVoid(presenter, BaseMvpPresenter::detach);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
