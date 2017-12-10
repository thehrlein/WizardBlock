package com.tobiashehrlein.tobiswizardblock.utils;

import android.content.Context;

/**
 * Created by Tobias Hehrlein on 02.12.2017.
 */

public class GeneralUtils {

    public static int pxFromDp(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
