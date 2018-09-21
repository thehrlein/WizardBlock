package com.tobiashehrlein.tobiswizardblock.utils;

import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import com.tobiashehrlein.tobiswizardblock.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public final class Constants {

    public static final String ISTIPPMODE = "is_tipp_mode";
    public static final String CHANGELASTTOUNDINPUT = "change_last_round_input";

    @IntDef({EnterType.TIPPS, EnterType.RESULTS})
    @Retention(RetentionPolicy.CLASS)
    public @interface EnterType {
        int TIPPS = 0;
        int RESULTS = 1;
    }

    public enum Validation {
        VALID(R.string.validation_valid),
        TIPPS_CAN_NOT_BE_EQUAL_STITCHES(R.string.validation_tipp_can_not_be_equal_stitches),
        STITCHES_MUST_BE_EQUAL_ROUNDS(R.string.validation_stitches_must_be_equal_rounds),
        STITCHES_CAN_NOT_BE_MORE_THAN_ROUNDS(R.string.validation_stitches_can_not_be_more_than_rounds);

        @StringRes public int stringResId;

        Validation(@StringRes int stringResId) {
            this.stringResId = stringResId;
        }
    }
}
