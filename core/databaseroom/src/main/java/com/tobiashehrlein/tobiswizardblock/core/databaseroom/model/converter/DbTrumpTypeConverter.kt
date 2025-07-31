package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter

import androidx.room.TypeConverter
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbTrumpType

class DbTrumpTypeConverter {

    @TypeConverter
    fun fromString(value: Int): DbTrumpType {
        return DbTrumpType.getClass(value)
    }

    @TypeConverter
    fun fromType(trumpType: DbTrumpType): Int {
        return trumpType.getQualifier()
    }
}
