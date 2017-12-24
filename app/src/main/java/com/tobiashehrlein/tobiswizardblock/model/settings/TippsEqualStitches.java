package com.tobiashehrlein.tobiswizardblock.model.settings;

import android.support.annotation.StringRes;

import io.realm.RealmList;
import io.realm.RealmObject;

import static com.tobiashehrlein.tobiswizardblock.utils.Constants.Validation.*;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class TippsEqualStitches implements Settings {

    @Override
    public @StringRes
    int validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        int combinedInput = getCombinedInput(input);
        return isTippMode || validResultInput(combinedInput, round) ? VALID.stringResId : STITCHES_MUST_BE_EQUAL_ROUNDS.stringResId;
    }

    private boolean validResultInput(int combinedInput, int round) {
        return combinedInput == round;
    }
}