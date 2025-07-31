package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbPlayerResultData
import java.lang.reflect.Type

class DbPlayerResultDataListConverter {

    @TypeConverter
    fun fromString(value: String?): List<DbPlayerResultData>? {
        if (value == null) return null
        val listType: Type = object : TypeToken<List<DbPlayerResultData>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DbPlayerResultData>?): String? {
        if (list == null) return null
        return Gson().toJson(list)
    }
}
