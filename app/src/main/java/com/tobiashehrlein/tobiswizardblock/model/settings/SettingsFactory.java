package com.tobiashehrlein.tobiswizardblock.model.settings;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class SettingsFactory {

    @IntDef({SettingsType.NORMAL, SettingsType.NORMAL_ANNIVERSARY, SettingsType.NORMAL_FIRST_ROUND, SettingsType.NORMAL_FIRST_ROUND_ANNIVERARY,
            SettingsType.TIPPS_EQUAL_STITCHES, SettingsType.TIPPS_EQUAL_STITCHES_ANNIVERSARY, })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SettingsType {
        int NORMAL = 0;
        int NORMAL_ANNIVERSARY = 1;
        int NORMAL_FIRST_ROUND = 2;
        int NORMAL_FIRST_ROUND_ANNIVERARY = 3;
        int TIPPS_EQUAL_STITCHES = 4;
        int TIPPS_EQUAL_STITCHES_ANNIVERSARY = 5;
    }

    public @SettingsType int getSettings(boolean tippsEqualStitches, boolean exceptionFirstRound, boolean anniversaryMode) {
        @SettingsType int settings;
        if (tippsEqualStitches && !exceptionFirstRound && anniversaryMode) {
            settings =SettingsType.TIPPS_EQUAL_STITCHES_ANNIVERSARY;
        } else if (tippsEqualStitches && !exceptionFirstRound && !anniversaryMode) {
            settings = SettingsType.TIPPS_EQUAL_STITCHES;
        } else if (!tippsEqualStitches && exceptionFirstRound && anniversaryMode) {
            settings = SettingsType.NORMAL_FIRST_ROUND_ANNIVERARY;
        } else if (!tippsEqualStitches && !exceptionFirstRound && anniversaryMode) {
            settings = SettingsType.NORMAL_ANNIVERSARY;
        } else if (!tippsEqualStitches && exceptionFirstRound && !anniversaryMode) {
            settings = SettingsType.NORMAL_FIRST_ROUND;
        } else {
            settings = SettingsType.NORMAL;
        }

        return settings;
    }

    public Settings getSettings(@SettingsType int type) {
        switch (type) {
            case SettingsType.TIPPS_EQUAL_STITCHES_ANNIVERSARY:
                return new TippsEqualStitchesAnniversary();
            case SettingsType.TIPPS_EQUAL_STITCHES:
                return new TippsEqualStitches();
            case SettingsType.NORMAL_FIRST_ROUND_ANNIVERARY:
                return new NormalButFirstRoundAnniversary();
            case SettingsType.NORMAL_ANNIVERSARY:
                return new NormalAnniversary();
            case SettingsType.NORMAL_FIRST_ROUND:
                return new NormalButFirstRound();
            case SettingsType.NORMAL:
            default:
                return new Normal();
        }
    }
}
