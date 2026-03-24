package com.rawg.feature.games.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room [TypeConverter] for storing `List<String>` as JSON in the database.
 *
 * Uses Gson for serialization/deserialization. Replaces the previous
 * comma-separated approach which was fragile (values containing commas
 * would break parsing).
 */
class StringListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}
