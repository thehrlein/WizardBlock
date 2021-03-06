package com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiapplications.thutils.GeneralUtils;
import com.tobiapplications.thutils.dialog.DialogBuilderUtil;
import com.tobiapplications.thutils.dialog.DialogTwoButtonListener;
import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentGameBlockBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.views.BlockRoundRow;
import com.tobiashehrlein.tobiswizardblock.ui.views.ChangePlayerNamesDialog;
import com.tobiashehrlein.tobiswizardblock.ui.views.GameHeader;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNull;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.let;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockFragment extends Fragment implements GameBlockContract.View {

    private FragmentGameBlockBinding bind;
    private GameBlockContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;
    private LinearLayout.LayoutParams roundViewParams;
    private boolean enterClickable;
    private GameHeader gameHeader;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = bind.getRoot().getContext();

        roundViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralUtils.pxFromDp(context, 60));

        presenter.attach(this);
        presenter.init(listener, R.menu.menu_game_block);

        enterClickable = true;
    }

    @Override
    public void initRoundHeadline() {
        bind.roundHeadline.setText(context.getString(R.string.round));
    }

    @Override
    public void initHeader(List<String> playerNames) {
        gameHeader = new GameHeader(context);
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
        bind.enterButton.setOnClickListener(view -> {
            if (enterClickable) {
                enterClickable = false;
                letVoid(presenter, GameBlockContract.Presenter::openNextInputEntering);
                new Handler().postDelayed(() -> enterClickable = true, 2000);
            }
        });
    }

    @Override
    public boolean onMenuItemClicked(int itemId) {
        switch (itemId) {
            case R.id.action_change_player_names:
                changePlayerNames();
                return true;
            case R.id.action_change_last_tipps_or_results:
                changeLastRoundInput();
                return true;
            case R.id.action_new_game:
                openStartNewGameDialog();
                return true;
            case R.id.action_about:
                openAbout();
                return true;
            default:
                return false;
        }
    }

    private void changePlayerNames() {
        if (isNotNull(presenter)) {
            presenter.changePlayerNames();
        }
    }

    @Override
    public void openChangePlayerNamesDialog(RealmList<String> playerNames) {
        ChangePlayerNamesDialog playerDialog = new ChangePlayerNamesDialog(context, R.style.Theme_Transparent_Full_Width);
        playerDialog.setPlayerNames(playerNames);
        playerDialog.setPlayerNameChangeListener(this::saveNewPlayerNames);
        playerDialog.show();
    }

    private void saveNewPlayerNames(RealmList<String> newPlayerName) {
        Storage.getInstance().savePlayerNames(newPlayerName);
        gameHeader.setPlayerNames(newPlayerName);
    }

    private void changeLastRoundInput() {
        letVoid(presenter, GameBlockContract.Presenter::changeLastRoundInput);
    }

    private void openStartNewGameDialog() { // TODO
        String title = context.getString(R.string.action_title_are_you_sure);
        String message = context.getString(R.string.action_start_new_game);
        Dialog startNewGameDialog = DialogBuilderUtil.createDialog(context, title, message, new DialogTwoButtonListener() {
            @Override
            public void onConfirm() {
                letVoid(presenter, GameBlockContract.Presenter::startNewGame);
            }

            @Override
            public void onCancel() {

            }
        });
        startNewGameDialog.show();
    }

    private void openAbout() {
        letVoid(presenter, GameBlockContract.Presenter::openAbout);
    }

    @Override
    public void disableEnterButton() {
        bind.enterButton.setEnabled(false);
        bind.enterButton.setText(context.getString(R.string.start_new_game));
        bind.enterButton.setOnClickListener(v -> openStartNewGameDialog());
        new Handler().postDelayed(() -> bind.enterButton.setEnabled(true), 3000);
    }

    @Override
    public void showWinnerDialog(Map<String, Integer> winner) {
        String title = context.getString(R.string.winner_title);
        String message;
        if (winner == null) {
            message = getString(R.string.winner_message_none);
        } else {
            String firstWinnerName = new ArrayList<>(winner.keySet()).get(0);
            int winnerPoints = winner.get(firstWinnerName);
            if (winner.size() == 1) {
                message = context.getString(R.string.winner_message_single, firstWinnerName, winnerPoints);
            } else {
                ArrayList<String> winnerNames = new ArrayList<>(winner.keySet());
                StringBuilder completeWinnerNames = let(presenter, p -> p.getCompleteWinnerString(winnerNames));
                message = context.getString(R.string.winner_message_multipe, completeWinnerNames, winnerPoints);
            }
        }

        Dialog winnerDialog = DialogBuilderUtil.createOneButtonDialog(context, title, message);
        winnerDialog.show();
    }

    @Override
    public void scrollTo(int roundNumber) {
        int maxRounds = bind.roundLayout.getChildCount();
        if (roundNumber < maxRounds) {
            View view = bind.roundLayout.getChildAt(roundNumber);
            bind.scrollView.post(() -> bind.scrollView.scrollTo(0, view.getBottom() + 5));
        }
    }

    @Override
    public void finishGameEarly() {
        letVoid(presenter, GameBlockContract.Presenter::finishGameEarly);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
