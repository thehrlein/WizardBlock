package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbPlayerTipData
import java.lang.reflect.Type

class DbPlayerTipDataListConverter {

    @TypeConverter
    fun fromString(value: String?): List<DbPlayerTipData>? {
        if (value == null) return null
        val listType: Type = object : TypeToken<List<DbPlayerTipData>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DbPlayerTipData>?): String? {
        if (list == null) return null
        return Gson().toJson(list)
    }
}
