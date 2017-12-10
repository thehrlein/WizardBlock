package com.tobiashehrlein.tobiswizardblock.ui.coverpage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiashehrlein.tobiswizardblock.databinding.FragmentCoverPageBinding;
import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public class CoverPage extends Fragment implements CoverPageContract.View {

    private FragmentCoverPageBinding bind;
    private CoverPageContract.Presenter presenter;
    private CoverPageListener listener;

    public static CoverPage newInstance() {
        return new CoverPage();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (activity instanceof CoverPageListener) {
            listener = (CoverPageListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentCoverPageBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CoverPagePresenter();
        presenter.attach(this);
        presenter.init(listener);
    }

    @Override
    public void initCounter(int timeOut) {
        new Handler().postDelayed(() -> presenter.onWaitCompleted(), timeOut);
    }
}
