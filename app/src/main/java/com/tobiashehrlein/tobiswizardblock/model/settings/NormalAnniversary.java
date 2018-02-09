package com.tobiashehrlein.tobiswizardblock.model.settings;

import android.support.annotation.StringRes;

import io.realm.RealmList;

import static com.tobiashehrlein.tobiswizardblock.utils.Constants.Validation.*;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class NormalAnniversary implements Settings {

    @Override
    public @StringRes int validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        int combinedInput = getCombinedInput(input);
        return isTippMode ? validTippInput(combinedInput, round) : validAnniversaryResults(combinedInput, round);
    }

    private @StringRes int validTippInput(int combinedInput, int round) {
        return combinedInput != round ? VALID.stringResId : TIPPS_CAN_NOT_BE_EQUAL_STITCHES.stringResId;
    }
}
