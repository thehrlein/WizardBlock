package com.tobiashehrlein.tobiswizardblock.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public final class Constants {

    public static final String GAME = "game";
    public static final String GAME_SETTINGS = "player_count";
    public static final String ROUND = "round";

    @IntDef({EnterType.TIPPS, EnterType.RESULTS})
    @Retention(RetentionPolicy.CLASS)
    public @interface EnterType {
        int TIPPS = 0;
        int RESULTS = 1;
    }
}
