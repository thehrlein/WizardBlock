package com.tobiashehrlein.tobiswizardblock.model.settings;

import android.support.annotation.StringRes;

import io.realm.RealmList;

import static com.tobiashehrlein.tobiswizardblock.utils.Constants.Validation.*;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class Normal implements Settings {

    @Override
    public @StringRes int validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        int combinedInput = getCombinedInput(input);
        if (isTippMode) {
            return validTippInput(combinedInput, round);
        } else {
            return validResultInput(combinedInput, round);
        }
    }

    private @StringRes int validTippInput(int combinedInput, int round) {
        return combinedInput != round ? VALID.stringResId : TIPPS_CAN_NOT_BE_EQUAL_STITCHES.stringResId;
    }

    private @StringRes int validResultInput(int combinedInput, int round) {
        return combinedInput == round ? VALID.stringResId : STITCHES_MUST_BE_EQUAL_ROUNDS.stringResId;
    }
}
