package ru.burtelov.lab6.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// From here https://developer.android.com/codelabs/android-room-with-a-view-kotlin#7

@Database(entities = [Node::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class Db : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        fun getDb(context: Context): Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    "db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}