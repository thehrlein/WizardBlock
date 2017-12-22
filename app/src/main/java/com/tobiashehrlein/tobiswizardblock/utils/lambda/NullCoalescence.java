package com.tobiashehrlein.tobiswizardblock.utils.lambda;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;


/**
 * This class tries to simulate Null Coalescense ála
 * Scala / Kotlin / Swift
 *
 * Created by wolfgangreithmeier on 28/11/16.
 */

public class NullCoalescence {

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = null}
     * (to avoid overloading ambiguities and simulte Kotlin's let)
     *
     * @param object on which the {@code method} provide going to be executed
     * @param letFunc of {@code object} that is going to be executed
     * @return return value of {@code method} or {@code null} in case {@code object} is {@code null}
     */
    @Nullable
    public static <T, RV> RV let(T object, @NonNull Function<T, RV> letFunc) {
        return safe (object, letFunc, null);
    }

    /**
     * Executes {@code letFunc} in case {@code object} is not {@code null}
     * (to avoid overloading ambiguities and simulate Kotlin's let)
     *
     * @param object on which the {@code method} provide going to be executed
     * @param letFunc of {@code object} that is going to be executed
     */
    public static <T> void letVoid(T object, @NonNull Consumer<T> letFunc) {
        if (object != null) {
            letFunc.accept(object);
        }
    }

    /**
     * Method starts a safe message-passing toward the {@code object} provided, means:
     * The {@code method} is only executed once the {@code object} provide is not null.
     * In case {@code object == null} the {@code defaultValue} is returned
     *
     * Sample
     * {@code
     *  String test = null;
     *  String help = safe(test, String::toLowerCase, "TEST");
     *  // help will be "TEST"
     * }
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @param defaultValue returned in case {@code object == null}
     * @return return value of {@code method} or {@code defaultValue} in case {@code object} is {@code null}
     */
    public static <V, RV> RV safe(V object, Function<V, RV> method, RV defaultValue) {
        return object == null ? defaultValue : method.apply(object);
    }

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = null}
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @return return value of {@code method} or {@code null} in case {@code object} is {@code null}
     */
    @SuppressWarnings("FunctionalInterfaceClash")
    public static <V, RV> RV safe(V object, Function<V, RV> method) {
        return safe (object, method, null);
    }

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = false}
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @return return value of {@code method} or {@code null} in case {@code object} is {@code null}
     */
    public static <V> boolean safeBool(V object, Function<V, Boolean> method) {
        return safe (object, method, false);
    }

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = 0}
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @return return value of {@code method} or 0 in case {@code object} is {@code null}
     */
    public static <V> int safeInt(V object, Function<V, Integer> method) {
        return safe (object, method, 0);
    }

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = 0L}
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @return return value of {@code method} or {@code null} in case {@code object} is {@code null}
     */
    public static <V> long safeLong(V object, Function<V, Long> method) {
        return safe (object, method, 0L);
    }

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = 0.0d}
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @return return value of {@code method} or {@code null} in case {@code object} is {@code null}
     */
    public static <V> double safeDouble(V object, Function<V, Double> method) {
        return safe (object, method, 0.0d);
    }

    /**
     * Overloading {@link #safe(Object, Function, Object)} with {@code defaultValue = 0.0f}
     *
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     * @return return value of {@code method} or {@code null} in case {@code object} is {@code null}
     */
    public static <V> float safeFloat(V object, Function<V, Float> method) {
        return safe (object, method, 0.0f);
    }

    /**
     * Same as {@link #safe(Object, Function, Object)} but for methods returning {@code void}
     *
     * @deprecated Please use {@link #letVoid(Object, Consumer)} instead
     * @param object on which the {@code method} provide going to be executed
     * @param method of {@code object} that is going to be executed
     */
    @Deprecated
    @SuppressWarnings("FunctionalInterfaceClash")
    public static <V> void safe (V object, Consumer<V> method) {
        if (object != null) {
            method.accept(object);
        }
    }

    /**
     * This method returns the {@code object} or {@code defaultValue} in case {@code object} == {@code null}
     *
     * <b>Attention: </b> {@code defaultValue} will be passed as a parameter, it will be executed first before!
     *
     * @param object to be tested for null
     * @param defaultValue fallback value to be returned in case {@code object} == {@code null}
     * @return {@code object} in case it is not {@code null} otherwise {@code defaultValue}
     */
    public static <V> V safe(V object, V defaultValue) {
        return object != null ? object : defaultValue;
    }
}
