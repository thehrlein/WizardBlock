package com.tobiashehrlein.tobiswizardblock.model.settings;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class TippsEqualStitches implements Settings {

    @Override
    public boolean validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        int combinedInput = getCombinedInput(input);
        return isTippMode || validResultInput(combinedInput, round);
    }

    private boolean validResultInput(int combinedInput, int round) {
        return combinedInput == round;
    }
}
