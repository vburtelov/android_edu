package ru.burtelov.lab6.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Отсюда - https://gist.github.com/tinmegali/d4a477785f01e57066915a44543db6ed

class Converter {

    @TypeConverter
    fun fromList(list: List<Node>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<Node> {
        val listType = object : TypeToken<List<Node>>() {}.type
        return Gson().fromJson(value, listType)
    }
}