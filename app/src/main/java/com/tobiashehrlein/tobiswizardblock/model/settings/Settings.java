package com.tobiashehrlein.tobiswizardblock.model.settings;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public interface Settings {

    boolean validInput(RealmList<Integer> input, int round, boolean isTippMode);

    default int getCombinedInput(RealmList<Integer> inputs) {
        int combined = 0;

        for (Integer value : inputs) {
            combined += value;
        }

        return combined;
    }
}
