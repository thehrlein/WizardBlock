package com.tobiashehrlein.tobiswizardblock.model.settings;


import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 06.12.2017.
 */

public abstract class BaseSettings {

    public int getCombinedInput(RealmList<Integer> inputs) {
        int combined = 0;

        for (Integer value : inputs) {
            combined += value;
        }

        return combined;
    }
}
