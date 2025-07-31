package com.tobiashehrlein.tobiswizardblock.core.repositories.datasource.sharedpref

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SharedPreferenceDelegates(
    context: Context
) {

    protected abstract val preferencesFileName: String
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)
    }

    protected abstract class PrefDelegate<T>(val prefKey: String) : ReadWriteProperty<Any, T> {
        abstract override operator fun getValue(thisRef: Any, property: KProperty<*>): T
        abstract override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T)
    }

    protected inner class IntPrefDelegate(
        prefKey: String,
        private val defaultValue: Int
    ) : PrefDelegate<Int>(prefKey) {
        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return prefs.getInt(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            prefs.edit().putInt(prefKey, value).apply()
        }
    }

    protected inner class FloatPrefDelegate(
        prefKey: String,
        private val defaultValue: Float
    ) : PrefDelegate<Float>(prefKey) {
        override fun getValue(thisRef: Any, property: KProperty<*>): Float {
            return prefs.getFloat(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
            prefs.edit().putFloat(prefKey, value).apply()
        }
    }

    protected inner class BooleanPrefDelegate(
        prefKey: String,
        private val defaultValue: Boolean
    ) : PrefDelegate<Boolean>(prefKey) {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
            return prefs.getBoolean(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            prefs.edit().putBoolean(prefKey, value).apply()
        }
    }

    protected inner class LongPrefDelegate(
        prefKey: String,
        private val defaultValue: Long
    ) : PrefDelegate<Long>(prefKey) {
        override fun getValue(thisRef: Any, property: KProperty<*>): Long {
            return prefs.getLong(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
            prefs.edit().putLong(prefKey, value).apply()
        }
    }

    protected inner class StringPrefDelegate(
        prefKey: String,
        private val defaultValue: String?
    ) : PrefDelegate<String?>(prefKey) {
        override fun getValue(thisRef: Any, property: KProperty<*>): String? {
            return prefs.getString(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
            prefs.edit().putString(prefKey, value).apply()
        }
    }
}
