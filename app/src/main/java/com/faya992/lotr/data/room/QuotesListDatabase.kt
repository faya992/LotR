package com.faya992.lotr.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faya992.lotr.data.room.dao.QuotesListDao
import com.faya992.lotr.data.room.dao.QuotesListEntity


@Database(entities = [QuotesListEntity::class], version = 1, exportSchema = false)
abstract class QuotesDatabase:RoomDatabase() {

    abstract fun quotesListDao(): QuotesListDao

    companion object {

        @Volatile
        private var database: QuotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): QuotesDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    QuotesDatabase::class.java,
                    "database"
                ).build()
                database as QuotesDatabase
            } else database as QuotesDatabase
        }
    }
}