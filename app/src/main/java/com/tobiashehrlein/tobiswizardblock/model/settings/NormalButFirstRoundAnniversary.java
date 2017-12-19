package com.tobiashehrlein.tobiswizardblock.model.settings;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class NormalButFirstRoundAnniversary implements Settings {

    @Override
    public boolean validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        int combinedInput = getCombinedInput(input);
        return !isTippMode || validTippInput(combinedInput, round);
    }

    private boolean validTippInput(int combinedInput, int round) {
        return round == 1 || combinedInput != round;
    }
}
