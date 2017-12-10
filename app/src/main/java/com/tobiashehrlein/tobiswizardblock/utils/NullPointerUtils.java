package com.tobiashehrlein.tobiswizardblock.utils;

/**
 * Created by Tobias Hehrlein on 28.11.2017.
 */

public class NullPointerUtils {

    public static boolean nullOrEmpty(String string) {
        return string == null || string.equals("");
    }

    public static boolean notEmpty(String string) {
        return !nullOrEmpty(string);
    }
}
