package com.tobiashehrlein.tobiswizardblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.classes.DbTrumpType

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
