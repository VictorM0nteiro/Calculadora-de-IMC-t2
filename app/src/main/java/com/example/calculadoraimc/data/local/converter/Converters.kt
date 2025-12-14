package com.example.calculadoraimc.data.local.converter

import androidx.room.TypeConverter
import com.example.calculadoraimc.feature.home.model.Gender
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromGender(value: String?): Gender? {
        return value?.let { enumValueOf<Gender>(it) }
    }

    @TypeConverter
    fun genderToString(gender: Gender?): String? {
        return gender?.name
    }
}
