package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentGameBlockBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.views.BlockRoundRow;
import com.tobiashehrlein.tobiswizardblock.ui.views.GameHeader;
import com.tobiashehrlein.tobiswizardblock.utils.GeneralUtils;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockFragment extends Fragment implements GameBlockContract.View {

    private FragmentGameBlockBinding bind;
    private GameBlockContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;
    private LinearLayout.LayoutParams roundViewParams;

    public static GameBlockFragment newInstance() {
        return new GameBlockFragment();
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
        presenter = new GameBlockPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentGameBlockBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = bind.getRoot().getContext();

        roundViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralUtils.pxFromDp(context, 60));

        presenter.attach(this);
        presenter.init(listener);
    }

    @Override
    public void initRoundHeadline() {
        bind.roundHeadline.setText(context.getString(R.string.round));
    }

    @Override
    public void initHeader(List<String> playerNames) {
        GameHeader gameHeader = new GameHeader(context);
        gameHeader.setPlayerNames(playerNames);
        bind.header.addView(gameHeader);
    }

    @Override
    public void addRoundNumbersFor(int roundsToPlay) {
        for (int i = 1; i <= roundsToPlay; i++) {
            addRoundView(i);
        }
    }

    private void addRoundView(int i) {
        TextView roundTextView = new TextView(context);
        roundTextView.setText(String.valueOf(i));
        roundTextView.setGravity(Gravity.CENTER);
        roundTextView.setLayoutParams(roundViewParams);
        bind.roundLayout.addView(roundTextView);
    }

    @Override
    public void clearBlock() {
        bind.block.removeAllViews();
    }

    @Override
    public void addRound(RealmList<Integer> tippsAnnounced, RealmList<Integer> stitchesMade, RealmList<Integer> pointsAdded, RealmList<Integer> pointsTotal) {
        if (tippsAnnounced == null || stitchesMade == null) {
            return;
        }

        BlockRoundRow blockRoundRow = new BlockRoundRow(context);
        blockRoundRow.setData(tippsAnnounced, stitchesMade, pointsAdded, pointsTotal);
        bind.block.addView(blockRoundRow);

        if (pointsAdded != null && !pointsAdded.isEmpty()) {
            bind.block.addView(createDivider());
        }
    }


    private View createDivider() {
        View divider = new View(context);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralUtils.pxFromDp(context, 1)));
        divider.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackGroundGrey));
        return divider;
    }

    @Override
    public void setButtonTipps() {
        bind.enterButton.setText(context.getString(R.string.enter_tipps));
    }

    @Override
    public void setButtonResults() {
        bind.enterButton.setText(context.getString(R.string.enter_results));
    }

    @Override
    public void setListener() {
        bind.enterButton.setOnClickListener(view -> presenter.openTippsResult());
    }


}
