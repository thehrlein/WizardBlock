package com.tobiashehrlein.tobiswizardblock.model.settings;

import androidx.annotation.StringRes;

import io.realm.RealmList;
import static com.tobiashehrlein.tobiswizardblock.utils.Constants.Validation.*;
/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public interface Settings {

    @StringRes int validInput(RealmList<Integer> input, int round, boolean isTippMode);

    default int getCombinedInput(RealmList<Integer> inputs) {
        int combined = 0;

        for (Integer value : inputs) {
            combined += value;
        }

        return combined;
    }

    default @StringRes int validAnniversaryResults(int combinedInput, int round) {
        return combinedInput <= round ? VALID.stringResId : STITCHES_CAN_NOT_BE_MORE_THAN_ROUNDS.stringResId;
    }
}
