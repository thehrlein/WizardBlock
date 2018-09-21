package com.tobiashehrlein.tobiswizardblock.model.settings;

import androidx.annotation.StringRes;

import io.realm.RealmList;

import static com.tobiashehrlein.tobiswizardblock.utils.Constants.Validation.*;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class TippsEqualStitchesAnniversary implements Settings {

    @Override
    public @StringRes int validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        int combinedInput = getCombinedInput(input);
        if (isTippMode) {
            return VALID.stringResId;
        }

        return validAnniversaryResults(combinedInput, round);
    }
}
