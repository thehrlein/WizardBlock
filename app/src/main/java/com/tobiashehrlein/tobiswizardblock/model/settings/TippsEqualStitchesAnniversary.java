package com.tobiashehrlein.tobiswizardblock.model.settings;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class TippsEqualStitchesAnniversary implements Settings {

    @Override
    public boolean validInput(RealmList<Integer> input, int round, boolean isTippMode) {
        return true;
    }
}
