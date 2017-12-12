package com.tobiashehrlein.tobiswizardblock.model;


import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 06.12.2017.
 */

public class Settings extends RealmObject {

    private boolean easyMode;
    private boolean easyModeInFirstRound;
    private boolean anniversaryMode;

    public Settings() {

    }

    public Settings(boolean easyMode, boolean easyModeInFirstRound, boolean anniversaryMode) {
        this.easyMode = easyMode;
        this.easyModeInFirstRound = easyModeInFirstRound;
        this.anniversaryMode = anniversaryMode;
    }

    public boolean isEasyMode() {
        return easyMode;
    }

    public boolean isEasyModeInFirstRound() {
        return easyModeInFirstRound;
    }

    public boolean isAnniversaryMode() {
        return anniversaryMode;
    }
}
