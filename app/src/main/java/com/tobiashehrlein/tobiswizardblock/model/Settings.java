package com.tobiashehrlein.tobiswizardblock.model;

import com.google.auto.value.AutoValue;

/**
 * Created by Tobias Hehrlein on 06.12.2017.
 */

@AutoValue
public abstract class Settings {

    public abstract boolean isEasyMode();
    public abstract boolean isEasyModeFirstRound();
    public abstract boolean isAnniveraryMode();

    public static Settings create(boolean easyMode, boolean easyModeFirstRound, boolean anniversaryMode) {
        return new AutoValue_Settings(easyMode, easyModeFirstRound, anniversaryMode);
    }
}
